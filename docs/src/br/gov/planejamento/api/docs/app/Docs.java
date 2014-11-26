package br.gov.planejamento.api.docs.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import br.gov.planejamento.api.core.base.Session;

@ApplicationPath("/")
public class Docs extends Application {
	
	@Path("docs/licitacoes")
	public String licitacoesDocs(){
		Session session = Session.getCurrentSession();
		String relativePath = session.getRelativePath();
		/*
		 * TODO procurar o método com o path == relativePath aqui e
		 * pegar as annotations dele para criar a documentação
		 * usar padrão swagger
		 * */
		return null;
		
	}
}

