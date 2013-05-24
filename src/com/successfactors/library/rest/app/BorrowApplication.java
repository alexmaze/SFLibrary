package com.successfactors.library.rest.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.successfactors.library.rest.resource.BorrowResource;

public class BorrowApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> rrcs = new HashSet<Class<?>>();
		rrcs.add(BorrowResource.class);
		return rrcs;
	}
}