package dev.fmayoral.customermanagement.provider;

import com.google.common.base.VerifyException;
import dev.fmayoral.customermanagement.api_1_0.Error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

//FIXME decide if I want to have this or not

@Provider
public class VerifyExceptionHandler extends Throwable implements ExceptionMapper<VerifyException> {
	private final Logger LOG = Logger.getLogger(VerifyExceptionHandler.class.getName());

	@Override
	public Response toResponse(final VerifyException exception) {
		this.LOG.log(Level.WARNING, "Handling exception", exception);
		final int code = 400;
		return Response.status(code)
				.entity(new Error(code, exception.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
