package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GroupDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.grouptalk.dao.StingDAO;
import edu.upc.eetac.dsa.grouptalk.dao.StingDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Group;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 30/10/15.
 */
@Path("groups")
public class GroupResource {
    @Context
    private SecurityContext securityContext;

    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(BeeterMediaType.BEETER_AUTH_TOKEN)
    public Response createGroup(@FormParam("groupname") String groupname, @Context UriInfo uriInfo)throws URISyntaxException {
        if (groupname == null)
            throw new BadRequestException("all parameters are mandatory");
        GroupDAO groupDAO = new GroupDAOImpl();
        Group group = null;
        AuthToken authenticationToken = null;
        try {
            group = groupDAO.createGroup(this.securityContext.getUserPrincipal().getName(), groupname);

        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + group.getId());

        return Response.created(uri).type(BeeterMediaType.BEETER_GROUP).entity(group).build();
    }




   /*  @GET
    @Produces(BeeterMediaType.BEETER_STING_COLLECTION)
    public StingCollection getStings(@QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        StingCollection stingCollection = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            stingCollection = stingDAO.getStings(timestamp, before);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }
    */

    @Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_STING)
    public Group getGroup(@PathParam("id") String id, @Context Request request) {

        Group group = null;
        GroupDAO GroupDAO = new GroupDAOImpl();
        try {
            group = GroupDAO.getGroupById(id);
            if (group == null) {
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");
            }

        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return group;
    }

    @Path("/{id}")
    @DELETE
    public void deleteGroup(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        StingDAO stingDAO = new StingDAOImpl();
        try {
            String ownerid = stingDAO.getStingById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteSting(id))
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}

