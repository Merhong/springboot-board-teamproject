package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        if (compId != sessionComp.getUserId()) {
            throw new MyException("내꺼만볼수있음");
        }

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
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        bookmarkService.회사별북마크삭제(sessionComp.getUserId(), hiddenResumeId);
        return "redirect:/comp/" + sessionComp.getUserId() + "/bookmarkList";
    }

    @PostMapping("/comp/bookmarkList/saveAll")
    public String bookmarkSaveALL(@RequestParam(defaultValue = "") List<Integer> ResumeIdList) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        bookmarkService.회사북마크추가(sessionComp.getUserId(), ResumeIdList);

        return "redirect:/comp/" + sessionComp.getUserId() + "/bookmarkList";
    }

    // @ResponseBody
    @PostMapping("/comp/bookmarkList/save")
    public String bookmarkSave(@RequestParam(defaultValue = "") Integer ResumeId, String bookmark2) {
        System.out.println("테스트a:"+bookmark2);

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        if(bookmark2.equals("북마크 하기")){bookmarkService.회사북마크추가(sessionComp.getUserId(), ResumeId);}
        if(bookmark2.equals("북마크 삭제")){bookmarkService.회사별북마크삭제(sessionComp.getUserId(), ResumeId);}

        return "redirect:/resume/newWindow/" + ResumeId;
        // return Script.href("/resume/newWindow/"+ResumeId, "북마크 성공");
    }


    @PostMapping("/user/bookmarkForm/save")
    public ResponseEntity<String> userbookmarkSave(@RequestParam(defaultValue = "") Integer postingId) {
        Integer userId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId(); // 현재 로그인한 사용자의
        // ID를 가져옴

        try {
            bookmarkService.유저북마크추가(postingId, userId);
            return ResponseEntity.ok("북마크에 추가되었습니다.");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}