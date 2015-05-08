package br.gov.planejamento.api.core.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.utils.StringUtils;

public class ServiceJoinner {
	private Joinable[] joinables;
	
	public ServiceJoinner(Joinable...joinables){
		this.joinables = joinables;
	}
	
	public DatabaseData getAllFiltered() throws ApiException{
		StringBuilder query = new StringBuilder("SELECT ");
		StringBuilder queryCount = new StringBuilder("SELECT count(*) AS quantity ");
		int cont=0;
		for(Joinable joinable : joinables){
			cont++;
			if(cont==1) query.append("generated_alias_"+cont+".");
			query.append(StringUtils.join(", generated_alias_"+cont+".", joinable.getServiceConfiguration().getResponseFields()));
			cont++;
		}
		Map<ServiceConfiguration, String> mapConfigAlias = queryJoin(query);
		queryJoin(queryCount);
		
		RequestContext context = RequestContext.getContext();
		ArrayList<Filter> filtersFromRequest = context.getFilters();
		query.append(Service.getWhereStatement(filtersFromRequest, mapConfigAlias));
		queryCount.append(Service.getWhereStatement(filtersFromRequest, mapConfigAlias));
		
		
		String orderByValue = context.getOrderByValue();
		String orderValue = context.getOrderValue();
		Service.endPageQuery(orderByValue, orderValue, query);
		
		ServiceConfiguration[] configs = new ServiceConfiguration[joinables.length];
		
		for(int i=0; i<configs.length; i++){
			configs[i] = joinables[i].getServiceConfiguration();
			configs[i+joinables.length-1] = joinables[i].joinnable().getServiceConfiguration();
		}
		return Service.executeQuery(query.toString(), queryCount.toString(), mapConfigAlias, configs);
		
	}

	private Map<ServiceConfiguration, String> queryJoin(StringBuilder query) {
		Map<ServiceConfiguration, String> retorno = new HashMap<ServiceConfiguration, String>();
		int cont=0;
		query.append(" FROM ");
		for(Joinable joinable : joinables){
			cont++;
			ServiceConfiguration config = joinable.getServiceConfiguration();
			
			config.appendSchemaDotTable(query);
			query.append(" AS generated_alias_");
			query.append(cont);
			retorno.put(config, "generated_alias_"+cont);
			
			
			query.append(" JOIN ");			
			joinable.joinnable().getServiceConfiguration().appendSchemaDotTable(query);
			retorno.put(joinable.joinnable().getServiceConfiguration(), "generated_secondary_alias_"+cont);
			query.append(" AS generated_secondary_alias_");
			query.append(cont);
			query.append(" ON generated_alias_");
			query.append(cont);
			query.append(".");
			query.append(joinable.joinField());
			query.append("=");
			query.append("generated_secondary_alias_");
			query.append(cont);
			query.append(".");
			query.append(joinable.joinFieldReference());
			query.append(" ");
		}
		
		query.append(" WHERE ");
		return retorno;
	}
}
