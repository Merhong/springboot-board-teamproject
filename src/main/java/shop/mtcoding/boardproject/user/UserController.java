package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRequest.CompInfoDTO;
import shop.mtcoding.boardproject.recommend.Recommend;
import shop.mtcoding.boardproject.recommend.RecommendService;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    /* DI */
    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CompService compService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private HttpSession session;

    // 17_개인기업추천 화면
    @GetMapping("/user/recommendForm")
    public String userRecommendForm(
            @RequestParam(defaultValue = "all") List<String> skillList,
            @RequestParam(defaultValue = "all") String position,
            HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }

        List<Skill> sl = skillService.스킬이름전부();

        request.setAttribute("skillList", sl);

        request.setAttribute("position", position);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(skillList);
            // System.out.println("테스트"+json);
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Posting> compList = userService.기업추천검색(skillList, position);
        System.out.println("검색1: " + compList);

        // CompInfoDTO 리스트 생성
        List<CompInfoDTO> compInfoList = new ArrayList<>();
        for (Posting posting : compList) {
            CompInfoDTO compInfoDTO = new CompInfoDTO();
            compInfoDTO.setId(posting.getId());
            compInfoDTO.setCompname(posting.getUser().getCompname());
            compInfoDTO.setTitle(posting.getTitle());
            compInfoDTO.setPosition(posting.getPosition());
            // 공고에 해당하는 스킬 정보 가져오기
            List<PostingSkill> postingSkills = skillService.공고별스킬조회(posting.getId());
            compInfoDTO.setPostingSkills(postingSkills);
            compInfoList.add(compInfoDTO);
        }

        // compInfoList를 컨트롤러에서 뷰로 전달
        request.setAttribute("compInfoList", compInfoList);

        return "user/recommendForm";
    }

    // 12번 수정하기 버튼 POST
    @PostMapping("/user/update")
    public String userUpdate(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        return "redirect:/";
    }

    // 12_마이페이지 화면
    @GetMapping("/user/updateForm")
    public String userMyPage(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        User user = userService.회원정보보기(sessionUser.getId());

        request.setAttribute("user", user);
        return "user/updateForm";
    }

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
        Apply apply = new Apply();
        apply.setUser(sessionUser);
        apply.setResume(selectedResume);
        apply.setPosting(posting); // Apply 엔티티에 공고 설정
        applyService.지원(apply); // Apply 엔티티 저장
        return "redirect:/";
    }

    // 개인_지원하기 페이지
    @GetMapping("/user/applyForm/{postingId}")
    public String userApplyForm(Model model, @PathVariable("postingId") Integer postingId) {
        Posting posting = compService.공고찾기(postingId);

        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer user = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(user);

        model.addAttribute("resumes", resumes); // 이력서 목록 모델에 추가
        model.addAttribute("user", user); // 유저 아이디를 모델에 추가
        model.addAttribute("posting", posting); // 공고를 모델에 추가

        return "user/applyForm";
    }

    // 5_로그인 화면
    @GetMapping("/user/loginForm")
    public String loginForm() {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser != null) {
            return "redirect:/";
        } // 동시 로그인 방지

        return "user/loginForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser != null) {
            throw new MyException("이미 로그인 상태임");
        } // 동시 로그인 방지

        User sessionUser = userService.로그인(loginDTO);
        System.out.println("세션 " + sessionUser.getRole());

        // 로그인 세션 관리용 sessionAllUser
        session.setAttribute("sessionAllUser", sessionUser); // 개인/기업/관리자 모두 가지고있는거

        // 로그인 사용자의 역할(role)에 따라 세션을 구분합니다.

        if (sessionUser != null) {
            if (sessionUser.getRole() == 0) {
                // 관리자의 경우 sessionAdmin 세션을 설정합니다.
                session.setAttribute("sessionAdmin", sessionUser);
                System.out.println("x : 관리자 로그인");
            } else if (sessionUser.getRole() == 1) {
                // 개인 사용자의 경우 sessionUser 세션을 설정합니다.
                session.setAttribute("sessionUser", sessionUser);
                System.out.println("x : 유저 로그인");
            } else if (sessionUser.getRole() == 2) {
                // 기업 사용자의 경우 CompSession 세션을 설정합니다.
                session.setAttribute("CompSession", sessionUser);
                System.out.println("x : 기업 로그인");
            }

            // 개인 및 기업 사용자의 경우 세부 정보를 SessionCompDTO에 저장하여 세션에 추가합니다.
            if (sessionUser.getRole() == 1 || sessionUser.getRole() == 2) {
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
                session.setAttribute("sessionComp", sessionComp);
            }
        }
        // 로그인 후 메인 페이지로 리다이렉트합니다.
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User sessionAdmin = (User) session.getAttribute("sessionAdmin");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionUser == null && sessionComp == null && sessionAdmin == null) {
            return "redirect:/user/loginForm";
        }
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

    // 로그인 아이디(이메일) 중복체크
    @GetMapping("/check")
    public @ResponseBody ApiUtil<String> check(String useremail) {
        User user = userService.이메일중복체크(useremail);
        if (user != null) {
            return new ApiUtil<String>(false, "이메일이 중복 되었습니다.");
        }

        return new ApiUtil<String>(true, "이메일을 사용 할 수 있습니다.");
    }

    // 개인 이력서를 보고 기업에서 입사제안 하는 페이지
    @GetMapping("/user/resume/{resumeId}/offerList")
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
        return "user/offerList";
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
        return "redirect:/user/resume/" + resumeId + "/offerList";
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
        return "redirect:/user/resume/" + resumeId + "/offerList";
    }

    // // 기업추천 검색 POST
    // @PostMapping("/api/user/recommend")
    // public @ResponseBody ApiUtil<List<Posting>> userRecommend(@RequestBody
    // UserRequest.SearchDTO searchDTO,
    // HttpServletResponse response) {
    // List<Posting> postingList = userService.기업추천검색(searchDTO);
    // System.out.println("postingList : " + postingList.size());
    // return new ApiUtil<List<Posting>>(true, postingList);
    //
    // }

    @GetMapping("/api/user/{userId}/ismessage")
    public String messageCheckOut(@PathVariable Integer userId) {
        System.out.println("메시지 삭제 컨트롤러 실행");
        Integer sucsess = userService.받은메시지조회(false, userId);
        User user = userService.회원조회(userId);

        if (user.getRole() == 0) {
            // 관리자의 경우 sessionAdmin 세션을 설정합니다.
            session.setAttribute("sessionAdmin", user);
            System.out.println("x : 관리자 로그인");
        } else if (user.getRole() == 1) {
            // 개인 사용자의 경우 sessionUser 세션을 설정합니다.
            session.setAttribute("sessionUser", user);
            System.out.println("x : 유저 로그인");
        } else if (user.getRole() == 2) {
            // 기업 사용자의 경우 CompSession 세션을 설정합니다.
            session.setAttribute("CompSession", user);
            System.out.println("x : 기업 로그인");
        }

        if (sucsess == 1) {
            System.out.println("메시지 삭제 성공");
        } else {
            System.out.println("메시지 삭제 실패");
        }
        return "redirect:/";
    }

}
