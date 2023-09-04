package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.bookmark.BookmarkResponse.CompBookmarkDTO;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService BookmarkService;

    @Autowired
    private HttpSession session;

    // 북마크 삭제 버튼 POST
    @PostMapping("/bookmarkDelete")
    public String bookmarkDelete() {
        return null;
    }

    // 개인북마크 화면
    @GetMapping("/user/bookmarkForm")
    public String userBookMarkForm(HttpServletRequest request,
            BookmarkResponse.UserBookmarkDTO bookmarkDTO) {

        User user = (User) session.getAttribute("sessionUser");

        if (user == null) {
            return "redirect:/user/loginForm";
        }

        List<Posting> postingList = BookmarkService.유저북마크전체(user.getId());
        request.setAttribute("postingList", postingList);
        return "/user/bookmarkForm";
    }

    // 기업북마크 화면
    @GetMapping("/comp/{compId}/bookmarkList")
    public String bookmarkList(@PathVariable Integer compId, HttpServletRequest request,
            BookmarkResponse.CompBookmarkDTO compBookmarkDTO) {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            return "redirect:/user/loginForm";
        }

        List<CompBookmark> compBookmarkList = BookmarkService.기업북마크전체(5);
        List<CompBookmarkDTO> compBookmarkDTOList = new ArrayList<CompBookmarkDTO>();
        for (CompBookmark bookmark : compBookmarkList) {
            compBookmarkDTO.setResume(bookmark.getResume());
            compBookmarkDTO.setPosting(bookmark.getPosting());
            compBookmarkDTOList.add(compBookmarkDTO);
        }
        request.setAttribute("compBookmarkDTOList", compBookmarkDTOList);
        return "comp/bookmarkList";
    }

}
