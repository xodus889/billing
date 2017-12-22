package cloudlink.service.dynamodb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import cloudlink.domain.dynamodb.DynamodbDomain;


public class HandlingDynamodbServiceImpl implements HandlingDynamdodbService {

	DynamodbServiceImpl service 	= new DynamodbServiceImpl();
	private String path 		= "";	// csv 파일을 읽어들일 path
	
	/**
	 *	파일 읽어들일 path 초기화 
	 */
	public HandlingDynamodbServiceImpl(String path) {
		this.path = path;
	}
	
	@Override
	public void readFileInsertData() {
		
		Map<String, String> map = new HashMap<>();
		BufferedReader br 		= null;
		String[] keys 			= null;
		String version		= null;
		String sortKey 		= null;
		String line;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "euc-kr"));

			while((line = br.readLine()) != null) {
				
				// Key값이 있다면 value와 함께 json으로 반환한다
				// 반환된 json과 primarykey로  insert한다.
				if(keys != null) {
					
					map 				= changeToMap(keys, splitString(line));
					String primaryKey 	= map.get(DynamodbDomain.CSV_KEY);
					
					service.insertData(primaryKey, sortKey, version, map);
				}
					
				// 호출한 행에 SKU가 있다면 그 행은 Key값이다
				if(line.contains(DynamodbDomain.CSV_KEY)) {
					keys = splitString(line);
				}
				
				// 호출한 행에 Version이 있다면 version값 가져오기
				if(line.contains(DynamodbDomain.VERSION)) {
					version = subtractString(splitString(line)[1]);
				}
				
				// 호출한 행에 OfferCode가 있다면 OfferCode가져오기
				if(line.contains(DynamodbDomain.SORT_KEY)) {
					sortKey = subtractString(splitString(line)[1]);
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String[] splitString(String line) {
	
		Pattern splitter = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		return splitter.split(line);
	}
	
	@Override
	public Map<String, String> changeToMap(String[] keys, String[] values) {
		
		Map<String, String> map = new HashMap<>();
		String temp				= null;
		
		for(int i = 0; i < keys.length; i++) {
			
			// csv 파일에서 끝 열이 공백일 경우 행의 길이가 공백수 만큼 더 적게 들어온다
			// 공백은 임의의 값으로 채운다
			if(i >= values.length) {
				temp = DynamodbDomain.NULL;
			} else {
				temp = subtractString(values[i]);
			}
 			
			if(temp.length() == 0)
				temp = DynamodbDomain.NULL;
			
			map.put(subtractString(keys[i]), temp);
			
			System.out.println(subtractString(keys[i]));
			System.out.println(temp);

		}
		
		return map;
	}
	
	@Override
	public String subtractString(String str) {
		
		return str.replaceAll("\"", "");
	}
}
