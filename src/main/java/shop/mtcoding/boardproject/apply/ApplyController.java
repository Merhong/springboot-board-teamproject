package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ApplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ApplyService applyservice;

    // 15_개인지원내역 화면
    @GetMapping("/user/applyList")

    public String userApplyList(HttpServletRequest request) {
        User user = (User) session.getAttribute("sessionUser");
        List<Apply> applyList = applyservice.유저지원내역전체(user.getId());
        request.setAttribute("applyList", applyList);

        return "user/applyList";
    }

}
