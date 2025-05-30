package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.dinhphatphat.model.Order;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.OrderService;
import space.dinhphatphat.service.TokenService;
import space.dinhphatphat.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;
    @GetMapping("")
    public String user(HttpSession httpSession, Model model, @CookieValue(value = "token", required = false) String token){
        User user = userService.getCurrentUser(httpSession, token);
        if (user == null){
            return "redirect:/user/login";
        }

        model.addAttribute("page", "user");
        model.addAttribute("user", user);
        List<Order> orders = new ArrayList<>();
        if (user.getRole() ==0) {
            orders = orderService.findByUserId(user.getId());
        }
        else if (user.getRole() == 1){
            orders = orderService.findAll();
            model.addAttribute("categories", categoryService.findAll());
        }
        model.addAttribute("orders", orders);
        return "user/profile";
    }

    @GetMapping("/login")
    public String login(HttpSession httpSession, @CookieValue(value = "token", required = false) String token){
        User user = userService.getCurrentUser(httpSession, token);
        if(user == null){
            return "user/login";
        }
        return "redirect:/user";
    }

    @GetMapping("/register")
    public String register(HttpSession httpSession, @CookieValue(value = "token", required = false) String token){
        User user = userService.getCurrentUser(httpSession, token);
        if(user == null){
            return "user/register";
        }
        return "redirect:/user";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "user/forgotPassword";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token, Model model){
        //Check is valid account token, update user is active, delete used token, render verify page
        Token checkedToken = tokenService.checkToken(token);
        if(checkedToken != null){
            User user = checkedToken.getUser();
            user.setActive(true);
            userService.update(user, null);
            model.addAttribute("user", user);
            tokenService.delete(checkedToken);
            return "user/verify";
        }
        return "redirect:/user/register";
    }

    @GetMapping("/change-password")
    public String changePassword(@RequestParam String token, Model model){
        Token checkedToken = tokenService.checkToken(token);
        if(checkedToken != null){
            User user = checkedToken.getUser();
            model.addAttribute("user", user);
            return "user/changePassword";
        }
        return "redirect:/user/register";
    }
}
