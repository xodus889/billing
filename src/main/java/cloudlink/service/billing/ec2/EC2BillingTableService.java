package cloudlink.service.billing.ec2;

public interface EC2BillingTableService {

	/**
	 *  create table
	 */
	public void createEC2Table();
	
	/**
	 * insert data
	 */
	public void insertEC2Data();
	
	/**
	 * Scan dat
	 */
	public void scanEC2Data();
	
}
