package br.gov.planejamento.api.core.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.planejamento.api.core.exceptions.InvalidApiValueTypeException;

public class Value {
	private Date data;
	private String string;

	// private Link link;??? TODO ver se precisa disso

	public Value(String string) {
		this.string = string;
	}

	public Value(Date data) {
		this.data = data;
	}

	public String toString() {
		if (data != null)
			return (new SimpleDateFormat("dd/MM/YYY hh:mm:ss")).format(data);
		return string;
	}

	public String getType() {
		if (data != null)
			return "Date";
		else if (string != null)
			return "String";
		return "Não definido";
	}

	public void setData(Date data) throws InvalidApiValueTypeException {
		if (string != null)
			throw new InvalidApiValueTypeException(this);
		this.data = data;
	}

	public void setString(String string) throws InvalidApiValueTypeException {
		if (data != null)
			throw new InvalidApiValueTypeException(this);
		this.string = string;
	}

	public Date getData() {
		return data;
	}

	public String getString() {
		return string;
	}
}
