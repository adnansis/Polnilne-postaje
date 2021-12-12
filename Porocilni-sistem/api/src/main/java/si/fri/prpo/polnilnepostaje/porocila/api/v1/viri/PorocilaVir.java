package si.fri.prpo.polnilnepostaje.porocila.api.v1.viri;

import si.fri.prpo.polnilnepostaje.porocila.api.v1.dtos.PorociloDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("porocila")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PorocilaVir {

    private Logger log = Logger.getLogger(PorocilaVir.class.getName());

    private List<PorociloDTO> porocila;

    @PostConstruct
    public void init() {

        porocila = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.now();

        porocila.add(new PorociloDTO("Postaja ima poškodovan kabel za polnjenje.",
                2, datetime.format(dtf)));

        log.info("Vir za Porocila se je ustvaril.");
    }

    @PreDestroy
    public void destroy() {
        log.info("Vir za Porocila se je unicil.");
    }

    @GET
    public Response pridobiPorocila() {
        return Response.ok(porocila).build();
    }

    @POST
    public Response dodajPorocilo(PorociloDTO porociloDTO) {

        if(porociloDTO != null) {
            porocila.add(porociloDTO);
        }

        log.info("Dodano novo porocilo.");

        return Response.ok().build();
    }
}
