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
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.UserService;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CategoryService categoryService() {
            return Mockito.mock(CategoryService.class);
        }

        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        String jsonCategory = "{\"name\":\"Thể thao\"}";

        User adminUser = new User();
        adminUser.setRole(1); // Giả sử role > 0 là quản lý

        Mockito.when(userService.getCurrentUser(Mockito.any(HttpSession.class), Mockito.any()))
                .thenReturn(adminUser);
        Mockito.when(categoryService.findByName("Thể thao")).thenReturn(null);

        Mockito.when(categoryService.create(Mockito.any(Category.class)))
                .thenReturn(new Category());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategory))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Danh mục sản phẩm đã được tạo"));
    }

    @Test
    void testCreateCategory_DuplicateName() throws Exception {
        String jsonCategory = "{\"name\":\"Thể thao\"}";

        User adminUser = new User();
        adminUser.setRole(1);

        Category existingCategory = new Category();
        existingCategory.setName("Thể thao");

        Mockito.when(userService.getCurrentUser(Mockito.any(HttpSession.class), Mockito.any()))
                .thenReturn(adminUser);
        Mockito.when(categoryService.findByName("Thể thao")).thenReturn(existingCategory);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategory))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Tên danh mục đã tồn tại!"));
    }

    @Test
    void testCreateCategory_NotAdmin() throws Exception {
        String jsonCategory = "{\"name\":\"Thể thao\"}";

        User normalUser = new User();
        normalUser.setRole(0); // Không phải quản lý

        Mockito.when(userService.getCurrentUser(Mockito.any(HttpSession.class), Mockito.any()))
                .thenReturn(normalUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategory))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Hãy đăng nhập với tư cách quản lý"));
    }

    @Test
    void testDeleteCategory_Success() throws Exception {
        int id = 1;

        User adminUser = new User();
        adminUser.setRole(1);

        Category category = new Category();
        category.setName("Thể thao");

        Mockito.when(userService.getCurrentUser(Mockito.any(HttpSession.class), Mockito.any()))
                .thenReturn(adminUser);
        Mockito.when(categoryService.findById(id)).thenReturn(category);
        Mockito.doNothing().when(categoryService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Đã xóa câu danh mục sản phẩm"));
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        int id = 999;

        User adminUser = new User();
        adminUser.setRole(1);

        Mockito.when(userService.getCurrentUser(Mockito.any(HttpSession.class), Mockito.any()))
                .thenReturn(adminUser);
        Mockito.when(categoryService.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Không tìm thấy danh mục sản phẩm"));
    }

    @Test
    void testFindAllCategories() throws Exception {
        Category cat1 = new Category();
        cat1.setName("Thể thao");
        Category cat2 = new Category();
        cat2.setName("Thời trang");

        List<Category> categories = List.of(cat1, cat2);

        Mockito.when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/findAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Thể thao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Thời trang"));
    }
}
