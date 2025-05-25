package space.dinhphatphat.springCommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CartItemService;
import space.dinhphatphat.service.OrderService;
import space.dinhphatphat.service.UserService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    static class TestConfig {
        @Bean @Primary
        public CartItemService cartItemService() {
            return Mockito.mock(CartItemService.class);
        }

        @Bean @Primary
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean @Primary
        public OrderService orderService() {
            return Mockito.mock(OrderService.class);
        }
    }

    @Test
    void testOrderSuccess() throws Exception {
        List<Integer> ids = Arrays.asList(1, 2);
        Map<String, Object> body = new HashMap<>();
        body.put("cartItemIds", ids);
        body.put("recipientName", "Nguyen Van A");
        body.put("recipientPhoneNumber", "0123456789");
        body.put("shippingAddress", "123 ABC Street");

        User mockUser = new User();
        mockUser.setId(1);

        List<CartItem> cartItems = Arrays.asList(new CartItem(), new CartItem());

        Mockito.when(userService.getCurrentUser(any(), any())).thenReturn(mockUser);
        Mockito.when(cartItemService.findAllById(ids)).thenReturn(cartItems);
        Mockito.doNothing().when(orderService).order(anyList(), any(User.class), any(), any(), any());

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("Đơn hàng của bạn đã được đặt, hãy kiểm tra thông tin đơn hàng trong tài khoản"));
    }
}
