package org.example.test;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Aggregation strategy to be used with "Splitter pattern" as the 'Composed
 * Message pattern'. Concatenates old and new bodies (Strings) from splitted
 * messages.
 * 
 * NOTE: Please use the AggregateResponseWrapper bean to wrap the body into the
 * 'AggregateResponse' root element. This should always be done after the split
 * in the camel route.
 * 
 * @author manosh
 * @author Shumon Sharif
 *
 */
public class AddAggregationStrategy implements AggregationStrategy {

	/** The Constant REGEX_XML_PROCESSING_INST. */
	public static final String REGEX_XML_PROCESSING_INST = "<\\?xml.*?\\?>";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		if (newExchange == null) {
			return oldExchange;
		}
		// Check if it is the last split
//		Boolean splitComplete = newExchange.getProperty(Exchange.SPLIT_COMPLETE, Boolean.class);

		StringBuffer sb = new StringBuffer();
		if (oldExchange == null) {
			// if last split, wrap in the parent element
			sb.append(removeXMLProlog(newExchange.getIn().getBody(String.class)));
			newExchange.getIn().setBody(sb.toString());
			newExchange.setException(null);
			return newExchange;
		}
		// Combine old and new payloads
		sb.append(removeXMLProlog(oldExchange.getIn().getBody(String.class)));
		sb.append(removeXMLProlog(newExchange.getIn().getBody(String.class)));
		oldExchange.getIn().setBody(sb.toString());
		return oldExchange;
	}

	/**
	 * Removes the XML prolog.
	 *
	 * @param xml the xml
	 * @return the string
	 */
	public static String removeXMLProlog(String xml) {
		String returnVal = xml;
		if (xml.startsWith("<?xml")) {
			returnVal = xml.replaceAll(REGEX_XML_PROCESSING_INST, "");
		}
		return returnVal;
	}
}
