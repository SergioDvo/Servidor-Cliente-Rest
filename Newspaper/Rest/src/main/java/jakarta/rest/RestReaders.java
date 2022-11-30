package jakarta.rest;

import modelo.utils.Reader;
import domain.servicios.ServicesReaders;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path(ConstatesRest.READERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestReaders {
    private final ServicesReaders servicesReaders;

    @Inject
    public RestReaders(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
    }

    @GET
    public Response getAllReaders() {
        return Response.ok(servicesReaders.getReadersList()).build();

    }

    @GET
    @Path(ConstatesRest.UNO)
    public Response getReader(@QueryParam(ConstatesRest.ID) int id) {
        return Response.ok(servicesReaders.getReaderById(id)).build();
    }

    @GET
    @Path(ConstatesRest.ALL_BY_TYPE)
    public Response getAllReaders(@QueryParam(ConstatesRest.ID) int idType) {
        return Response.ok(servicesReaders.getReadersListByArticleType(idType)).build();

    }

    @GET
    @Path(ConstatesRest.QUERY_OLD_NEWSPAPER)
    public Response getReadersByNewspaperDate() {
        return Response.ok(servicesReaders.getReadersListByNewspaperDate()).build();

    }

    @GET
    @Path(ConstatesRest.GET_READERS_BY_NEWSPAPER)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getReadersListByNewspaper(@QueryParam(ConstatesRest.ID_NEWSPAPER) int idNewspaper) {
        return Response.ok(servicesReaders.getReadersListByNewspaper(idNewspaper)).build();
    }

    @GET
    @Path(ConstatesRest.GET_SUBSCRIPTIONS_LIST)
    public Response getSubscriptionsListDeUnReader(@QueryParam(ConstatesRest.ID_READER) int id) {
        return Response.ok(servicesReaders.getSubscriptionsList(id)).build();
    }

    @GET
    @Path(ConstatesRest.COUNTER_READERS)
    public Response counterReaders(@QueryParam(ConstatesRest.ID_ARTICLE) int id) {
        return Response.ok(servicesReaders.counterReaders(id)).build();
    }

    @POST
    public Response addReader(Reader reader) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesReaders.addReader(reader))
                .build();
    }

    @PUT
    public Response updateReader(Reader reader) {
        return Response.status(Response.Status.OK)
                .entity(servicesReaders.addReader(reader))
                .build();
    }

    @DELETE
    @Path(ConstatesRest.ID_PATH_PARAM)
    public Response deleteReader(@PathParam(ConstatesRest.ID) int id) {
        servicesReaders.deleteReader(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
