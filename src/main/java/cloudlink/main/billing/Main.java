package cloudlink.main.billing;

import cloudlink.domain.S3.S3Domain;
import cloudlink.domin.com.OfferCode;
import cloudlink.service.s3.HandlingS3Service;
import cloudlink.service.s3.HandlingS3ServiceImpl;

public class Main {

	public static void main(String[] args) {
		
//		DynamodbServiceImpl table = new DynamodbServiceImpl();
//		table.createTable();
//		OfferCode.values();
//		HandlingDynamodbServiceImpl handle = new HandlingDynamodbServiceImpl(OfferCode.PATH_HEAD);
//		handle.readFileInsertData();
		
		/**
		 * S3
		 */
		String csvPth 			= S3Domain.PATH_HEAD + OfferCode.CLOUDFRONT.toString() + S3Domain.PATH_TAIL;
		HandlingS3Service s3 	= new HandlingS3ServiceImpl();
		s3.handleS3(S3Domain.BUCKET_NAME, csvPth);
		
	}
}
