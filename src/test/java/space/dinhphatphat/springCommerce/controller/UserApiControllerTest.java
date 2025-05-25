package space.dinhphatphat.springCommerce.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.JwtService;
import space.dinhphatphat.service.TokenService;
import space.dinhphatphat.service.UserService;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtService jwtService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        public TokenService tokenService() {
            return Mockito.mock(TokenService.class);
        }

        @Bean
        public JwtService jwtService() {
            return Mockito.mock(JwtService.class);
        }
    }

    @Test
    void testLoginSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password123";

        // Mock userService.checkLogin trả về 2 (login thành công)
        Mockito.when(userService.checkLogin(email, password)).thenReturn(2);

        // Mock userService.findByEmail trả về Optional<User>
        User mockUser = new User();
        mockUser.setEmail(email);
        Mockito.when(userService.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Mock jwtService.generateToken trả về token giả
        Mockito.when(jwtService.generateToken(email)).thenReturn("fake-jwt-token");

        // Thực hiện gọi API POST /api/user/login
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\""+email+"\", \"password\":\""+password+"\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Đăng nhập thành công"));
    }

    @Test
    void testLoginFailEmptyEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\", \"password\":\"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email không được để trống!"));
    }

    // Bạn có thể viết thêm test cho register, update, forgot-password, change-password, logout tương tự
}
