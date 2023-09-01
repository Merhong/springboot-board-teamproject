package shop.mtcoding.boardproject.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject.user.CompRequest.JoinDTO;
import shop.mtcoding.boardproject.user.CompRequest.SaveDTO;
import shop.mtcoding.boardproject.user.CompRequest.UpdateDTO;
import shop.mtcoding.boardproject.user.CompRequest.compUpdateDTO;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.PostingSkillRepository;

@Service
public class CompService {

    @Autowired
    private PostingSkillRepository postingSkillRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        User user = User.builder()
        .role(joinDTO.getRole())
        .email(joinDTO.getEmail())
        .password(joinDTO.getPassword())
        .compname(joinDTO.getCompname())
        .compRegister(joinDTO.getCompRegister())
        .tel(joinDTO.getTel())
        .address(joinDTO.getAddress())
        .build();
        userRepository.save(user);

    }

    public List<Posting> 회사별공고찾기(Integer compId) {
        List<Posting> postingList = postingRepository.findByUserId(compId);
        return postingList;
    }

    @Transactional
    public void 공고작성(SaveDTO saveDTO) {
        Posting posting = new Posting();
        posting.setUser(new User());
        posting.getUser().setId(
            ((CompRequest.SessionCompDTO) session.getAttribute("sessionComp")).getUserId()
        );
        posting.setTitle(saveDTO.getTitle());
        posting.setDesc(saveDTO.getDesc());
        posting.setPosition(saveDTO.getPosition());
        posting.setRegion(saveDTO.getRegion());
        posting.setCareer(saveDTO.getCareer());
        posting.setEducation(saveDTO.getEducation());
        
        Timestamp timestamp = new Timestamp(saveDTO.getExpiryDate().getTime());
        posting.setExpiryDate(timestamp);

        // System.out.println("테스트 : " + saveDTO.getPostingSkill());

        List<PostingSkill> psList = new ArrayList<>();
        
        List<String> psStringList = saveDTO.getPostingSkill();

        postingRepository.save(posting);

        int get=0;
        while(psStringList.size()>0){
            psList.add(new PostingSkill());
            psList.get(get).setPosting(posting);
            psList.get(get++).setSkill(psStringList.get(0));
            psStringList.remove(0);
        }
        // System.out.println("테스트11:"+psList);

        // posting.setPostingSkill(psList);

        // System.out.println("테스트12"+posting.getId());
        for (PostingSkill skill : psList) {
            skill.getPosting().setId(posting.getId());
            postingSkillRepository.save(skill);
        }

        // System.out.println(psList);

    }

    public Posting 공고찾기(Integer postingId) {
        Optional<Posting> postingOP = postingRepository.findById(postingId);
        if (postingOP.isPresent()) {
            return postingOP.get();
        } else {
            throw new MyException(postingId+" 없음");
        }
    }

    public List<Posting> 모든공고찾기() {
        List<Posting> postingList = postingRepository.findAll();
        return postingList;
    }

    @Transactional
    public void 공고수정(Integer postingId, UpdateDTO updateDTO) {
        Optional<Posting> postingOP = postingRepository.findById(postingId);

        if (postingOP.isPresent()) {
            Posting posting = postingOP.get();
            posting.setTitle(updateDTO.getTitle());
            posting.setDesc(updateDTO.getDesc());
            posting.setPosition(updateDTO.getPosition());
            posting.setRegion(updateDTO.getRegion());
            posting.setCareer(updateDTO.getCareer());
            posting.setEducation(updateDTO.getEducation());
            Timestamp timestamp = new Timestamp(updateDTO.getExpiryDate().getTime());
            posting.setExpiryDate(timestamp);

            // List<PostingSkill> PostingSkillList = updateDTO.getPostingSkill();

            List<PostingSkill> psList = new ArrayList<>();

            List<String> psStringList = updateDTO.getPostingSkill();

            postingSkillRepository.deleteByPostingId(postingId);

            int get=0;
            while(psStringList.size()>0){
                psList.add(new PostingSkill());
                psList.get(get).setPosting(posting);
                psList.get(get++).setSkill(psStringList.get(0));
                psStringList.remove(0);
            }

            for (PostingSkill skill : psList) {
                skill.getPosting().setId(posting.getId());
                postingSkillRepository.save(skill);
            }

        } else{
            throw new MyException(postingId + "공고없음");
        }
    }


    public void 기업정보수정(Integer userId, compUpdateDTO dTO) {



    }

}
