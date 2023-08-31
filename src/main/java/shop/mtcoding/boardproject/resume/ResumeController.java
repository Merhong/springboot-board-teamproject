package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    


    // 13번 이력서 등록 버튼 POST
    @PostMapping("/userResumeSave")
    public String userResumeSave(ResumeRequest.ResumeDTO resumeDTO, Integer id) {
        resumeService.이력서등록(resumeDTO, 3);

        return "redirect:/";
    }

    // 13_개인이력서등록 화면
    @GetMapping("/user/userResumeForm")
    public String userResumeForm() {
        return "user/userResumeForm";
    }



}
