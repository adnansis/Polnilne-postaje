package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.zrna.PostajeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("postaje")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostajeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PostajeZrno postajeZrno;

    @GET
    public Response pridobiPostaje() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long postajeCount = postajeZrno.getPostajeCount(query);

        return Response
                .ok(postajeZrno.getPostaje(query))
                .header("X-Total-Count", postajeCount)
                .build();
    }

    @GET
    @Path("{id}")
    public Response pridobiPostajoById(@PathParam("id") int id) {

        Postaja postaja = postajeZrno.getById(id);

        if(postaja != null) {
            return Response.ok(postaja).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("lokacije/{lokacija}")
    public Response pridobiPostajoByLocation(@PathParam("lokacija") String lokacija) {

        List<Postaja> postaje = postajeZrno.getByLocation(lokacija);

        if(postaje != null) {
            return Response.ok(postaje).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("navoljo/{id}")
    public Response pridobiNaVoljo(@PathParam("id") int id) {

        List<Postaja> postaje = postajeZrno.getAllAvailable(id);

        if(postaje != null) {
            return Response.ok(postaje).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajPostajo(Postaja postaja) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.addPostaja(postaja))
                .build();
    }

    @PUT
    public Response posodobiPostajo(Postaja postaja) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.updatePostaja(postaja))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiPostajo(@PathParam("id") int id) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.removePostaja(id))
                .build();
    }
}
