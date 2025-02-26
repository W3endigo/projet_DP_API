package isen.projet_dp_api.dao.file;

import io.minio.MakeBucketArgs;

public interface FileServiceDAO {

    boolean bucketExists(String bucketName);

    void createBucket(MakeBucketArgs bucket);
}
