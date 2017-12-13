package cloudlink.service.billing.cf;

import com.google.gson.JsonObject;

public interface CFBillingTableService {

	/*************************************************************
	 **************************** CSV ****************************
	 *************************************************************/
	
	/**
	 *  create table
	 */
	public void createCFTable_CSV();
	
	/**
	 * insert data
	 */
	public void insertCFData_CSV(String primaryKey, JsonObject obj);
	
	/**
	 * Scan data
	 */
	public void scanCFData();
	
	
	/*************************************************************
	 **************************** JSON ****************************
	 *************************************************************/
	
}
