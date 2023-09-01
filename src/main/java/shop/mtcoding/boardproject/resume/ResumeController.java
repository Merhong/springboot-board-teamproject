package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private HttpSession session;

    // 개인이력서 삭제 버튼 POST
    @PostMapping("/user/resumeDelete")
    public String userResumeDelete() {
        return null;
    }

    // 개인이력서 수정 버튼 POST
    @PostMapping("/user/resumeUpdate")
    public String userResumeUpdate() {
        return null;
    }

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
        System.out.println("userResumeSave 테스트 : " + resumeDTO.getSkillList().get(0));
        System.out.println("userResumeSave 테스트 : " + resumeDTO.getSkillList().get(1));
        System.out.println("userResumeSave 테스트 : " + resumeDTO.getSkillList().get(2));
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeDTO, sessionUser.getId());

        return "redirect:/";
    }

    // 개인이력서등록 화면
    @GetMapping("/user/resumeForm")
    public String userResumeForm(HttpServletRequest request) {

        List<Skill> skillList = skillService.모든스킬찾기();
        request.setAttribute("skillList", skillList);
        return "user/resumeForm";
    }

}
