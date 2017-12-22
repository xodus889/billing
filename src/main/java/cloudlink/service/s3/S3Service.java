package cloudlink.service.s3;

import com.amazonaws.services.s3.model.Bucket;

/**
 * Creating, Listing S3 Buckets
 */
public interface S3Service {
	
	// bucket을 생성하고 존재하는 bucket이면 반환한다
	public Bucket createBucket(String bucketName);
	
	// butcket을 반환한다
	public Bucket getBucket(String bucketName);
	
	// upload object
	public boolean putObject(String bucketName, String filePath);


}
