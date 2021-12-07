package si.fri.prpo.storitve.dtos;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Schema(name = "DodajTerminDTO", implementation = DodajTerminDTO.class)
public class DodajTerminDTO {

    @Schema(required = true, example = "1")
    private int id_uporabnik;

    @Schema(required = true, example = "1")
    private int id_postaja;

    @Schema(required = true, example = "2021-12-01 10:00:00")
    private Timestamp termin_od;

    @Schema(required = true, example = "2021-12-01 12:00:00")
    private Timestamp termin_do;

    public int getId_uporabnik() {
        return id_uporabnik;
    }

    public void setId_uporabnik(int id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }

    public int getId_postaja() {
        return id_postaja;
    }

    public void setId_postaja(int id_postaja) {
        this.id_postaja = id_postaja;
    }

    public Timestamp getTermin_od() {
        return termin_od;
    }

    public void setTermin_od(Timestamp termin_od) {
        this.termin_od = termin_od;
    }

    public Timestamp getTermin_do() {
        return termin_do;
    }

    public void setTermin_do(Timestamp termin_do) {
        this.termin_do = termin_do;
    }
}
