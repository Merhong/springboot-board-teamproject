package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService BookmarkService;

    @Autowired
    private HttpSession session;

    // 개인북마크 화면
    @GetMapping("/user/bookmarkForm")
    public String userBookMarkForm(HttpServletRequest request,
            BookmarkResponse.UserBookmarkDTO bookmarkDTO) {
        User user = (User) session.getAttribute("sessionUser");
        List<Posting> postingList = BookmarkService.유저북마크전체(user.getId());
        request.setAttribute("postingList", postingList);
        return "/user/bookmarkForm";
    }

    // 기업북마크 화면
    @GetMapping("/comp/{compId}/bookmarkList")
    public String bookmarkList(@PathVariable Integer compId, HttpServletRequest request) {

        // User user = (User) session.getAttribute("sessionUser");
        // List<Resume> resumeList = BookmarkService.기업북마크전체(user.getId());
        // request.setAttribute("resumeList", resumeList);
        return "comp/bookmarkList";
    }

}
