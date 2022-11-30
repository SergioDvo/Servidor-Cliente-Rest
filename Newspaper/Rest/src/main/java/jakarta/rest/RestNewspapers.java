package jakarta.rest;

import errores.ApiError;
import modelo.utils.Newspaper;
import domain.servicios.ServicesNewspaper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path(ConstatesRest.NEWSPAPERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNewspapers {

    private final ServicesNewspaper servicesNewspaper;

    @Inject
    public RestNewspapers(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
    }

    @GET
    public Response getAllNewspapers() {
        return Response.ok(servicesNewspaper.getNewspaperList()).build();
    }

    @POST
    public Response saveNewspaper(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED)
                    .entity(servicesNewspaper.addNewspaper(newspaper))
                    .build();
    }

    @PUT
    public Response updateNewspaper(Newspaper newspaper) {
        return Response.status(Response.Status.OK)
                .entity(servicesNewspaper.updateNewspaper(newspaper))
                .build();
    }

    @DELETE
    @Path(ConstatesRest.ID_PATH_PARAM)
    public Response deleteNewspaper(@PathParam(ConstatesRest.ID) int id) {
        servicesNewspaper.deleteNewspaper(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
