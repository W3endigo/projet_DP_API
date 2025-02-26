package isen.projet_dp_api.dao.file.impl;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import isen.projet_dp_api.dao.file.FileServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("!test")
public class FileServiceDAODefault implements FileServiceDAO {

    private final MinioClient minioClient;

    public FileServiceDAODefault(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            var bucketList = minioClient.listBuckets();
            for (var bucket : bucketList) {
                if (bucket.name().equals(bucketName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CHECKING_BUCKET, e, bucketName);
            throw new ApiException(e, ErrorMessage.ERROR_CHECKING_BUCKET, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void createBucket(MakeBucketArgs bucket) {
        try {
            minioClient.makeBucket(bucket);
        } catch (Exception e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_BUCKET + bucket, e, bucket.bucket());
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_BUCKET, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
