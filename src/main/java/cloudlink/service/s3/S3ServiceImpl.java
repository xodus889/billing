package cloudlink.service.s3;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

public class S3ServiceImpl implements S3Service {

	private AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

	// bucket을 생성하고 존재하는 bucket이면 반환한다
	@SuppressWarnings("deprecation")
	@Override
	public Bucket createBucket(String bucketName) {

		Bucket b = null;

		if (s3.doesBucketExist(bucketName)) {
			System.out.format("Bucket %s already exists.\n", bucketName);
			b = getBucket(bucketName);

		} else {

			try {
				b = s3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}

		return b;
	}

	// butcket을 반환한다
	@Override
	public Bucket getBucket(String bucketName) {

		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucketName)) {
				named_bucket = b;
			}
		}

		return named_bucket;

	}

	@Override
	public boolean putObject(String bucketName, String filePath) {

		String key_name = "asdfasdf/kkk/" + Paths.get(filePath).getFileName().toString();

		File file = new File("https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/AmazonRedshift/current/index.csv");
		
		try {
			s3.putObject(bucketName, key_name, file);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		return true;
	}

}
