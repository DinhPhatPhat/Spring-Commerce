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
import space.dinhphatphat.service.CartItemService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartItemApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemService cartItemService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public CartItemService cartItemService() {
            return Mockito.mock(CartItemService.class);
        }
    }

    @Test
    void testIncreaseQuantity_Success() throws Exception {
        Map<String, Integer> body = new HashMap<>();
        body.put("id", 1);

        Mockito.doNothing().when(cartItemService).increaseQuantity(1);

        mockMvc.perform(post("/api/cartItem/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void testReduceQuantity_Success() throws Exception {
        Map<String, Integer> body = new HashMap<>();
        body.put("id", 2);

        Mockito.doNothing().when(cartItemService).reduceQuantity(2);

        mockMvc.perform(post("/api/cartItem/reduce")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }
}
