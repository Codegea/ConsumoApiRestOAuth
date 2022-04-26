package cl.com.fidelis.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionCustom {

	public ExceptionCustom() {

	}

	public String getDescription(HttpStatus code) {
		String description = "";
		switch (code) {
		case OK:
			description = "200: La solicitud ha tenido éxito.";
			break;
		case ACCEPTED:
			description = "202: La solicitud se ha recibido, pero aún no se ha actuado.";
			break;
		case NON_AUTHORITATIVE_INFORMATION:
			description = "203: La petición se ha completado con éxito, pero su contenido no se ha obtenido de la fuente originalmente solicitada, sino que se recoge de una copia local o de un tercero.";
			break;
		case NO_CONTENT:
			description = "204: La petición se ha completado con éxito pero su respuesta no tiene ningún contenido, aunque los encabezados pueden ser útiles.";
			break;
		case PARTIAL_CONTENT:
			description = "206: La petición servirá parcialmente el contenido solicitado.";
			break;

		case BAD_REQUEST:
			description = "400: El servidor no pudo interpretar la solicitud dada una sintaxis inválida.";
			break;
		case UNAUTHORIZED:
			description = "401: Es necesario autenticar para obtener la respuesta solicitada. Esta es similar a 403, pero en este caso, la autenticación es posible.";
			break;
		case FORBIDDEN:
			description = "403: El cliente no posee los permisos necesarios para cierto contenido, por lo que el servidor está rechazando otorgar una respuesta apropiada.";
			break;
		case NOT_FOUND:
			description = "404: El servidor no pudo encontrar el contenido solicitado.";
			break;
		case METHOD_NOT_ALLOWED:
			description = "405: El método solicitado es conocido por el servidor, pero ha sido deshabilitado y no puede ser utilizado.";
			break;
		case PROXY_AUTHENTICATION_REQUIRED:
			description = "407: Esto es similar al código 401, pero la autenticación debe estar hecha a partir de un proxy.";
			break;
		default:
			break;
		}
		return description;

	}
}
