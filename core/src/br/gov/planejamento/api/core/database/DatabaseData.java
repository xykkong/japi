package br.gov.planejamento.api.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public class DatabaseData implements Iterable<DataRow> {

	private ArrayList<DataRow> data = new ArrayList<DataRow>();
	private int count = 0;

	@Override
	public Iterator<DataRow> iterator() {
		return data.iterator();
	}

	public DatabaseData(ResultSet resultSet, ServiceConfiguration...configs) throws ApiException {
		try {
			while (resultSet.next()) {
				DataRow row = new DataRow();
				for(ServiceConfiguration config : configs){
					for (String column : config.getResponseFields()) {
						row.put(column, resultSet.getString(column));
					}
					data.add(row);
				}
			}
		} catch (SQLException e) {
			throw new CoreException("Houve um erro ao processar o retorno do banco de dados. Verifique sua query.",e);
		}
	}

	public DatabaseData(ResultSet resultSet,
			Map<ServiceConfiguration, String> mapConfigsAlias) throws CoreException {
		try {
			while (resultSet.next()) {
				DataRow row = new DataRow();
				for(Entry<ServiceConfiguration, String> set : mapConfigsAlias.entrySet()){
					String alias = set.getValue();
					ServiceConfiguration configs = set.getKey();
					for (String column : configs.getResponseFields()) {
						row.put(column, resultSet.getString(alias+"."+column));
					}
					data.add(row);
				}
			}
		} catch (SQLException e) {
			throw new CoreException("Houve um erro ao processar o retorno do banco de dados. Verifique sua query.",e);
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
