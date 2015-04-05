package br.gov.planejamento.api.licitacoes.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.planejamento.api.commons.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.LinkProperty;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.constants.Constants.DateFormats;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.CoreException;

public class TesteResource extends Resource {

	private String testeString;
	private String testeInt;
	private String testeNumeric;
	private String testeDate;
	private String testeTime;
	private String testeBoolean;
	
	public TesteResource(DataRow teste) {
		setTesteDate(teste.get("teste_date"));
		setTesteInt(teste.get("teste_int"));
		setTesteNumeric(teste.get("teste_numeric"));
		setTesteString(teste.get("teste_string"));
		setTesteTime(teste.get("teste_time"));
		setTesteBoolean(teste.get("teste_boolean"));
		
		// Construção das informações do resource
		setTitle("Resource de nome "+this.testeString);
		setSelfLink(LicitacaoConstants.URIConstants.Mirror.LICITACAO+this.testeInt);
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
	
	@Description("String que é um nome")
	public Property getTesteString() {
		return new Property("Nome", testeString);
	}
	
	@Description("Int que é uma idade")
	public LinkProperty getTesteInt() {
		return new LinkProperty(LicitacaoConstants.Properties.Names.IDADE, testeInt, LicitacaoConstants.Properties.Link.IDADE+testeInt, "licitacoes");
	}
	
	@Description("Numeric que é uma altura")
	public Property getTesteNumeric() {
		return new Property("Altura", testeNumeric);
	}
	
	@Description("Date que é um nascimento")
	public Property getTesteDate() throws CoreException  {
		try{
			String name = "Nascimento";
			String value = this.testeDate;
			
			if(RequestContext.getContext().isCurrentFormat(RequestFormats.HTML)) {
				SimpleDateFormat formatter = new SimpleDateFormat(DateFormats.AMERICAN);
				Date dt = formatter.parse(value);
				value = (new SimpleDateFormat(DateFormats.BRAZILIAN)).format(dt);
			}
			
			return new Property(name, value);
		} catch(ParseException e) {
			throw new CoreException("Houve um erro ao formatar a data.", e);
		}
	}	
	
	@Description("Boolean de Teste")
	public Property getTesteBoolean(){
		return new Property("Ativo", (Boolean.valueOf(this.testeBoolean) ? "Sim" : "Não"));
	}	

	@Description("Time que é uma hora preferida")
	public Property getTesteTime() {
		return new Property("Hora Preferida", testeTime);
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
	
	/*
	@Override
	@Description("")
	public SelfLink getSelfLink() {
		return new SelfLink(LicitacaoConstants.URIConstants.Mirror.LICITACAO+this.testeInt, this.testeString);
	}
	*/
	
	@Description("")
	public Link getUasg()
	{
		return new Link(LicitacaoConstants.URIConstants.List.LICITACOES+"?uasg=2000", "Todas as licitacoes uasg 2000", "uasg");
	}
	
 

}
