package shop.mtcoding.boardproject.reply;

import org.springframework.stereotype.Controller;

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
