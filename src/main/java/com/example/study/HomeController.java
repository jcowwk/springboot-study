package com.example.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class HomeController {
    // 파일 업로드 디렉토리 경로
    private static final String uploadPath = "/Users/soyeon/Desktop/";
    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @PostMapping("/upload")
    public String file(Model model, @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        // 2. 서버에 파일 저장 & DB에 파일 정보(fileinfo) 저장
        // - 동일 파일명을 피하기 위해 random값 사용
        String originalFilename = multipartFile.getOriginalFilename();
        String saveFileName = createSaveFileName(originalFilename);

        // 2-1.서버에 파일 저장
        multipartFile.transferTo(new File(getFullPath(saveFileName)));

        // 저장된 파일의 URL 생성
        String fileUrl = getFileUrl(saveFileName);

        // 모델에 파일 URL 추가
        model.addAttribute("fileUrl", fileUrl);

        return "redirect:/";
    }

    // 파일 저장 이름 만들기
    // - 사용자들이 올리는 파일 이름이 같을 수 있으므로, 자체적으로 랜덤 이름을 만들어 사용한다
    private String createSaveFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자명 구하기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // fullPath 만들기
    private String getFullPath(String filename) {
        return uploadPath + filename;
    }

    // 저장된 파일의 URL 생성
    private String getFileUrl(String filename) throws MalformedURLException {
        Path path = Paths.get(uploadPath, filename);
        return path.toUri().toURL().toString();
    }
}