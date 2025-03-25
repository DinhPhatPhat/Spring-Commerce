package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.StoryService;

@Controller
@RequestMapping("story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping("/create")
    public String create(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "/story/createStory";}

        return "redirect:/user/login";
    }

    @GetMapping("/update/{id}")
    public String update(HttpSession session, Model model, @PathVariable int id) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            Story story = storyService.findById(id);
            if (story != null) {
                model.addAttribute("story", story);
            }
            return "/story/updateStory";}

        return "redirect:/user/login";
    }

}
