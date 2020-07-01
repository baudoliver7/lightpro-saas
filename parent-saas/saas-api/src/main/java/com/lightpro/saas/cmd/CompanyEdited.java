package com.lightpro.saas.cmd;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyEdited {
	
	private final UUID id;
	private final String denomination;
	private final String shortName;
	private final String rccm;
	private final String ncc;
	private final String siegeSocial;
	private final String bp;
	private final String tel;
	private final String fax;
	private final String email;
	private final String webSite;
	private final String logo;
	private final String currencyId;
	
	public CompanyEdited(){
		throw new UnsupportedOperationException("#CompanyEdited()");
	}
	
	@JsonCreator
	public CompanyEdited( @JsonProperty("id") final UUID id,
						  @JsonProperty("denomination") final String denomination,
						  @JsonProperty("shortName") final String shortName,
						  @JsonProperty("rccm") final String rccm,
						  @JsonProperty("ncc") final String ncc,
						  @JsonProperty("siegeSocial") final String siegeSocial,
						  @JsonProperty("bp") final String bp,
						  @JsonProperty("tel") final String tel,
						  @JsonProperty("fax") final String fax,
						  @JsonProperty("email") final String email,
						  @JsonProperty("webSite") final String webSite,
						  @JsonProperty("logo") final String logo,
						  @JsonProperty("currencyId") final String currencyId){
		
		this.id = id;
		this.denomination = denomination;
		this.shortName = shortName;
		this.rccm = rccm;
		this.ncc = ncc;
		this.siegeSocial = siegeSocial;
		this.bp = bp;
		this.tel = tel;
		this.fax = fax;
		this.email = email;
		this.webSite = webSite;
		this.logo = logo;
		this.currencyId = currencyId;
	}
	
	public UUID id(){
		return this.id;
	}
	public String getDenomination(){
		return denomination;
	}
	
	public String rccm(){
		return rccm;
	}
	
	public String ncc(){
		return ncc;
	}
	
	public String siegeSocial(){
		return siegeSocial;
	}
	
	public String bp(){
		return bp;
	}
	
	public String tel(){
		return tel;
	}
	
	public String fax(){
		return fax;
	}
	
	public String email(){
		return email;
	}
	
	public String webSite(){
		return webSite;
	}
	
	public String logo(){
		return logo;
	}
	
	public String currencyId(){
		return currencyId;
	}
	
	public String shortName(){
		return shortName;
	}
}
