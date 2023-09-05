package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.util.Script;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.bookmark.BookmarkResponse.CompBookmarkDTO;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HttpSession session;

    // 개인북마크 화면
    @GetMapping("/user/bookmarkForm")
    public String userBookMarkForm(HttpServletRequest request,
            BookmarkResponse.UserBookmarkDTO bookmarkDTO) {

        User user = (User) session.getAttribute("sessionUser");

        if (user == null) {
            return "redirect:/user/loginForm";
        }

        List<Posting> postingList = bookmarkService.유저북마크전체(user.getId());
        request.setAttribute("postingList", postingList);

        return "/user/bookmarkForm";
    }

    // 기업북마크 화면
    @GetMapping("/comp/{compId}/bookmarkList")
    public String bookmarkList(@PathVariable Integer compId, HttpServletRequest request) {

        // List<CompBookmark> compBookmarkList = BookmarkService.기업북마크전체(5);
        // List<CompBookmarkDTO> compBookmarkDTOList = new ArrayList<CompBookmarkDTO>();
        // for (CompBookmark bookmark : compBookmarkList) {
        // compBookmarkDTO.setResume(bookmark.getResume());
        // // compBookmarkDTO.setPosting(bookmark.getPosting());
        // compBookmarkDTOList.add(compBookmarkDTO);
        // }
        // request.setAttribute("compBookmarkDTOList", compBookmarkDTOList);

        // request.setAttribute("compId", compId);

        List<Resume> resumeList = bookmarkService.회사별북마크찾기(compId);
        request.setAttribute("resumeList", resumeList);

        return "comp/bookmarkList";
    }

    @PostMapping("/comp/bookmarkList/delete")
    public String bookmarkDelete(Integer hiddenResumeId) {
        Integer compId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        bookmarkService.회사별북마크삭제(compId, hiddenResumeId);
        return "redirect:/comp/" + compId + "/bookmarkList";
    }

    @PostMapping("/comp/bookmarkList/saveAll")
    public String bookmarkSaveALL(@RequestParam(defaultValue = "") List<Integer> ResumeIdList) {
        // System.out.println("테스트"+ResumeIdList);
        Integer compId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        bookmarkService.회사북마크추가(compId, ResumeIdList);
        return "redirect:/comp/" + compId + "/bookmarkList";
    }

    // @ResponseBody
    @PostMapping("/comp/bookmarkList/save")
    public String bookmarkSave(@RequestParam(defaultValue = "") Integer ResumeId) {
        Integer compId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        bookmarkService.회사북마크추가(compId, ResumeId);

        return "redirect:/resume/newWindow/" + ResumeId;
        // return Script.href("/resume/newWindow/"+ResumeId, "북마크 성공");
    }

    @PostMapping("/user/bookmarkForm/save")
    public String userbookmarkSave(@RequestParam(defaultValue = "") Integer postingId) {
        Integer userId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId(); // 현재 로그인한 사용자의 ID를 가져옴
        bookmarkService.개인북마크추가(userId, postingId);
        
        return "redirect:/resume/newWindow/"+postingId; // 개인 북마크 추가 후 개인 프로필 페이지로 리다이렉트
    }

}