package br.gov.planejamento.api.core.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.interfaces.IJoinable;
import br.gov.planejamento.api.core.interfaces.IServiceConfigurationAndFiltersContainer;
import br.gov.planejamento.api.core.utils.StringUtils;

public class ServiceJoiner {
	private IJoinable[] joinables;
	
	public ServiceJoiner(IJoinable...joinables){
		this.joinables = joinables;
	}
	
	public DatabaseData getAllFiltered() throws ApiException{
		StringBuilder query = new StringBuilder("SELECT ");
		StringBuilder queryCount = new StringBuilder("SELECT count(*) AS quantity ");
		List<Filter> filters = new ArrayList<>();
		RequestContext context = RequestContext.getContext();
		int cont=0;
		Map<String, IServiceConfigurationAndFiltersContainer> mapContainerAlias =
				new HashMap<String, IServiceConfigurationAndFiltersContainer>();
		for(IJoinable joinable : joinables){
			filters.addAll(joinable.getService().getFilters());
			filters.addAll(joinable.getFilters());
			ServiceConfiguration jServiceConfiguration = joinable.getServiceConfiguration();
			ServiceConfiguration jSServiceConfiguration = joinable.getService().getServiceConfiguration();
			List<String> values = jServiceConfiguration.getValidOrderByStringValues();
			values.addAll(jSServiceConfiguration.getValidOrderByStringValues());
			context.addAvailableOrderByValues(values);
			
			cont++;
			if(cont==1) query.append("generated_alias_"+cont+".");
			
			query.append(StringUtils.join(", generated_alias_"+cont+".", jServiceConfiguration.getResponseFields()));
			query.append(", generated_secondary_alias_"+cont+".");
			query.append(StringUtils.join(", generated_secondary_alias_"+cont+".", jSServiceConfiguration.getResponseFields()));
			mapContainerAlias.put("generated_alias_"+cont, joinable);
			mapContainerAlias.put("generated_secondary_alias_"+cont, 
					(IServiceConfigurationAndFiltersContainer) joinable.getService());
		}
		Map<String, ServiceConfiguration> mapConfigAlias = queryJoin(query);
		queryJoin(queryCount);

		String whereStatement = Service.getWhereStatement(mapContainerAlias);
		query.append(whereStatement);
		queryCount.append(whereStatement);
		
		String orderValue = context.getOrderValue();
		Service.endPageQuery(orderValue, query, mapConfigAlias.values());
		
		ServiceConfiguration[] configs = new ServiceConfiguration[joinables.length];
		
		for(int i=0; i<configs.length; i++){
			configs[i] = joinables[i].getServiceConfiguration();
			configs[i+joinables.length-1] = joinables[i].getService().getServiceConfiguration();
		}
		System.out.println(query.toString());
		return Service.executeQuery(filters, query.toString(), queryCount.toString(), mapConfigAlias, configs);
		
	}

	private Map<String, ServiceConfiguration> queryJoin(StringBuilder query) {
		Map<String, ServiceConfiguration> mapAliasConfig = new HashMap<String, ServiceConfiguration>();
		int cont=0;
		query.append(" FROM ");
		for(IJoinable joinable : joinables){
			cont++;
			ServiceConfiguration config = joinable.getServiceConfiguration();
			
			config.appendSchemaDotTable(query);
			query.append(" AS generated_alias_");
			query.append(cont);
			mapAliasConfig.put("generated_alias_"+cont, config);
			
			query.append(" JOIN ");			
			joinable.getService().getServiceConfiguration().appendSchemaDotTable(query);
			mapAliasConfig.put("generated_secondary_alias_"+cont, joinable.getService().getServiceConfiguration());
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
		return mapAliasConfig;
	}
}
