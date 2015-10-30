package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GroupDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 30/10/15.
 */

@Path("group")
public class JoinResource
{
    @Context
    private SecurityContext securityContext;

    @Path("/{id}")
    @POST
    @Produces(BeeterMediaType.BEETER_GROUP)
    public Response joinGroup(@PathParam("id") String id, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (id == null ) {
            throw new BadRequestException("all parameters are mandatory");
        }
        GroupDAO groupDAO            = new GroupDAOImpl();
        AuthToken authenticationToken = null;
        try {
            groupDAO.joinGroup(this.securityContext.getUserPrincipal().getName(), id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return Response.noContent().build();

    }


    @Path("/{id}")
    @DELETE
    public Response leaveGroup(@PathParam("id") String id, @Context UriInfo uriInfo) throws URISyntaxException
    {
        if (id == null) {
            throw new BadRequestException("all parameters are mandatory");
        }
        GroupDAO  groupDAO            = new GroupDAOImpl();
        AuthToken authenticationToken = null;
        try {
            groupDAO.leaveGroup(this.securityContext.getUserPrincipal().getName(), id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return Response.noContent().build();
    }
}
