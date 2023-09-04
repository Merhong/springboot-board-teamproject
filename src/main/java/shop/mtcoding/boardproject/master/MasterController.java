package shop.mtcoding.boardproject.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.master.MasterResponse.MasterListDTO;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MasterController {

    @Autowired
    private CompService compService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private MasterService masterService;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Skill> skillList = skillRepository.findAll();
        request.setAttribute("skillList", skillList);
        List<Posting> postingList = compService.모든공고찾기();
        request.setAttribute("postingList", postingList);
        return "index";
    }

    @GetMapping("/master/help")
    public String qna(HttpServletRequest request) {
        return "/master/qna";
    }

    @GetMapping("/question/Form")
    public String questionForm(HttpServletRequest request) {
        User user = null;
        System.out.println(session.getAttribute("sessionUser"));
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
        }
        if (session.getAttribute("sessionAdmin") != null) {
            user = (User) session.getAttribute("sessionAdmin");
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
        }
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }
        request.setAttribute("user", user);
        return "/master/questionForm";
    }

    @PostMapping("/question/save")
    public String questionSave(MasterRequest.MasterDTO masterDTO) {

        User user = null;
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
        }
        if (session.getAttribute("sessionAdmin") != null) {
            user = (User) session.getAttribute("sessionAdmin");
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
        }
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }
        masterService.문의등록(masterDTO, user);
        return "redirect:/question/List";
    }

    @GetMapping("/question/List")
    public String questionList(HttpServletRequest request) {

        List<Master> masterList = new ArrayList<>();
        User user = null;
        if (session.getAttribute("sessionAdmin") != null) {
            masterList = masterService.모든문의찾기();
        }
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
            masterList = masterService.유저로문의찾기(user.getId());
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
            masterList = masterService.유저로문의찾기(user.getId());
        }
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }
        List<MasterListDTO> DTOList = new ArrayList<MasterListDTO>();
        for (Master master : masterList) {
            MasterListDTO newDTO = new MasterListDTO();
            newDTO.setId(master.getId());
            newDTO.setTitle(master.getTitle());
            DTOList.add(newDTO);
        }

        request.setAttribute("DTOList", DTOList);
        return "/master/questionList";
    }

    @GetMapping("/question/{id}")
    public String questionForm(@PathVariable Integer id, HttpServletRequest request) {
        Master master = masterService.문의넘버로찾기(id);
        request.setAttribute("master", master);
        return "/master/question";
    }

}