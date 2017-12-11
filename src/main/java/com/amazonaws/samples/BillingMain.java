package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;

public class BillingMain {

	public static final String CSV_PATH = "C:/Users/taeyeonkim/Desktop/work/Dev/PricingData/csv/currentVersionUrl/AmazonEC2/20171114234759.csv";
	public static final String EUC_KR = "euc-kr";
	
	public static void main(String[] args) {

		String[] keys = null;
		BufferedReader br = null;
		String keySplit = "SKU";
		String csvSplitBy = ",";
		String line;

		try {

			JsonObject obj = new JsonObject();

			br = new BufferedReader(new InputStreamReader(new FileInputStream(CSV_PATH), EUC_KR));

			for (int i = 0; i < 10; i++) {

				line = br.readLine();
				
				// Key값이 있다면 value와 함께 반환한다
				if(keys != null) 
					
				// SKU가 있다면 그 행은 Key값이다
				if (line.contains(keySplit))
					keys = line.split(csvSplitBy);

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
}
