package shop.mtcoding.boardproject.comp;

import java.sql.Timestamp;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.Image;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyRepository;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.ResumeRepository;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.PostingSkillRepository;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;
import shop.mtcoding.boardproject.comp.CompRequest.JoinDTO;
import shop.mtcoding.boardproject.comp.CompRequest.SaveDTO;
import shop.mtcoding.boardproject.comp.CompRequest.UpdateDTO;
import shop.mtcoding.boardproject.comp.CompRequest.compUpdateDTO;

@Service
public class CompService {

    @Autowired
    private PostingSkillRepository postingSkillRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ApplyRepository applyRepository;

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

        List<String> StringList = saveDTO.getPostingSkill();

        List<Skill> skillList = skillRepository.findAll();

        List<PostingSkill> psList = new ArrayList<>();

        postingRepository.save(posting);

        int get=0; 
        while(StringList.size()>0){
            psList.add(new PostingSkill());
            psList.get(get).setPosting(posting);

            for (Skill s : skillList) {
                if(s.getSkillname().equals(StringList.get(0))){psList.get(get++).setSkill(s); break;}
            }
            StringList.remove(0);
        }

        // posting.setPostingSkill(psList); // 안해도됨

        for (PostingSkill sk : psList) {
            sk.getPosting().setId(posting.getId());
            postingSkillRepository.save(sk);
        }
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

            List<String> StringList = updateDTO.getPostingSkill();
            
            List<Skill> skillList = skillRepository.findAll();
            
            List<PostingSkill> psList = new ArrayList<>();

            postingSkillRepository.deleteByPostingId(postingId);

            int get=0;
            while(StringList.size()>0){
                psList.add(new PostingSkill());
                psList.get(get).setPosting(posting);
    
                for (Skill s : skillList) {
                    if(s.getSkillname().equals(StringList.get(0))){psList.get(get++).setSkill(s); break;}
                }
                StringList.remove(0);
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
        // TODO : 공고를 북마크한것도 지우거나 연결끊어야함

        List<PostingSkill> skillList = postingSkillRepository.findByPostingId(postingId);
        for (PostingSkill skill : skillList) {
            skill.setPosting(null);
        }

        List<Apply> applyList = applyRepository.findByPostingId(postingId);
        for (Apply apply : applyList) {
            apply.setPosting(null);
        }



        try {
            postingRepository.deleteById(postingId);
        } catch (Exception e) {
            throw new MyException("없는공고");
        }
    }

    public List<Apply> 공고지원신청찾기(Integer postingId) {

        List<Apply> applyList = applyRepository.findByPostingId(postingId);
        
        // System.out.println("테스트33:"+applyList);
        // System.out.println("테스트33:"+applyList.get(0));
        // System.out.println("테스트33:"+applyList.get(0).getId());
        // System.out.println("테스트33:"+applyList.get(0).getStatement());
        // System.out.println("테스트33:"+applyList.get(0).getPosting().getTitle());
        // System.out.println("테스트33:"+applyList.get(0).getResume().getTitle());

        // List<Resume> resumeList = new ArrayList<>();

        // while(applyList.size()>0){
        //     resumeList.add(applyList.get(0).getResume());
        //     applyList.remove(0);
        // }

        return applyList;
    }

    public List<User> 인재추천검색(List<String> skillList, String position) {

        List<User> userList = new ArrayList<>();

        if(skillList.size()==0 || skillList.get(0).equals("all")){
            userList = userRepository.findByRole(1);
        } else{
            Set<User> userSet = new LinkedHashSet<>(); // 중복 제거하려고 Set으로 했다가 List로 변경 
            for (String s : skillList) {
                List<User> tempList = userRepository.findBykillResumeReturnUser(s);
                userSet.addAll(tempList);
            }
            userList = new ArrayList<>(userSet);
        }

        if(position==null || position.equals("all")){
            //
        }else{
            List<User> tempList = new ArrayList<>();
            for (User user : userList) {
                if(!(user.getPosition().equals(position))){
                    tempList.add(user);
                }
            }
            userList.removeAll(tempList);
        }




        return userList;
    }

    // public void 테스트2(String string) {
    //     PostingSkill.findBySkill
    // }


}