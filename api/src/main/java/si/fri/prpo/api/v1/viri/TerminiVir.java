package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Termin;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.zrna.TerminiZrno;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("termini")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TerminiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private TerminiZrno terminiZrno;

    @Operation(description = "Vrne seznam terminov.", summary = "Seznam terminov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam terminov.",
                    content = @Content(schema = @Schema(implementation = Termin.class, type = SchemaType.ARRAY)),
                    headers = @Header(name = "X-Total-Count", description = "Število vrnjenih terminov."))
    })
    @RolesAllowed("user")
    @GET
    public Response pridobiTermine() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long terminiCount = terminiZrno.getTerminiCount(query);

        return Response
                .ok(terminiZrno.getTermini(query))
                .header("X-Total-Count", terminiCount)
                .build();
    }

    @Operation(description = "Vrne podrobnosti termina.", summary = "Podrobnosti termina.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Podrobnosti termina.",
                    content = @Content(schema = @Schema(implementation = Termin.class))
            )})
    @RolesAllowed("user")
    @GET
    @Path("{id}")
    public Response pridobiTermin(@Parameter(
            description = "Identifikator termina za iskanje.",
            required = true) @PathParam("id") int id) {

        Termin termin = terminiZrno.getById(id);

        if(termin != null) {
            return Response.ok(termin).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam terminov uporabnika z določenim identifikatorjem.", summary = "Seznam terminov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam terminov.",
                    content = @Content(schema = @Schema(implementation = Termin.class, type = SchemaType.ARRAY))
            )})
    @RolesAllowed("user")
    @GET
    @Path("uporabniki/{id}")
    public Response pridobiTermineUporabnikId(@Parameter(
            description = "Identifikator uporabnika za iskanje terminov.",
            required = true) @PathParam("id") int id) {

        List<Termin> termini = terminiZrno.getByUserId(id);

        if(termini != null) {
            return Response.ok(termini).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam terminov na postaji z določenim identifikatorjem.", summary = "Seznam terminov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam terminov.",
                    content = @Content(schema = @Schema(implementation = Termin.class, type = SchemaType.ARRAY))
            )})
    @RolesAllowed("user")
    @GET
    @Path("postaje/{id}")
    public Response pridobiTerminePostajaId(@Parameter(
            description = "Identifikator postaje za iskanje terminov.",
            required = true) @PathParam("id") int id) {

        List<Termin> termini = terminiZrno.getByPostajaId(id);

        if(termini != null) {
            return Response.ok(termini).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Dodaj termin.", summary = "Dodajanje termina.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Termin uspešno dodan."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    @RolesAllowed("admin")
    @POST
    public Response dodajTermin(@RequestBody(
            description = "DTO objekt za dodajanje termina.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Termin.class)
            )) Termin termin) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.addTermin(termin))
                .build();
    }

    @Operation(description = "Posodobi termin.", summary = "Posodabljanje terminov.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Termin uspešno posodobljen."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    @RolesAllowed("admin")
    @PUT
    public Response posodobiTermin(@RequestBody(
            description = "DTO objekt za posodabljanje terminov.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Termin.class)
            )) Termin termin) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.updateTermin(termin))
                .build();
    }

    @Operation(description = "Izbriši termin.", summary = "Izbris terminov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Termin uspešno izbrisan."),
            @APIResponse(responseCode = "404", description = "Termin ne obstaja.")
    })
    @RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response izbrisiTermin(@Parameter(
            description = "Identifikator termina za brisanje.",
            required = true) @PathParam("id") int id) {

        return Response
                .status(Response.Status.CREATED)
                .entity(terminiZrno.removeTermin(id))
                .build();
    }
}
