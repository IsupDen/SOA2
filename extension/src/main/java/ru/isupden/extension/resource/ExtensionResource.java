package ru.isupden.extension.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import ru.isupden.extension.resource.dto.CalcResult;
import ru.isupden.extension.service.CalculatingService;

@Path("/calculate")
public class ExtensionResource {
    private final CalculatingService calculatingService = new CalculatingService();

    @GET
    @Path("/length/{id1}/{id2}")
    @Produces(MediaType.APPLICATION_XML)
    public Response calculateDistance(@PathParam("id1") long id1, @PathParam("id2") long id2) throws JAXBException {
        double distance = calculatingService.calculateDistance(id1, id2);
        return Response.ok(new CalcResult(distance)).build();
    }

    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_XML)
    public Response ping() {
        return Response.ok("pong").build();
    }

    @GET
    @Path("/between-oldest-and-newest")
    @Produces(MediaType.APPLICATION_XML)
    public Response calculateDistance() throws JAXBException {
        double distance = calculatingService.calculateDistance();
        return Response.ok(new CalcResult(distance)).build();
    }
}
