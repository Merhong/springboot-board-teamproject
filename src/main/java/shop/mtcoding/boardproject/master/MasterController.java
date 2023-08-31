package shop.mtcoding.boardproject.master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MasterController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/help")
    public String qna() {
        return "/board/qna";
    }

    @GetMapping("/questionForm")
    public String question() {
        return "/board/questionForm";
    }
}