package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ApplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ApplyRepository applyRepository;

    // 15_개인지원내역 화면
    @GetMapping("/user/applyList")

    public String userApplyList(HttpServletRequest request) {
        List<Apply> applyList = applyRepository.findApplyByUserId(2);
        request.setAttribute("applyList", applyList);

        return "user/applyList";
    }

}
