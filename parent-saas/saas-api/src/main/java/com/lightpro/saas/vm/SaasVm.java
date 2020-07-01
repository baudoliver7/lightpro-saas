package com.lightpro.saas.vm;

import java.io.IOException;
import java.util.UUID;

import com.saas.domains.api.Saas;

public final class SaasVm {
	
	public final UUID id;
	public final String name;
	public final String shortName;
	
	public SaasVm(){
		throw new UnsupportedOperationException("#SalesVm()");
	}
	
	public SaasVm(final Saas origin) {
		try {
			this.id = origin.id();
	        this.name = origin.name();
	        this.shortName = origin.shortName();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
    }
}
