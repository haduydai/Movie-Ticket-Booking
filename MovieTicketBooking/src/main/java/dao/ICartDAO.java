package dao;

import model.Cart;
import model.CartItem;

public interface ICartDAO {
    Cart getCartByUserId(int userId);
    Cart createCartForUser(int userId);
    void saveOrUpdateCart(Cart cart);
    CartItem getCartItemById(int itemId);
    void deleteCartItem(CartItem item);
}