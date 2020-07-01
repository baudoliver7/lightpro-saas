package com.lightpro.saas.rs;

import java.io.IOException;

import com.saas.domains.api.Saas;
import com.saas.domains.impl.SaasDb;
import com.securities.api.BaseRs;
import com.securities.api.Module;
import com.securities.api.ModuleType;

public abstract class SaasBaseRs extends BaseRs {
	
	public SaasBaseRs() {
		super(ModuleType.SAAS);
	}

	protected Saas saas() throws IOException {
		return saas(currentModule);
	}
	
	protected Saas saas(Module module) throws IOException {
		return new SaasDb(base, module);
	}
}
