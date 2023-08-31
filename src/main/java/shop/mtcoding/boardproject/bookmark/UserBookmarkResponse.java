package shop.mtcoding.boardproject.bookmark;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

public class UserBookmarkResponse {

    @Getter
    @Setter
    public static class UserBookmarkDTO {

        private List<Posting> postingList;

        public UserBookmarkDTO(List<Posting> postingList) {
            this.postingList = postingList;
        }

    }
}
