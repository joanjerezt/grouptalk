package edu.upc.eetac.dsa.grouptalk;


import edu.upc.eetac.dsa.grouptalk.entity.BeeterError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by juan on 05/10/15.
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace();
        BeeterError error = new BeeterError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), throwable.getMessage());
        return Response.status(error.getStatus()).entity(error).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
