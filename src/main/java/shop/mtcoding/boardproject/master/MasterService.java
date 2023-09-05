package shop.mtcoding.boardproject.master;


import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject.master.MasterRequest.MasterDTO;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.posting.PostingQueryRepository;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;


@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private PostingQueryRepository postingQueryRepository;


    public List<Posting> 메인화면검색(List<String> skillList, String position, String region) {

        List<Posting> postingList = new ArrayList<>();

        if(skillList.size()==0 || skillList.get(0).equals("all")){
            postingList = postingRepository.findAll();
        } else{
            Set<Posting> postingSet = new LinkedHashSet<>(); // 중복 제거하려고 Set으로 했다가 List로 변경 
            for (String s : skillList) {
                List<Posting> tempList = postingRepository.joinSkillPosting(s);
                postingSet.addAll(tempList);
            }
            postingList = new ArrayList<>(postingSet);
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

    public List<Posting> 메인화면검색V2(List<String> skillList, String position, String region) {

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
}