package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeRepository;
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
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private PostingRepository postingRepository;


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

    public List<CompBookmark> 기업북마크전체(Integer id) {
        List<CompBookmark> compBookmarksList = compBookmarkRepository.findAllByUserId(id);
        return compBookmarksList;
    }

    public List<Resume> 회사별북마크찾기(Integer compId) {
        List<Resume> resumeList = resumeRepository.findResumeByCompId(compId);
        return resumeList;
    }

    @Transactional
    public void 회사별북마크삭제(Integer compId, Integer resumeId) {
        compBookmarkRepository.deleteByUserIdAndResumeId(compId, resumeId);
    }

    @Transactional
    public void 회사북마크추가(Integer compId, List<Integer> resumeIdList) {

        List<Resume> resumeList = resumeRepository.findAllById(resumeIdList);
        // System.out.println("테스트1size "+resumeList.size());

        List<Resume> tempList = new ArrayList<>();
        for (Resume resume : resumeList) {
            if (resume.getDisclosure() != true) {
                tempList.add(resume);
            }
        }
        resumeList.removeAll(tempList);
        // System.out.println("테스트2size "+resumeList.size());

        User user = new User();
        user.setId(compId);
        for (Resume resume : resumeList) {
            // CompBookmark tempBookmark =
            // compBookmarkRepository.findByUserIdAndResumeId(compId, resume.getId());
            // System.out.println("테스트"+tempBookmarkList);

            if (compBookmarkRepository.findByUserIdAndResumeId(compId, resume.getId()) == null) { // 똑같은거 북마크 안되게
                // System.out.println("테스트넘어옴");
                CompBookmark compBookmark = new CompBookmark(user, resume);
                compBookmarkRepository.save(compBookmark);
            }
        }
    }

    @Transactional
    public void 회사북마크추가(Integer compId, Integer resumeId) {

        if ((compBookmarkRepository.findByUserIdAndResumeId(compId, resumeId)) != null) { // 똑같은거 북마크 안되게
            throw new MyException("이미 북마크임");
        }

        Optional<Resume> resumeOP = resumeRepository.findById(resumeId);
        Resume resume = new Resume();
        if (resumeOP.isPresent()) {
            resume = resumeOP.get();
        } else {
            throw new MyException(resumeId + "없음");
        }

        if (resume.getDisclosure() == false) { // 이력서 공개여부 체크
            return;
        }

        User user = new User();
        user.setId(compId);

        CompBookmark compBookmark = new CompBookmark(user, resume);
        compBookmarkRepository.save(compBookmark);
    }

    /**
     * @param userId
     * @param postingId
     */
    @Transactional
public void 개인북마크추가(Integer userId, Integer postingId) {
    // 이미 북마크가 있는지 확인합니다.
    if (userBookmarkRepository.findByUserIdAndPostingId(userId, postingId) != null) {
        throw new MyException("이미 북마크된 공고입니다.");
    }

    // User 객체를 생성하고 ID를 설정합니다.
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new MyException("사용자를 찾을 수 없습니다."));

    // Posting 객체를 데이터베이스에서 가져옵니다.
    Posting posting = postingRepository.findById(postingId)
            .orElseThrow(() -> new MyException("공고를 찾을 수 없습니다."));

    // 북마크를 생성하고 저장합니다.
    UserBookmark userBookmark = new UserBookmark(user, posting);
    userBookmarkRepository.save(userBookmark);

    }

}