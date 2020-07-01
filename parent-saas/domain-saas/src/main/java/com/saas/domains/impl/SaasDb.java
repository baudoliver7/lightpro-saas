package com.saas.domains.impl;

import java.io.IOException;
import java.util.UUID;

import com.infrastructure.core.EntityBase;
import com.infrastructure.datasource.Base;
import com.saas.domains.api.Saas;
import com.securities.api.Companies;
import com.securities.api.Company;
import com.securities.api.Feature;
import com.securities.api.FeatureSubscribed;
import com.securities.api.Features;
import com.securities.api.Indicators;
import com.securities.api.Log;
import com.securities.api.Module;
import com.securities.api.ModuleType;
import com.securities.impl.CompaniesDb;

public final class SaasDb extends EntityBase<Saas, UUID> implements Saas {

	private final transient Base base;
	private final transient Module origin;
	
	public SaasDb(final Base base, final Module origin){
		super(origin.id());
		this.base = base;
		this.origin = origin;
	}

	@Override
	public Company company() throws IOException {
		return origin.company();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public Module install() throws IOException {
		Module module = origin.install();
		return new SaasDb(base, module);
	}

	@Override
	public boolean isInstalled() {
		return origin.isInstalled();
	}

	@Override
	public boolean isSubscribed() {
		return origin.isSubscribed();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public int order() throws IOException {
		return origin.order();
	}

	@Override
	public String shortName() throws IOException {
		return origin.shortName();
	}

	@Override
	public ModuleType type() throws IOException {
		return origin.type();
	}

	@Override
	public Module uninstall() throws IOException {
		
		// vérifier qu'il n'existe pas de société gérée
		if(companies().count() > 1)
			throw new IllegalArgumentException("Impossible de désinstaller ce module : vous devez supprimer premièrement les sociétés que vous gérez !");
		
		Module module = origin.uninstall();
		return new SaasDb(base, module);
	}

	@Override
	public void activate(boolean active) throws IOException {
		origin.activate(active);
	}
	
	@Override
	public Companies companies() throws IOException {
		return new CompaniesDb(base, company());
	}

	@Override
	public boolean isActive() {
		return origin.isActive();
	}

	@Override
	public Features featuresAvailable() throws IOException {
		return origin.featuresAvailable();
	}

	@Override
	public Features featuresSubscribed() throws IOException {
		return origin.featuresSubscribed();
	}

	@Override
	public Indicators indicators() throws IOException {
		return origin.indicators();
	}

	@Override
	public Module subscribe() throws IOException {
		return origin.subscribe();
	}

	@Override
	public FeatureSubscribed subscribeTo(Feature feature) throws IOException {
		return origin.subscribeTo(feature);
	}

	@Override
	public Module unsubscribe() throws IOException {
		return origin.unsubscribe();
	}

	@Override
	public void unsubscribeTo(Feature feature) throws IOException {
		origin.unsubscribeTo(feature);
	}

	@Override
	public Features featuresProposed() throws IOException {
		return origin.featuresProposed();
	}

	@Override
	public Log log() throws IOException {
		return origin.log();
	}
}
