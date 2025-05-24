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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public String showProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        if(product == null) {
            return "product/notFound";
        }
        model.addAttribute("product", product);
        return "product/show";
    }

    @GetMapping("create")
    public String create(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "product/create";
    }

    @GetMapping("update/{id}")
    public String update(HttpSession session, Model model, @PathVariable int id, @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        List<Category> categories = categoryService.findAll();
        if (user != null) {
            Product product = productService.findById(id);
            if (product != null) {
                model.addAttribute("product", product);
                model.addAttribute("categories", categories);
                return "product/update";
            }
            return "/product/notFound";
        }
        return "redirect:/user/login";
    }

}
