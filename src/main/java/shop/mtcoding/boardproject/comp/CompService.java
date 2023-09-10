package shop.mtcoding.boardproject.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.util.Image;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyRepository;
import shop.mtcoding.boardproject.bookmark.CompBookmarkRepository;
import shop.mtcoding.boardproject.bookmark.UserBookmark;
import shop.mtcoding.boardproject.bookmark.UserBookmarkRepository;
import shop.mtcoding.boardproject.comp.CompRequest.JoinDTO;
import shop.mtcoding.boardproject.comp.CompRequest.SaveDTO;
import shop.mtcoding.boardproject.comp.CompRequest.UpdateDTO;
import shop.mtcoding.boardproject.comp.CompRequest.compUpdateDTO;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.recommend.Recommend;
import shop.mtcoding.boardproject.recommend.RecommendRepository;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.skill.PostingSkillRepository;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CompService {

    /* DI */
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
    private UserBookmarkRepository userBookmarkRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private CompBookmarkRepository compBookmarkRepository;


    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        // 기업 프로필 사진
        String fileName = Image.updateImage(joinDTO);
        // 기업회원에 해당하는 필드값을 builder로 담는다.
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
        // JPA save
        userRepository.save(user);

    }

    public List<Posting> 회사별공고찾기(Integer compId) {
        List<Posting> postingList = postingRepository.findByUserId(compId);
        return postingList;
    }

    @Transactional
    public void 공고작성(SaveDTO saveDTO, Integer compId) {
        Posting posting = new Posting();
        posting.setUser(new User());
        posting.getUser().setId(compId);
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

        int get = 0;
        while (StringList.size() > 0) {
            psList.add(new PostingSkill());
            psList.get(get).setPosting(posting);

            for (Skill s : skillList) {
                if (s.getSkillname().equals(StringList.get(0))) {
                    psList.get(get++).setSkill(s);
                    break;
                }
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
            throw new MyException(postingId + " 없음");
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

            int get = 0;
            while (StringList.size() > 0) {
                psList.add(new PostingSkill());
                psList.get(get).setPosting(posting);

                for (Skill s : skillList) {
                    if (s.getSkillname().equals(StringList.get(0))) {
                        psList.get(get++).setSkill(s);
                        break;
                    }
                }
                StringList.remove(0);
            }

            for (PostingSkill skill : psList) {
                skill.getPosting().setId(posting.getId());
                postingSkillRepository.save(skill);
            }

        } else {
            throw new MyException(postingId + "공고없음");
        }
    }


    @Transactional
    public CompRequest.SessionCompDTO 기업정보수정(Integer userId, compUpdateDTO DTO) {
        Optional<User> userOP = userRepository.findById(userId);

        String fileName = Image.updateImage(DTO);

        if (userOP.isPresent()) {
            User user = userOP.get();
            user.setAddress(DTO.getAddress());
            user.setTel(DTO.getTel());
            user.setHomepage(DTO.getHomepage());
            if (fileName != null) { // 사진 안넣으면 기존 사진 유지하게
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

            return sessionComp;

        } else {
            throw new MyException(userId + "없음");
        }
    }

    @Transactional
    public void 공고삭제(Integer postingId) {

        List<UserBookmark> userBookmarkList = userBookmarkRepository.findByPostingId(postingId);
        userBookmarkRepository.deleteAll(userBookmarkList);

        List<PostingSkill> skillList = postingSkillRepository.findByPostingId(postingId);
        postingSkillRepository.deleteAll(skillList);

        List<Apply> applyList = applyRepository.findByPostingId(postingId);
        applyRepository.deleteAll(applyList);

        List<Recommend> recommendList = recommendRepository.findByPostingId(postingId);
        recommendRepository.deleteAll(recommendList);


        try {
            postingRepository.deleteById(postingId);
        } catch (Exception e) {
            throw new MyException("없는공고");
        }
    }

    public User 이메일중복체크(String email) {
        User user = userRepository.FindByemail(email);
        return user;
    }

    public List<Apply> 공고지원신청찾기(Integer postingId) {
        List<Apply> applyList = applyRepository.findByPostingId(postingId);
        return applyList;
    }

    public List<Recommend> 공고로제안한목록찾기(Integer postingId) {
        List<Recommend> recommendList = recommendRepository.findByPostingId(postingId);
        return recommendList;
    }


    public List<User> 인재추천검색(List<String> skillList, String position) {

        List<User> userList = new ArrayList<>();

        if (skillList.size() == 0 || skillList.get(0).equals("all")) {
            userList = userRepository.findByRole(1);
        } else {
            Set<User> userSet = new LinkedHashSet<>(); // 중복 제거하려고 Set으로 했다가 List로 변경
            for (String s : skillList) {
                List<User> tempList = userRepository.findBykillResumeReturnUser(s);
                userSet.addAll(tempList);
            }
            userList = new ArrayList<>(userSet);
        }

        if (position == null || position.equals("all")) {
            //
        } else {
            List<User> tempList = new ArrayList<>();
            for (User user : userList) {
                if (!(user.getPosition().equals(position))) {
                    tempList.add(user);
                }
            }
            userList.removeAll(tempList);
        }

        return userList;

    }

    public Boolean 북마크중복체크(Integer compId, Integer resumeId) {

        if (compBookmarkRepository.findByUserIdAndResumeId(compId, resumeId) == null) {
            // System.out.println("테스트: 기업:"+compId+"  이력서:"+resumeId);
            return true;
        } else {
            return false;
        }

    }

    public User 기업찾기(Integer compId) {
        Optional<User> companyOP = userRepository.findById(compId);

        if (companyOP.isPresent()) {
            User company = companyOP.get();
            if (company.getRole() != 2) {
                throw new MyException("기업아님");
            }
            return company;
        } else {
            throw new MyException(compId + " 없음");
        }
    }

    // public void 테스트2(String string) {
    //     PostingSkill.findBySkill
    // }


}