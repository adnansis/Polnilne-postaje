package si.fri.prpo.storitve.zrna;

import si.fri.prpo.entitete.Termin;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.anotacije.BeleziKlice;
import si.fri.prpo.storitve.dtos.CenaPolnjenjaDTO;
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.dtos.PreveriNaVoljoDTO;
import si.fri.prpo.storitve.izjeme.NeveljavenTerminDtoIzjema;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.util.UUID;

@BeleziKlice
@ApplicationScoped
public class UpravljanjePolnilnihPostajZrno {

    private UUID uuid = UUID.randomUUID();
    private static Logger log = Logger.getLogger(UpravljanjePolnilnihPostajZrno.class.getName());

    @Inject
    private UporabnikiZrno uporabnikiZrno;
    @Inject
    private TerminiZrno terminiZrno;
    @Inject
    private PostajeZrno postajeZrno;

    @PostConstruct
    public void init() {
        log.info("Zrno za upravljanje polnilnih postaj se je ustvarilo. ID = " + this.uuid.toString());
    }

    @PreDestroy
    public void destroy() {
        log.info("Zrno za upravljanje polnilnih postaj se je unicilo. ID = " + this.uuid.toString());
    }

    public boolean dodajTermin(DodajTerminDTO dodajTerminDTO) {

        Uporabnik uporabnik = uporabnikiZrno.getById(dodajTerminDTO.getId_uporabnik());
        Termin termin = null;

        Timestamp current_time = new Timestamp(System.currentTimeMillis());

        if(dodajTerminDTO.getId_postaja() <= 0 || dodajTerminDTO.getTermin_do() == null || dodajTerminDTO.getTermin_od() == null ||
            dodajTerminDTO.getId_uporabnik() <= 0) {
            String msg = "Podan termin ne vsebuje vseh obveznih podatkov!";
            log.severe(msg);
            throw new NeveljavenTerminDtoIzjema(msg);
        }

        if(uporabnik != null) {

            termin = new Termin();

            termin.setUporabnik(uporabnik);

            Timestamp termin_od = dodajTerminDTO.getTermin_od();
            Timestamp termin_do = dodajTerminDTO.getTermin_do();

            if(current_time.before(termin_od) && current_time.before(termin_do) && termin_od.before(termin_do)) {
                termin.setTermin_od(dodajTerminDTO.getTermin_od());
                termin.setTermin_do(dodajTerminDTO.getTermin_do());
            } else {
                log.info("Napaka pri dodajanju termina. Polje termin_od ni veljavno.");
                return false;
            }

            if(dodajTerminDTO.getId_postaja() >= 1) {
                termin.setPostaja(postajeZrno.getById(dodajTerminDTO.getId_postaja()));
            } else {
                log.info("Napaka pri dodajanju termina. Postaja ne obstaja.");
                return false;
            }

        } else {
            log.info("Napaka pri dodajanju termina. Uporabnik ne obstaja.");
            return false;
        }

        return terminiZrno.addTermin(termin);
    }

    public boolean preveriNaVoljo(PreveriNaVoljoDTO preveriNaVoljoDTO) {

        Timestamp termin_od = preveriNaVoljoDTO.getTermin_od();
        Timestamp termin_do = preveriNaVoljoDTO.getTermin_do();

        Timestamp current_time = new Timestamp(System.currentTimeMillis());

        if(current_time.after(termin_od) && current_time.before(termin_do)) {
            return true;
        }

        return false;
    }

    public double cenaPolnjenja(CenaPolnjenjaDTO cenaPolnjenjaDTO) {

        Double cena = 0.0;

        if(cenaPolnjenjaDTO != null) {

            Timestamp termin_od = cenaPolnjenjaDTO.getTermin_od();
            Timestamp termin_do = cenaPolnjenjaDTO.getTermin_do();

            // razlika v milisekundah
            long razlikaMS = termin_do.getTime() - termin_od.getTime();

            if(razlikaMS <= 0) {
                log.info("Napaka pri izračunu cene. Termin ni veljaven.");
                return -1;
            } else {
                long minute = (razlikaMS / 1000) / 60;

                cena = (minute * cenaPolnjenjaDTO.getCena());

                return cena;
            }
        }

        return -1;
    }

}
