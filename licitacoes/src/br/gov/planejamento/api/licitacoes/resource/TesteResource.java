package br.gov.planejamento.api.licitacoes.resource;

import java.util.HashMap;

import br.gov.planejamento.api.commons.constants.LicitacaoConstants;
import br.gov.planejamento.api.commons.masks.HtmlOnlyDateMask;
import br.gov.planejamento.api.commons.routers.LicitacoesRouter;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.LinkProperty;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.masks.CPFMask;
import br.gov.planejamento.api.core.masks.MoneyMask;
import br.gov.planejamento.api.core.masks.TimeMask;
import br.gov.planejamento.api.core.utils.StringUtils;

public class TesteResource extends Resource {

	private String testeString;
	private String testeInt;
	private String testeNumeric;
	private String testeDate;
	private String testeTime;
	private String testeBoolean;
	
	private LicitacoesRouter licitacoesRouter = new LicitacoesRouter();
	
	public TesteResource(DataRow teste) {
		super(teste);
		setTesteDate(teste.get("teste_date"));
		setTesteInt(teste.get("teste_int"));
		setTesteNumeric(teste.get("teste_numeric"));
		setTesteString(teste.get("teste_string"));
		setTesteTime(teste.get("teste_time"));
		setTesteBoolean(teste.get("teste_boolean"));
	}

	/*
	 * SETTERS
	 * Métodos para definir todos os valores que um Resource precisa para existir.
	 * 
	 * Não há getters para estes valores. Tudo o que um Resource deve retornar são Properties ou Links.
	 */
	
	public void setTesteNumeric(String testeNumeric) {
		this.testeNumeric = testeNumeric;
	}	
	public void setTesteString(String testeString) {
		this.testeString = testeString;
	}	
	public void setTesteInt(String testeInt) {
		this.testeInt = testeInt;
	}	
	public void setTesteDate(String testeDate) {
		this.testeDate = testeDate;
	}	
	public void setTesteTime(String testeTime) {
		this.testeTime = testeTime;
	}
	
	public void setTesteBoolean(String testeBoolean) {
		this.testeBoolean = testeBoolean;
	}
	
	/*
	 * PROPERTIES
	 * Métodos que retornam as Properties do Resource.
	 * 
	 * Um Property simples possui somente um nome de exibição e um valor, ambos Strings.
	 * Podem ser criadas Properties personalizadas que herdem desta Property base. Um exemplo é a 
	 * LinkProperty, que extende Property e possui um "Link" associado.
	 * 
	 * O id de uma Property será gerado automaticamente a partir do nome do método em questão.
	 * O padrão é "getIdDaPropropriedade()" ser traduzido para "id_da_propriedade". Este id será
	 * usado como label da propriedade no JSON e no XML. O nome de exibição será usado somente para HTML. 
	 * 
	 * Todos os métodos que retornam Property são automaticamente chamados em todos os formatos de request.
	 * Caso um método não deva ser automaticamente chamado, deve ser anotado com @Ignore ou
	 * @HTMLIgnore, @JSONIgnore, @XMLIgnore, @CSVIgnore caso deva ser ignorado somente em formatos específicos.
	 * 
	 * Para gerar a documentação automática do Resource, utiliza-se a annotation @Docs, passando uma String
	 * como description.
	 */
	
	@Description("String que é um CPF")
	public Property getTesteString() {
		return new Property("CPF", testeString, new CPFMask());
	}
	
	/*@Description("String que é um CPF")
	 *public Property getTesteString() {
	 *	return new Property("CNPJ", testeString, new CNPJMask());
	 *}
	 */
	
	@Description("Int que é uma idade")
	public LinkProperty getTesteInt() {
		return new LinkProperty(LicitacaoConstants.Properties.Names.IDADE, testeInt, LicitacoesRouter.TESTE_UNICO+testeInt, "licitacoes");
	}
	
	@Description("Numeric que é um preço")
	public Property getTesteNumeric() {
		return new Property("Preço", testeNumeric, new MoneyMask("US$")); //O argumento na mask é opicional. Padrão é R$.
	}
	
	@Description("Date que é um nascimento")
	public Property getTesteDate() {
		return new Property("Data de nascimento", testeDate, new HtmlOnlyDateMask());
	}	
	
	@Description("Boolean de Teste")
	public Property getTesteBoolean(){
		return new Property("Ativo", (Boolean.valueOf(this.testeBoolean) ? "Sim" : "Não"));
	}	

	@Description("Time que é uma hora preferida")
	public Property getTesteTime() {
		return new Property("Hora Preferida", testeTime, new TimeMask("horas, ", "minutos, ", "segundos")); //Argumentos não obrigatórios. Padrão: HHhMMminSSs
	}
	
	
	/*
	 * LINKS
	 * Métodos que retornam os Links do Resource.
	 * 
	 * Todo Resource deve retornar pelo menos um Link, que é o Link a ele próprio (SelfLink), e por isso getSelfLink
	 * deve ser sobrescrito.
	 * 
	 * Além do SelfLink, um Resource pode possuir outros métodos que retornem links, fazendo referências a
	 * ResourceLists. Todos os métodos que retornem Link serão automaticamente chamados
	 * em todos os formatos de request.
	 * Caso um método não deva ser automaticamente chamado, deve ser anotado com @Ignore ou
	 * @HTMLIgnore, @JSONIgnore, @XMLIgnore, @CSVIgnore caso deva ser ignorado somente em formatos específicos.
	 * 
	 * Uma observação importante é que estes métodos só devem ser usados para retornar Links genéricos,
	 * que não estejam associados a nenhuma propriedade do Resource. Caso deseje inserir um link associado
	 * a uma Property, como geralmente usado em chaves estrangeiras, você deve criar um método que retorne Property,
	 * na seção acima, e retornar uma LinkProperty. 
	 */
	
	
	@Override
	@Description("")
	public SelfLink getSelfLink() {
		return new SelfLink(LicitacoesRouter.TESTE+this.testeInt, "Elemento de teste de nome "+this.getTesteString());
	}
	
	
	@Description("")
	public Link getUasg() throws ApiException
	{
		HashMap<String, String> params = StringUtils.jsonListToHashMap("uasg:2000");
		String url = licitacoesRouter.urlTo(LicitacoesRouter.LICITACOES, params);
		return new Link(url, "Todas as licitacoes uasg 2000", "uasg");
	}
	
 

}
