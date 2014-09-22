package br.gov.planejamento.api.licitacoes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.gov.planejamento.api.core.base.ConnectionManager;

public class LicitacaoService {
	
	public String licitacoes() throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM public.licitacoes");
		ResultSet rs = pst.executeQuery();
		
		String retorno = "";
		
		while(rs.next()) {
			retorno += rs.getString("id") + " " + rs.getString("nome") + " ";
		}
		
		return retorno;
	}
	
}
