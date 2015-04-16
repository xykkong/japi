package br.gov.planejamento.api.core.responses;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;

public class DocumentationResponse  extends Response{

	private ArrayList<Request> requests;
	private String modulo;
	private String relativePath = "";
	private String template = null;
	
	public String getTemplate() {
		return template;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}
	
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
		private String example_url;
		private String path_filterless;
		public String getPath_filterless() {
			return path_filterless;
		}


		public void setPath_filterless(String path_filterless) {
			this.path_filterless = path_filterless;
		}

		private ArrayList<Parameter> parameters;
		private ArrayList<Property> properties;
		
		public String getExample_url() {
			return example_url;
		}


		public void setExample_url(String example_url) {
			this.example_url = example_url;
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
	
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	@Override
	public SelfLink getSelfLink() throws ApiException {
		return new SelfLink(RequestContext.getContext().getRootURL()+"/"+relativePath, "Documentação "+this.modulo);
	}

	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		return new ArrayList<Link>();
	}

	@Override
	public String toXML() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCSV() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJSON() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toHTML() throws ApiException {
		return HTMLSerializer.fromDocumentationResponse(this);
	}

	@Override
	public int getHttpStatusCode() {
		return 200;
	}

}
