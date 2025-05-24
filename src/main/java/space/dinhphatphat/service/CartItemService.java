package space.dinhphatphat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.CartItemRepository;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem findByProductIdAndUserId(Integer userId, Integer productId) {
        return cartItemRepository.findByUser_IdAndProduct_Id(userId, productId).orElse(null);
    }

    public CartItem addCartItem(CartItem cartItem) {
        CartItem existingCartitem = cartItemRepository.findByUser_IdAndProduct_Id(cartItem.getUser().getId(), cartItem.getProduct().getId()).orElse(null);
        if (existingCartitem != null) {
            existingCartitem.setQuantity(existingCartitem.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingCartitem);
        }
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public List<CartItem> findByUserId(Integer userId) {
        return cartItemRepository.findByUser_Id(userId);
    }

    public void increaseQuantity(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if(cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        }
    }

    public void reduceQuantity(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() - 1;
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);
            }
        }
    }

    public List<CartItem> findAllById(List<Integer> cartItemIds) {
        return cartItemRepository.findAllById(cartItemIds);
    }
}
