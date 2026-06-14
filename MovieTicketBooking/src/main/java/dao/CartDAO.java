package dao;

import model.Cart;
import model.CartItem;
import model.User;
import org.hibernate.Session;
import utils.HibernateUtil;

public class CartDAO implements ICartDAO {

    @Override
    public Cart getCartByUserId(int userId) {
        // Lấy Session hiện tại (được liên kết với Request Thread nhờ TransactionFilter)
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.createQuery(
                        "select distinct c from Cart c left join fetch c.items i " +
                                "left join fetch i.showTime s " +
                                "left join fetch i.seat se " +
                                "left join fetch i.combo cb " +
                                "left join fetch s.movie m " +
                                "left join fetch s.room r " +
                                "left join fetch s.cinema cn " +
                                "where c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public Cart createCartForUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        User userRef = session.getReference(User.class, userId);
        Cart cart = new Cart(userRef);
        session.persist(cart);
        return cart;
    }

    @Override
    public void saveOrUpdateCart(Cart cart) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(cart);
    }

    @Override
    public CartItem getCartItemById(int itemId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session.get(CartItem.class, itemId);
    }

    @Override
    public void deleteCartItem(CartItem item) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.remove(item);
    }
}