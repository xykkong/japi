package br.gov.planejamento.api.core.base;

import br.gov.planejamento.api.core.exceptions.ParametersAndValuesMismatchException;

public class GenericModel {

	private String type = "Generic";
	private String parameters[];
	private Value values[];

	/**
	 * 
	 * @param parameters
	 * @param values
	 * @throws ParametersAndValuesMismatchException
	 *             A ordem deve estar correta, senão vai dar mega-ruim
	 */
	public GenericModel(String parameters[], Value values[])
			throws ParametersAndValuesMismatchException {
		if (parameters.length != values.length)
			throw new ParametersAndValuesMismatchException(parameters.length,
					values.length);
		this.parameters = parameters;
		this.values = values;
	}

	public GenericModel(String parameters[], Value values[], String type)
			throws ParametersAndValuesMismatchException {
		this(parameters, values);
		this.type = type;
	}

	public String[] getParameters() {
		return parameters;
	}

	public Value[] getValues() {
		return values;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("{");
		for (int i = 0; i < parameters.length; i++) {
			String parameter = parameters[i];
			String value = values[i].toString();
			result.append("\n\t");
			result.append(parameter);
			result.append(" : ");
			result.append(value);
		}
		result.append("\n}");
		return result.toString();
	}
}
