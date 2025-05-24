package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.Product;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.ProductService;
import space.dinhphatphat.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("create")
    public String create() {
        return "category/create";
    }
    @GetMapping("update/{id}")
    public String update(HttpSession session, Model model,
                         @PathVariable int id,
                         @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user != null && user.getRole() == 1 ) {
            if(categoryService.findById(id)!= null){
                model.addAttribute("category", categoryService.findById(id));
                List<Product> products = productService.findByCategoryId(id);
                model.addAttribute("products", products);
                return "/category/update";
            }
            return "/category/notFound";
        }

        return "redirect:/user/login";
    }
    @GetMapping("/{name}")
    public String show(@PathVariable String name, Model model) {
        Category category = categoryService.findByName(name);
        if (category == null) {
            return "/category/notFound";
        }
        model.addAttribute("category", category);
        return "/category/show";
    }
}