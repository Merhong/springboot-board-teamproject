package shop.mtcoding.boardproject.bookmark;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

@Controller
public class UserBookmarkController {

    @Autowired
    private BookmarkService userBookmarkService;

    @Autowired
    private HttpSession session;

    // 16번 북마크 삭제 버튼 POST
    @PostMapping("/userBookMarkDelete")
    public String userBookMarkDelete() {
        return "redirect:/";
    }

    // 16_개인북마크 화면
    @GetMapping("/userBookMarkForm")
    public String userBookMarkForm(HttpSession session, HttpServletRequest request,
            UserBookmarkResponse.UserBookmarkDTO bookmarkDTO) {
        // User user = (User) session.getAttribute("sessioUser");
        List<Posting> postingList = userBookmarkService.유저북마크전체(2);
        request.setAttribute("postingList", postingList);
        System.out.println(request.getAttribute("postingList"));
        return "user/userBookMarkForm";
    }

}
