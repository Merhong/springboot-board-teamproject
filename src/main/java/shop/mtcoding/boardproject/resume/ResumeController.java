package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.skill.UserSkill;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private HttpSession session;

    @PostMapping("/resume/delete/{resumeId}")
    public String delete(@PathVariable Integer resumeId) {
        Resume resume = resumeService.이력서상세보기(resumeId);

        if (resume == null) {
            throw new MyException("없는 이력서 입니다.");
        }

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser.getId().equals(resume.getUser().getId())) {
            resumeService.이력서삭제(resumeId);
            return "redirect:/user/resumeManage";
        }

        throw new MyException("권한이 없습니다.");
    }

    // 이력서 수정하기 Post
    @PostMapping("/resume/{resumeId}/update")
    public String update(@PathVariable Integer resumeId, ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {

        Resume resume = resumeService.이력서상세보기(resumeId);

        if (resume == null) {
            throw new MyException("없는 이력서 입니다.");
        }

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser.getId().equals(resume.getUser().getId())) {
            resumeService.이력서수정(resumeId, resumeUpdateDTO);

            return "redirect:/user/resume/" + resumeId;
        }
        throw new MyException("권한이 없습니다.");
    }

    // 이력서 수정하기 페이지
    @GetMapping("/user/resumeUpdateForm/{id}")
    public String userResumeUpdate(@PathVariable Integer id, HttpServletRequest request) {
        Resume resume = resumeService.이력서상세보기(id);
        if (resume != null) {
            User sessionUser = (User) session.getAttribute("sessionUser");
            if (sessionUser.getId().equals(resume.getUser().getId())) {

                List<Skill> skillList = skillService.전체기술조회();
                List<UserSkill> userSkillList = skillService.유저스킬조회(sessionUser.getId());

                request.setAttribute("userSkillList", userSkillList);
                request.setAttribute("skillList", skillList);
                request.setAttribute("resume", resume);
                return "/user/resumeUpdateForm";
            }

        }
        return "redirect:/user/resumeManage"; // 권한 없으면 이력서 관리 페이지로 리다이렉트
    }

    // 개인이력서 상세보기
    @GetMapping("/user/resume/{id}")
    public String userResumeDetail(@PathVariable Integer id, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        Resume resume = resumeService.이력서상세보기(id);
        if (resume != null) {
            if (sessionUser != null || sessionComp != null) {

                List<Skill> skillList = skillService.전체기술조회();
                List<UserSkill> userSkillList = skillService.유저스킬조회(sessionUser.getId());

                request.setAttribute("userSkillList", userSkillList);
                request.setAttribute("skillList", skillList);
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
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
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
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        resumeService.이력서등록(resumeDTO, sessionUser.getId());
        return "redirect:/";
    }

    // 개인이력서등록 화면
    @GetMapping("/user/resumeForm")
    public String userResumeForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        List<Skill> skillList = skillService.전체기술조회();
        request.setAttribute("skillList", skillList);
        return "user/resumeForm";
    }

}