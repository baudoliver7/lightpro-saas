package com.lightpro.saas.rs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.common.utilities.convert.TimeConvert;
import com.infrastructure.core.PaginationSet;
import com.infrastructure.core.Period;
import com.infrastructure.core.impl.PeriodBase;
import com.lightpro.saas.vm.EventLogVm;
import com.securities.api.Company;
import com.securities.api.EventLogCategory;
import com.securities.api.EventLogType;
import com.securities.api.Log;
import com.securities.api.ModuleType;
import com.securities.api.Secured;
import com.securities.api.User;

@Path("/saas/log")
public class LogRs extends SaasBaseRs {
	
	@GET
	@Secured
	@Path("/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Response search( @QueryParam("page") int page, 
							@QueryParam("pageSize") int pageSize, 
							@QueryParam("filter") String filter,
							@QueryParam("start") Date start,
							@QueryParam("end") Date end,
							@QueryParam("companyId") UUID companyId,
							@QueryParam("categoryId") int eventLogCategoryId,
							@QueryParam("typeId") int eventLogTypeId,
							@QueryParam("moduleTypeId") int moduleTypeId,
							@QueryParam("userId") UUID userId) throws IOException {
		
		return createHttpResponse(
				new Callable<Response>(){
					@Override
					public Response call() throws IOException {
						
						Company currentCompany = saas().companies().get(companyId);
						EventLogCategory eventLogCategory = EventLogCategory.get(eventLogCategoryId);
						EventLogType eventLogType = EventLogType.get(eventLogTypeId);
						LocalDate stateDate = TimeConvert.toLocalDate(start, ZoneId.systemDefault());
						LocalDate endDate = TimeConvert.toLocalDate(end, ZoneId.systemDefault());
						User user = currentCompany.moduleAdmin().membership().build(userId);
						Period period = new PeriodBase(stateDate, endDate);	
						
						ModuleType moduleType = ModuleType.get(moduleTypeId);
						
						Log log = currentCompany.log().of(period).of(eventLogCategory).of(eventLogType).ofUser(user.username()).ofModule(moduleType);
						
						List<EventLogVm> itemsVm = log.find(page, pageSize, filter).stream()
															 .map(m -> new EventLogVm(m))
															 .collect(Collectors.toList());
													
						long count = log.count(filter);
						PaginationSet<EventLogVm> pagedSet = new PaginationSet<EventLogVm>(itemsVm, page, count);
						
						return Response.ok(pagedSet).build();
					}
				});	
				
	}
}
