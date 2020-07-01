package com.saas.domains.api;

import java.io.IOException;

import com.securities.api.Companies;
import com.securities.api.Module;

public interface Saas extends Module {
	Companies companies() throws IOException;
}
