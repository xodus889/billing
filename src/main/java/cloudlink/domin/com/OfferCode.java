package cloudlink.domin.com;

/**
 * amazon api price csv 파일관련 
 * @author taeyeonkim
 *
 */
public enum OfferCode {
	
	// OfferCode(product) 관련
	APIGAETWAY("AmazonApiGateway"),
	CLOUDFRONT("AmazonCloudFront"),
	CLOUDSEARCH("AmazonCloudSearch"),
	CLOUDWATCH("AmazonCloudWatch"),
	CLOUDFRDYNAMODBONT("AmazonDynamoDB"),
	EC2("AmazonEC2"),
	POLLY("AmazonPolly"),
	RDS("AmazonRDS"),
	REKONGITION("AmazonRekognition"),
	ROUTE53("AmazonRoute53"),
	S3("AmazonS3"),
	SIMPLEDB("AmazonSimpleDB"),
	SNS("AmazonSNS"),
	KMS("awskms"),
	LAMBDA("AWSLambda"),
	QUEUESERVICE("AWSQueueService"),
	SUPPORTBUSINESS("AWSSupportBusiness");
	
	private String value;
	
	private OfferCode(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
