package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ResumeController {
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private HttpSession session;

    @PostMapping("/resume/delete/{resumeId}")
    public @ResponseBody String delete(@PathVariable Integer resumeId) {
        resumeService.이력서삭제(resumeId);
        return "redirect:/user/resumeManage";
    }

    @PostMapping("/resume/{resumeId}/update")
    public String update(@PathVariable Integer resumeId, ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {
        resumeService.이력서수정(resumeId, resumeUpdateDTO);
        return "redirect:/user/resumeManage";

    }

    @GetMapping("/user/resumeUpdateForm/{id}")
    public String userResumeUpdate(@PathVariable Integer id, HttpServletRequest request) {
        Resume resume = resumeService.이력서상세보기(id);
        System.out.println("아이디: " + resume.getId());
        if (resume != null) {
            User sessionUser = (User) session.getAttribute("sessionUser");
            if (sessionUser.getId().equals(resume.getUser().getId())) {
                request.setAttribute("resume", resume);
                return "/user/resumeUpdateForm";
            }
        }
        return "redirect:/user/resumeManage"; // 권한 없으면 이력서 관리 페이지로 리다이렉트
    }

    // 개인이력서 상세보기
    @GetMapping("/user/resume/{id}")
    public String userResumeDetail(@PathVariable Integer id, HttpServletRequest request) {
        Resume resume = resumeService.이력서상세보기(id);
        System.out.println("아이디: " + resume.getId());
        if (resume != null) {
            User sessionUser = (User) session.getAttribute("sessionUser");
            if (sessionUser.getId().equals(resume.getUser().getId())) {
                request.setAttribute("resume", resume);
                return "/user/resumeDetailForm";
            }
        }
        return "redirect:/user/resumeManage"; // 권한 없으면 이력서 관리 페이지로 리다이렉트
    }

    // 14_개인이력서관리 화면
    @GetMapping("/user/resumeManage")
    public String userResumeManage(Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer userId = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(userId);
        model.addAttribute("resumes", resumes);
        model.addAttribute("userId", userId); // 유저 아이디를 모델에 추가
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