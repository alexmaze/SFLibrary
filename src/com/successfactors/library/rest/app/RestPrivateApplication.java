package com.successfactors.library.rest.app;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class RestPrivateApplication extends JaxRsApplication {
	
	public RestPrivateApplication(Context context) {
		
        super(context);
        
		System.out.println("----------------------Private-Server-On----------------------");
        this.add(new BookApplication());
        this.add(new BorrowApplication());
        this.add(new RecommendApplication());
        this.add(new OrderApplication());
        this.add(new UserApplication());
		System.out.println("---------------------Add-Applications-End--------------------");
        		
    }
	
}