package br.gov.planejamento.api.core.interceptors;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import br.gov.planejamento.api.core.base.ErrorResource;
import br.gov.planejamento.api.core.exceptions.JapiException;

@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		
		if(!(exception instanceof JapiException)) {
			exception = new JapiException(exception);
		}
		
		ErrorResource error = new ErrorResource();
		error.setMensagem(exception.getCause().getMessage());
		error.setCodigo(httpStatus.getStatusCode()+"");
		
		br.gov.planejamento.api.core.base.Response response = new br.gov.planejamento.api.core.base.Response(1);
		response.add(error);
		response.isList(false);
			
		try {
			return Response.status(httpStatus).entity(response.toHTML()).build();
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
		}
		return null;
	}	
}
