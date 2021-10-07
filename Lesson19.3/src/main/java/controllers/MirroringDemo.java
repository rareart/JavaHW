package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MirroringDemo {
    @GetMapping("/mirroring")
    public String mirroringDemo(@RequestParam String value, Model model){
        model.addAttribute("output", value);
        return "view";
    }
}
