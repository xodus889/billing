package cloudlink.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import cloudlink.service.billing.cf.CFBillingTableServiceImpl;

@Service
public class BillingMain {

	
	public static final String CSV_PATH 	= "C:/Users/taeyeonkim/Desktop/work/Dev/PricingData/csv/currentVersionUrl/AmazonCloudFront/index.csv";
	public static final String EUC_KR 		= "euc-kr";
	public static final String PRIMARY_KEY 	= "RateCode";
	public static final String NULL			= "empty";
	
	public static void main(String[] args) {
		
		//임시
		CFBillingTableServiceImpl ec2Service = new CFBillingTableServiceImpl();
		
		BufferedReader br 	= null;
		JsonObject json		= new JsonObject();
		String keySplit 	= "SKU";
		String[] keys 		= null;
		String line;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(CSV_PATH), EUC_KR));

			while((line = br.readLine()) != null) {
				
				// Key값이 있다면 value와 함께 json으로 반환한다
				// 반환된 json과 primarykey로  insert한다.
				if(keys != null) {
					
					json 				= makeJsonObject(keys, splitString(line));
					String primaryKey 	= subtractString(json.get(PRIMARY_KEY).toString());
					
					ec2Service.insertCFData_CSV(primaryKey, json);
				}
					
				// 호출한 행에 SKU가 있다면 그 행은 Key값이다
				if (line.contains(keySplit)) {
					keys = splitString(line);
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
	
	
	public static String[] splitString(String line) {
	
		// "desc,desc" ,,,,,  와 같은 pattern 때문에 정규식 사용해야 함
		Pattern splitter = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		
		return splitter.split(line);
	}
	
	// 이거 다시
	public static JsonObject makeJsonObject(String[] keys, String[] values) {
		
		JsonObject obj 	= new JsonObject();
		String temp		= null;
		
		for(int i = 0; i < keys.length; i++) {
			
			// csv 파일에서 끝 열이 공백일 경우 행의 길이가 공백수 만큼 더 적게 들어온다
			// 공백은 임의의 값으로 채운다
			if(i >= values.length) {
				temp = NULL;
			} else {
				temp = subtractString(values[i]);
			}
 			
			if(temp.length() == 0)
				temp = NULL;

			obj.addProperty(subtractString(keys[i]), temp); // "\"value"\" -> "value"로 변환
		}
		
		System.out.println("length : " + obj.size());
		System.out.println(obj);

		return obj;
	}
	
	public static String subtractString(String str) {
		
		return str.replaceAll("\"", "");
	}
	
		
}
