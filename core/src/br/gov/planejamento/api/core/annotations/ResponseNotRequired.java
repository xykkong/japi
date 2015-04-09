package br.gov.planejamento.api.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * Esta anotation impede que a framework exija que o retorno seja
 * do tipo Response para que você possa retornar a string direto
 * 
 * @see br.gov.planejamento.api.core.base.Response
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseNotRequired {

}
