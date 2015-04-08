package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.gov.planejamento.api.core.exceptions.ApiException;

@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		if(exception instanceof ApiException){
			Exception original = ((ApiException) exception).getOriginalException();
			if(original != null){
				System.out.println("Exceção original:");
				original.printStackTrace();
			}
		}
		/*Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		
		if(!(exception instanceof RequestException)) {
			exception = new RequestException(exception);
		}
		
		ErrorResource error = new ErrorResource();
		error.setMensagem(exception.getCause().getMessage());
		error.setCodigo(httpStatus.getStatusCode()+"");
		
		br.gov.planejamento.api.core.base.Response response = new br.gov.planejamento.api.core.base.Response(1);
		response.add(error);
		response.isList(false);
			
		try {
			String retorno = "";
			switch (RequestContext.getContext().getRequestFormat()) {
				case RequestFormats.HTML:
					retorno = response.toHTML().toString();
					break;
				case RequestFormats.JSON:
					retorno = response.toJSON();
					break;
				case RequestFormats.XML:
					retorno = response.toXML();
					break;
				case RequestFormats.CSV:
					retorno = response.toHTML().toString();
					break;
			}
			return Response.status(httpStatus).entity(retorno).build();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;
	}	
}
