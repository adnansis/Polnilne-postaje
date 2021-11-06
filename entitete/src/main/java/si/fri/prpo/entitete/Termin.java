package si.fri.prpo.entitete;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "termin")
@NamedQueries(value = {
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Termin.getAll", query = "SELECT t FROM termin t"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Termin.findById", query = "SELECT t FROM termin t WHERE t.id_termin = :id_termin"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Termin.findByUsername", query = "SELECT t FROM termin t WHERE t.uporabnik.id_uporabnik = :id_uporabnik"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Termin.findByPostaja", query = "SELECT t FROM termin t WHERE t.postaja.id_postaja = :id_postaja"),
})
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_termin;

    @Column(name = "termin_od", nullable = false, unique = false)
    private java.sql.Timestamp termin_od;

    @Column(name = "termin_do", nullable = false, unique = false)
    private java.sql.Timestamp termin_do;

    @ManyToOne
    @JoinColumn(name = "ID_uporabnik")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "ID_postaja")
    private Postaja postaja;

    public Integer getId_termin() {
        return id_termin;
    }

    public void setId_termin(Integer id_termin) {
        this.id_termin = id_termin;
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

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Postaja getPostaja() {
        return postaja;
    }

    public void setPostaja(Postaja postaja) {
        this.postaja = postaja;
    }
}
