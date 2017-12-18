package cloudlink.service.billing;

import java.util.Arrays;

import org.junit.Test;

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

import cloudlink.domin.bililng.ComDomain;

public class CRUDBililngTable {

	/**
	 * 여긴 나중에 정리
	 */
	// --------------------------------------
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
			new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2")).build();

	DynamoDB dynamoDB = new DynamoDB(client);


	/**
	 * table을 생성한다
	 */
	@Test
	public void createTable() {

		try {

			System.out.println("Attempting to create table; please wait...");
			Table table = dynamoDB.createTable(ComDomain.TABLE_NAME,
					Arrays.asList(new KeySchemaElement(ComDomain.PRIMARY_KEY, KeyType.HASH), new KeySchemaElement(ComDomain.SORT_KEY, KeyType.RANGE)),
					Arrays.asList(new AttributeDefinition(ComDomain.PRIMARY_KEY, ScalarAttributeType.S), new AttributeDefinition(ComDomain.SORT_KEY, ScalarAttributeType.S)),
					new ProvisionedThroughput(1L, 1L));
			
			table.waitForActive();
			System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}

	}

	/**
	 * key를 insert한다 
	 */
	public void insertData(String primaryKey, String sortKey, JsonObject obj) {

		Table table = dynamoDB.getTable(ComDomain.TABLE_NAME);
		
		Item item = new Item().withPrimaryKey(ComDomain.PRIMARY_KEY, primaryKey, ComDomain.SORT_KEY, sortKey);

		item.withString("111", "222");
		item.withString("333", "444");
		item.withString("555", "666");
		
		
		table.putItem(item);
		
	}

	public void scanCFData() {
		// TODO Auto-generated method stub

	}

}
