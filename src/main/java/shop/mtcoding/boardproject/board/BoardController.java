package shop.mtcoding.boardproject.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    BoardRepository boardRepository;
  
    @Autowired
    private BoardService boardService;


    @GetMapping("/")
    public String index() {
        return "index";
    }
}