package shop.mtcoding.boardproject.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.recommend.Recommend;
import shop.mtcoding.boardproject.recommend.RecommendService;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CompService compService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private HttpSession session;

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/comp/joinForm")
    public String compJoinForm() {
        return "comp/joinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }


    @GetMapping("/comp/main")
    public String Main() {
        // CompRequest.SessionCompDTO sessionUser = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        return "comp/main";
    }

    @GetMapping("/comp/{compId}/postingList")
    public String listView(@PathVariable Integer compId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionComp.getUserId() != compId) {
            throw new MyException("내 공고가 아닙니다.");
        }

        List<Posting> postingList = compService.회사별공고찾기(compId);
        request.setAttribute("postingList", postingList);
        return "comp/postingList";
    }

    @GetMapping("/comp/posting/saveForm")
    public String saveForm(HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        List<Skill> skillList = skillService.스킬이름전부();

        request.setAttribute("skillList", skillList);
        return "comp/saveForm";
    }

    @GetMapping("/comp/posting/{postingId}")
    public String detail(@PathVariable Integer postingId, HttpServletRequest request) {

        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        return "comp/postingDetail";
    }

    @GetMapping("/comp/posting/newWindow/{postingId}")
    public String detail2(@PathVariable Integer postingId, HttpServletRequest request) {
        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        return "comp/postingDetailOnly";
    }

    @GetMapping("/comp/posting/{postingId}/updateForm")
    public String updateForm(@PathVariable Integer postingId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        List<Skill> skillList = skillService.스킬이름전부();

        request.setAttribute("skillList", skillList);

        Posting posting = compService.공고찾기(postingId);

        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }

        request.setAttribute("posting", posting);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(posting);
            // System.out.println("테스트"+json);
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "comp/updateForm";
    }

    @GetMapping("/comp/posting/{postingId}/resumeList")
    public String resumeList(@PathVariable Integer postingId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");


        List<Apply> applyList = applyService.공고지원현황(postingId);

        Posting posting = compService.공고찾기(postingId);
        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }

        request.setAttribute("posting", posting);
        
        List<Apply> resumeList = compService.공고지원신청찾기(postingId);
        
        // System.out.println("테스트:" +resumeList.get(0).getTitle());
        
        request.setAttribute("applyList", applyList);
        
        request.setAttribute("resumeList", resumeList);
        
        return "comp/resumeList";
    }
    
    @GetMapping("/comp/posting/{postingId}/offerList")
    public String offerList(@PathVariable Integer postingId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        
        Posting posting = compService.공고찾기(postingId);
        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }
        request.setAttribute("posting", posting);
        
        List<Recommend> recommendList = compService.공고로제안한목록찾기(postingId);
        request.setAttribute("recommendList", recommendList);
        
        return "comp/offerList";
    } // TODO //



    @GetMapping("/comp/recommend")
    public String recommend(@RequestParam(defaultValue = "all") List<String> skillList, @RequestParam(defaultValue = "all") String position, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
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

        List<User> userList = compService.인재추천검색(skillList, position);
        request.setAttribute("userList", userList);

        return "comp/recommend";
    }

    @GetMapping("/resume/{resumeId}")
    public String resumeDetail(@PathVariable Integer resumeId, HttpServletRequest request) {
        Resume resume = resumeService.이력서찾기(resumeId);
        request.setAttribute("resume", resume);
        return "comp/resumeDetail";
    }

    @GetMapping("/resume/newWindow/{resumeId}")
    public String resumeDetail2(@PathVariable Integer resumeId, HttpServletRequest request) {
        Resume resume = resumeService.이력서찾기(resumeId);
        // System.out.println("테스트"+resume.getUser().getUsername());
        request.setAttribute("resume", resume);
        return "comp/resumeDetailOnly";
    }

    @PostMapping("/comp/join")
    public String join(CompRequest.JoinDTO joinDTO) {
        compService.회원가입(joinDTO);
        return "redirect:/user/loginForm";
    }

    @PostMapping("/comp/posting/save")
    public String postingSave(CompRequest.SaveDTO saveDTO) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        compService.공고작성(saveDTO, sessionComp.getUserId());
        return "redirect:/comp/" + sessionComp.getUserId() + "/postingList";
    }

    @PostMapping("/comp/posting/{postingId}/update")
    public String postingUpdate(@PathVariable Integer postingId, CompRequest.UpdateDTO updateDTO) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        int compId = sessionComp.getUserId();
        Posting posting = compService.공고찾기(postingId);

        if (posting == null) {
            throw new MyException("없는 공고 입니다.");
        }

        if (compId != posting.getUser().getId()) {
            throw new MyException("권한이 없습니다.");
        }
        
        compService.공고수정(postingId, updateDTO);
        return "redirect:/comp/" + compId + "/postingList";
    }

    @PostMapping("/comp/main/{compId}/update")
    public @ResponseBody String compUpdate(@PathVariable Integer compId, CompRequest.compUpdateDTO DTO) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        if(compId != sessionComp.getUserId()){
            throw new MyException("권한이 없습니다.");
        }

        // compService.기업정보수정(compId, DTO);
        session.setAttribute("sessionComp", compService.기업정보수정(compId, DTO)); // 세션 수정한걸로 변경

        return Script.href("/comp/main", "정보 수정 완료");
    }

    @PostMapping("/comp/posting/{postingId}/delete")
    public @ResponseBody String delete(@PathVariable Integer postingId) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        int compId = sessionComp.getUserId();
        Posting posting = compService.공고찾기(postingId);

        if (posting == null) {
            throw new MyException("없는 공고 입니다.");
        }

        if (compId == posting.getUser().getId()) {
            compService.공고삭제(postingId);
            return Script.href("/comp/" + compId + "/postingList", "삭제 완료");
        }


        throw new MyException("권한이 없습니다.");
    }

    @PostMapping("/comp/posting/apply/{applyId}/pass")
    public String applyPass(@PathVariable Integer applyId) {
        
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        Apply apply = applyService.공고지원합격(applyId, sessionComp.getUserId());

        return "redirect:/comp/posting/" + apply.getPosting().getId() + "/resumeList";
    }

    @PostMapping("/comp/posting/apply/{applyId}/fail")
    public String applyFail(@PathVariable Integer applyId) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        Apply apply = applyService.공고지원불합(applyId, sessionComp.getUserId());

        return "redirect:/comp/posting/" + apply.getPosting().getId() + "/resumeList";
    }

    @PostMapping("/comp/posting/offer/{recommendId}/cancel")
    public String recommendCancel(@PathVariable Integer recommendId) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        Integer postingId = recommendService.입사제안취소(recommendId, sessionComp.getUserId());

        return "redirect:/comp/posting/" + postingId + "/offerList";
    }





    // 중복체크
    @GetMapping("/comp/check")
    public @ResponseBody ApiUtil<String> check(String useremail) {
        User user = compService.이메일중복체크(useremail);
        if (user != null) {
            return new ApiUtil<String>(false, "이메일이 중복 되었습니다.");
        }
        System.out.println("테스트 3");
        return new ApiUtil<String>(true, "이메일을 사용 할 수 있습니다.");
    }
}
