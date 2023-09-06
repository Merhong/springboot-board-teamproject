package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.resume.ResumeRepository;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Apply 공고지원합격(Integer applyId, Integer compId) {
        Optional<Apply> applyOP = applyRepository.findById(applyId);
        if (applyOP.isPresent()) {
          
            Apply apply = applyOP.get();

            if(apply.getPosting().getUser().getId() != compId){
                throw new MyException("권한이 없습니다.");
            }
            if(!(apply.getStatement().equals("대기"))){
                throw new MyException("이미 답변했습니다.");
            }

            apply.setStatement("합격");
            return apply;
        } else {
            throw new MyException(applyId + "없음");
        }
    }

    @Transactional
    public Apply 공고지원불합(Integer applyId, Integer compId) {
        Optional<Apply> applyOP = applyRepository.findById(applyId);
        if (applyOP.isPresent()) {
            Apply apply = applyOP.get();

            if(apply.getPosting().getUser().getId() != compId){
                throw new MyException("권한이 없습니다.");
            }
            if(!(apply.getStatement().equals("대기"))){
                throw new MyException("이미 답변했습니다.");
            }

            apply.setStatement("불합");
            return apply;
        } else {
            throw new MyException(applyId + "없음");
        }
    }

    public void 지원(Apply apply) {
        applyRepository.save(apply);
    }
}
