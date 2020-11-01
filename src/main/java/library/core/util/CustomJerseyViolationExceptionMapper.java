package library.core.util;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ConstraintMessage;
import io.dropwizard.jersey.validation.JerseyViolationException;
import io.dropwizard.jersey.validation.JerseyViolationExceptionMapper;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import org.glassfish.jersey.server.model.Invocable;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.StringJoiner;

public class CustomJerseyViolationExceptionMapper implements ExceptionMapper<JerseyViolationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyViolationExceptionMapper.class);
    @Override
    public Response toResponse(final JerseyViolationException exception) {
        LOGGER.debug("Object validation failure", exception);
        final Invocable invocable = exception.getInvocable();
        ImmutableList<String> errors = FluentIterable.from(exception.getConstraintViolations()).transform((violation) -> {
            return ConstraintMessage.getMessage(violation, invocable);
        }).toList();

        StringJoiner errorsString = new StringJoiner(", ");
        for(String error : errors) {
            errorsString.add(error);
        }

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), errorsString.toString())).build();
    }



}