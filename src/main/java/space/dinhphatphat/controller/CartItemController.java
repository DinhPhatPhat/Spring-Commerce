package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CartItemService;
import space.dinhphatphat.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/cartItem")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserService userService;

    @RequestMapping("")
    public String showCartItem(Model model, HttpSession session, @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null) {
            return "user/login";
        }
        List<CartItem> cartItems = cartItemService.findByUserId(user.getId());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        return "/cartItem/show";
    }



}
