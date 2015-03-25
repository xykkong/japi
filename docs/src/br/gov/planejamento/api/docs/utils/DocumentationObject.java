package br.gov.planejamento.api.docs.utils;

import java.util.ArrayList;

public class DocumentationObject {
	
	ArrayList<Request> requests;
	String modulo;
	
	public ArrayList<Request> getRequests() {
		return requests;
	}

	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public class Request{
		private String path;
		private Returns returns;
		private String description;
		private String method_name;
		private String example_query_string;
		private ArrayList<Parameter> parameters;
		private ArrayList<Property> properties;
		
		public String getExample_query_string() {
			return example_query_string;
		}


		public void setExample_query_string(String example_query_string) {
			this.example_query_string = example_query_string;
		}
		
		public String getMethod_name() {
			return method_name;
		}


		public void setMethod_name(String method_name) {
			this.method_name = method_name;
		}


		public String getPath() {
			return path;
		}


		public void setPath(String path) {
			this.path = path;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public ArrayList<Parameter> getParameters() {
			return parameters;
		}


		public void setParameters(ArrayList<Parameter> parameters) {
			this.parameters = parameters;
		}
		

		public ArrayList<Property> getProperties() {
			return properties;
		}


		public void setProperties(ArrayList<Property> properties) {
			this.properties = properties;
		}

		public Returns getReturns() {
			return returns;
		}


		public void setReturns(Returns returns) {
			this.returns = returns;
		}

		public class Returns{
			private String resource;
			private Boolean isList;
			public String getResource() {
				return resource;
			}
			public void setResource(String resource) {
				this.resource = resource;
			}
			public boolean getIsList() {
				return isList;
			}
			public void setList(Boolean isList) {
				this.isList = isList;
			}
		}
		
		
		public class Parameter{
			private String name;
			private String description;
			private String type;
			private String required;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getRequired() {
				return required;
			}
			public void setRequired(String required) {
				this.required = required;
			}
		}
		
		public class Property {
			private String name;
			private String description;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
		}
	}
}
