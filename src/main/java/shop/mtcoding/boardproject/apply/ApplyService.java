package shop.mtcoding.boardproject.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.boardproject._core.error.ex.MyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplyService {

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

            if (apply.getPosting().getUser().getId() != compId) {
                throw new MyException("권한이 없습니다.");
            }
            if (!(apply.getStatement().equals("대기"))) {
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

            if (apply.getPosting().getUser().getId() != compId) {
                throw new MyException("권한이 없습니다.");
            }
            if (!(apply.getStatement().equals("대기"))) {
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

    public List<ApplyResponse.ApplyCompDTO> 기업별모든지원찾기(Integer compId) {

        List<Object[]> applyList = applyRepository.findByCompId(compId);

        List<ApplyResponse.ApplyCompDTO> applyCompDTOList = new ArrayList<>();

        for (Object[] objects : applyList) {
            ApplyResponse.ApplyCompDTO applyCompDTO = new ApplyResponse.ApplyCompDTO();
            applyCompDTO.setApplyId((Integer) objects[0]);
            applyCompDTO.setPostingId((Integer) objects[1]);
            applyCompDTO.setResumeId((Integer) objects[2]);
            applyCompDTO.setPostingTitle((String) objects[3]);
            applyCompDTO.setResumeTitle((String) objects[4]);
            applyCompDTO.setResumeCareer((String) objects[5]);
            applyCompDTO.setResumeGrade((String) objects[6]);
            applyCompDTO.setUsername((String) objects[7]);
            applyCompDTO.setStatement((String) objects[8]);
            applyCompDTOList.add(applyCompDTO);
        }

        return applyCompDTOList;
    }
}
