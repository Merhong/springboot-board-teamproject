package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;

import java.util.List;

public class BookmarkResponse {

    @Getter
    @Setter
    public static class UserBookmarkDTO {

        private List<Posting> postingList;

        public UserBookmarkDTO(List<Posting> postingList) {
            this.postingList = postingList;
        }

    }
}
