package shop.mtcoding.boardproject.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;

@Controller
public class CompController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CompService compService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private HttpSession session;

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/comp/joinForm")
    public String compJoinForm() {
        return "comp/joinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }

    @GetMapping("/comp/test")
    public String test() {
        return "comp/test";
    }

    @GetMapping("/comp/main")
    public String Main() {
        return "comp/main";
    }

    @GetMapping("/comp/{compId}/postingList")
    public String listView(@PathVariable Integer compId, HttpServletRequest request) {
        List<Posting> postingList = compService.회사별공고찾기(compId);
        request.setAttribute("postingList", postingList);
        return "comp/postingList";
    }

    @GetMapping("/comp/posting/saveForm")
    public String saveForm(HttpServletRequest request) {
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


    // @ResponseBody
    // @GetMapping("/comp/posting/check")
    // public ResponseEntity<String> check(int postingId){
    //     Posting posting = compService.공고찾기(postingId);
    //     if (posting != null) {
    //         return posting;
    //     }
    // }

    @GetMapping("/comp/posting/{postingId}/updateForm")
    public String updateForm(@PathVariable Integer postingId, HttpServletRequest request) {
        List<Skill> skillList = skillService.스킬이름전부();
        request.setAttribute("skillList", skillList);
        
        Posting posting = compService.공고찾기(postingId);
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
        Posting posting = compService.공고찾기(postingId);
        request.setAttribute("posting", posting);
        
        List<Resume> resumeList = compService.공고에지원한이력서찾기(postingId);

        // System.out.println("테스트:" +resumeList.get(0).getTitle());



        request.setAttribute("resumeList", resumeList);

        return "comp/resumeList";
    }

    @GetMapping("/comp/recommend")
    public String recommend(HttpServletRequest request) {
        List<Skill> skillList = skillService.스킬이름전부();
        request.setAttribute("skillList", skillList);
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
        // System.out.println("테스트saveDTO:"+saveDTO);
        compService.공고작성(saveDTO);
        int id = ((CompRequest.SessionCompDTO)session.getAttribute("sessionComp")).getUserId();
        return "redirect:/comp/"+id+"/postingList";
    }
    
    @PostMapping("/comp/posting/{postingId}/update")
    public String postingUpdate(@PathVariable Integer postingId, CompRequest.UpdateDTO updateDTO) {
        // System.out.println("테스트updateDTO:"+updateDTO);
        compService.공고수정(postingId, updateDTO);
        int id = ((CompRequest.SessionCompDTO)session.getAttribute("sessionComp")).getUserId();
        return "redirect:/comp/"+id+"/postingList";
    }

    @PostMapping("/comp/main/{userId}/update")
    public @ResponseBody String compUpdate(@PathVariable Integer userId, CompRequest.compUpdateDTO DTO) {
        compService.기업정보수정(userId, DTO);
        return Script.href("/comp/main","정보 수정 완료");
    }

    @PostMapping("/comp/posting/{postingId}/delete")
    public @ResponseBody String delete(@PathVariable Integer postingId){
        int userId = ((CompRequest.SessionCompDTO)session.getAttribute("sessionComp")).getUserId();
        compService.공고삭제(postingId);
        return Script.href("/comp/"+userId+"/postingList","삭제 완료");
    }





    // @GetMapping("/comp/test2")
    // public String compTest2() {
    //     compService.테스트2("Java");
    //     return "comp/main";
    // }


}
