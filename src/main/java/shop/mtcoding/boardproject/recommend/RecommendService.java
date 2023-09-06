package shop.mtcoding.boardproject.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeRepository;

import java.util.List;
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
    public String 입사제안하기(Integer postingId, Integer resumeId, Integer compId) {

        Optional<Resume> resumeOP =  resumeRepository.findById(resumeId);
        Resume resume;
        if (resumeOP.isPresent()){
            resume = resumeOP.get();
        } else{
            return resumeId+"없음";
        }
        if(resume.getDisclosure()==false){
            return "비공개이력서";
        }

        List<Recommend> tempRecommendList = recommendRepository.findByPostingIdAndResumeUserId(postingId, resume.getUser().getId());
        if(tempRecommendList.size() != 0){
            return "이미똑같은유저에게오퍼보냄";
        }

        // if(recommendRepository.findByPostingIdAndResumeId(postingId, resumeId) != null){
        //     return "이미오퍼보냄";
        // }

        Recommend recommend = new Recommend();
        recommend.setStatement("대기");
        
        Posting posting;
        Optional<Posting> postingOP = postingRepository.findById(postingId);
        if (postingOP.isPresent()){
            posting = postingOP.get();
            if(posting.getUser().getId() != compId){
                return "내공고아님";
            }
        } else{
            return postingId+"없음";
        }
        recommend.setPosting(posting);
        recommend.setResume(resume);
        
        recommendRepository.save(recommend);
        
        return "진행시켜";
    }

    public List<Recommend> 이력서받은오퍼찾기(Integer resumeId) {
        List<Recommend> recommendList = recommendRepository.findByResumeId(resumeId);
        return recommendList;
    }

    @Transactional
    public Integer 오퍼수락(Integer recommendId, Integer userId) {
        Optional<Recommend> recommendOP = recommendRepository.findById(recommendId);
        if (recommendOP.isPresent()) {
          
            Recommend recommend = recommendOP.get();

            if(recommend.getResume().getUser().getId() != userId){
                throw new MyException("권한이 없습니다.");
            }
            if(!(recommend.getStatement().equals("대기"))){
                throw new MyException("이미 답변했습니다.");
            }

            recommend.setStatement("수락");
            return recommend.getResume().getId();
        } else {
            throw new MyException(recommendId + "없음");
        }
    }

    @Transactional
    public Integer 오퍼거절(Integer recommendId, Integer userId) {
        Optional<Recommend> recommendOP = recommendRepository.findById(recommendId);
        if (recommendOP.isPresent()) {
          
            Recommend recommend = recommendOP.get();

            if(recommend.getResume().getUser().getId() != userId){
                throw new MyException("권한이 없습니다.");
            }
            if(!(recommend.getStatement().equals("대기"))){
                throw new MyException("이미 답변했습니다.");
            }

            recommend.setStatement("거절");
            return recommend.getResume().getId();
        } else {
            throw new MyException(recommendId + "없음");
        }
    }

}
