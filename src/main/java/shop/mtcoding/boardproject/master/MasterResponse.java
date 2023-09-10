package shop.mtcoding.boardproject.master;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.reply.Reply;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

public class MasterResponse {

    @Getter
    @Setter
    public static class MasterListDTO {

        private Integer id;
        private String title;

    }

    @Getter
    @Setter
    public static class SearchDTO {

        private List<User> compUserList;
        private List<User> normalUserList;
        private List<Posting> postingList;
        private List<Resume> ResumeList;

    }

    @Getter
    @Setter
    public static class MasterReplyDTO {

        private Master master;
        private List<Reply> replyList;

    }

}
