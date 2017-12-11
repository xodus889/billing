package com.amazonaws.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cloudlink.service.billing.ec2.EC2BillingTableService;

public class DynamdoDBTest {

	@Autowired
	EC2BillingTableService ec2Service;
	
	
	@Test
	public void test() {

		ec2Service.createEC2Table();
	}

}
