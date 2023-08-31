package shop.mtcoding.boardproject.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

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