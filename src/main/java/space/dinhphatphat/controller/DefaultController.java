package space.dinhphatphat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("")
public class DefaultController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String index(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("page", "info");
        return "info";
    }
}
