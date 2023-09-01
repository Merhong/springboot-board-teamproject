package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.resume.ResumeRepository;

import java.util.List;

@Service
public class ApplyService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ApplyRepository applyRepository;

    public List<Apply> 유저지원내역전체(Integer id) {
        List<Apply> applyList = applyRepository.findApplyResumeByUserId(id);
        return applyList;
    }

    public List<Apply> 공고지원현황(Integer id) {
        List<Apply> applyList = applyRepository.findApplyPostingByPositngId(id);
        return applyList;
    }

}
