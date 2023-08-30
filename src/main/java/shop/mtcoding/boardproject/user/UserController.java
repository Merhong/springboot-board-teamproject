package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 17_개인기업추천 화면
    @GetMapping("/userRecommendForm")
    public String userRecommendForm() {
        return "user/userRecommendForm";
    }

    // 16번 북마크 삭제 버튼 POST
    @PostMapping("/userBookMarkDelete")
    public String userBookMarkDelete() {
        return "redirect:/";
    }

    // 16_개인북마크 화면
    @GetMapping("/userBookMarkForm")
    public String userBookMarkForm() {
        return "user/userBookMarkForm";
    }

    // 15_개인지원내역 화면
    @GetMapping("/userApplyList")
    public String userApplyList() {
        return "user/userApplyList";
    }

    // 14번 이력서 수정 버튼 POST



    // 14번 이력서 삭제 버튼 POST




    // 14_개인이력서관리 화면
    @GetMapping("/userResumeManage")
    public String userResumeManage() {
        return "user/userResumeManage";
    }

    // 13번 사진수정 버튼 POST




    // 13번 이력서 등록 버튼 POST
    @PostMapping("/userResumeSave")
    public String userResumeSave() {
        return "redirect:/";
    }

    // 13_개인이력서등록 화면
    @GetMapping("/userResumeForm")
    public String userResumeForm() {
        return "user/userResumeForm";
    }

    // 12번 수정하기 버튼 POST
    @PostMapping("/userUpdate")
    public String userUpdate() {
        return "redirect:/";
    }

    // 12_마이페이지 화면
    @GetMapping("/userMyPage")
    public String userMyPage() {
        return "user/userMyPage";
    }

    // 11번 지원하기 버튼 POST
    @PostMapping("/userApply")
    public String userApply() {
        return "redirect:/";
    }

    // 11_개인지원하기 화면
    @GetMapping("/userApplyForm")
    public String userApplyForm() {
        return "user/userApplyForm";
    }

    // 10_개인공고상세보기 화면
    @GetMapping("/userPostingDetail")
    public String userPostingDetail() {
        return "user/userPostingDetail";
    }

    // 5_로그인 화면
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 3_개인회원가입 화면
    @GetMapping("/userJoinForm")
    public String userJoinForm() {
        return "user/userJoinForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 2_회원가입유형선택 화면
    @GetMapping("/selectJoinForm")
    public String selectJoinForm() {
        return "user/selectJoinForm";
    }

}
