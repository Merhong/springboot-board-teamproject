package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.PostingSkill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompController {

    @Autowired
    private CompService compService;

    @Autowired
    private HttpSession session;

    // 기업페이지_기업정보수정
    @PostMapping("comp/update")
    public String compUpdate() {
        return null;
    }

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/comp/joinForm")
    public String compJoinForm() {
        return "comp/joinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }

    // 기업페이지
    @GetMapping("/comp/main")
    public String Main() {
        return "comp/main";
    }

    // 기업 공고관리
    @GetMapping("/comp/{compId}/postingList")
    public String listView(@PathVariable Integer compId, HttpServletRequest request) {
        List<Posting> postingList = compService.회사별공고찾기(compId);
        request.setAttribute("postingList", postingList);
        return "comp/postingList";
    }

    // 기업 공고등록
    @GetMapping("/comp/posting/saveForm")
    public String saveForm() {
        return "comp/saveForm";
    }

    // 기업 공고 상세보기
    @GetMapping("/comp/posting/{postingId}")
    public String detail(@PathVariable Integer postingId, HttpServletRequest request) {
        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        return "comp/detail";
    }

    // 기업 공고 수정화면
    @GetMapping("/comp/posting/{postingId}/updateForm")
    public String updateForm(@PathVariable Integer postingId, HttpServletRequest request) {
        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);

        String position = posting.getPosition();
        if (position.equals("백엔드")) {
            request.setAttribute("백엔드", true);
        }
        if (position.equals("프론트엔드")) {
            request.setAttribute("프론트엔드", true);
        }
        if (position.equals("풀스택")) {
            request.setAttribute("풀스택", true);
        }
        if (position.equals("안드로이드")) {
            request.setAttribute("안드로이드", true);
        }
        if (position.equals("IOS")) {
            request.setAttribute("IOS", true);
        }
        if (position.equals("임베디드")) {
            request.setAttribute("임베디드", true);
        }
        if (position.equals("빅데이터")) {
            request.setAttribute("빅데이터", true);
        }
        if (position.equals("서버")) {
            request.setAttribute("서버", true);
        }
        if (position.equals("머신러닝")) {
            request.setAttribute("머신러닝", true);
        }

        List<PostingSkill> skillList = posting.getPostingSkill();
        for (PostingSkill s : skillList) {
            if (s.getSkill().indexOf("Java") == 0 && s.getSkill().indexOf("cript") == -1) {
                request.setAttribute("Java", true);
                continue;
            }
            if (s.getSkill().indexOf("DB") == 0) {
                request.setAttribute("DB", true);
                continue;
            }
            if (s.getSkill().indexOf("HTML") == 0) {
                request.setAttribute("HTML", true);
                continue;
            }
            if (s.getSkill().indexOf("Python") == 0) {
                request.setAttribute("Python", true);
                continue;
            }
            if (s.getSkill().indexOf("JavaScript") == 0) {
                request.setAttribute("JavaScript", true);
                continue;
            }
            if (s.getSkill().indexOf("Git") == 0) {
                request.setAttribute("Git", true);
                continue;
            }
            if (s.getSkill().indexOf("Spring") == 0) {
                request.setAttribute("Spring", true);
                continue;
            }
            if (s.getSkill().equals("C")) {
                request.setAttribute("C", true);
                continue;
            }
        }

        return "comp/updateForm";
    }


    @GetMapping("/comp/posting/{postingId}/resumeList")
    public String resumeList(@PathVariable Integer postingId, HttpServletRequest request) {
        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        return "comp/resumeList";
    }

    @GetMapping("/comp/recommend")
    public String recommend() {
        return "comp/recommend";
    }

    @GetMapping("/resume/{resumeId}")
    public String resumeDetail(@PathVariable Integer resumeId) {
        // request.setAttribute("postingId", postingId);
        return "comp/resumeDetail";
    }

    @PostMapping("/comp/join")
    public String join(CompRequest.JoinDTO joinDTO) {
        compService.회원가입(joinDTO);
        return "redirect:/user/loginForm";
    }

    @PostMapping("/comp/posting/save")
    public String postingSave(CompRequest.SaveDTO saveDTO) {
        // System.out.println("테스트saveDTO:"+saveDTO);
        compService.공고작성(saveDTO);
        int id = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        return "redirect:/comp/" + id + "/postingList";
    }

    @PostMapping("/comp/posting/{postingId}/update")
    public String postingUpdate(@PathVariable Integer postingId, CompRequest.UpdateDTO updateDTO) {
        // System.out.println("테스트updateDTO:"+updateDTO);
        compService.공고수정(postingId, updateDTO);
        int id = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        return "redirect:/comp/" + id + "/postingList";
    }

    @PostMapping("/comp/main/{userId}/update")
    public String compUpdate(@PathVariable Integer userId, CompRequest.compUpdateDTO DTO) {
        compService.기업정보수정(userId, DTO);
        return "redirect:/comp/main";
    }
}
