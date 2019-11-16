package com.stumosys.service;

import java.io.IOException;












import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.mail.imap.Utility.Condition;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter{

	/* @Override
	    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cresp) {

	        cresp.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
	        cresp.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
	        cresp.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
	        cresp.getHttpHeaders().add("Access-Control-Max-Age","1000");
	        cresp.getHttpHeaders().add("Access-Control-Allow-Headers", "x-requested-with, Content-Type, origin, authorization, accept, client-security-token");
	   					
	        
	     
	        return cresp;
	    }*/
	
	 public ContainerResponse filter(ContainerRequest req, ContainerResponse containerResponse) {
		 
	        ResponseBuilder responseBuilder = Response.fromResponse(containerResponse.getResponse());
	        
	        // *(allow from all servers) OR http://crunchify.com/ OR http://example.com/
	        responseBuilder.header("Access-Control-Allow-Origin", "*")
	        
	        // As a part of the response to a request, which HTTP methods can be used during the actual request.
	        .header("Access-Control-Allow-Methods", "API, CRUNCHIFYGET, GET, POST, PUT, UPDATE, OPTIONS")
	        
	        // How long the results of a request can be cached in a result cache.
	        .header("Access-Control-Max-Age", "151200")
	        
	        // As part of the response to a request, which HTTP headers can be used during the actual request.
	        .header("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
	 
	        String requestHeader = req.getHeaderValue("Access-Control-Request-Headers");
	 
	        if (null != requestHeader && !requestHeader.equals(null)) {
	        	responseBuilder.header("Access-Control-Allow-Headers", requestHeader);
	        }
	 
	        containerResponse.setResponse(responseBuilder.build());
	        return containerResponse;
	    }
	/* 
	 public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			chain.doFilter(req, res);
		}

		public void init(FilterConfig filterConfig) {}

		public void destroy() {}*/
	/*public void filter(ContainerRequest requestContext, ContainerRequest responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		headers.add("Access-Control-Allow-Origin", "*");
		//headers.add("Access-Control-Allow-Origin", "http://52.206.206.216"); //allows CORS requests only coming from podcastpedia.org		
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");			
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
	}

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
