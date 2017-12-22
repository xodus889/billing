package cloudlink.service.dynamodb;

import java.util.Map;

/**
 *  Billing관련 데이터를 table로 만들어서
 *  CRUD 한다
 */
public interface DynamodbService {

	// table을 생성한다
	public void createTable();
	
	// data를 insert한다
	public void insertData(String primaryKey, String sortKey, String version, Map<String, String> map);
}
