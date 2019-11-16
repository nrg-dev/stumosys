package com.restfull.robert;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import sms.Testing;


public class RestClient {
	final static Logger logger = Logger.getLogger(RestClient.class);
	private final static String URI="https://apicmp.adreach.co/interactive_api/service/interactive_api_v2.php?sender=6281322222&recipient=8000&message=register&msg_id=1231231232&encoding=7";
	public static void main(String args[]) throws JSONException {
		Client client=Client.create();
		WebResource webresource= client.resource(URI);
	ClientResponse clientresponse=webresource.get(ClientResponse.class);//get 
	String op=clientresponse.getEntity(String.class);
	logger.debug("Client Side OutPut "+op);
/*	if(clientresponse.getStatus() == 200){
		String op=clientresponse.getEntity(String.class);
		logger.debug("Client Side OutPut "+op);
	*/}
		 /*JSONObject inputJsonObj = new JSONObject();
		  inputJsonObj.put("usename", "nrg");
		  inputJsonObj.put("pwd","nrgngl");
		ClientResponse clientresponse=webresource.accept(MediaType.APPLICATION_JSON).entity(inputJsonObj,MediaType.APPLICATION_JSON).post(ClientResponse.class);
		//logger.debug(clientresponse);
		if(clientresponse.getStatus() == 200){
			String op=clientresponse.getEntity(String.class);
			logger.debug("Client Side OutPut "+op);
		}*/
		
		/*JSONObject inputJsonObj = new JSONObject();
		  inputJsonObj.put("nfromdate", "12-06-2015");
		  inputJsonObj.put("ntodate","13-06-2015");
		  inputJsonObj.put("subject", "nrg");
		  inputJsonObj.put("noticefollower","nrgngl");
		  inputJsonObj.put("nnotice", "1234");
		  
		ClientResponse clientresponse=webresource.accept(MediaType.APPLICATION_JSON).entity(inputJsonObj,MediaType.APPLICATION_JSON).post(ClientResponse.class);
		logger.debug(clientresponse);
	
		if(clientresponse.getStatus() == 200){
			String op=clientresponse.getEntity(String.class);
			logger.debug("Client Side OutPut "+op);
		}
		
	}*/

}
