package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CompService compService;

    @Autowired
    private HttpSession session;


    // 11번 지원하기 버튼 POST
    @PostMapping("/user/apply")
    public String userApply(@RequestParam("selectedResume") Integer selectedResumeId,
                            @RequestParam("postingId") Integer postingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }

        Posting posting = compService.공고찾기(postingId);
        Resume selectedResume = resumeService.이력서찾기(selectedResumeId); // 선택한 이력서를 ID로 조회
        if (selectedResume.getDisclosure() == false) {
            throw new MyException("비공개 이력서로 지원 못함");
        }
        Apply apply = new Apply();
        apply.setUser(sessionUser);
        apply.setResume(selectedResume);
        apply.setPosting(posting); // Apply 엔티티에 공고 설정
        applyService.지원(apply); // Apply 엔티티 저장
        return "redirect:/";
    }

    // 개인_지원하기 페이지
    @GetMapping("/user/apply/form/{postingId}")
    public String userApplyForm(Model model, @PathVariable("postingId") Integer postingId) {
        Posting posting = compService.공고찾기(postingId);

        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer user = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(user);

        model.addAttribute("resumes", resumes); // 이력서 목록 모델에 추가
        model.addAttribute("user", user); // 유저 아이디를 모델에 추가
        model.addAttribute("posting", posting); // 공고를 모델에 추가

        return "user/apply/form";
    }

    // 15_개인지원내역 화면
    @GetMapping("/user/apply/list")
    public String userApplyList(HttpServletRequest request) {
        User user = (User) session.getAttribute("sessionUser");

        if (user == null) {
            return "redirect:/user/loginForm";
        }
        List<Apply> applyList = applyService.유저지원내역전체(user.getId());

        request.setAttribute("applyList", applyList);

        return "user/apply/list";
    }
}