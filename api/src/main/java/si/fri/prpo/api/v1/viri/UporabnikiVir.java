package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.zrna.UporabnikiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @GET
    public Response pridobiUporabnike() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.getUporabnikiCount(query);

        return Response
                .ok(uporabnikiZrno.getUporabniki(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") int id) {

        Uporabnik uporabnik = uporabnikiZrno.getById(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("uporabniskaimena/{username}")
    public Response pridobiUporabnika(@PathParam("username") String username) {

        Uporabnik uporabnik = uporabnikiZrno.getByUsername(username);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("tipi/{type}")
    public Response pridobiUporabnikeByType(@PathParam("type") String type) {

        List<Uporabnik> uporabnik = uporabnikiZrno.getByType(type);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajUporabnika(Uporabnik uporabnik) {

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikiZrno.addUporabnik(uporabnik))
                .build();
    }

    @PUT
    public Response posodobiUporabnika(Uporabnik uporabnik) {

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikiZrno.updateUporabnik(uporabnik))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiUporabnika(@PathParam("id") int id) {

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikiZrno.removeUporabnik(id))
                .build();
    }
}
