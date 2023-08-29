package shop.mtcoding.boardproject.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompController {

    @Autowired
    private CompRepository compRepository;

    @Autowired
    private CompService compService;


    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    // 4_기업회원가입 화면
    @GetMapping("/compJoinForm")
    public String compJoinForm() {
        return "comp/compJoinForm"; // view 파일 호출 comp/compJoinForm 파일 호출
    }
}
