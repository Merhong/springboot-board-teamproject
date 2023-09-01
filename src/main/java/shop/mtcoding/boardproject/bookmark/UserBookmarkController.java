package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.mtcoding.boardproject.posting.Posting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserBookmarkController {

    @Autowired
    private BookmarkService userBookmarkService;

    @Autowired
    private HttpSession session;

    // 16_개인북마크 화면
    @GetMapping("/user/bookMarkForm")
    public String userBookMarkForm(HttpSession session, HttpServletRequest request,
            BookmarkResponse.UserBookmarkDTO bookmarkDTO) {
        // User user = (User) session.getAttribute("sessioUser");
        List<Posting> postingList = userBookmarkService.유저북마크전체(2);
        System.out.println(postingList.get(0).getTitle());
        request.setAttribute("postingList", postingList);
        System.out.println(request.getAttribute("postingList"));
        return "/user/bookMarkForm";
    }

}
