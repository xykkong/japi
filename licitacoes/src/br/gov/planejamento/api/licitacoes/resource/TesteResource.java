package br.gov.planejamento.api.licitacoes.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.planejamento.api.core.annotations.JSONProperties;
import br.gov.planejamento.api.core.annotations.Properties;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants.DateFormats;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;

@Properties({"testeString", "testeInt", "testeNumeric", "testeDate", "testeTime"})
@JSONProperties({"testeString","testeNumeric"})
public class TesteResource extends Resource {

	private String testeString;
	private String testeInt;
	private String testeNumeric;
	private String testeDate;
	private String testeTime;

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder("xuxuxu xaxaxa");
		return sb.toString();
	}

	public Property getTesteString() {
		return new Property("Nome", testeString);
	}

	public void setTesteString(String testeString) {
		this.testeString = testeString;
	}

	public Property getTesteInt() {
		return new Property("Idade Artística", testeInt);
	}

	public void setTesteInt(String testeInt) {
		this.testeInt = testeInt;
	}

	public Property getTesteNumeric() {
		return new Property("Altura", testeNumeric);
	}

	public void setTesteNumeric(String testeNumeric) {
		this.testeNumeric = testeNumeric;
	}

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

	public void setTesteDate(String testeDate) {
		this.testeDate = testeDate;
	}

	public Property getTesteTime() {
		return new Property("Hora Preferida", testeTime);
	}

	public void setTesteTime(String testeTime) {
		this.testeTime = testeTime;
	}

}
