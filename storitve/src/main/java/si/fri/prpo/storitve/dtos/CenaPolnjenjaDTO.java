package si.fri.prpo.storitve.dtos;

import java.sql.Timestamp;

public class CenaPolnjenjaDTO {

    private Timestamp termin_od;

    private Timestamp termin_do;

    private Double cena;

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

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }
}
