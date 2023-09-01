package shop.mtcoding.boardproject._core.util;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.vo.MyPath;
import shop.mtcoding.boardproject.comp.CompRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Image {

    public static String updateImage(CompRequest.PicDTO DTO) {

        if (DTO.getPhoto().getSize() == 0) { // 사진 파일이 업로드되지 않아도 MultipartFile 객체 자체는 생성된다. DTO.getPic()==null 로는 체크 못함
            // System.out.println("테스트 : 사진파일없음");
            return null;
        }

        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid + "_" + DTO.getPhoto().getOriginalFilename();
        // System.out.println("테스트 : fileName : "+fileName);

        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);

        try {
            Files.write(filePath, DTO.getPhoto().getBytes());
        } catch (Exception e) {
            throw new MyException("사진등록오류");
        }
        // System.out.println("테스트 : 사진등록완료");

        return fileName;

    }

}
