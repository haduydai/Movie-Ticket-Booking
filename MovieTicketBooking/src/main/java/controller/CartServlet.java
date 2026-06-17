package controller;

import dao.CartDAO;
import dao.ICartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final ICartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cart cart = cartDAO.getCartByUserId(user.getId());
        if (cart == null) {
            cart = cartDAO.createCartForUser(user.getId());
        }

        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        Cart cart = cartDAO.getCartByUserId(user.getId());
        if (cart == null) {
            cart = cartDAO.createCartForUser(user.getId());
        }

        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            if ("add".equals(action)) {
                String typeStr = request.getParameter("type"); // "TICKET" hoặc "COMBO"
                CartItemType type = CartItemType.valueOf(typeStr);

                if (type == CartItemType.TICKET) {
                    int showtimeId = Integer.parseInt(request.getParameter("showtimeId"));
                    int seatId = Integer.parseInt(request.getParameter("seatId"));

                    // Kiểm tra xem vé ghế này đã có trong giỏ hàng chưa
                    boolean exists = false;
                    for (CartItem ci : cart.getItems()) {
                        if (ci.getItemType() == CartItemType.TICKET &&
                                ci.getShowTime().getId() == showtimeId &&
                                ci.getSeat().getId() == seatId) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        ShowTime showTime = hibernateSession.getReference(ShowTime.class, showtimeId);
                        Seat seat = hibernateSession.getReference(Seat.class, seatId);
                        CartItem newItem = new CartItem(cart, CartItemType.TICKET, showTime, seat, null, 1);
                        cart.getItems().add(newItem);
                    }
                } else if (type == CartItemType.COMBO) {
                    int comboId = Integer.parseInt(request.getParameter("comboId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    boolean exists = false;
                    for (CartItem ci : cart.getItems()) {
                        if (ci.getItemType() == CartItemType.COMBO && ci.getCombo().getId() == comboId) {
                            ci.setQuantity(ci.getQuantity() + quantity);
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        Combo combo = hibernateSession.getReference(Combo.class, comboId);
                        CartItem newItem = new CartItem(cart, CartItemType.COMBO, null, null, combo, quantity);
                        cart.getItems().add(newItem);
                    }
                }
                cartDAO.saveOrUpdateCart(cart);

            } else if ("update".equals(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                CartItem item = cartDAO.getCartItemById(itemId);
                if (item != null && item.getCart().getId() == cart.getId()) {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                        hibernateSession.merge(item);
                    } else {
                        cart.getItems().remove(item);
                        cartDAO.deleteCartItem(item);
                    }
                }

            } else if ("remove".equals(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                CartItem item = cartDAO.getCartItemById(itemId);
                if (item != null && item.getCart().getId() == cart.getId()) {
                    cart.getItems().remove(item);
                    cartDAO.deleteCartItem(item);
                }

            } else if ("clear".equals(action)) {
                for (CartItem item : cart.getItems()) {
                    cartDAO.deleteCartItem(item);
                }
                cart.getItems().clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý giỏ hàng: " + e.getMessage());
        }

        response.sendRedirect("cart");
    }
}