package shop.mtcoding.boardproject.master;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;

@Service
public class MasterService {

    @Autowired
    private PostingRepository postingRepository;


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

}
