package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpSession;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private HttpSession session;

    // 개인이력서 상세보기
    @GetMapping("/user/{id}/resume")
    public String userResumeDetail() {
        return null;
    }

    // 14_개인이력서관리 화면
    @GetMapping("/user/resumeManage")
    public String userResumeManage() {

        return "/user/resumeManage";
    }

    // 이력서 등록 버튼 POST
    @PostMapping("/user/resumeSave")
    public String userResumeSave(ResumeRequest.ResumeDTO resumeDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeDTO, sessionUser.getId());

        return "redirect:/";
    }

    // 개인이력서등록 화면
    @GetMapping("/user/resumeForm")
    public String userResumeForm() {
        return "user/resumeForm";
    }


}
