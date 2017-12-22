package cloudlink.service.dynamodb;

import java.util.Map;

/**
 * csv파일에서 가져온 데이터를 
 * 정제한다 
 *
 */
public interface HandlingDynamdodbService {
	
	// csv 파일을 한줄씩 읽고 데이터 가공 후 dynamodb에 insert 한다 
	public void readFileInsertData();
	
	 // 예: "desc,desc" ,,,,,  와 같은 pattern을 ',' 으로 구분할 때
	 // "desc,desc" , , , , , 처럼 split하기 위해서 정규식 사용해야 함 
	public String[] splitString(String line);
	
	
	 // String 타입의 key와 value를 Map 타입으로 변환하여 return한다
	 // value가 null인 것은 임의의 값으로 채워준다 
	public Map<String, String> changeToMap(String[] keys, String[] values); 
	
	// String 변환함수 '"' 제거
	public String subtractString(String str);

}
