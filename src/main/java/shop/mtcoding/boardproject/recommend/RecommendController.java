package shop.mtcoding.boardproject.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRequest;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecommendController {

    /* DI */
    @Autowired
    private UserService userService;

    @Autowired
    private CompService compService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private HttpSession session;


    // 기업 인재찾기 페이지
    @GetMapping("/comp/recommend/form")
    public String recommendForm(@RequestParam(defaultValue = "all") List<String> skillList, @RequestParam(defaultValue = "all") String position, HttpServletRequest request) {
        // sessionAllUser(모든 유저가 가지고 있는 속성)을 찾는다.
        User sessionAllUser = (User) session.getAttribute("sessionAllUser");
        if (sessionAllUser == null) {
            return "redirect:/user/loginForm";
        }
        if (sessionAllUser.getRole() != 2) {
            throw new MyException("기업회원만 가능합니다.");
        }
        // 스킬리스트를 찾는다.
        List<Skill> sl = skillService.스킬이름전부();
        // request에 스킬리스트를 담는다.
        request.setAttribute("skillList", sl);
        // request에 변수로 받아온 직무를 담는다.
        request.setAttribute("position", position);

        // 매핑
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 스킬리스트를 json으로 변환
            String json = objectMapper.writeValueAsString(skillList);
            // 변환한 json을 request에 담는다.
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 스킬리스트와 직무를 가지고 조건에 만족하는 유저들을 찾아서 리스트에 담는다.
        List<User> userList = compService.인재추천검색(skillList, position);
        // request에 유저리스트를 담는다.
        request.setAttribute("userList", userList);

        // 인재찾기 페이지를 보여준다.
        return "comp/recommend/form";
    }


    // 17_개인기업추천 화면
    @GetMapping("/user/recommend/form")
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
        List<PostingRequest.CompInfoDTO> compInfoList = new ArrayList<>();
        for (Posting posting : compList) {
            PostingRequest.CompInfoDTO compInfoDTO = new PostingRequest.CompInfoDTO();
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

        return "user/recommend/form";
    }
}
