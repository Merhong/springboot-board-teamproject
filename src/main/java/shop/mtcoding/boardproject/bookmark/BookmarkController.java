package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookmarkController {

    /* DI */
    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HttpSession session;

    // 개인북마크 화면
    @GetMapping("/user/bookmark/form")
    public String userBookMarkForm(HttpServletRequest request,
                                   BookmarkResponse.UserBookmarkDTO bookmarkDTO) {

        User user = (User) session.getAttribute("sessionUser");

        if (user == null) {
            return "redirect:/user/loginForm";
        }

        List<Posting> postingList = bookmarkService.유저북마크전체(user.getId());
        request.setAttribute("postingList", postingList);

        return "user/bookmark/form";
    }

    // 기업북마크 화면
    @GetMapping("/comp/{compId}/bookmark/list")
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

        return "comp/bookmark/list";
    }

    // 기업 이력서 북마크 삭제 POST
    @PostMapping("/comp/bookmark/list/delete")
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
        return "redirect:/comp/" + sessionComp.getUserId() + "/bookmark/list";
    }

    // 인재찾기 > 모두 북마크 POST
    @PostMapping("/comp/bookmark/list/saveAll")
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

        return "redirect:/comp/" + sessionComp.getUserId() + "/bookmark/list";
    }

    // 공고관리 > 지원자보기 > 이력서 > 북마크 하기 POST
    @PostMapping("/comp/bookmark/list/save")
    public String bookmarkSave(@RequestParam(defaultValue = "") Integer ResumeId, String bookmark2) {
        System.out.println("테스트a:" + bookmark2);

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        if (bookmark2.equals("북마크 하기")) {
            bookmarkService.회사북마크추가(sessionComp.getUserId(), ResumeId);
        }
        if (bookmark2.equals("북마크 삭제")) {
            bookmarkService.회사별북마크삭제(sessionComp.getUserId(), ResumeId);
        }

        return "redirect:/resume/newWindow/" + ResumeId;
        // return Script.href("/resume/newWindow/"+ResumeId, "북마크 성공");
    }


    // 개인 공고 북마크하기 POST
    @PostMapping("/user/bookmark/form/save")
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


    @PostMapping("/user/bookmark/form/delete")
    public ResponseEntity<String> deleteUserBookmark(@RequestParam Integer postingId) {
        Integer userId = ((User) session.getAttribute("sessionUser")).getId(); // 현재 로그인한 사용자의
        // ID를 가져옴
        try {
            bookmarkService.유저북마크삭제(postingId, userId);
            return ResponseEntity.ok("북마크가 삭제되었습니다.");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 북마크 API, 북마크 (하트)
    @GetMapping("api/user/bookmark")
    public @ResponseBody ApiUtil<List<Posting>> checkBookmark() {
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            return new ApiUtil<List<Posting>>(false, null);
        }
        List<Posting> list = bookmarkService.유저북마크전체(user.getId());
        if (list != null) {
            return new ApiUtil<List<Posting>>(true, list);
        } else {
            return new ApiUtil<List<Posting>>(false, null);
        }
    }

    // 북마크 API, 북마크 하기 (하트)
    @GetMapping("api/user/bookmark/{id}/save")
    public @ResponseBody ApiUtil<String> userBookmarkSave(@PathVariable Integer id) {
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            return new ApiUtil<String>(false, "로그인 안됨");
        }
        Integer sucuess = bookmarkService.유저북마크추가(id, user.getId());

        System.out.println("테스트" + sucuess);
        if (sucuess == 1) {
            return new ApiUtil<String>(true, "북마크 성공");
        } else {
            return new ApiUtil<String>(false, "북마크 실패");
        }
    }

    // 북마크 API, 북마크 취소 (하트)
    @GetMapping("/api/user/bookmark/{id}/delete")
    public @ResponseBody ApiUtil<String> userBookmarkDelete(@PathVariable Integer id) {
        System.out.println("id: " + id);
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            return new ApiUtil<String>(false, "로그인 안됨");
        }
        Integer sucuess = bookmarkService.유저북마크삭제(id, user.getId());
        System.out.println("테스트" + sucuess);
        if (sucuess == 1) {
            return new ApiUtil<String>(true, "북마크 제거 성공");
        } else {
            return new ApiUtil<String>(false, "북마크 제거 실패");
        }
    }
}