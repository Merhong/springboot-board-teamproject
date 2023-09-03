package shop.mtcoding.boardproject.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.boardproject.skill.SkillService;

@Controller
public class MasterController {

    @Autowired
    private MasterService masterService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "all") List<String> skillList, @RequestParam(defaultValue = "all") String position, @RequestParam(defaultValue = "all") String region, HttpServletRequest request) {

        List<Skill> sl = skillService.스킬이름전부();
        request.setAttribute("skillList", sl);
        // 뷰에서 기술목록 뿌릴때 사용

        request.setAttribute("position", position);
        request.setAttribute("region", region);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(skillList);
            // System.out.println("테스트"+json);
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 뷰에 뭘 검색한건지 적혀있게
        
        List<Posting> postingList = masterService.메인화면검색(skillList, position, region);
        request.setAttribute("postingList", postingList);

        return "index";
    }

    @GetMapping("/master/help")
    public String qna() {
        return "/master/qna";
    }

    @GetMapping("/questionForm")
    public String question() {
        return "/master/questionForm";
    }
}