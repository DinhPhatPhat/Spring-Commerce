package space.dinhphatphat.springCommerce.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import space.dinhphatphat.controller.ProductApiController;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.Product;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.*;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductApiControllerTest {

    @InjectMocks
    private ProductApiController productApiController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private CartItemService cartItemService;

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        User adminUser = new User();
        adminUser.setRole(1);  // Admin role

        Category category = new Category();
        category.setId(1);

        Product product = new Product();
        product.setCategory(category);
        product.setName("Test Product");

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3});

        when(userService.getCurrentUser(session, "token")).thenReturn(adminUser);
        when(categoryService.findById(1)).thenReturn(category);
        when(bindingResult.hasErrors()).thenReturn(false);

        Mockito.when(productService.create(Mockito.any(Product.class), Mockito.any()))
                .thenReturn(new Product());

        ResponseEntity<?> response = productApiController.createProduct(product, bindingResult, image, session, "token");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Sản phẩm mới đã được thêm", response.getBody());
        verify(productService, times(1)).create(any(Product.class), any());
    }

    @Test
    void testCreateProduct_NotAdmin() throws IOException {
        User normalUser = new User();
        normalUser.setRole(0);  // Not admin

        Product product = new Product();

        when(userService.getCurrentUser(session, "token")).thenReturn(normalUser);

        ResponseEntity<?> response = productApiController.createProduct(product, bindingResult, null, session, "token");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Hãy đăng nhập với tư cách quản lý", response.getBody());
        verify(productService, never()).create(any(), any());
    }

    @Test
    void testAddToCart_Success() {
        User user = new User();
        user.setRole(0);

        Product product = new Product();
        product.setId(1);

        when(userService.getCurrentUser(session, "token")).thenReturn(user);
        when(productService.findById(1)).thenReturn(product);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(1);

        when(cartItemService.addCartItem(any(CartItem.class))).thenReturn(cartItem);

        Map<String, Integer> body = Map.of("id", 1);

        ResponseEntity<?> response = productApiController.addToCart(body, session, "token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Đã thêm sản phẩm vào giỏ hàng", response.getBody());
        verify(cartItemService, times(1)).addCartItem(any(CartItem.class));
    }

    @Test
    void testAddToCart_NoUser() {
        when(userService.getCurrentUser(session, "token")).thenReturn(null);

        Map<String, Integer> body = Map.of("id", 1);

        ResponseEntity<?> response = productApiController.addToCart(body, session, "token");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Hãy đăng nhập để mua hàng", response.getBody());
        verify(cartItemService, never()).addCartItem(any());
    }

}
