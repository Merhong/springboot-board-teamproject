package shop.mtcoding.boardproject.master;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.CompService;

@Controller
public class MasterController {

    @Autowired
    private CompService compService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Posting> postingList = compService.모든공고찾기();
        request.setAttribute("postingList", postingList);
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