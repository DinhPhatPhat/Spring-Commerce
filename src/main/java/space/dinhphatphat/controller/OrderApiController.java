package space.dinhphatphat.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.Order;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.OrderRepository;
import space.dinhphatphat.service.CartItemService;
import space.dinhphatphat.service.OrderService;
import space.dinhphatphat.service.UserService;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/order")
@RestController
public class OrderApiController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> order(@RequestBody Map<String, Object> body,
                                   HttpSession session,
                                   @CookieValue(value = "token", required = false) String token)
    {
        List<Integer> cartItemIds = (List<Integer>) body.get("cartItemIds");
        String recipientName = (String) body.get("recipientName");
        String recipientPhoneNumber = (String) body.get("recipientPhoneNumber");
        String shippingAddress = (String) body.get("shippingAddress");

        User user = userService.getCurrentUser(session, token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập để đặt hàng");
        }

        List<CartItem> cartItems = cartItemService.findAllById(cartItemIds);
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không có sản phẩm để đặt hàng");
        }
        try {
            orderService.order(cartItems, user, recipientName, recipientPhoneNumber, shippingAddress);
            return ResponseEntity.status(HttpStatus.OK).body("Đơn hàng của bạn đã được đặt, hãy kiểm tra thông tin đơn hàng trong tài khoản");
        }
        catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi đặt hàng, thử lại sau");
        }
    }

    @PostMapping("updateStatus")
    @ResponseBody
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, Object> data, HttpSession session,
                                               @CookieValue(value = "token", required = false) String token) {
        int orderId = Integer.parseInt(data.get("orderId").toString());
        String status = data.get("status").toString();

        try {
            Order order = orderService.updateStatus(orderId, status);
            if (order != null) {
                return ResponseEntity.ok("Cập nhật trạng thái đơn hàng thành công");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật trạng thái đơn hàng thất bại");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật trạng thái đơn hàng thất bại");
        }

    }
}
