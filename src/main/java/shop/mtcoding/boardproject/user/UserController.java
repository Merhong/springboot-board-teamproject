package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired // DI
    private ResumeService resumeService;

    @Autowired // DI
    private ApplyService applyService;

    @Autowired // DI
    private CompService compService;

    @Autowired
    private HttpSession session;

    // 17_개인기업추천 화면
    @GetMapping("/user/recommendForm")
    public String userRecommendForm() {
        return "user/recommendForm";
    }

    // 12번 수정하기 버튼 POST
    @PostMapping("/user/update")
    public String userUpdate(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("Session user: " + sessionUser);

        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    // 12_마이페이지 화면
    @GetMapping("/user/updateForm")
    public String userMyPage(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    // 15_개인지원내역 화면
    @GetMapping("/user/applyList")
    public String userApplyList(HttpServletRequest request) {
        User user = (User) session.getAttribute("sessionUser");
        List<Apply> applyList = applyService.유저지원내역전체(user.getId());
        request.setAttribute("applyList", applyList);
        return "user/applyList";
    }

    // 11번 지원하기 버튼 POST
    @PostMapping("/user/apply")
    public String userApply(@RequestParam("selectedResume") Integer selectedResumeId,
            @RequestParam("postingId") Integer postingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Posting posting = compService.공고찾기(postingId);
        Resume selectedResume = resumeService.이력서찾기(selectedResumeId); // 선택한 이력서를 ID로 조회

        Apply apply = new Apply();
        apply.setUser(sessionUser);
        apply.setResume(selectedResume);
        apply.setPosting(posting); // Apply 엔티티에 공고 설정
        applyService.지원(apply); // Apply 엔티티 저장

        return "redirect:/";
    }

    // 11_개인지원하기 화면
    @GetMapping("/user/applyForm/{postingId}")
    public String userApplyForm(Model model, @PathVariable("postingId") Integer postingId) {
        Posting posting = compService.공고찾기(postingId);

        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer user = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(user);

        model.addAttribute("resumes", resumes); // 목록 추가
        model.addAttribute("user", user); // 유저 아이디를 모델에 추가
        model.addAttribute("posting", posting); // 유저 아이디를 모델에 추가
        return "user/applyForm";
    }

    // 10_개인공고상세보기 화면
    @GetMapping("/user/postingDetail")
    public String userPostingDetail() {
        return "user/postingDetail";
    }

    // 5_로그인 화면
    @GetMapping("/user/loginForm")
    public String loginForm() {
        return "user/loginForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);

        if (sessionUser.getCompname() != null) {
            CompRequest.SessionCompDTO sessionComp = CompRequest.SessionCompDTO.builder()
                    .userId(sessionUser.getId())
                    .email(sessionUser.getEmail())
                    .compname(sessionUser.getCompname())
                    .compRegister(sessionUser.getCompRegister())
                    .tel(sessionUser.getTel())
                    .photo(sessionUser.getPhoto())
                    .address(sessionUser.getAddress())
                    .homepage(sessionUser.getHomepage())
                    .role(sessionUser.getRole())
                    .build();
            // System.out.println("테스트:"+sessionComp);
            session.setAttribute("sessionComp", sessionComp);
        }

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션 무효화(세션 전체를 비움 - 서랍 비우는 거)
        return "redirect:/";
    }

    // 3_개인회원가입 화면
    @GetMapping("/user/joinForm")
    public String userJoinForm() {
        return "user/joinForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 2번 회원가입 버튼 POST
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "user/loginForm";
    }

    // 2_회원가입유형선택 화면
    @GetMapping("/selectJoinForm")
    public String selectJoinForm() {
        return "user/selectJoinForm";
    }

}
