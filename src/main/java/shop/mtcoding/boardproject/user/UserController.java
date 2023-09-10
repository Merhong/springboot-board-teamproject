package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject.comp.CompRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    /* DI */
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    // 12번 수정하기 버튼 POST
    @PostMapping("/user/update")
    public String userUpdate(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        return "redirect:/user/updateForm";
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

    // 개인회원가입 화면
    @GetMapping("/user/joinForm")
    public String userJoinForm() {
        return "user/joinForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 회원가입 버튼 POST
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "user/loginForm";
    }

    // 회원가입유형선택 화면
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

    @PostMapping("/api/user/ismessage")
    public String messageCheckOut(@RequestBody UserRequest.CheckOutDTO checkOutDTO) {
        System.out.println("메시지 삭제 컨트롤러 실행");
        Integer sucsess1 = userService.받은메시지조회변경(false, checkOutDTO.getUserId());
        Integer sucsess2 = userService.지원상태변경(false, checkOutDTO.getUsername());
        System.out.println("checkOutDTO userId" + checkOutDTO.getUserId());
        System.out.println("checkOutDTO getUsername" + checkOutDTO.getUsername());
        User user = userService.회원조회(checkOutDTO.getUserId());

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

        if (sucsess1 == 1 || sucsess2 == 1) {
            System.out.println("메시지 삭제 성공");
        } else {
            System.out.println("메시지 삭제 실패");
        }
        return "redirect:/";
    }

    @PostMapping("/api/user/changeState")
    public String changeState(@RequestBody UserRequest.StateDTO stateDTO) {
        System.out.println("changeState username 컨트롤러 때려짐");
        String username = stateDTO.getUsername();
        userService.지원상태변경(true, username);
        System.out.println("username : " + username);
        return "redirect:/user/offer/list";
    }
}
