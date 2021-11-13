package si.fri.prpo.storitve.dtos;

import java.sql.Timestamp;

public class DodajTerminDTO {

    private int id_uporabnik;

    private int id_postaja;

    private Timestamp termin_od;

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
