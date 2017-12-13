package cloudlink.service.billing.ec2;

import com.google.gson.JsonObject;

public interface EC2BillingTableService {

	/*************************************************************
	 **************************** CSV ****************************
	 *************************************************************/
	
	/**
	 *  create table
	 */
	public void createEC2Table_CSV();
	
	/**
	 * insert data
	 */
	public void insertEC2Data_CSV(String primaryKey, JsonObject obj);
	
	/**
	 * Scan data
	 */
	public void scanEC2Data();
	
	
	/*************************************************************
	 **************************** JSON ****************************
	 *************************************************************/
	
}
