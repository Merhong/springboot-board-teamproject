package shop.mtcoding.boardproject.resume;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserService;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private HttpSession session;



    // 13번 이력서 등록 버튼 POST
    @PostMapping("/user/resumeSave")
    public String userResumeSave(ResumeRequest.ResumeDTO resumeDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeDTO, sessionUser.getId());

        return "redirect:/";
    }

    // 13_개인이력서등록 화면
    @GetMapping("/user/resumeForm")
    public String userResumeForm() {
        return "user/resumeForm";
    }



}
