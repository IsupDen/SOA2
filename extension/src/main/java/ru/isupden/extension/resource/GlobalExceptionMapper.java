package ru.isupden.extension.resource;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ru.isupden.extension.resource.dto.ErrorResponse;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotFoundException) {
            ErrorResponse errorResponse = new ErrorResponse("Error", "wrong URL");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .type("application/xml")
                    .build();
        }
        if (exception instanceof ServiceUnavailableException) {
            ErrorResponse errorResponse = new ErrorResponse("Error", "The service being called is unavailable" );
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(errorResponse)
                    .type("application/xml")
                    .build();
        }
        ErrorResponse errorResponse = new ErrorResponse("Error", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type("application/xml")
                .build();
    }
}

