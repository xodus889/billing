package cloudlink.service.billing.ec2;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

@Service
public class EC2BillingTableServiceImpl implements EC2BillingTableService {

	/**
	 * 여긴 나중에 정리
	 */
	//--------------------------------------
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
			new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2")).build();

	DynamoDB dynamoDB = new DynamoDB(client);
	//--------------------------------------
	public static final String TABLE_NAME 	= "BILLING_EC2";
	public static final String TERM_TYPE 	= "TermType";
	public static final String OPERATION 	= "Operation";
	public static final String USAGE_TYPE 	= "UsageType";
	
	
	@Override
	public void createEC2Table() {


        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(TABLE_NAME,
                Arrays.asList(new KeySchemaElement(TERM_TYPE, KeyType.HASH), new KeySchemaElement(OPERATION, KeyType.RANGE), new KeySchemaElement(USAGE_TYPE, KeyType.RANGE)), 
                Arrays.asList(new AttributeDefinition(TERM_TYPE, ScalarAttributeType.S), new AttributeDefinition(OPERATION, ScalarAttributeType.S), new AttributeDefinition(USAGE_TYPE, ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }

	}

	@Override
	public void insertEC2Data() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scanEC2Data() {
		// TODO Auto-generated method stub

	}

}
