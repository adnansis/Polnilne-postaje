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
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.zrna.UporabnikiZrno;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

//@Secure
@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Operation(description = "Vrne seznam uporabnikov.", summary = "Seznam uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam uporabnikov.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = @Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov."))
    })
    //@RolesAllowed("user")
    @GET
    public Response pridobiUporabnike() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.getUporabnikiCount(query);

        return Response
                .ok(uporabnikiZrno.getUporabniki(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }

    @Operation(description = "Vrne podrobnosti uporabnika.", summary = "Podrobnosti uporabnika.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Podrobnosti uporabnika.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@Parameter(
            description = "Identifikator uporabnika za iskanje.",
            required = true) @PathParam("id") int id) {

        Uporabnik uporabnik = uporabnikiZrno.getById(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne podrobnosti uporabnika.", summary = "Podrobnosti uporabnika.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Podrobnosti uporabnika.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("uporabniskaimena/{username}")
    public Response pridobiUporabnika(@Parameter(
            description = "Uporabnisko ime uporabnika za iskanje.",
            required = true) @PathParam("username") String username) {

        Uporabnik uporabnik = uporabnikiZrno.getByUsername(username);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam uporabnikov.", summary = "Seznam uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam uporabnikov.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("tipi/{type}")
    public Response pridobiUporabnikeByType(@Parameter(
            description = "Tip uporabnika za iskanje.",
            required = true) @PathParam("type") String type) {

        List<Uporabnik> uporabnik = uporabnikiZrno.getByType(type);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Dodaj uporabnika.", summary = "Dodajanje uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Uporabnik uspešno dodan."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    //@RolesAllowed("admin")
    @POST
    public Response dodajUporabnika(@RequestBody(
            description = "DTO objekt za dodajanje uporabnika.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class)
            )) Uporabnik uporabnik) {

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikiZrno.addUporabnik(uporabnik))
                .build();
    }

    @Operation(description = "Posodobi uporabnika.", summary = "Posodabljanje uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Uporabnik uspešno posodobljen."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    //@RolesAllowed("admin")
    @PUT
    public Response posodobiUporabnika(@RequestBody(
            description = "DTO objekt za posodabljanje uporabnika.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class)
            )) Uporabnik uporabnik) {

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikiZrno.updateUporabnik(uporabnik))
                .build();
    }

    @Operation(description = "Izbriši uporabnika.", summary = "Izbris uporabnikov.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Uporabnik uspešno izbrisan."),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja.")
    })
    //@RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response izbrisiUporabnika(@Parameter(
            description = "Identifikator uporabnika za brisanje.",
            required = true) @PathParam("id") int id) {

        return Response
                .status(Response.Status.OK)
                .entity(uporabnikiZrno.removeUporabnik(id))
                .build();
    }
}
