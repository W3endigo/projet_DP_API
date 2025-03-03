package isen.projet_dp_api.controller.file.impl;

import com.google.common.net.HttpHeaders;
import isen.projet_dp_api.controller.file.FileController;
import isen.projet_dp_api.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.List;

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

    @Override
    public ResponseEntity<InputStreamResource> getFile(List<String> fileNames, String projectName) {
        log.debug("Downloading file(s) : {} for project: {}", fileNames.toString(), projectName);
        var content = fileService.getFile(fileNames, projectName);

        if (fileNames.size() > 1) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + projectName + ".zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(content));
        } else {
            var mediaType = URLConnection.guessContentTypeFromName(fileNames.getFirst());
            if (!mediaType.contains("application")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(fileNames.getFirst()))
                        .body(new InputStreamResource(content));
            } else {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNames.getFirst())
                        .contentType(MediaType.parseMediaType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE))
                        .body(new InputStreamResource(content));
            }
        }
    }
}