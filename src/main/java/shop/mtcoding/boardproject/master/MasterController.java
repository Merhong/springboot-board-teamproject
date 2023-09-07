package shop.mtcoding.boardproject.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject._core.util.Script;
import shop.mtcoding.boardproject.bookmark.BookmarkService;
import shop.mtcoding.boardproject.master.MasterResponse.MasterListDTO;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.boardproject.skill.SkillService;

@Controller
public class MasterController {

    /* DI */
    @Autowired
    private MasterService masterService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HttpSession session;


    
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "")String keyword, 
            @RequestParam(name = "pageSearchAll", defaultValue = "0") Integer page,
            HttpServletRequest request) {

        keyword = keyword.trim();
        
        request.setAttribute("keyword", keyword);

        // if(keyword == null || keyword.isEmpty()){
        if(keyword == null){
            return "/master/search";
        }

        MasterResponse.SearchDTO searchDTO = masterService.전체검색(keyword);

        if(searchDTO == null){
            return "/master/search";
        }

        searchDTO.setNormalUserList(new ArrayList<User>()); // 일반유저 검색은 제외하기로 함


        
        final int PAGESIZE=4;
        
        int compUserListSize=0;
        if(searchDTO.getCompUserList() != null){
            compUserListSize = searchDTO.getCompUserList().size();
        }
        int normalUserListSize=0;
        if(searchDTO.getNormalUserList() != null){
            normalUserListSize = searchDTO.getNormalUserList().size();
        }
        int postingListSize=0;
        if(searchDTO.getPostingList() != null){
            postingListSize = searchDTO.getPostingList().size();
        }
        int resumeListSize=0;
        if(searchDTO.getResumeList() != null){
            resumeListSize = searchDTO.getResumeList().size();
        }

        // System.out.println("테스트 : compUserListSize :"+compUserListSize+" / normalUserListSize :"+normalUserListSize+" / postingListSize :"+postingListSize+" / resumeListSize :"+resumeListSize);

        int totalCount = compUserListSize + normalUserListSize + postingListSize + resumeListSize;
        // 모든 공고 합친 개수
        
        boolean last = false; // 끝페이지인지 확인
        if(totalCount <= (page + 1)* PAGESIZE){
            last = true;
        }
        
        boolean first = false; // 첫페이지인지 확인
        if(page <= 0){
            first = true;
        } else{
            first = false;
        }
        
        int pageStart = page * PAGESIZE;
        int pageEnd = Math.min(pageStart + PAGESIZE, totalCount);
        
        if (pageStart >= pageEnd || page < 0) {
            // request.setAttribute("postingList", null);
            request.setAttribute("searchDTO", new ArrayList<>()); // 범위 벗어난 페이지면 0개리스트 줌
        }else{

            List<Object> allList = new ArrayList<>();

            if(searchDTO.getCompUserList() != null && searchDTO.getCompUserList().size() != 0){
                allList.addAll(searchDTO.getCompUserList());
            }
            if(searchDTO.getNormalUserList() != null && searchDTO.getNormalUserList().size() != 0){
                allList.addAll(searchDTO.getNormalUserList());
            }
            if(searchDTO.getPostingList() != null && searchDTO.getPostingList().size() != 0){
                allList.addAll(searchDTO.getPostingList());
            }
            if(searchDTO.getResumeList() != null && searchDTO.getResumeList().size() != 0){
                allList.addAll(searchDTO.getResumeList());
            }

            allList = allList.subList(pageStart, pageEnd);
            
            List<User> newCompUserList = new ArrayList<>();
            List<User> newNormalUserList = new ArrayList<>();
            List<Posting> newPostingList = new ArrayList<>();
            List<Resume> newResumeList = new ArrayList<>();

            for (Object object : allList) {
                if (object instanceof User) {
                    if(  ( (User)object ).getRole()==2  ){
                        newCompUserList.add( (User)object );
                    } else{
                        newNormalUserList.add( (User)object );
                    }
                } else if (object instanceof Posting) {
                    newPostingList.add( (Posting)object );
                } else if (object instanceof Resume) {
                    newResumeList.add( (Resume)object );
                }
            }

            MasterResponse.SearchDTO newSearchDTO = new MasterResponse.SearchDTO();
            newSearchDTO.setCompUserList(newCompUserList);
            newSearchDTO.setNormalUserList(newNormalUserList);
            newSearchDTO.setPostingList(newPostingList);
            newSearchDTO.setResumeList(newResumeList);

            request.setAttribute("searchDTO", newSearchDTO);
        }

        request.setAttribute("pageSearchAll", page);
        request.setAttribute("first", first);
        request.setAttribute("last", last);
        request.setAttribute("totalCount", totalCount);
        
        int totalPage = totalCount / PAGESIZE;
        if(totalCount % PAGESIZE != 0){
            totalPage++;
        }
        request.setAttribute("totalPage", totalPage);
        return "/master/search";
    }

    // 관리자 페이지, 추가할 기술 이름 관리(코드 테이블)
    @GetMapping("/master/admin")
    public String admin() {
        return "/master/admin";
    }

    // 코드 테이블 스킬 추가 POST
    @PostMapping("/master/skill")
    public @ResponseBody String admin(String skillName) {
        masterService.스킬추가(skillName);
        return Script.href("/","스킬 추가 : "+skillName);
    }

    // 인덱스(홈) 페이지
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "all") List<String> skillList,
            @RequestParam(defaultValue = "all") String position, 
            @RequestParam(defaultValue = "all") String region, 
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {

        List<Skill> sl = skillService.스킬이름전부();
        request.setAttribute("skillList", sl);
        // 뷰에서 기술목록 뿌릴때 사용

        // request에 담아서 전달
        request.setAttribute("position", position);
        request.setAttribute("region", region);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(skillList);
            // System.out.println("테스트"+json);
            request.setAttribute("json", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 뷰에 뭘 검색한건지 적혀있게


        List<Posting> postingList = masterService.메인화면검색한방쿼리(skillList, position, region); // 핵심기능
        
        // System.out.println("테스트 전체size:"+postingList.size());
        // System.out.println("테스트 page:"+page);

        final int PAGESIZE=4; // 한페이지에 보여줄 공고 개수

        int totalCount = postingList.size(); // 모든 공고 합친 개수
        
        boolean last = false; // 끝페이지인지 확인
        if(totalCount <= (page + 1)* PAGESIZE){
            last = true;
        }

        boolean first = false; // 첫페이지인지 확인
        if(page <= 0){
            first = true;
        } else{
            first = false;
        }


        int pageStart = page * PAGESIZE;
        int pageEnd = Math.min(pageStart + PAGESIZE, totalCount);

        if (pageStart >= pageEnd || page < 0) {
            // request.setAttribute("postingList", null);
            request.setAttribute("postingList", new ArrayList<>()); // 범위 벗어난 페이지면 0개리스트 줌
        }else{
            request.setAttribute("postingList", postingList.subList(pageStart, pageEnd)); // 페이지 맞으면 리스트에서 거기에 맞게 잘라서 줌
        }

        
        // request.setAttribute("postingList", postingList);
        request.setAttribute("page", page);
        request.setAttribute("first", first);
        request.setAttribute("last", last);
        request.setAttribute("totalCount", totalCount);
        
        int totalPage = totalCount / PAGESIZE;
        if(totalCount % PAGESIZE != 0){
            totalPage++;
        }
        request.setAttribute("totalPage", totalPage);

        return "index";
    }

    // 고객센터 페이지
    @GetMapping("/master/help")
    public String qna(HttpServletRequest request) {
        return "/master/help";
    }

    // 고객센터의 문의하기 페이지
    @GetMapping("/question/Form")
    public String questionForm(HttpServletRequest request) {
        User user = null;
        System.out.println(session.getAttribute("sessionUser"));
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
        }
        if (session.getAttribute("sessionAdmin") != null) {
            user = (User) session.getAttribute("sessionAdmin");
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
        }
        // 세션 유저가 없다면 로그인 화면으로 이동
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }
        // request에 user를 담는다.
        request.setAttribute("user", user);
        // 리다이렉트
        return "/master/questionForm";
    }

    // 문의 제출하기 POST
    @PostMapping("/question/save")
    public String questionSave(MasterRequest.MasterDTO masterDTO) {
        User user = null;
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
        }
        if (session.getAttribute("sessionAdmin") != null) {
            user = (User) session.getAttribute("sessionAdmin");
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
        }
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }

        masterService.문의등록(masterDTO, user);
        return "redirect:/question/List";
    }

    // 문의 리스트 페이지
    @GetMapping("/question/List")
    public String questionList(HttpServletRequest request) {

        List<Master> masterList = new ArrayList<>();
        User user = null;
        if (session.getAttribute("sessionAdmin") != null) {
            masterList = masterService.모든문의찾기();
        }
        if (session.getAttribute("sessionUser") != null) {
            user = (User) session.getAttribute("sessionUser");
            masterList = masterService.유저로문의찾기(user.getId());
        }
        if (session.getAttribute("CompSession") != null) {
            user = (User) session.getAttribute("CompSession");
            masterList = masterService.유저로문의찾기(user.getId());
        }
        if (user.equals(null)) {
            return "redirect:/user/loginForm";
        }
        List<MasterListDTO> DTOList = new ArrayList<MasterListDTO>();
        for (Master master : masterList) {
            MasterListDTO newDTO = new MasterListDTO();
            newDTO.setId(master.getId());
            newDTO.setTitle(master.getTitle());
            DTOList.add(newDTO);
        }

        request.setAttribute("DTOList", DTOList);
        return "/master/questionList";
    }

    // 문의 상세보기 페이지
    @GetMapping("/question/{id}")
    public String questionForm(@PathVariable Integer id, HttpServletRequest request) {
        Master master = masterService.문의넘버로찾기(id);
        request.setAttribute("master", master);
        return "/master/question";
    }




    @GetMapping("/zzz")
    public String zzz() {
        return "/user/zzz";
    }
}
