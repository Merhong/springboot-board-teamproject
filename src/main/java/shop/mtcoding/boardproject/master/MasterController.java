package shop.mtcoding.boardproject.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/master/help")
    public String qna() {
        return "/master/qna";
    }

    @GetMapping("/questionForm")
    public String question() {
        return "/master/questionForm";
    }
}