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

        for (Apply apply : applyList) {
            if (apply.getStatement() == null) {
                // statement가 null인 경우 대기 상태로 설정
                apply.setStatement("대기 상태");
            }

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
