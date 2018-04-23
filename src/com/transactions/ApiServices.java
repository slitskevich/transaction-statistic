package com.transactions;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class ApiServices extends ResourceConfig {
	
	public ApiServices() {
		packages("com.fasterxml.jackson.jaxrs.json");
        packages("com.transactions.resources");
	}
}
