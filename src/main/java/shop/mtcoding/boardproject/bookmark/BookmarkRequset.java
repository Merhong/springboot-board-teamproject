package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

import java.util.List;

public class BookmarkRequset {

    @Getter
    @Setter
    public static class UserBookmarkDTO {
        private List<Posting> postingList;

        public UserBookmarkDTO(List<Posting> postingList) {
            this.postingList = postingList;
        }
    }

    @Getter
    @Setter
    public static class CompBookmarkDTO {
        private Posting posting;
        private Resume resume;

        public CompBookmarkDTO(Posting posting, Resume resume) {
            this.posting = posting;
            this.resume = resume;
        }
    }

    @Getter
    @Setter
    public static class UserDTO {
        private Posting posting;

        public UserDTO(Posting posting) {
            this.posting = posting;
        }
    }
}
