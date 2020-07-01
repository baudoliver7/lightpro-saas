package com.lightpro.saas.rs;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lightpro.saas.cmd.FeatureSubscription;
import com.lightpro.saas.vm.FeatureVm;
import com.securities.api.Feature;
import com.securities.api.FeatureType;
import com.securities.api.Features;
import com.securities.api.Module;
import com.securities.api.Secured;

@Path("/saas/company/{companyid}/module/{moduleTypeId}/feature")
public class FeatureRs extends SaasBaseRs {
	
	private transient Integer moduleTypeId;
	private transient UUID companyId;
	
	@PathParam("companyid")
	public void setCompanyId(final UUID id) throws IOException {
		companyId = id;
	}
	
	@PathParam("moduleTypeId")
	public void setModuleId(final Integer moduleTypeId) throws IOException {
		this.moduleTypeId = moduleTypeId;
	}
	
	private Module module() throws IOException {
		return saas().companies().get(companyId).modulesSubscribed().get(moduleTypeId);
	}
	
	@GET
	@Secured
	@Path("/category/proposed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCategoriesProposed() throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<FeatureVm> itemsVm = module().featuresProposed().of(FeatureType.FEATURE_CATEGORY).all()
										.stream()
								 		.map(m -> new FeatureVm(m))
								 		.collect(Collectors.toList());

						return Response.ok(itemsVm).build();					
					}
				});		
	}
	
	@GET
	@Secured
	@Path("/category/{id}/child/proposed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getChildrenOfCategoryProposed(@PathParam("id") final String id) throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Feature item = module().featuresProposed().get(id);
						
						List<FeatureVm> itemsVm = item.children()
													  .stream()
											 		  .map(m -> new FeatureVm(m))
											 		  .collect(Collectors.toList());

						return Response.ok(itemsVm).build();					
					}
				});		
	}
	
	@GET
	@Secured
	@Path("/category/subscribed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCategoriesSubscribed() throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<FeatureVm> itemsVm = module().featuresSubscribed().of(FeatureType.FEATURE_CATEGORY).all()
										.stream()
								 		.map(m -> new FeatureVm(m))
								 		.collect(Collectors.toList());

						return Response.ok(itemsVm).build();					
					}
				});		
	}
	
	@GET
	@Secured
	@Path("/category/{id}/child/subscribed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getChildrenOfCategorySubscribed(@PathParam("id") final String id) throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Feature item = module().featuresSubscribed().get(id);
						
						List<FeatureVm> itemsVm = item.children()
								  .stream()
						 		  .map(m -> new FeatureVm(m))
						 		  .collect(Collectors.toList());

						return Response.ok(itemsVm).build();					
					}
				});		
	}
	
	@POST
	@Secured
	@Path("/subscribe")
	@Produces({MediaType.APPLICATION_JSON})
	public Response subscribeModuleFeature(final FeatureSubscription subscription) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Features featuresProposed = module().featuresAvailable();
						
						for (String id : subscription.featuresToAdd()) {
							Feature item = featuresProposed.get(id);							
							module().subscribeTo(item);							
						}
						
						for (String id : subscription.featuresToDelete()) {
							Feature item = featuresProposed.get(id);
							module().unsubscribeTo(item);							
						}
						
						log.info(String.format("Souscription aux fonctionnalités du module %s", module().name()));
						return Response.status(Response.Status.OK).build();	
					}
				});					
	}
}
