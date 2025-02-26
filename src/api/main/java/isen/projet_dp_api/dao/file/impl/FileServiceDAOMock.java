package isen.projet_dp_api.dao.file.impl;

import io.minio.MakeBucketArgs;
import isen.projet_dp_api.dao.file.FileServiceDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class FileServiceDAOMock implements FileServiceDAO {

    @Override
    public boolean bucketExists(String bucketName) {
        return true;
    }

    @Override
    public void createBucket(MakeBucketArgs bucket) {
        return;
    }
}
