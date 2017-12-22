package cloudlink.service.s3;

public interface HandlingS3Service {

	// s3 관련 메소드들을 핸들링한다
	// 목적 : 다운로드한 csv 파일을 지정한 s3 경로에 올린다
	public void handleS3(String bucketName, String csvPth);
}
