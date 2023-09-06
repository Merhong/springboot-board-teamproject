package shop.mtcoding.boardproject.master;


import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject.master.MasterRequest.MasterDTO;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;
import shop.mtcoding.boardproject.posting.PostingQueryRepository;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeRepository;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;


@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private PostingQueryRepository postingQueryRepository;


    // public List<Posting> 메인화면검색구버전(List<String> skillList, String position, String region) {

    //     List<Posting> postingList = new ArrayList<>();

    //     if(skillList.size()==0 || skillList.get(0).equals("all")){
    //         postingList = postingRepository.findAll();
    //     } else{
    //         Set<Posting> postingSet = new LinkedHashSet<>(); // 중복 제거하려고 Set으로 했다가 List로 변경 
    //         for (String s : skillList) {
    //             List<Posting> tempList = postingRepository.joinSkillPosting(s);
    //             postingSet.addAll(tempList);
    //         }
    //         postingList = new ArrayList<>(postingSet);
    //     }
        
    //     if(position==null || position.equals("all")){
    //         //
    //     }else{
    //         List<Posting> tempList = new ArrayList<>();
    //         for (Posting posting : postingList) {
    //             if(!(posting.getPosition().equals(position))){
    //                 tempList.add(posting);
    //             }
    //         }
    //         postingList.removeAll(tempList);
    //     }
        
    //     if(region==null || region.equals("all")){
    //         //
    //     }else{
    //         List<Posting> tempList = new ArrayList<>();
    //         for (Posting posting : postingList) {
    //             if(!(posting.getRegion().equals(region))){
    //                 tempList.add(posting);
    //             }
    //         }
    //         postingList.removeAll(tempList);
    //     }
        
        

    //     return postingList;
    // }

    public List<Posting> 메인화면검색(List<String> skillList, String position, String region) {

        List<Posting> postingList = new ArrayList<>();

        if(skillList.size()==0 || skillList.get(0).equals("all")){
            postingList = postingRepository.findAll();
        } else{
            postingList = postingQueryRepository.joinSkillPosting(skillList);
        }
        
        if(position==null || position.equals("all")){
            //
        }else{
            List<Posting> tempList = new ArrayList<>();
            for (Posting posting : postingList) {
                if(!(posting.getPosition().equals(position))){
                    tempList.add(posting);
                }
            }
            postingList.removeAll(tempList);
        }
        
        if(region==null || region.equals("all")){
            //
        }else{
            List<Posting> tempList = new ArrayList<>();
            for (Posting posting : postingList) {
                if(!(posting.getRegion().equals(region))){
                    tempList.add(posting);
                }
            }
            postingList.removeAll(tempList);
        }
        
        

        return postingList;
    }



    public List<Master> 모든문의찾기() {
        List<Master> masterList = masterRepository.findAll();
        return masterList;
    }

    public Master 문의넘버로찾기(Integer id) {
        Optional<Master> master = masterRepository.findById(id);
        if (master != null) {
            return master.get();
        }
        throw new NullPointerException();
    }

    @Transactional
    public void 문의등록(MasterDTO masterDTO, User user) {
        Master master = Master.builder()
                .user(User.builder().id(user.getId()).email(user.getEmail()).build())
                .category(masterDTO.getCategory())
                .title(masterDTO.getTitle())
                .content(masterDTO.getContent())
                .build();
        masterRepository.save(master);
    }

    public List<Master> 유저로문의찾기(Integer Id) {

        List<Master> masterList = masterRepository.findByUserId(Id);
        return masterList;
    }



    public void 스킬추가(String skillName) {
        Skill skill = new Skill();
        skill.setSkillname(skillName);
        skillRepository.save(skill);
    }



    public MasterResponse.SearchDTO 전체검색(String keyword) {
        // List<User> userList = userRepository.findByCompname(keyword);
        // List<User> tempUserList = userRepository.findByUsername(keyword);
        // userList.addAll(tempUserList);
        
        
        List<User> userList = userRepository.findByUsernameContainingOrCompnameContaining(keyword, keyword);
        List<Posting> postingList = postingRepository.findByTitleContaining(keyword);
        List<Resume> resumeList = resumeRepository.findByTitleContaining(keyword);

        // System.out.println("테스트:"+userList);
        // System.out.println("테스트:"+postingList);
        // System.out.println("테스트:"+resumeList);

        MasterResponse.SearchDTO searchDTO = new MasterResponse.SearchDTO();

        if(userList.size()==0 && postingList.size()==0 && resumeList.size()==0){
            return null;
        }

        if(userList.size() != 0){
            List<User> normalUserList = new ArrayList<>();
            List<User> compUserList = new ArrayList<>();
            for (User user : userList) {
                if(user.getRole()==1){normalUserList.add(user);} // 개인
                if(user.getRole()==2){compUserList.add(user);} // 기업
            }
            searchDTO.setNormalUserList(normalUserList);
            searchDTO.setCompUserList(compUserList);
        }
        if(resumeList.size() != 0){
            List<Resume> tempResumeList = new ArrayList<>();
            for (Resume resume : resumeList) {
                if(resume.getDisclosure()==false){ // 비공개 이력서 빼기
                    tempResumeList.add(resume);
                }
            }
            resumeList.removeAll(tempResumeList);
            searchDTO.setResumeList(resumeList);
        }
        if(postingList.size() != 0){searchDTO.setPostingList(postingList);}

        return searchDTO;
    }
}