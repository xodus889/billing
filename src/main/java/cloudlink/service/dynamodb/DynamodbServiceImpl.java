package cloudlink.service.dynamodb;

import java.util.Arrays;
import java.util.Map;

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

import cloudlink.domain.dynamodb.DynamodbDomain;

public class DynamodbServiceImpl implements DynamodbService{

	/**
	 * 여긴 나중에 정리
	 */
	// --------------------------------------
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
			new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2")).build();



//	
//	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build(); 

	DynamoDB dynamoDB = new DynamoDB(client);
	
	@Override
	public void createTable() {

		try {

			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDB.createTable(DynamodbDomain.TABLE_NAME,
					Arrays.asList(new KeySchemaElement(DynamodbDomain.PRIMARY_KEY, KeyType.HASH), new KeySchemaElement(DynamodbDomain.SORT_KEY, KeyType.RANGE)),
					Arrays.asList(new AttributeDefinition(DynamodbDomain.PRIMARY_KEY, ScalarAttributeType.S), new AttributeDefinition(DynamodbDomain.SORT_KEY, ScalarAttributeType.S)),
					new ProvisionedThroughput(1L, 1L));
			
			table.waitForActive();
			System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void insertData(String primaryKey, String sortKey, String version, Map<String, String> map) {

		System.out.println("primaryKey: " + primaryKey);
		System.out.println("sortKey: " + sortKey);
		System.out.println("version: " + version);
		
		Table table	= dynamoDB.getTable(DynamodbDomain.TABLE_NAME);
		Item item 	= new Item().withPrimaryKey(DynamodbDomain.PRIMARY_KEY, primaryKey, DynamodbDomain.SORT_KEY, sortKey);
		
		item.withString(DynamodbDomain.VERSION, version);
		item.withString(DynamodbDomain.SORT_KEY, sortKey);
		
		map.forEach((k, v) -> {
			item.withString(k, v);
		});
		
		table.putItem(item);
		
	}

	public void scanCFData() {
		// TODO Auto-generated method stub

	}

}
