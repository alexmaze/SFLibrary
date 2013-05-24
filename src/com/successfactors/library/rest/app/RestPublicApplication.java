package com.successfactors.library.rest.app;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class RestPublicApplication extends JaxRsApplication {
	
	public RestPublicApplication(Context context) {
		
        super(context);
        
		System.out.println("----------------------Public-Server-On----------------------");
        this.add(new BookApplication());
//        this.add(new BorrowApplication());
//        this.add(new RecommendApplication());
//        this.add(new OrderApplication());
//        this.add(new UserApplication());
		System.out.println("---------------------Add-Applications-End--------------------");
        
    }
	
}