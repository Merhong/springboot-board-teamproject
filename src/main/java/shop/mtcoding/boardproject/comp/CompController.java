package shop.mtcoding.boardproject.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CompController {

    @Autowired
    private CompRepository compRepository;

    @Autowired
    private CompService compService;

}
