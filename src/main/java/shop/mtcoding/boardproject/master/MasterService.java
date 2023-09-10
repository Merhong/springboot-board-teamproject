package shop.mtcoding.boardproject.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.boardproject.master.MasterRequest.MasterDTO;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingQueryRepository;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeRepository;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.skill.SkillRepository;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterService {

    /* DI */
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


    public List<Posting> 메인화면검색한방쿼리(List<String> skillList, String position, String region) {
        List<Posting> postingList = postingQueryRepository.joinSkillPostingOneHitQuery(skillList, position, region);
        return postingList;
    }


    /**
     * 메인화면검색 메서드
     * (소거법)
     * 1. 스킬을 통해 먼저 공고를 찾는다.
     * 2. 직무와 관련없는 공고 소거
     * 3. 지역과 관련없는 공고 소거
     * 4. 남은 공고들을 리턴
     */
    public List<Posting> 메인화면검색(List<String> skillList, String position, String region) {
        List<Posting> postingList = new ArrayList<>();
        // 스킬을 선택하지 않거나 모두 선택했을때
        if (skillList.size() == 0 || skillList.get(0).equals("all")) {
            // 리스트에 모든 공고를 담는다.
            postingList = postingRepository.findAll();
        } else { // 스킬을 1개이상 선택했을때 해당스킬을 요구하는 공고를 찾아서 공고리스트에 담는다.
            postingList = postingQueryRepository.joinSkillPosting(skillList);
        }
        // 직무가 없거나 모두 선택했을때
        if (position == null || position.equals("all")) {
            // 스킬로 찾은 공고에서 소거법으로 제거하기 위해 (통과)
        } else {
            // 관계없는 직무와 관련된 공고를 담을 리스트
            List<Posting> tempList = new ArrayList<>();
            // 스킬공고리스트 크기만큼 반복문을 돌면서
            for (Posting posting : postingList) {
                // 관계없는 직무를 찾아서 임시 리스트에 담는다.
                if (!(posting.getPosition().equals(position))) {
                    tempList.add(posting);
                }
            }
            // 관계없는 직무를 요구하는 공고를 스킬공고리스트에서 제거한다.
            postingList.removeAll(tempList);
        }
        // 지역이 없거나 모두 선택했을때
        if (region == null || region.equals("all")) {
            // 위에서 남은 리스트에서 소거법으로 제거하기 위해 통과
        } else {
            // 관계없는 지역과 관련된 공고를 담을 리스트
            List<Posting> tempList = new ArrayList<>();
            // 스킬공고리스트 크기만큼 반복문을 돌면서
            for (Posting posting : postingList) {
                // 관계없는 지역을 찾아서 임시 리스트에 담는다.
                if (!(posting.getRegion().equals(region))) {
                    tempList.add(posting);
                }
            }
            // 관계없는 지역의 공고를 스킬리스트에서 제거한다.
            postingList.removeAll(tempList);
        }
        // 소거법을 통해 검색에서 요구하는 조건을 모두 만족하는 리스트를 리턴
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


    // 관리자용, 스킬테이블은 코드테이블이라 관리자가 스킬을 추가해야 하는데 그때 사용하는 메서드
    public void 스킬추가(String skillName) {
        Skill skill = new Skill();
        skill.setSkillname(skillName);
        skillRepository.save(skill);
    }


    // 검색 메서드
    public MasterResponse.SearchDTO 전체검색(String keyword) {
        // 이름을 검색해서 나온 값들을 담은 유저리스트
        List<User> userList = userRepository.findByUsernameContainingOrCompnameContaining(keyword, keyword);
        // 공고 제목을 검색해서 나온 값들을 담은 공고리스트
        List<Posting> postingList = postingRepository.findPostingByTitleOrJoinUserCompname(keyword, keyword);
        // 이력서 제목을 검색해서 나온 값들을 담은 이력서리스트
        List<Resume> resumeList = resumeRepository.findResumeByTitleOrJoinUserUsername(keyword, keyword);

        MasterResponse.SearchDTO searchDTO = new MasterResponse.SearchDTO();

        // 모든 리스트 크기가 0일때, 검색된 결과가 없으니 null
        if (userList.size() == 0 && postingList.size() == 0 && resumeList.size() == 0) {
            return null;
        }

        // 유저리스트 크기가 0이 아닐때, 검색된 결과 존재
        if (userList.size() != 0) {
            List<User> normalUserList = new ArrayList<>();
            List<User> compUserList = new ArrayList<>();
            // 유저리스트 크기만큼 반복
            for (User user : userList) {
                // 회원유형이 1일땐 개인유저리스트에 담고
                if (user.getRole() == 1) {
                    normalUserList.add(user);
                }
                // 회원유형이 2일땐 기업유저리스트에 담는다.
                if (user.getRole() == 2) {
                    compUserList.add(user);
                }
            }
            // 담아서 바뀐 리스트를 DTO에 다시 설정해준다.
            searchDTO.setNormalUserList(normalUserList);
            searchDTO.setCompUserList(compUserList);
        }
        // 이력서리스트 크기가 0이 아닐때, 검색된 결과 존재
        if (resumeList.size() != 0) {
            List<Resume> tempResumeList = new ArrayList<>();
            // 이력서리스트 크기만큼 반복
            for (Resume resume : resumeList) {
                // 비공개 이력서는 빼야하므로 임시리스트에 담는다.
                if (resume.getDisclosure() == false) {
                    tempResumeList.add(resume);
                }
            }
            // 이력서 리스트에서 임시리스트에 담긴 내용을 제거한다.
            resumeList.removeAll(tempResumeList);
            // 바뀐 리스트를 DTO에 다시 설정해준다.
            searchDTO.setResumeList(resumeList);
        }
        // 공고리스트 크기가 0이 아닐때, 검색 결과 존재
        if (postingList.size() != 0) {
            // DTO에 담아서 설정
            searchDTO.setPostingList(postingList);
        }

        // 변경된 리스트 내용이 설정된 DTO를 리턴한다.(keyword 검색 결과값)
        return searchDTO;
    }
}