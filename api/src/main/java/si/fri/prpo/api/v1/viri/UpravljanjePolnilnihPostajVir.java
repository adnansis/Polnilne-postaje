package si.fri.prpo.api.v1.viri;

import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Termin;
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.zrna.PostajeZrno;
import si.fri.prpo.storitve.zrna.TerminiZrno;
import si.fri.prpo.storitve.zrna.UpravljanjePolnilnihPostajZrno;
import si.fri.prpo.storitve.dtos.CenaPolnjenjaDTO;
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.dtos.PreveriNaVoljoDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Time;
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

    @POST
    @Path("termini")
    public Response dodajTermin(DodajTerminDTO dodajTerminDTO) {

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjePolnilnihPostajZrno.dodajTermin(dodajTerminDTO))
                .build();
    }

    @GET
    @Path("postaje/{id}")
    public Response pridobiNaVoljo(@PathParam("id") int id) {

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

    @GET
    @Path("termini/{id}")
    public Response pridobiCeno(@PathParam("id") int id) {

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
