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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infrastructure.core.PaginationSet;
import com.lightpro.saas.vm.ModuleVm;
import com.securities.api.Company;
import com.securities.api.Module;
import com.securities.api.ModuleStatus;
import com.securities.api.Modules;
import com.securities.api.Secured;

@Path("/saas/company/{companyid}/module")
public class ModuleRs extends SaasBaseRs {
	
	private transient UUID companyId;
	
	@PathParam("companyid")
	public void setCompanyOwner(final UUID id) throws IOException {
		companyId = id;
	}
	
	private Company company() throws IOException{
		return saas().companies().get(companyId);
	}
	
	@GET
	@Secured
	@Path("/subscribed/{moduleTypeId}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSingleModuleSubscribed(@PathParam("moduleTypeId") final Integer moduleTypeId) throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Module item = company().modulesSubscribed().get(moduleTypeId);

						return Response.ok(new ModuleVm(item)).build();					
					}
				});		
	}
	
	@GET
	@Secured
	@Path("/subscribed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModulesSubscribed() throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<ModuleVm> modulesVm = company().modulesSubscribed().all()
										.stream()
								 		.map(m -> new ModuleVm(m))
								 		.collect(Collectors.toList());

						return Response.ok(modulesVm).build();					
					}
				});		
	}	
	
	@GET
	@Secured
	@Path("/subscribed/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response searchSubscribed( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Modules container = company().modulesSubscribed();
						
						List<ModuleVm> itemsVm = container.find(page, pageSize, filter).stream()
															 .map(m -> new ModuleVm(m))
															 .collect(Collectors.toList());
													
						long count = container.count(filter);
						PaginationSet<ModuleVm> pagedSet = new PaginationSet<ModuleVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
	
	@GET
	@Secured
	@Path("/available")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModulesAvailable() throws IOException {		
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<ModuleVm> modulesVm = company().modulesProposed().all()
										.stream()
								 		.map(m -> new ModuleVm(m))
								 		.collect(Collectors.toList());

						return Response.ok(modulesVm).build();					
					}
				});		
	}
	
	@GET
	@Secured
	@Path("/available/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response searchAvailables( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Modules container = company().modulesProposed();
						
						List<ModuleVm> itemsVm = container.find(page, pageSize, filter).stream()
															 .map(m -> new ModuleVm(m))
															 .collect(Collectors.toList());
													
						long count = container.count(filter);
						PaginationSet<ModuleVm> pagedSet = new PaginationSet<ModuleVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
	
	@GET
	@Secured
	@Path("/installed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModulesInstalled() throws IOException {	
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<ModuleVm> modulesVm = company().modulesInstalled().all()
								.stream()
						 		.map(m -> new ModuleVm(m))
						 		.collect(Collectors.toList());		

						return Response.ok(modulesVm).build();			
					}
				});			
	}
	
	@GET
	@Secured
	@Path("/installed/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response searchInstalled( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Modules container = company().modulesInstalled();
						
						List<ModuleVm> itemsVm = container.find(page, pageSize, filter).stream()
															 .map(m -> new ModuleVm(m))
															 .collect(Collectors.toList());
													
						long count = container.count(filter);
						PaginationSet<ModuleVm> pagedSet = new PaginationSet<ModuleVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
	
	@GET
	@Secured
	@Path("/used")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModulesUsed() throws IOException {	
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<ModuleVm> modulesVm = company().modulesInstalled().of(ModuleStatus.ACTIVATED).all()
								.stream()
						 		.map(m -> new ModuleVm(m))
						 		.collect(Collectors.toList());		

						return Response.ok(modulesVm).build();			
					}
				});			
	}
	
	@GET
	@Secured
	@Path("/used/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response searchUsed( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Modules container = company().modulesInstalled().of(ModuleStatus.ACTIVATED);
						
						List<ModuleVm> itemsVm = container.find(page, pageSize, filter).stream()
															 .map(m -> new ModuleVm(m))
															 .collect(Collectors.toList());
													
						long count = container.count(filter);
						PaginationSet<ModuleVm> pagedSet = new PaginationSet<ModuleVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
	
	@POST
	@Secured
	@Path("/{moduleTypeId}/subscribe")
	@Produces({MediaType.APPLICATION_JSON})
	public Response subscribeModule(@PathParam("moduleTypeId") int moduleTypeId) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Module module = company().modulesProposed().get(moduleTypeId);						
						module.subscribe();
						
						log.info(String.format("Souscription au module %s", module.name()));
						return Response.status(Response.Status.OK).build();	
					}
				});					
	}
	
	@POST
	@Secured
	@Path("/{moduleTypeId}/unsubscribe")
	@Produces({MediaType.APPLICATION_JSON})
	public Response removeModule(@PathParam("moduleTypeId") final int moduleTypeId) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Module module = company().modulesSubscribed().get(moduleTypeId);
						
						if(module.equals(saas()))
							throw new IllegalArgumentException("Vous ne pouvez pas désouscrire au module SAAS courant !");
						
						module.unsubscribe();
						
						log.info(String.format("Désinscription au module %s", module.name()));
						return Response.status(Response.Status.OK).build();
					}
				});			
	}
	
	@POST
	@Secured
	@Path("/{moduleTypeId}/activate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response activateModule(@PathParam("moduleTypeId") final int moduleTypeId) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Module module = company().modulesSubscribed().get(moduleTypeId);					
						module.activate(true);
						
						log.info(String.format("Activation du module %s", module.name()));
						return Response.status(Response.Status.OK).build();
					}
				});			
	}
	
	@POST
	@Secured
	@Path("/{moduleTypeId}/unactivate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response unactivateModule(@PathParam("moduleTypeId") final int moduleTypeId) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Module module = company().modulesSubscribed().get(moduleTypeId);
						
						if(module.equals(saas()))
							throw new IllegalArgumentException("Vous ne pouvez pas désactiver le module SAAS courant !");
						
						module.activate(false);
						
						log.info(String.format("Désactivaction du module %s", module.name()));
						return Response.status(Response.Status.OK).build();
					}
				});			
	}
}
