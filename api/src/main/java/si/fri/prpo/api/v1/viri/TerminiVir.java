package si.fri.prpo.api.v1.viri;

import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Termin;
import si.fri.prpo.storitve.zrna.TerminiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("termini")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TerminiVir {

    @Inject
    private TerminiZrno terminiZrno;

    @GET
    public Response pridobiTermine() {

        List<Termin> termini = terminiZrno.getTermini();

        if(termini != null) {
            return Response.ok(termini).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id}")
    public Response pridobiTermin(@PathParam("id") int id) {

        Termin termin = terminiZrno.getById(id);

        if(termin != null) {
            return Response.ok(termin).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("uporabniki/{id}")
    public Response pridobiTermineUporabnikId(@PathParam("id") int id) {

        List<Termin> termini = terminiZrno.getByUserId(id);

        if(termini != null) {
            return Response.ok(termini).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("postaje/{id}")
    public Response pridobiTerminePostajaId(@PathParam("id") int id) {

        List<Termin> termini = terminiZrno.getByPostajaId(id);

        if(termini != null) {
            return Response.ok(termini).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response dodajTermin(Termin termin) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.addTermin(termin))
                .build();
    }

    @PUT
    public Response posodobiTermin(Termin termin) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.updateTermin(termin))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiTermin(@PathParam("id") int id) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.removeTermin(id))
                .build();
    }
}
