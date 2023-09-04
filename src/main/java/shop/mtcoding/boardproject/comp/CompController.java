package shop.mtcoding.boardproject.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompController {

    @Autowired
    private CompService compService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private HttpSession session;

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/comp/joinForm")
    public String compJoinForm() {
        return "comp/joinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }

    // 기업페이지
    @GetMapping("/comp/main")
    public String Main() {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            return "redirect:/user/loginForm";
        }

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
    public String saveForm(HttpServletRequest request) {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            return "redirect:/user/loginForm";
        }

        List<Skill> skillList = skillRepository.findAll();
        request.setAttribute("skillList", skillList);
        return "comp/saveForm";
    }

    // 기업 공고 상세보기
    @GetMapping("/comp/posting/{postingId}")
    public String detail(@PathVariable Integer postingId, HttpServletRequest request) {

        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        return "comp/detail";
    }

    // @ResponseBody
    // @GetMapping("/comp/posting/check")
    // public ResponseEntity<String> check(int postingId){
    // Posting posting = compService.공고찾기(postingId);
    // if (posting != null) {
    // return posting;
    // }
    // }

    // TODO : 가져온걸 화면에 뿌려야하는데 자바에서 하니까 너무 노가다임. 현재 기술까지만 되어있음
    // 기업 공고 수정화면
    @GetMapping("/comp/posting/{postingId}/updateForm")
    public String updateForm(@PathVariable Integer postingId, HttpServletRequest request) {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            throw new MyException("권한이 없습니다.");
        }

        List<Skill> skillList = skillRepository.findAll();
        request.setAttribute("skillList", skillList);

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

        // List<PostingSkill> sl = posting.getPostingSkill();
        // for (PostingSkill s : sl) {
        // if(s.getSkill().getId()==1){request.setAttribute("Java", true); continue;}
        // if(s.getSkill().getId()==2){request.setAttribute("Spring", true); continue;}
        // if(s.getSkill().getId()==3){request.setAttribute("DB", true); continue;}
        // if(s.getSkill().getId()==4){request.setAttribute("HTML", true); continue;}
        // if(s.getSkill().getId()==5){request.setAttribute("Python", true); continue;}
        // if(s.getSkill().getId()==6){request.setAttribute("JavaScript", true);
        // continue;}
        // if(s.getSkill().getId()==7){request.setAttribute("Git", true); continue;}
        // if(s.getSkill().getId()==8){request.setAttribute("C", true); continue;}
        // }

        return "comp/updateForm";
    }

    @GetMapping("/comp/posting/{postingId}/resumeList")
    public String resumeList(@PathVariable Integer postingId, HttpServletRequest request) {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            throw new MyException("권한이 없습니다.");
        }

        List<Apply> applyList = applyService.공고지원현황(postingId);
        // Posting posting = compService.공고찾기(postingId);
        Posting posting = applyList.get(0).getPosting();
        request.setAttribute("posting", posting);
        request.setAttribute("applyList", applyList);
        return "comp/resumeList";
    }

    @GetMapping("/comp/recommend")
    public String recommend(HttpServletRequest request) {
        List<Skill> skillList = skillRepository.findAll();
        request.setAttribute("skillList", skillList);
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

        int id = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();
        Posting posting = compService.공고찾기(postingId);

        if (posting == null) {

            throw new MyException("없는 공고 입니다.");
        }

        if (id == posting.getUser().getId()) {

            compService.공고수정(postingId, updateDTO);
            return "redirect:/comp/" + id + "/postingList";
        }

        throw new MyException("권한이 없습니다.");
    }

    @PostMapping("/comp/main/{userId}/update")
    public @ResponseBody String compUpdate(@PathVariable Integer userId, CompRequest.compUpdateDTO DTO) {

        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp == null) {
            throw new MyException("권한이 없습니다.");
        }

        compService.기업정보수정(userId, DTO);
        return Script.href("/comp/main", "정보 수정 완료");

    }

    @PostMapping("/comp/posting/{postingId}/delete")
    public @ResponseBody String delete(@PathVariable Integer postingId) {
        int userId = ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId();

        Posting posting = compService.공고찾기(postingId);

        if (posting == null) {
            throw new MyException("없는 공고 입니다.");
        }

        if (userId == posting.getUser().getId()) {
            compService.공고삭제(postingId);
            return Script.href("/comp/" + userId + "/postingList", "삭제 완료");
        }

        throw new MyException("권한이 없습니다.");
    }

}
