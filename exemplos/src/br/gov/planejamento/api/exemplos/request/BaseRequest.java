package br.gov.planejamento.api.exemplos.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.BaseConstants;
import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Module;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.DateEqualFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.parameters.DateParam;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.exemplos.resource.BaseResource;
import br.gov.planejamento.api.exemplos.service.BaseService;

@Path("/")
@Module(CommonConstants.Modules.LICITACOES)
public class BaseRequest {	
	/*
	 *  Criamos, primeiramente, uma instancia da classe BaseService para podermos requisitar os dados do banco
	 *  através de um metódo estático .getAllFiltered()
	 *  
	 */	
	private BaseService bService = new BaseService();
	
	/*
	 * Dentro da annotation @About nós preciamos setar o nome do módulo assim como a sua descrição
	 * podemos, se for interessante, setar uma query de exemplo do método ainda dentro da annotation @About
	 * 
	 */
	
	@GET
	@About(name = "base", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?algum_retorno_do_banco=70024")
	@Path(BaseConstants.Requests.List.BASE)
	
	/*
	 * O método abaixo retorno uma Response que será exibida para o cliente.
	 * 
	 * Precisamos setar os parametros de pesquisa dos campos que serão pegos pelo BaseService através das annotations @Parameter
	 */
	public ResourceListResponse<BaseResource> base(
			@Parameter(name = "nome_da_coluna_1_na_tabela", description = "Uma informação qualquer proveniente do banco") String nomeDoDado,
			@Parameter(name = "nome_da_coluna_2_na_tabela", description = "Outra informaçao qualquer") String nomeDoDado2,
			@Parameter(name = "nome_da_coluna_x_na_tabela", description = "Ultima informaçao.") String nomeDoDadoX)
			throws ApiException {
	
	/*
     * O método addFilter descrito abaixo é o responsável por adicionar na Session os filtros que serão feitos no banco
     * de dados.
     *
     * os argumentos são do tipo Filter, e a API reconhece três possiveis como padrão, podendo ser criados novos pelo
     * usuário.
     *
     */

		RequestContext.getContext().addFilter(
				new EqualFilter(Integer.class, "nome_da_coluna_1_na_tabela as filtro"),
				new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_da_coluna_2_na_tabela")),
				new DateEqualFilter(DateParam.class, new DatabaseAlias("nome_da_coluna_x_na_tabela")));

		DatabaseData dados = bService.getAllFiltered();
		return ResourceListResponse.factory(dados, BaseResource.class);
	}

	
}