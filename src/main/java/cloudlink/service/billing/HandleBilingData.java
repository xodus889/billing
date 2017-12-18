package cloudlink.service.billing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import cloudlink.domin.bililng.ComDomain;


public class HandleBilingData {

	CRUDBililngTable service 	= new CRUDBililngTable();
	private String path 		= "";	// csv 파일을 읽어들일 path
	
	/**
	 *	파일 읽어들일 path 초기화 
	 */
	public HandleBilingData(String path) {
		
		this.path = path;
	}
	
	/**
	 * csv 파일을 한줄씩 읽고 데이터 가공 후 dynamodb에 insert 한다 
	 */
	public void readFileInsertData() {
		
		BufferedReader br 	= null;
		String[] keys 		= null;
		JsonObject json		= new JsonObject();
		String[] version	= null;
		String[] sortKey 	= null;
		String line;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "euc-kr"));

			while((line = br.readLine()) != null) {
				
				// Key값이 있다면 value와 함께 json으로 반환한다
				// 반환된 json과 primarykey로  insert한다.
				if(keys != null) {
					
					json 				= makeJsonObject(keys, splitString(line));
					String primaryKey 	= subtractString(json.get(ComDomain.CSV_KEY).toString());
					
					service.insertData(primaryKey, sortKey[1], json);
				}
					
				// 호출한 행에 SKU가 있다면 그 행은 Key값이다
				if (line.contains(ComDomain.CSV_KEY)) {
					keys = splitString(line);
				}
				
				// 호출한 행에 Version이 있다면 version값 가져오기
				if (line.contains(ComDomain.VERSION)) {
					version = splitString(line);
				}
				
				// 호출한 행에 OfferCode가 있다면 OfferCode가져오기
				if (line.contains(ComDomain.SORT_KEY)) {
					sortKey = splitString(line);
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
	
	/**
	 *  예: "desc,desc" ,,,,,  와 같은 pattern을 ',' 으로 구분할 때
	 *  "desc,desc" , , , , , 처럼 split하기 위해서 정규식 사용해야 함 
	 */
	public String[] splitString(String line) {
	
		Pattern splitter = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		return splitter.split(line);
	}
	
	/**
	 * String 타입의 key와 value를 json 타입으로 변환하여 return한다 
	 */
	public JsonObject makeJsonObject(String[] keys, String[] values) {
		
		JsonObject obj 	= new JsonObject();
		String temp		= null;
		
		for(int i = 0; i < keys.length; i++) {
			
			// csv 파일에서 끝 열이 공백일 경우 행의 길이가 공백수 만큼 더 적게 들어온다
			// 공백은 임의의 값으로 채운다
			if(i >= values.length) {
				temp = ComDomain.NULL;
			} else {
				temp = subtractString(values[i]);
			}
 			
			if(temp.length() == 0)
				temp = ComDomain.NULL;

			obj.addProperty(subtractString(keys[i]), temp); // "\"value"\" -> "value"로 변환
		}
		
		System.out.println("length : " + obj.size());
		System.out.println(obj);

		return obj;
	}
	
	/**
	 * String 변환함수
	 * "\"value"\" -> "value"로 변환
	 */
	public String subtractString(String str) {
		
		return str.replaceAll("\"", "");
	}
}
