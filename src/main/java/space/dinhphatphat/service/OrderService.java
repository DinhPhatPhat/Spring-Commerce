package space.dinhphatphat.service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.Order;
import space.dinhphatphat.model.OrderItem;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.CartItemRepository;
import space.dinhphatphat.repository.OrderItemRepository;
import space.dinhphatphat.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    public void order(List<CartItem> cartItems, User user, String recipientName, String recipientPhoneNumber, String shippingAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setRecipientName(recipientName);
        order.setRecipientPhoneNumber(recipientPhoneNumber);
        order.setTotalPrice(BigDecimal.ONE);
        BigDecimal totalPrice = BigDecimal.ZERO;
        orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());

            BigDecimal itemTotal = cartItem.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            orderItem.setPrice(itemTotal);
            orderItemRepository.save(orderItem);

            totalPrice = totalPrice.add(itemTotal);
            cartItemRepository.delete(cartItem);
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }

    public List<Order> findByUserId(int userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order updateStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            return order;
        }
        return null;
    }
}
