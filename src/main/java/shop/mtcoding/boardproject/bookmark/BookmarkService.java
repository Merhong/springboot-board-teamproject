package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private UserBookmarkRepository userBookmarkRepository;

    @Autowired
    private CompBookmarkRepository compBookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Posting> 유저북마크전체(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new RuntimeException();
        }
        List<UserBookmark> bookmarkList = userBookmarkRepository.findAllByUserId(user.get().getId());
        List<Posting> postingList = new ArrayList<Posting>();
        for (UserBookmark bookmark : bookmarkList) {
            postingList.add(bookmark.getPosting());
        }

        return postingList;

    }

    public List<Resume> 기업북마크전체(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new RuntimeException();
        }
        List<CompBookmark> bookmarkList = compBookmarkRepository.findAllByUserId(user.get().getId());
        List<Resume> resumeList = new ArrayList<Resume>();
        for (CompBookmark bookmark : bookmarkList) {
            resumeList.add(bookmark.getResume());
        }

        return resumeList;
    }
}