package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.apply.ApplyResponse;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.recommend.Recommend;
import shop.mtcoding.boardproject.recommend.RecommendService;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.skill.UserSkill;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ResumeController {

    /* DI */
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CompService compService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    // 개인 이력서를 보고 기업에서 입사제안 하는 페이지
    @GetMapping("/user/resume/{resumeId}/offer/list")
    public String offerListUser(@PathVariable Integer resumeId, HttpServletRequest request) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 이력서를 찾는다.
        Resume resume = resumeService.이력서찾기(resumeId, sessionAllUser.getId());
        // 세션id와 이력서의 유저id가 같은지 비교한다.
        if (sessionAllUser.getId() != resume.getUser().getId()) {
            throw new MyException("내 이력서가 아닙니다.");
        }
        // id가 동일하다면 request에 이력서를 담는다.
        request.setAttribute("resume", resume);
        // 리스트에 찾은 이력서를 넣는다.
        List<Recommend> recommendList = recommendService.이력서받은오퍼찾기(resumeId);
        // request에 추천리스트를 담는다.
        request.setAttribute("recommendList", recommendList);
        // 입사 제안 보기 페이지로 이동
        return "user/offer/list";
    }


    // 입사제안에 대한 거절 POST
    @PostMapping("/user/resume/offer/{recommendId}/fail")
    public String offerFail(@PathVariable Integer recommendId) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면 이동시킨다.
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 세션의id와 추천id를 비교해서 거절
        Integer resumeId = recommendService.오퍼거절(recommendId, sessionAllUser.getId());
        // 입사제안 화면으로 리다이렉트
        return "redirect:/user/resume/" + resumeId + "/offer/list";
    }


    // 입사제안에 대한 수락 POST
    @PostMapping("/user/resume/offer/{recommendId}/pass")
    public String offerPass(@PathVariable Integer recommendId) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면 이동시킨다.
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 세션의id와 추천id를 비교해서 수락
        Integer resumeId = recommendService.오퍼수락(recommendId, sessionAllUser.getId());
        // 입사제안 화면으로 리다이렉트
        return "redirect:/user/resume/" + resumeId + "/offer/list";
    }


    // TODO 이력서는 쓴 본인과 기업만 볼 수 있게 막아야 함.
    // 기업이력서 상세보기 페이지
    @GetMapping("/resume/{resumeId}")
    public String resumeDetail(@PathVariable Integer resumeId, HttpServletRequest request) {
        // 모든유저 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션유저가 있다면
        Integer userId = 0;
        if (sessionAllUser != null) {
            userId = sessionAllUser.getId();
        }
        // 이력서를 찾아서
        Resume resume = resumeService.이력서찾기(resumeId, userId);
        // request에 담는다.
        request.setAttribute("resume", resume);

        // 이력서 상세보기 페이지를 보여준다.
        return "comp/resume/detail";
    }


    // 기업 인재찾기 > 이력서 보기 페이지
    @GetMapping("/resume/newWindow/{resumeId}")
    public String resumeDetail2(@PathVariable Integer resumeId, HttpServletRequest request) {
        // 유저세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 유저id와 이력서id를가지고 북마크가 있는지 확인
        Boolean boomarkBoolean = compService.북마크중복체크(sessionAllUser.getId(), resumeId);
        // 세션 있으면 중복 아닌거니까 북마크 해도됨
        if (boomarkBoolean == true) {
            request.setAttribute("boomarkOK", true);
        }
        // 이력서를 찾는다.
        Resume resume = resumeService.이력서찾기(resumeId, sessionAllUser.getId());
        // request에 이력서를 담는다.
        request.setAttribute("resume", resume);

        // 이력서 보기 페이지를 보여준다.
        return "comp/resume/detailOnly";
    }


    // 기업이 받은 이력서 목록
    @GetMapping("/comp/{compId}/resume/listCompReceived")
    public String resumeListCompReceived(@PathVariable Integer compId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp.getUserId() != compId) {
            throw new MyException("내꺼 아니라 못봄");
        }

        List<ApplyResponse.ApplyCompDTO> applyCompDTOList = applyService.기업별모든지원찾기(compId);
        request.setAttribute("applyCompDTOList", applyCompDTOList);

        return "comp/resume/listCompReceived";
    }


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
            return "redirect:/user/resume/manage";
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


    // 개인이력서 수정 화면
    @GetMapping("/user/resume/updateForm/{resumeId}")
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
                return "user/resume/updateForm";
            }

        }
        return "redirect:user/resume/manage"; // 권한 없으면 이력서 관리 페이지로 리다이렉트
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
                return "user/resume/detailForm";
            }
        }
        return "redirect:/user/resume/manage"; // 권한 없으면 이력서 관리 페이지로 리다이렉트
    }


    // 14_개인이력서관리 화면
    @GetMapping("/user/resume/manage")
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
        return "user/resume/manage";
    }


    // 개인이력서 등록 버튼 POST
    @PostMapping("/user/resume/save")
    public String userResumeSave(ResumeRequest.ResumeDTO resumeDTO) {
        // 1. 세션유저를 찾는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        // 2. 이력서 등록
        resumeService.이력서등록(resumeDTO, sessionUser.getId());

        // 3. 유저 테이블에 직무 정보 업데이트
        sessionUser.setPosition(resumeDTO.getPosition()); // 직무 정보 업데이트
        userService.회원정보업데이트(sessionUser); // 유저 정보 업데이트

        return "redirect:/";
    }


    // 개인이력서등록 화면
    @GetMapping("/user/resume/form")
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
        return "user/resume/form";
    }

}