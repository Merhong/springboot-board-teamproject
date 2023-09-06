package shop.mtcoding.boardproject.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    /* DI */
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


    // 기업페이지
    @GetMapping("/comp/main")
    public String Main() {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면으로 이동시킴
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 회원유형(role)이 2일때만 통과
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        // 페이지를 보여준다.
        return "comp/main";
    }

    // 기업 공고 관리 페이지
    @GetMapping("/comp/{compId}/postingList")
    public String listView(@PathVariable Integer compId, HttpServletRequest request) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션 없으면 로그인 화면으로 이동
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 기업회원이 아니면 예외처리
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        // 세션에서 sessionComp 속성을 가져와서 DTO에 담는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // sessionComp의 유저id와 기업id를 비교
        if (sessionComp.getUserId() != compId) {
            throw new MyException("내 공고가 아닙니다.");
        }
        // 기업id를 통해 공고 리스트를 찾아서 담는다.
        List<Posting> postingList = compService.회사별공고찾기(compId);
        // 찾은 리스트를 request에 담는다.
        request.setAttribute("postingList", postingList);
        // 담은 리스트를 화면에 보여준다.
        return "comp/postingList";
    }

    // 기업 공고 등록 페이지
    @GetMapping("/comp/posting/saveForm")
    public String saveForm(HttpServletRequest request) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면으로 이동시킴
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 기업회원이 아니면 예외처리
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }
        // 스킬 이름 전부를 리스트에 넣는다.
        List<Skill> skillList = skillService.스킬이름전부();
        // request에 리스트를 담는다.
        request.setAttribute("skillList", skillList);
        // 공고 등록 화면을 보여준다.
        return "comp/saveForm";
    }

    // 공고 상세보기 페이지
    @GetMapping("/comp/posting/{postingId}")
    public String detail(@PathVariable Integer postingId, HttpServletRequest request) {
        // 공고id로 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 찾은 공고를 request에 담는다.
        request.setAttribute("posting", posting);
        // 상세보기 화면을 보여준다.
        return "comp/postingDetail";
    }


    @GetMapping("/comp/company/{compId}")
    public String findComp(@PathVariable Integer compId, HttpServletRequest request) {

        User company = compService.기업찾기(compId);
        request.setAttribute("company", company);
        return "comp/compDetail";
    }


    // 공고관리 페이지에서 공고를 눌렀을때 새창으로 뜨는 페이지(JavaScript 사용)
    @GetMapping("/comp/posting/newWindow/{postingId}")
    public String detail2(@PathVariable Integer postingId, HttpServletRequest request) {
        // 공고id로 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 찾은 공고를 request에 담는다.
        request.setAttribute("posting", posting);
        // 상세보기 화면을 새창으로 보여준다. onclick="openNewWindow('/comp/posting/newWindow/{{id}}')"
        return "comp/postingDetailOnly";
    }
    
    // 공고관리 > 공고 수정 페이지
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
            throw new MyException("오류");
        }

        return "comp/updateForm";
    }

    // 공고관리 > 지원자 보기 페이지
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

    // 공고관리 > 입사 제안 보기
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
    }

    // 기업 인재찾기 페이지
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

    //
    @GetMapping("/resume/{resumeId}")
    public String resumeDetail(@PathVariable Integer resumeId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");

        Integer userId=0;
        if(sessionAllUser!=null){userId=sessionAllUser.getId();}
        Resume resume = resumeService.이력서찾기(resumeId, userId);

        request.setAttribute("resume", resume);
        return "comp/resumeDetail";
    }

    // 인재찾기 > 이력서 보기 페이지
    @GetMapping("/resume/newWindow/{resumeId}")
    public String resumeDetail2(@PathVariable Integer resumeId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
                
        Boolean boomarkBoolean = compService.북마크중복체크(sessionAllUser.getId(), resumeId);
        if(boomarkBoolean==true){
            request.setAttribute("boomarkOK", true); // 세션 있으면 중복 아닌거니까 북마크 해도됨
        }

        Resume resume = resumeService.이력서찾기(resumeId, sessionAllUser.getId());

        request.setAttribute("resume", resume);
        return "comp/resumeDetailOnly";
    }

    // 기업 회원가입 페이지
    @PostMapping("/comp/join")
    public String join(CompRequest.JoinDTO joinDTO) {
        compService.회원가입(joinDTO);
        return "redirect:/user/loginForm";
    }

    // 기업 공고 등록 POST
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

    // 기업 공고 수정 POST
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

    // 기업페이지 수정 POST
    @PostMapping("/comp/main/{compId}/update")
    public @ResponseBody String compUpdate(@PathVariable Integer compId, CompRequest.compUpdateDTO DTO) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            // return "redirect:/user/loginForm";
            return Script.href("/user/loginForm");
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
            // return Script.back("기업회원만 가능합니다.");
        }
        if(compId != sessionComp.getUserId()){
            throw new MyException("권한이 없습니다.");
            // return Script.back("권한이 없습니다.");
        }

        // compService.기업정보수정(compId, DTO);
        session.setAttribute("sessionComp", compService.기업정보수정(compId, DTO)); // 세션 수정한걸로 변경

        // return "redirect:/comp/main";
        return Script.href("/comp/main","정보 수정 완료");
    }

    // 기업 공고 삭제 POST
    @PostMapping("/comp/posting/{postingId}/delete")
    public @ResponseBody String delete(@PathVariable Integer postingId) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            // return "redirect:/user/loginForm";
            return Script.href("/user/loginForm"); // ResponseBody라 이거로
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

    // 공고관리 > 지원자보기 > 합격 POST 
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

    // 공고관리 > 지원자보기 > 불합격 POST
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

    // 공고관리 > 입사제안 > 제안 취소 POST
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

    // 공고관리 > 지원자보기 > 이력서 상세보기
    @GetMapping("/offer/newWindow/{resumeId}") // TODO : 예외처리
    public String offerDetail2(@PathVariable Integer resumeId, HttpServletRequest request) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if(sessionAllUser.getRole()!=2){
            throw new MyException("기업회원만 가능합니다.");
        }

        Resume resume = resumeService.이력서찾기(resumeId, sessionAllUser.getId());
        request.setAttribute("resume", resume);
        
        List<Posting> postingList = compService.회사별공고찾기(sessionComp.getUserId());
        request.setAttribute("postingList", postingList);

        return "comp/offerDetailOnly";
    }

    // 공고관리 > 지원자보기 > 이력서 상세보기 > 입사 제안 POST
    @PostMapping("/offer/save")
    public ResponseEntity<String> offerSave(@RequestParam(name = "selectPosting") Integer postingId , @RequestParam(name = "selectResume") Integer resumeId) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return ResponseEntity.badRequest().body("로그인안함");
        }
        if(sessionAllUser.getRole()!=2){
            return ResponseEntity.badRequest().body("기업유저아님");
        }

        if(postingId == null || resumeId == null){
            return ResponseEntity.badRequest().body("오류");
        }
        
        String recommendSaveTest = recommendService.입사제안하기(postingId, resumeId, sessionComp.getUserId());
        
        if(!(recommendSaveTest.equals("진행시켜"))){
            return ResponseEntity.badRequest().body(recommendSaveTest);
        }
        
        // return "redirect:/comp/posting/" + postingId + "/offerList";
        return ResponseEntity.ok("오퍼성공"); // 새창 열린거니까 끄게
    } //


    // 이메일(아이디) 중복체크
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
