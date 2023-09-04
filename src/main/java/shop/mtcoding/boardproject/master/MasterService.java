package shop.mtcoding.boardproject.master;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject.master.MasterRequest.MasterDTO;
import shop.mtcoding.boardproject.user.User;

@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;

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
