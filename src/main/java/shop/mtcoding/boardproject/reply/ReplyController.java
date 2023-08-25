package shop.mtcoding.boardproject.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.util.ApiUtil;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save() {
        replyService.댓글쓰기();
        return new ApiUtil<String>(true, "댓글쓰기 성공");
    }

    @DeleteMapping("/api/reply/{id}/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {

        // 2. 핵심로직
        replyService.댓글삭제();

        // 3. 응답
        return new ApiUtil<String>(true, "댓글삭제 완료");
    }
}
