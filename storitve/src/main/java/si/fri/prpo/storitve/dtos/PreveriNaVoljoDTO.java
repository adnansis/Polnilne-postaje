package si.fri.prpo.storitve.dtos;

import java.sql.Timestamp;

public class PreveriNaVoljoDTO {

    private Timestamp termin_od;

    private Timestamp termin_do;

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
