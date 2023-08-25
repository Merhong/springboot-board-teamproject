package shop.mtcoding.boardproject.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) { // Model == HttpServletRequest
        // Board board = boardService.상세보기(id);
        // request.setAttribute("board", board);
        return "bard/detail";
    }

    @PostMapping("/board/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id){
        // boardService.삭제하기(id);
        // return Script.href("/");
        return "";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        // Board board = boardService.상세보기(id);
        // request.setAttribute("board", board);
        return "board/updateForm";
    }

}
