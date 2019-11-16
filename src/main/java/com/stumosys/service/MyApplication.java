package com.stumosys.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.stumosys.managedBean.RestAPI;

public class MyApplication extends Application{
	  @Override
	 public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<Class<?>>();
	        s.add(CORSFilter.class);
	        s.add(EndPointService.class);
	        s.add(RestAPI.class);
	        return s;
	    }
}
