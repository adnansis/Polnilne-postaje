package si.fri.prpo.api.v1.viri;

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
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.zrna.PostajeZrno;
import si.fri.prpo.storitve.zrna.TerminiZrno;
import si.fri.prpo.storitve.zrna.UpravljanjePolnilnihPostajZrno;
import si.fri.prpo.storitve.dtos.CenaPolnjenjaDTO;
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.dtos.PreveriNaVoljoDTO;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
@Path("upravljanje")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UpravljanjePolnilnihPostajVir {

    @Inject
    private UpravljanjePolnilnihPostajZrno upravljanjePolnilnihPostajZrno;
    @Inject
    private TerminiZrno terminiZrno;
    @Inject
    private PostajeZrno postajeZrno;

    @Operation(description = "Dodaj Termin.", summary = "Dodajanje terminov.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Termin uspešno dodan."),
            @APIResponse(responseCode = "405", description = "Validacijska napaka.")
    })
    @RolesAllowed("user")
    @POST
    @Path("termini")
    public Response dodajTermin(@RequestBody(
            description = "DTO objekt za dodajanje termina.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = DodajTerminDTO.class)
            )) DodajTerminDTO dodajTerminDTO) {

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjePolnilnihPostajZrno.dodajTermin(dodajTerminDTO))
                .build();
    }

    @Operation(description = "Vrne odgovor ali je postaja na voljo.", summary = "Podrobnosti postaje.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Podrobnosti postaje.",
                    content = @Content(schema = @Schema(implementation = Postaja.class, type = SchemaType.ARRAY))
            )})
    @RolesAllowed("user")
    @GET
    @Path("postaje/{id}")
    public Response pridobiNaVoljo(@Parameter(
            description = "Identifikator postaje za iskanje.",
            required = true) @PathParam("id") int id) {

        Postaja postaja = postajeZrno.getById(id);

        if(postaja == null) {
            return Response.status(Response.Status.OK).entity("Postaja ne obstaja").build();
        }

        List<Termin> termini = terminiZrno.getByPostajaId(id);

        PreveriNaVoljoDTO preveriNaVoljoDTO = new PreveriNaVoljoDTO();

        Timestamp termin_od;
        Timestamp termin_do;

        for(Termin t: termini) {

            termin_od = t.getTermin_od();
            termin_do = t.getTermin_do();

            preveriNaVoljoDTO.setTermin_od(termin_od);
            preveriNaVoljoDTO.setTermin_do(termin_do);

            if(upravljanjePolnilnihPostajZrno.preveriNaVoljo(preveriNaVoljoDTO) == true) {

                return Response.status(Response.Status.OK).entity("Postaja ni na voljo").build();
            }
        }

        return Response.status(Response.Status.OK).entity("Postaja je na voljo").build();

    }

    @Operation(description = "Vrne ceno polnjenja.", summary = "Cena polnjenja.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Cena polnjenja.",
                    content = @Content(schema = @Schema(implementation = Termin.class, type = SchemaType.ARRAY))
            )})
    @RolesAllowed("user")
    @GET
    @Path("termini/{id}")
    public Response pridobiCeno(@Parameter(
            description = "Identifikator termina za iskanje.",
            required = true) @PathParam("id") int id) {

        Termin termin = terminiZrno.getById(id);

        int id_postaja = termin.getPostaja().getId_postaja();

        Postaja postaja = postajeZrno.getById(id_postaja);

        CenaPolnjenjaDTO cenaPolnjenjaDTO = new CenaPolnjenjaDTO();

        cenaPolnjenjaDTO.setTermin_od(termin.getTermin_od());
        cenaPolnjenjaDTO.setTermin_do(termin.getTermin_do());
        cenaPolnjenjaDTO.setCena(postaja.getCena());

        Double cena = upravljanjePolnilnihPostajZrno.cenaPolnjenja(cenaPolnjenjaDTO);

        return Response.status(Response.Status.OK).entity(cena).build();

    }
}
