package com.lightpro.saas.rs;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infrastructure.core.PaginationSet;
import com.lightpro.saas.cmd.CompanyEdited;
import com.lightpro.saas.vm.CompanyVm;
import com.securities.api.Companies;
import com.securities.api.Company;
import com.securities.api.Currency;
import com.securities.api.Secured;

@Path("/saas/company")
public class CompanyRs extends SaasBaseRs {

	private Companies companies() throws IOException{
		return saas().companies();		
	}
	
	@GET
	@Path("/{id}")
	@Secured
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSingle(@PathParam("id") final UUID id) throws IOException {	
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Company item = companies().get(id);
						return Response.ok(new CompanyVm(item, item.equals(currentCompany))).build();						
					}
				});			
	}	
	
	@POST
	@Secured
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(final CompanyEdited cmd) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Currency currency = currentCompany.currencies().build(cmd.currencyId());
						
						Company item = companies().add(cmd.getDenomination(), 
										 cmd.shortName(),
									     cmd.rccm(), 
									     cmd.ncc(), 
									     cmd.siegeSocial(), 
									     cmd.bp(), 
									     cmd.tel(), 
									     cmd.fax(),
									     cmd.email(), 
									     cmd.webSite(), 
									     cmd.logo(),
									     currency);
				
						log.info(String.format("Création de l'entreprise %s", cmd.getDenomination()));
						return Response.ok(new CompanyVm(item, false)).build();
					}
				});			
	}
	
	@PUT
	@Path("/{id}")
	@Secured
	@Produces({MediaType.APPLICATION_JSON})
	public Response update(@PathParam("id") final UUID id, final CompanyEdited cmd) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Company item = companies().get(id);
						
						Currency currency = currentCompany.currencies().build(cmd.currencyId());
						
						item.update(cmd.getDenomination(), 
										 cmd.shortName(),
									     cmd.rccm(), 
									     cmd.ncc(), 
									     cmd.siegeSocial(), 
									     cmd.bp(), 
									     cmd.tel(), 
									     cmd.fax(),
									     cmd.email(), 
									     cmd.webSite(), 
									     cmd.logo(),
									     currency);
				
						log.info(String.format("Mise à jour des données de l'entreprise %s", cmd.getDenomination()));
						return Response.status(Response.Status.OK).build();
					}
				});			
	}
	
	@GET
	@Secured
	@Path("/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response search( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						List<CompanyVm> itemsVm = companies().find(page, pageSize, filter).stream()
															 .map(m -> new CompanyVm(m, m.equals(currentCompany)))
															 .collect(Collectors.toList());
													
						long count = saas().companies().count(filter);
						PaginationSet<CompanyVm> pagedSet = new PaginationSet<CompanyVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
	
	@DELETE
	@Secured
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response delete(@PathParam("id") final UUID id) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
				
						Company item = companies().get(id);
						
						if(item.equals(currentCompany))
							throw new IllegalArgumentException("Vous ne pouvez pas supprimer votre propre entreprise !");
						
						companies().delete(item);
						
						log.info(String.format("Suppression de l'entreprise %s", item.denomination()));
						return Response.status(Response.Status.OK).build();
					}
				});	
	}
}
