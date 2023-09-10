package shop.mtcoding.boardproject.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.master.Master;
import shop.mtcoding.boardproject.master.MasterRequest.ReplyDTO;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReplyServiece {

    @Autowired
    private ReplyRepository replyRepository;

    public List<Reply> 문의넘버로찾기(Integer id) {
        List<Reply> replyList = replyRepository.findByMasterId(id);
        return replyList;
    }

    @Transactional
    public Reply 댓글작성하기(Integer id, ReplyDTO replyDTO) {
        Reply reply = Reply.builder()
                .master(new Master().builder()
                        .id(id).build())
                .content(replyDTO.getContent())
                .build();
        Reply savedReply = replyRepository.save(reply);
        System.out.println("insert 테스트 : " + savedReply);
        return savedReply;
    }

}
