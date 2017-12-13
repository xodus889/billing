package cloudlink.service.billing.ec2;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.google.gson.JsonObject;

@Service
public class EC2BillingTableServiceImpl implements EC2BillingTableService {

	/**
	 * 여긴 나중에 정리
	 */
	// --------------------------------------
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
			new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2")).build();

	DynamoDB dynamoDB = new DynamoDB(client);
	// --------------------------------------
	
	
	
	/*************************************************************
	 **************************** CSV ****************************
	 *************************************************************/
	public static final String CSV_TABLE_NAME = "CSV_AWS_ECS";
	public static final String CSV_PARTITION_KEY = "RateCode";
	public static final String CSV_SORT_KEY = "";

	@Override
	public void createEC2Table_CSV() {

		try {

			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDB.createTable(CSV_TABLE_NAME,
					Arrays.asList(new KeySchemaElement(CSV_PARTITION_KEY, KeyType.HASH)),
					Arrays.asList(new AttributeDefinition(CSV_PARTITION_KEY, ScalarAttributeType.S)),
					new ProvisionedThroughput(10L, 10L));
			
			table.waitForActive();
			System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void insertEC2Data_CSV(String primaryKey, JsonObject obj) {

		Table table = dynamoDB.getTable(CSV_TABLE_NAME);
		System.out.println(obj.toString());
		
		table.putItem(new Item().withPrimaryKey(CSV_PARTITION_KEY, primaryKey).withJSON("info", obj.toString()));
		
	}

	@Override
	public void scanEC2Data() {
		// TODO Auto-generated method stub

	}

}
