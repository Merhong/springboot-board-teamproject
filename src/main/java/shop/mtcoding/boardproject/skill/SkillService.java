package shop.mtcoding.boardproject.skill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;


    public List<Skill> 스킬이름전부() {
        return skillRepository.findAll();
    }

}
