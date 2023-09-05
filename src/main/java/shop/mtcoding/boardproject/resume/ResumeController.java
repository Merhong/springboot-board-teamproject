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

    /* DI */
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private HttpSession session;

    // 이력서 삭제 POST
    @PostMapping("/resume/delete/{resumeId}")
    public String delete(@PathVariable Integer resumeId) {
        // 1. 이력서를 찾는다.
        Resume resume = resumeService.이력서상세보기(resumeId);
        if (resume == null) {
            throw new MyException("없는 이력서 입니다.");
        }
        // 2-1. 세션에 해당하는 유저id와 같은지 체크후 이력서 삭제
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser.getId().equals(resume.getUser().getId())) {
            resumeService.이력서삭제(resumeId);
            return "redirect:/user/resumeManage";
        }
        // 2-2. id가 다르다면, 예외처리
        throw new MyException("권한이 없습니다.");
    }


    // 이력서 수정하기 Post
    @PostMapping("/resume/{resumeId}/update")
    public String update(@PathVariable Integer resumeId, ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {
        // 1. 이력서를 찾는다.
        Resume resume = resumeService.이력서상세보기(resumeId);
        if (resume == null) {
            throw new MyException("없는 이력서 입니다.");
        }
      
        // 2-1. 세션에 해당하는 유저id와 같은지 체크 후 이력서 수정
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser.getId().equals(resume.getUser().getId())) {
            resumeService.이력서수정(resumeId, resumeUpdateDTO);

            return "redirect:/user/resume/" + resumeId;
        }
      
        // 2-2. id가 다르면 예외처리
        throw new MyException("권한이 없습니다.");
    }

    // 이력서 수정 화면
    @GetMapping("/user/resumeUpdateForm/{resumeId}")
    public String userResumeUpdate(@PathVariable Integer resumeId, HttpServletRequest request) {
        // 1. 이력서를 찾는다.
        Resume resume = resumeService.이력서상세보기(resumeId);
        // 2. 이력서가 존재하면, 아이디 같은지 체크 후 request에 담아서 수정 화면을 보여준다.
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
    @GetMapping("/user/resume/{resumeId}")
    public String userResumeDetail(@PathVariable Integer resumeId, HttpServletRequest request) {
        // 1. 개인회원 세션을 찾는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 2. 공고기업 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 3. 이력서를 찾는다.
        Resume resume = resumeService.이력서상세보기(resumeId);
        // 4. 이력서가 존재하고 유저와 공고기업 세션이 존재하면 request에 담아서 이력서 상세보기 화면을 보여준다.
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
        // 1. 세션을 찾는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        // 2. 세션유저(개인)의 id를 가지고 이력서를 찾아서 리스트에 담는다.
        Integer userId = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(userId);
        // 3. 이력서 리스트와 유저id를 모델에 추가하고 화면을 보여준다.
        model.addAttribute("resumes", resumes);
        model.addAttribute("userId", userId); // 유저 아이디를 모델에 추가
        return "/user/resumeManage";
    }

    // 이력서 등록 버튼 POST
    @PostMapping("/user/resumeSave")
    public String userResumeSave(ResumeRequest.ResumeDTO resumeDTO) {
        // 1. 세션유저를 찾는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        // 2. 이력서 등록
        resumeService.이력서등록(resumeDTO, sessionUser.getId());
        return "redirect:/";
    }

    // 개인이력서등록 화면
    @GetMapping("/user/resumeForm")
    public String userResumeForm(HttpServletRequest request) {
        // 1. 세션유저를 찾는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        // 2. 개인스킬리스트를 찾는다.
        List<Skill> skillList = skillService.전체기술조회();
        // 3. 찾은 리스트를 request에 담는다.
        request.setAttribute("skillList", skillList);
        // 4. 담은것을 화면에 보여준다.
        return "user/resumeForm";
    }

}