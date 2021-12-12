package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
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
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.zrna.PostajeZrno;

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
@Path("postaje")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostajeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PostajeZrno postajeZrno;

    @Operation(description = "Vrne seznam postaj.", summary = "Seznam postaj.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam postaj.",
                    content = @Content(schema = @Schema(implementation = Postaja.class, type = SchemaType.ARRAY)),
                    headers = @Header(name = "X-Total-Count", description = "Število vrnjenih postaj."))
    })
    //@RolesAllowed({"user"})
    @GET
    public Response pridobiPostaje() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long postajeCount = postajeZrno.getPostajeCount(query);

        return Response
                .ok(postajeZrno.getPostaje(query))
                .header("X-Total-Count", postajeCount)
                .build();
    }

    @Operation(description = "Vrne podrobnosti postaje.", summary = "Podrobnosti postaje.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Podrobnosti postaje.",
                    content = @Content(schema = @Schema(implementation = Postaja.class))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("{id}")
    public Response pridobiPostajoById(@Parameter(
            description = "Identifikator postaje za iskanje.",
            required = true) @PathParam("id") int id) {

        Postaja postaja = postajeZrno.getById(id);

        if(postaja != null) {
            return Response.ok(postaja).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam postaj na lokaciji.", summary = "Seznam postaj.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam postaj.",
                    content = @Content(schema = @Schema(implementation = Postaja.class, type = SchemaType.ARRAY))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("lokacije/{lokacija}")
    public Response pridobiPostajoByLocation(@Parameter(
            description = "Lokacija postaj za iskanje.",
            required = true) @PathParam("lokacija") String lokacija) {

        List<Postaja> postaje = postajeZrno.getByLocation(lokacija);

        if(postaje != null) {
            return Response.ok(postaje).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Vrne seznam postaj, ki so na voljo.", summary = "Seznam postaj.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Seznam postaj.",
                    content = @Content(schema = @Schema(implementation = Postaja.class, type = SchemaType.ARRAY))
            )})
    //@RolesAllowed("user")
    @GET
    @Path("navoljo/{id}")
    public Response pridobiNaVoljo(@Parameter(
            description = "Identifikator postaje za iskanje.",
            required = true) @PathParam("id") int id) {

        List<Postaja> postaje = postajeZrno.getAllAvailable(id);

        if(postaje != null) {
            return Response.ok(postaje).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Dodaj postajo.", summary = "Dodajanje postaj.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Postaja uspešno dodana."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    //@RolesAllowed("admin")
    @POST
    public Response dodajPostajo(@RequestBody(
            description = "DTO objekt za dodajanje postaje.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Postaja.class)
            )) Postaja postaja) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.addPostaja(postaja))
                .build();
    }

    @Operation(description = "Posodobi postajo.", summary = "Posodabljanje postaje.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Postaja uspešno posodobljena."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    //@RolesAllowed("admin")
    @PUT
    public Response posodobiPostajo(@RequestBody(
            description = "DTO objekt za posodabljanje postaje.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Postaja.class)
            )) Postaja postaja) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.updatePostaja(postaja))
                .build();
    }

    @Operation(description = "Izbriši postajo.", summary = "Izbris postaje.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Postaja uspešno izbrisana."),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja.")
    })
    @DELETE
    //@RolesAllowed("admin")
    @Path("{id}")
    public Response izbrisiPostajo(@Parameter(
            description = "Identifikator postaje za brisanje.",
            required = true) @PathParam("id")  int id) {

        return Response
                .status(Response.Status.CREATED)
                .entity(postajeZrno.removePostaja(id))
                .build();
    }
}
