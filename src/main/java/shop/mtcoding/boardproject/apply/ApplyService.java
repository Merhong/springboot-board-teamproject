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

        // 각 Apply 엔티티의 posting 필드에 접근하여 title을 가져오고자 할 때
        for (Apply apply : applyList) {
            String postingTite = apply.getPosting().getTitle();
            // 이제 postingTitle 변수에 해당 Apply의 Posting title이 들어있습니다.
        }
        return applyList;
    }

    public List<Apply> 공고지원현황(Integer id) {
        List<Apply> applyList = applyRepository.findApplyPostingByPositngId(id);
        return applyList;
    }

    public void 지원(Apply apply) {
        applyRepository.save(apply);
    }

}
