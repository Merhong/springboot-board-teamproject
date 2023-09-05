package shop.mtcoding.boardproject.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeRepository;

import java.util.Optional;

@Service
public class RecommendService {

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private ResumeRepository resumeRepository;


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
    
    @Transactional
    public Boolean 입사제안하기(Integer postingId, Integer resumeId, Integer compId) {
        
        Recommend recommend = new Recommend();
        recommend.setStatement("대기");
        
        Posting posting;
        Optional<Posting> postingOP = postingRepository.findById(postingId);
        if (postingOP.isPresent()){
            posting = postingOP.get();
            if(posting.getUser().getId() != compId){
                return false;
            }
        } else{
            return false;
        }
        recommend.setPosting(posting);
        
        Resume resume;
        Optional<Resume> resumeOP = resumeRepository.findById(resumeId);
        if (resumeOP.isPresent()){
            resume = resumeOP.get();
        } else{
            return false;
        }
        recommend.setResume(resume);
        
        recommendRepository.save(recommend);
        
        return true;
    }




}
