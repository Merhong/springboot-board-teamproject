package shop.mtcoding.boardproject.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.recommend.RecommendResponse;
import shop.mtcoding.boardproject.recommend.RecommendService;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompController {

    /* DI */
    @Autowired
    private CompService compService;

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
    @GetMapping("/comp/updateForm")
    public String updateForm() {
        // 세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 세션이 없다면 로그인 화면으로 이동시킴
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        // 회원유형(role)이 2일때만 통과
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 페이지를 보여준다.
        return "comp/updateForm";
    }


    // 기업 상세보기 페이지
    @GetMapping("/comp/company/{compId}")
    public String findComp(@PathVariable Integer compId, HttpServletRequest request) {
        // 유저id로 기업을 찾는다.
        User company = compService.기업찾기(compId);
        // 찾은 기업을 request에 담는다.
        request.setAttribute("company", company);
        // 기업페이지 화면을 보여준다.
        return "comp/detail";
    }


    // 기업 회원가입 페이지
    @PostMapping("/comp/join")
    public String join(CompRequest.JoinDTO joinDTO) {
        compService.회원가입(joinDTO);
        return "redirect:/user/loginForm";
    }


    // 기업페이지 수정 POST
    @PostMapping("/comp/updateForm/{compId}/update")
    public @ResponseBody String compUpdate(@PathVariable Integer compId, CompRequest.compUpdateDTO DTO) {
        // 유저세션을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        // 기업유저 세션을 찾는다.
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        // 세션이 없다면 로그인 페이지로 이동시킴
        if (sessionAllUser == null) {
            // return "redirect:/user/loginForm";
            return Script.href("/user/loginForm");
        }
        // 기업유저가 아니라면 예외처리
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
            // return Script.back("기업회원만 가능합니다.");
        }
        // 유저id와 기업세션id를 비교한다.
        if (compId != sessionComp.getUserId()) {
            throw new MyException("권한이 없습니다.");
            // return Script.back("권한이 없습니다.");
        }

        // compService.기업정보수정(compId, DTO);
        // 세션 수정한걸로 변경
        session.setAttribute("sessionComp", compService.기업정보수정(compId, DTO));

        // 메인페이지로 리다이렉트
        // return "redirect:/comp/main";
        return Script.href("/comp/updateForm", "정보 수정 완료");
    }


    // 기업이 제안한 오퍼 목록
    @GetMapping("/comp/{compId}/offer/listCompSent")
    public String offerListCompSent(@PathVariable Integer compId, HttpServletRequest request) {
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

        List<RecommendResponse.RecommendCompDTO> recommendCompDTOList = recommendService.기업별모든오퍼찾기(compId);
        request.setAttribute("recommendCompDTOList", recommendCompDTOList);

        return "comp/offer/listCompSent";
    }


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
