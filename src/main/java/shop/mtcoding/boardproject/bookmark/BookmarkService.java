package shop.mtcoding.boardproject.bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository userBookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Posting> 유저북마크(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new RuntimeException();
        }
        System.out.println("테스트 user.get().getId()" + user.get().getId());
        List<Bookmark> bookmarkList = userBookmarkRepository.findAllByUserId(user.get().getId());
        List<Posting> postingList = new ArrayList<Posting>();
        for (Bookmark bookmark : bookmarkList) {
            postingList.add(bookmark.getPosting());
        }

        return postingList;

    }
}