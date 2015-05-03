package br.gov.planejamento.api.exemplos.module;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.base.Module;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.SwaggerResponse;

@Path("/")
@ApplicationPath("/")
public class BaseModule extends Module {
	
	/*
     * Dentro da classe que define o módulo, devemos configurar o sistema de Documentação.
     *
     * No Path definimos o endereço por onde o json será encontrado. Esse Path deve ser definido dentro de uma constante configurada no "commons"
     *
     * O método extractDocumentation lê todo o arquivo que é apontado (no caso, a camada request do módulo "exemplos") e, a partir das annotations, gera o json.
     */
	
	@GET	
	@Path(CommonConstants.DOCS)
	public SwaggerResponse getDocs() throws ApiException {
		return extractDocumentation("br.gov.planejamento.api.exemplos.request");
	}
}

