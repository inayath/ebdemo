package org.example.test;

import org.apache.camel.Exchange;

public class AggregateResponseWrapper {
	
	public static void wrapAggregateResponse(Exchange exchange) {
		String body = (String) exchange.getIn().getBody();
		body = "<AggregationResult>" + body + "</AggregationResult>";
		exchange.getIn().setBody(body);
	}

}
