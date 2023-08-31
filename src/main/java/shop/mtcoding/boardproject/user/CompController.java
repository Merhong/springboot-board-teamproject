package shop.mtcoding.boardproject.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CompController {

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/compJoinForm")
    public String compJoinForm() {
        return "comp/compJoinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }

    @GetMapping("/comp/test")
    public String test() {
        return "comp/test";
    }

    @GetMapping("/comp/main")
    public String Main() {
        return "comp/compMain";
    }

    @GetMapping("/comp/{compId}/noticeList")
    public String listView(@PathVariable Integer compId) {
        // request.setAttribute("compId", compId);
        return "comp/compNoticeList";
    }

    @GetMapping("/comp/notice/saveForm")
    public String saveForm() {
        return "comp/compSaveForm";
    }

    @GetMapping("/comp/notice/{noticeId}")
    public String detail(@PathVariable Integer noticeId) {
        // request.setAttribute("noticeId", noticeId);
        return "comp/compDetail";
    }

    @GetMapping("/comp/notice/{noticeId}/updateForm")
    public String updateForm(@PathVariable Integer noticeId) {
        // request.setAttribute("noticeId", noticeId);
        return "comp/compUpdateForm";
    }

    @GetMapping("/comp/notice/{noticeId}/resumeList")
    public String resumeList(@PathVariable Integer noticeId) {
        // request.setAttribute("noticeId", noticeId);
        return "comp/compResumeList";
    }

    @GetMapping("/comp/{compId}/bookmarkList")
    public String bookmarkList(@PathVariable Integer compId) {
        // request.setAttribute("compId", compId);
        return "comp/compBookmarkList";
    }

    @GetMapping("/comp/recommend")
    public String recommend() {
        return "comp/compRecommend";
    }

    @GetMapping("/comp/resume/{resumeId}")
    public String resumeDetail(@PathVariable Integer resumeId) {
        // request.setAttribute("noticeId", noticeId);
        return "comp/compResumeDetail";
    }


}
