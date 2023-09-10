package shop.mtcoding.boardproject.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.posting.Posting;
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

    public List<UserBookmark> 유저가북마크한공고(Integer userId) {

        List<UserBookmark> list = userBookmarkRepository.findAllByUserId(userId);

        return list;

    }

    /**
     * @param userId
     * @param postingId
     */
    @Transactional
    public Integer 유저북마크추가(Integer postingId, Integer userId) {
        int success = 0;
        // 이미 북마크가 있는지 확인합니다.
        if (userBookmarkRepository.findByUserIdAndPostingId(postingId, userId) != null) {
            // 이미 북마크된 경우 409 (Conflict) 응답을 반환합니다.
            throw new MyException("이미 북마크된 공고입니다.", HttpStatus.CONFLICT);
        } else {
            // 북마크가 없으면 저장한다.
            success = userBookmarkRepository.saveByPostingAndUserId(postingId, userId);
        }

        return success;
    }


    @Transactional
    public Integer 유저북마크삭제(Integer postingId, Integer userId) {
        // 해당 사용자의 북마크를 찾습니다.
        int success = 0;
        UserBookmark userBookmark = userBookmarkRepository.findByUserIdAndPostingId(postingId, userId);

        if (userBookmark != null) {
            // 북마크가 존재하면 삭제합니다.
            userBookmarkRepository.delete(userBookmark);
            success = 1;
        } else {
            // 북마크가 없는 경우 예외를 던집니다.
            throw new MyException("북마크를 찾을 수 없습니다.");
        }

        return success;
    }


}