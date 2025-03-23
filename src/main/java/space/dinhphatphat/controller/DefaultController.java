package space.dinhphatphat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.service.StoryService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class DefaultController {

    @Autowired
    private StoryService storyService;

    @GetMapping
    public String index(Model model){
        List<Story> stories = storyService.findAll();
        model.addAttribute("stories", stories);
        return "index";
    }
    @GetMapping("/info")
    public String info(){
        return "info";
    }


}
