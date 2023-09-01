package shop.mtcoding.boardproject.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.Image;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.PostingSkillRepository;
import shop.mtcoding.boardproject.user.CompRequest.JoinDTO;
import shop.mtcoding.boardproject.user.CompRequest.SaveDTO;
import shop.mtcoding.boardproject.user.CompRequest.UpdateDTO;
import shop.mtcoding.boardproject.user.CompRequest.compUpdateDTO;

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
        
        String fileName = Image.updateImage(joinDTO);

        User user = User.builder()
        .role(joinDTO.getRole())
        .photo(fileName)
        .email(joinDTO.getEmail())
        .password(joinDTO.getPassword())
        .compname(joinDTO.getCompname())
        .compRegister(joinDTO.getCompRegister())
        .tel(joinDTO.getTel())
        .homepage(joinDTO.getHomepage())
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


    @Transactional
    public void 기업정보수정(Integer userId, compUpdateDTO DTO) {
        Optional<User> userOP = userRepository.findById(userId);
        
        String fileName = Image.updateImage(DTO);

        if (userOP.isPresent()) {
            User user = userOP.get();
            user.setAddress(DTO.getAddress());
            user.setTel(DTO.getTel());
            user.setHomepage(DTO.getHomepage());
            if(fileName != null){ // 사진 안넣으면 기존 사진 유지하게
                user.setPhoto(fileName);
            }
            CompRequest.SessionCompDTO sessionComp = CompRequest.SessionCompDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .compname(user.getCompname())
                .compRegister(user.getCompRegister())
                .tel(user.getTel())
                .photo(user.getPhoto())
                .address(user.getAddress())
                .homepage(user.getHomepage())
                .role(user.getRole())
                .build();
            session.setAttribute("sessionComp", sessionComp);

        } else{
            throw new MyException(userId + "없음");
        }
    }

    @Transactional
    public void 공고삭제(Integer postingId) {
        // TODO : 공고에 지원한 이력서랑 공고를 북마크한것도 처리 해야함
        
        List<PostingSkill> skillList = postingSkillRepository.findByPostingId(postingId);
        for (PostingSkill skill : skillList) {
            skill.setPosting(null);
        }

        try {
            postingRepository.deleteById(postingId);
        } catch (Exception e) {
            throw new MyException("없는공고");
        }
    }

    // public void 테스트2(String string) {
    //     PostingSkill.findBySkill
    // }






}
