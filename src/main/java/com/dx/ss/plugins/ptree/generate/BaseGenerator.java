package com.dx.ss.plugins.ptree.generate;

import javax.security.auth.login.Configuration;

public abstract class BaseGenerator {

	/** The configuration. */
    private Configuration configuration;

	public BaseGenerator(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public abstract void generate(Configuration configuration);
    
}
