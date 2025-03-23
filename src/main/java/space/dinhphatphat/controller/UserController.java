package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.StoryService;
import space.dinhphatphat.service.TokenService;
import space.dinhphatphat.service.UserService;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StoryService storyService;

    @GetMapping("")
    public String user(HttpSession httpSession, Model model){
        User user = (User) httpSession.getAttribute("user");
        if (user == null){
            return "redirect:/user/login";
        }

        List<Story> stories = storyService.findAllByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("stories", stories);
        return "user/profile";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }
    @GetMapping("/register")
    public String register(){
        return "user/register";
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
            user.setActivated(true);
            userService.update(user);
            model.addAttribute("user", user);
            tokenService.delete(checkedToken);
            return "/user/verify";
        }
        return "redirect:/user/register";
    }
    @GetMapping("/change-password")
    public String changePassword(@RequestParam String token, Model model){
        Token checkedToken = tokenService.checkToken(token);
        if(checkedToken != null){
            User user = checkedToken.getUser();
            model.addAttribute("user", user);
            return "/user/changePassword";
        }
        return "redirect:/user/register";
    }
}
