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
		private String resource_type;
		private String description;
		private String method_name;
		private ArrayList<Parameter> parameters;

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


		public String getResourceType() {
			return resource_type;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public void setResourceType(String resourceType) {
			this.resource_type = resourceType;
		}


		public ArrayList<Parameter> getParameters() {
			return parameters;
		}


		public void setParameters(ArrayList<Parameter> parameters) {
			this.parameters = parameters;
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
	}
}
