package shop.mtcoding.boardproject.posting;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
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
public class PostingController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private CompService compService;

    @Autowired
    private HttpSession session;


    // 공고관리 > 지원자보기 > 이력서 상세보기 > 입사 제안 POST
    @PostMapping("/offer/save")
    public ResponseEntity<String> offerSave(@RequestParam(name = "selectPosting") Integer postingId, @RequestParam(name = "selectResume") Integer resumeId) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return ResponseEntity.badRequest().body("로그인안함");
        }
        if (sessionAllUser.getRole() != 2) {
            return ResponseEntity.badRequest().body("기업유저아님");
        }

        if (postingId == null || resumeId == null) {
            return ResponseEntity.badRequest().body("오류");
        }

        String recommendSaveTest = recommendService.입사제안하기(postingId, resumeId, sessionComp.getUserId());

        if (!(recommendSaveTest.equals("진행시켜"))) {
            return ResponseEntity.badRequest().body(recommendSaveTest);
        }

        // return "redirect:/comp/posting/" + postingId + "/offerList";
        return ResponseEntity.ok("오퍼성공"); // 새창 열린거니까 끄게
    } //


    // 공고관리 > 지원자보기 > 이력서 상세보기
    @GetMapping("/offer/newWindow/{resumeId}") // TODO : 예외처리
    public String offerDetail2(@PathVariable Integer resumeId, HttpServletRequest request) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        Resume resume = resumeService.이력서찾기(resumeId, sessionAllUser.getId());
        request.setAttribute("resume", resume);

        List<Posting> postingList = compService.회사별공고찾기(sessionComp.getUserId());
        request.setAttribute("postingList", postingList);

        return "comp/offer/detailOnly";
    }

    // 공고관리 페이지에서 공고를 눌렀을때 새창으로 뜨는 페이지(JavaScript 사용)
    @GetMapping("/comp/posting/newWindow/{postingId}")
    public String detail2(@PathVariable Integer postingId, HttpServletRequest request) {
        // 공고id로 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 찾은 공고를 request에 담는다.
        request.setAttribute("posting", posting);
        // 상세보기 화면을 새창으로 보여준다. onclick="openNewWindow('/comp/posting/newWindow/{{id}}')"
        return "comp/posting/detailOnly";
    }

    // 공고관리 > 입사 제안 보기
    @GetMapping("/comp/posting/{postingId}/offer/list")
    public String offerList(@PathVariable Integer postingId, HttpServletRequest request) {
        // sessionAllUser(모든 유저가 가지고 있는 속성)을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 기업유저 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 세션유저id와 공고를 쓴 유저id 비교
        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }
        // request에 공고를 담는다.
        request.setAttribute("posting", posting);
        // 해당 공고에 제안한 목록을 리스트에 담는다.
        List<Recommend> recommendList = compService.공고로제안한목록찾기(postingId);
        // request에 제안 목록을 담는다.
        request.setAttribute("recommendList", recommendList);

        // 입사 제안 목록 페이지를 보여준다.
        return "comp/offer/list";
    }

    // 공고관리 > 공고 수정 페이지
    @GetMapping("/comp/posting/{postingId}/updateForm")
    public String updateForm(@PathVariable Integer postingId, HttpServletRequest request) {
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 스킬 전부를 리스트에 담는다.
        List<Skill> skillList = skillService.스킬이름전부();
        // request에 스킬리스트를 담는다.
        request.setAttribute("skillList", skillList);
        // 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 세션유저id와 공고쓴 유저id 비교
        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }
        // request에 공고를 담는다.
        request.setAttribute("posting", posting);
        // 매핑
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 공고를 json으로 매핑
            String json = objectMapper.writeValueAsString(posting);
            // request에 json으로 변환한 공고를 담는다.
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            throw new MyException("오류");
        }
        // 공고 수정 페이지를 보여준다.
        return "comp/posting/updateForm";
    }

    // 공고관리 > 지원자 보기 페이지
    @GetMapping("/comp/posting/{postingId}/resume/list")
    public String resumeList(@PathVariable Integer postingId, HttpServletRequest request) {
        // sessionAllUser(모든 유저가 가지고 있는 속성)을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 기업유저 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 공고 지원(Apply)을 찾아서 리스트에 담는다.
        List<Apply> applyList = applyService.공고지원현황(postingId);
        // 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 세션유저id와 공고를 쓴 유저id를 비교한다.
        if (sessionComp.getUserId() != posting.getUser().getId()) {
            throw new MyException("내 공고가 아닙니다.");
        }
        // request에 공고를 담는다.
        request.setAttribute("posting", posting);
        // 공고id로 해당 공고에 apply한 이력서를 찾아서 리스트에 담는다.
        List<Apply> resumeList = compService.공고지원신청찾기(postingId);
        // request에 지원 리스트를 담는다.
        request.setAttribute("applyList", applyList);
        // request에 이력서 리스트를 담는다.
        request.setAttribute("resumeList", resumeList);

        // 지원자 내역 페이지를 보여준다.
        return "comp/resume/list";
    }

    // 기업 공고 등록 페이지
    @GetMapping("/comp/posting/saveForm")
    public String postingSaveForm(HttpServletRequest request) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면으로 이동시킴
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 기업회원이 아니면 예외처리
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 스킬 이름 전부를 리스트에 넣는다.
        List<Skill> skillList = skillService.스킬이름전부();
        // request에 리스트를 담는다.
        request.setAttribute("skillList", skillList);
        // 공고 등록 화면을 보여준다.
        return "comp/posting/saveForm";
    }

    // 공고 상세보기 페이지
    @GetMapping("/comp/posting/{postingId}")
    public String detail(@PathVariable Integer postingId, HttpServletRequest request) {
        // 공고id로 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 찾은 공고를 request에 담는다.
        request.setAttribute("posting", posting);
        // 상세보기 화면을 보여준다.
        return "comp/posting/detail";
    }

    // 기업 공고 관리 페이지
    @GetMapping("/comp/{compId}/posting/list")
    public String listView(@PathVariable Integer compId, HttpServletRequest request) {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션 없으면 로그인 화면으로 이동
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 기업회원이 아니면 예외처리
        if (sessionAllUser.getRole() != 2) {
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
        return "comp/posting/list";
    }

    // 기업 공고 등록 POST
    @PostMapping("/comp/posting/save")
    public String postingSave(CompRequest.SaveDTO saveDTO) {
        // 유저세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 기업회원 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 기업회원id로 공고를 작성한다.
        compService.공고작성(saveDTO, sessionComp.getUserId());

        // 해당 기업유저의 공고목록을 리다이렉트한다.
        return "redirect:/comp/" + sessionComp.getUserId() + "/posting/list";
    }

    // 기업 공고 수정 POST
    @PostMapping("/comp/posting/{postingId}/update")
    public String postingUpdate(@PathVariable Integer postingId, CompRequest.UpdateDTO updateDTO) {
        // 유저세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 기업유저 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 기업유저의 id를 찾는다.
        int compId = sessionComp.getUserId();
        // 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 공고가 있는지 확인한다.
        if (posting == null) {
            throw new MyException("없는 공고 입니다.");
        }
        // 기업id와 공고를 쓴 유저id 비교
        if (compId != posting.getUser().getId()) {
            throw new MyException("권한이 없습니다.");
        }
        // 공고를 수정한다.
        compService.공고수정(postingId, updateDTO);

        // 해당 기업유저의 공고 목록으로 리다이렉트
        return "redirect:/comp/" + compId + "/posting/list";

    }

    // 기업 공고 삭제 POST
    @PostMapping("/comp/posting/{postingId}/delete")
    public @ResponseBody String delete(@PathVariable Integer postingId) {
        // 유저 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 기업유저 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 유저 세션이 없다면 로그인 페이지로 이동
        if (sessionAllUser == null) {
            // return "redirect:/user/loginForm";
            return Script.href("/user/loginForm"); // ResponseBody라 이거로
        }
        // 기업유저가 아니면 예외처리
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 기업유저의 id를 찾는다.
        int compId = sessionComp.getUserId();
        // 공고를 찾는다.
        Posting posting = compService.공고찾기(postingId);
        // 공고가 없다면 예외처리
        if (posting == null) {
            throw new MyException("없는 공고 입니다.");
        }
        // 유저id와 공고를 쓴 유저 id를 비교해서 삭제
        if (compId == posting.getUser().getId()) {
            compService.공고삭제(postingId);
            return Script.href("/comp/" + compId + "/posting/list", "삭제 완료");
        }

        throw new MyException("권한이 없습니다.");
    }

    // 공고관리 > 지원자보기 > 합격 POST
    @PostMapping("/comp/posting/apply/{applyId}/pass")
    public String applyPass(@PathVariable Integer applyId, String redirectWhere) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        Apply apply = applyService.공고지원합격(applyId, sessionComp.getUserId());

        if (redirectWhere != null) {
            return "redirect:/comp/" + sessionComp.getUserId() + "/resume/listCompReceived";
        }

        return "redirect:/comp/posting/" + apply.getPosting().getId() + "/resume/list";
    }

    // 공고관리 > 지원자보기 > 불합격 POST
    @PostMapping("/comp/posting/apply/{applyId}/fail")
    public String applyFail(@PathVariable Integer applyId, String redirectWhere) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        Apply apply = applyService.공고지원불합(applyId, sessionComp.getUserId());

        if (redirectWhere != null) {
            return "redirect:/comp/" + sessionComp.getUserId() + "/resume/listCompReceived";
        }

        return "redirect:/comp/posting/" + apply.getPosting().getId() + "/resume/list";
    }

    // 공고관리 > 입사제안 > 제안 취소 POST
    @PostMapping("/comp/posting/offer/{recommendId}/cancel")
    public String recommendCancel(@PathVariable Integer recommendId, String redirectWhere) {

        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");

        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }

        Integer postingId = recommendService.입사제안취소(recommendId, sessionComp.getUserId());

        if (redirectWhere.equals("offerListCompSent")) {
            return "redirect:/comp/" + sessionComp.getUserId() + "/offer/listCompSent";
        }
        return "redirect:/comp/posting/" + postingId + "/offer/list";
    }

}
