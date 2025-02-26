package isen.projet_dp_api.controller.file.impl;

import isen.projet_dp_api.controller.file.FileController;
import isen.projet_dp_api.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @Autowired
    public FileControllerImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<String> putFile(MultipartFile file, String projectName) {
        log.debug("Uploading file for project: {}", projectName);
        fileService.putFile(file, projectName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}