package com.example.study.service;

import com.example.study.entity.FileEntity;
import com.example.study.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void upload(FileEntity fileEntity, MultipartFile multipartFile) throws Exception {
        String projectPath = System.getProperty("user.dir") + "//src//main//webapp";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        multipartFile.transferTo(saveFile);

        fileEntity.setFile_name(fileName);
        fileEntity.setFile_path("/webapp/" + fileName);

        fileRepository.save(fileEntity);
    }
}
