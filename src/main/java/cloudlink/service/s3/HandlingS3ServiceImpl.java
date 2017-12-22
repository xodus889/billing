package cloudlink.service.s3;

import com.amazonaws.services.s3.model.Bucket;

public class HandlingS3ServiceImpl implements HandlingS3Service {

	private S3Service service 	= new S3ServiceImpl();

	/**
	 * 1. csv 파일을 가져올 bucket을 가져온다
	 * 2. csv 파일을 가져올 bucket을 가져온다
	 */
	@Override
	public void handleS3(String bucketName, String csvPth) {
		
		// csv 파일을 저장할 bucket을 가져온다
		Bucket bucket = service.createBucket(bucketName);
		
		// 다운로드된 csv 파일을 가져온다
		
		// 가져온 bucket에 csv파일을 올린다
		service.putObject(bucketName, csvPth);
	}
}
