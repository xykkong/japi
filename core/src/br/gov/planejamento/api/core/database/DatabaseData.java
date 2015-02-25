package br.gov.planejamento.api.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseData implements Iterable<DataRow> {

	private ArrayList<DataRow> data = new ArrayList<DataRow>();
	private int count = 0;

	@Override
	public Iterator<DataRow> iterator() {
		return data.iterator();
	}

	public DatabaseData(ResultSet resultSet, ServiceConfiguration configs)
			throws SQLException {
		while (resultSet.next()) {
			DataRow row = new DataRow();
			for (String column : configs.getResponseFields()) {
				row.put(column, resultSet.getString(column));
			}
			data.add(row);
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
