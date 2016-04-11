package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Role;
import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 14/10/15.
 */
@Path("stings")
public class StingResource {
    @Context
    private SecurityContext securityContext;

    @Path("/newthread")
    @POST
    public Response createFirstSting(@FormParam("subject") String subject, @FormParam("content") String content, @FormParam("groupid") String groupid, @Context UriInfo uriInfo) throws URISyntaxException {
        if(subject== null || content == null || groupid == null)
            throw new BadRequestException("all parameters are mandatory");
        StingDAO stingDAO = new StingDAOImpl();
        Sting sting = null;
        AuthToken authenticationToken = null;
        String userid = securityContext.getUserPrincipal().getName();

        GroupDAO groupDAO = new GroupDAOImpl();
        boolean suscribed = false;
        try {
            suscribed = groupDAO.isSuscribed(userid,groupid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(suscribed){
                sting = stingDAO.createSting(securityContext.getUserPrincipal().getName(), subject, content, groupid, null);
            }
            else {
                throw new ForbiddenException("Not permitted operation");
            }
        }
        catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_STING).entity(sting).build();
    }


    @POST
    public Response createSting(@FormParam("content") String content, @FormParam("groupid") String groupid, @FormParam("thread") String threadid, @Context UriInfo uriInfo) throws URISyntaxException {
        if(content == null || groupid == null)
            throw new BadRequestException("all parameters are mandatory");
        StingDAO stingDAO = new StingDAOImpl();
        Sting sting = null;
        AuthToken authenticationToken = null;
        String userid = securityContext.getUserPrincipal().getName();

        GroupDAO groupDAO = new GroupDAOImpl();
        boolean suscribed = false;
        try {
            suscribed = groupDAO.isSuscribed(userid,groupid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(suscribed){
                sting = stingDAO.createSting(securityContext.getUserPrincipal().getName(),null,content, groupid, threadid);
            }
            else {
                throw new ForbiddenException("Not permitted operation ");
            }
        }
        catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + sting.getId());
        return Response.created(uri).type(BeeterMediaType.BEETER_STING).entity(sting).build();
    }


    @GET
    @Produces(BeeterMediaType.BEETER_STING_COLLECTION)
    public StingCollection getStings(String groupid) {
        StingCollection stingCollection = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        boolean suscribed = false;
        try {
            suscribed = groupDAO.isSuscribed(userid,groupid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StingDAO stingDAO = new StingDAOImpl();
        try {
            if(suscribed) {
                stingCollection = stingDAO.getStingsByGroup(groupid);
            }
            else{
                throw new ForbiddenException();
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return stingCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(BeeterMediaType.BEETER_STING)
    public Response getSting(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Sting sting = null;
        StingDAO stingDAO = new StingDAOImpl();
        try {
            sting = stingDAO.getStingById(id);
            if (sting == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(sting.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(sting).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(BeeterMediaType.BEETER_STING)
    @Produces(BeeterMediaType.BEETER_STING)
    public Sting updateSting(@PathParam("id") String id, Sting sting) {
        if(sting == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(sting.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(sting.getUserid()))
            throw new ForbiddenException("operation not allowed");

        StingDAO stingDAO = new StingDAOImpl();
        try {
            sting = stingDAO.updateSting(id, sting.getContent());
            if(sting == null)
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return sting;
    }
    @Path("/{id}")
    @DELETE
    public void deleteSting(@PathParam("id") String id, @PathParam("threadid") String threadid) {
        String userid = securityContext.getUserPrincipal().getName();
        StingDAO stingDAO = new StingDAOImpl();
        try {
            String ownerid = stingDAO.getStingById(id).getUserid();
            if(!userid.equals(ownerid))
                if(!userid.equals(Role.admin)) {
                    throw new ForbiddenException("operation not allowed");
                }
            boolean firstpost = stingDAO.firstpost(id);
            if(firstpost==false){
                if(!stingDAO.deleteSting(id))
                    throw new NotFoundException("Sting with id = "+id+" doesn't exist");
                stingDAO.deleteThread(threadid);
            }
            else{
                if(!stingDAO.deleteSting(id))
                    throw new NotFoundException("Sting with id = "+id+" doesn't exist");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}


