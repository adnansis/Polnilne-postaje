package si.fri.prpo.storitve.dtos;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Schema(name = "PreveriNaVoljoDTO", implementation = PreveriNaVoljoDTO.class)
public class PreveriNaVoljoDTO {

    @Schema(required = true, example = "2021-12-01 10:00:00")
    private Timestamp termin_od;

    @Schema(required = true, example = "2021-12-01 12:00:00")
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
