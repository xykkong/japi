package br.gov.planejamento.api.licitacoes.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.planejamento.api.common.constants.CommonConstants;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants.DateFormats;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.database.DataRow;

public class TesteResource extends Resource {

	private String testeString;
	private String testeInt;
	private String testeNumeric;
	private String testeDate;
	private String testeTime;
	
	public TesteResource(DataRow teste) {
		setTesteDate(teste.get("teste_date"));
		setTesteInt(teste.get("teste_int"));
		setTesteNumeric(teste.get("teste_numeric"));
		setTesteString(teste.get("teste_string"));
		setTesteTime(teste.get("teste_time"));
	}

	/*
	 * SETTERS
	 * M�todos para definir todos os valores que um Resource precisa para existir.
	 * 
	 * N�o h� getters para estes valores. Tudo o que um Resource deve retornar s�o Properties ou Links.
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
	
	/*
	 * PROPERTIES
	 * M�todos que retornam as Properties do Resource.
	 * 
	 * Um Property simples possui somente um nome de exibi��o e um valor, ambos Strings.
	 * Podem ser criadas Properties personalizadas que herdem desta Property base. Um exemplo � a 
	 * LinkProperty, que extende Property e possui um "Link" associado.
	 * 
	 * O id de uma Property ser� gerado automaticamente a partir do nome do m�todo em quest�o.
	 * O padr�o � "getIdDaPropropriedade()" ser traduzido para "id_da_propriedade". Este id ser�
	 * usado como label da propriedade no JSON e no XML. O nome de exibi��o ser� usado somente para HTML. 
	 * 
	 * Todos os m�todos que retornam Property s�o automaticamente chamados em todos os formatos de request.
	 * Caso um m�todo n�o deva ser automaticamente chamado, deve ser anotado com @Ignore ou
	 * @HTMLIgnore, @JSONIgnore, @XMLIgnore, @CSVIgnore caso deva ser ignorado somente em formatos espec�ficos.
	 * 
	 * Para gerar a documenta��o autom�tica do Resource, utiliza-se a annotation @Docs, passando uma String
	 * como description.
	 */
	
	@Description("String que é um nome")
	public Property getTesteString() {
		return new Property("Nome", testeString);
	}
	
	@Description("Int que é uma idade")
	public Property getTesteInt() {
		return new Property("Idade Artística", testeInt);
	}
	
	@Description("Numeric que é uma altura")
	public Property getTesteNumeric() {
		return new Property("Altura", testeNumeric);
	}
	
	@Description("Date que é um nascimento")
	public Property getTesteDate() throws ParseException {
		String name = "Nascimento";
		String value = testeDate;
		
		if(Session.getCurrentSession().isCurrentFormat(RequestFormats.HTML)) {
			SimpleDateFormat formatter = new SimpleDateFormat(DateFormats.AMERICAN);
			Date dt = formatter.parse(value);
			value = (new SimpleDateFormat(DateFormats.BRAZILIAN)).format(dt);
		}
		
		return new Property(name, value);
	}	

	@Description("Time que é uma hora preferida")
	public Property getTesteTime() {
		return new Property("Hora Preferida", testeTime);
	}
	
	
	/*
	 * LINKS
	 * M�todos que retornam os Links do Resource.
	 * 
	 * Todo Resource deve retornar pelo menos um Link, que � o Link a ele pr�prio (SelfLink), e por isso getSelfLink
	 * deve ser sobrescrito.
	 * 
	 * Al�m do SelfLink, um Resource pode possuir outros m�todos que retornem links, fazendo refer�ncias a
	 * ResourceLists. Todos os m�todos que retornem Link ser�o automaticamente chamados
	 * em todos os formatos de request.
	 * Caso um m�todo n�o deva ser automaticamente chamado, deve ser anotado com @Ignore ou
	 * @HTMLIgnore, @JSONIgnore, @XMLIgnore, @CSVIgnore caso deva ser ignorado somente em formatos espec�ficos.
	 * 
	 * Uma observa��o importante � que estes m�todos s� devem ser usados para retornar Links gen�ricos,
	 * que n�o estejam associados a nenhuma propriedade do Resource. Caso deseje inserir um link associado
	 * a uma Property, como geralmente usado em chaves estrangeiras, voc� deve criar um m�todo que retorne Property,
	 * na se��o acima, e retornar uma LinkProperty. 
	 */
	
	@Override
	@Description("")
	public SelfLink getSelfLink() {
		return new SelfLink(CommonConstants.URIConstants.Licitacoes.Doc.LICITACAOTESTE+this.testeInt, this.testeString);
	}
	
	@Description("")
	public SelfLink getUasgDaZuera()
	{
		return new SelfLink("http://localhost:8080/licitacoes/v1/licitacoes?uasg=1000", "Todas as licitacoes uasg 1000");
	}
	@Description("")
	public Link getUasgDaZuera2()
	{
		return new Link("http://localhost:8080/licitacoes/v1/licitacoes?uasg=1000", "Todas as licitacoes uasg 2000");
	}
	
 

}
