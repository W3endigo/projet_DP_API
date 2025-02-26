package isen.projet_dp_api.service;

import io.minio.MakeBucketArgs;
import isen.projet_dp_api.dao.file.FileServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${MINIO_BUCKET_NAME}")
    private String bucketName;

    private final FileServiceDAO fileServiceDAO;

    @Autowired
    public FileService(FileServiceDAO fileServiceDAO) {
        this.fileServiceDAO = fileServiceDAO;
    }

    public void putFile(MultipartFile file, String objectName) {
        if (!fileServiceDAO.bucketExists(bucketName)) {
            var makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            fileServiceDAO.createBucket(makeBucketArgs);
        }
    }

}