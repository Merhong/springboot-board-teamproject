package shop.mtcoding.boardproject.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;

import java.util.Optional;

@Service
public class RecommendService {

    @Autowired
    private RecommendRepository recommendRepository;


    @Transactional
    public Integer 입사제안취소(Integer recommendId, Integer compId) {
        Optional<Recommend> recommendOP = recommendRepository.findById(recommendId);
        
        if (recommendOP.isPresent()){
            Recommend recommend = recommendOP.get();

            if(recommend.getPosting().getUser().getId() != compId){
                throw new MyException("권한이 없습니다.");
            }
            if(!(recommend.getStatement().equals("대기"))){
                throw new MyException("이미 답변했습니다.");
            }

            int postingId = recommend.getPosting().getId();

            recommendRepository.deleteById(recommendId);
            
            return postingId; // 리다이렉트용
        } else{
            throw new MyException(recommendId + "없음");
        }
    }





}
