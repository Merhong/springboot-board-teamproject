package shop.mtcoding.boardproject.master;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.skill.SkillService;
import shop.mtcoding.boardproject.user.CompService;

@Controller
public class MasterController {

    @Autowired
    private CompService compService;
    @Autowired
    private SkillService skillService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Posting> postingList = compService.모든공고찾기();
        List<Skill> skillList = skillService.모든스킬찾기();
        request.setAttribute("skillList", skillList);
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