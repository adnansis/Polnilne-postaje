package si.fri.prpo.entitete;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity(name = "postaja")
@NamedQueries(value = {
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Postaja.getAll", query = "SELECT p FROM postaja p"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Postaja.findById", query = "SELECT p FROM postaja p WHERE p.id_postaja = :id_postaja"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Postaja.findByLocation", query = "SELECT p FROM postaja p WHERE p.lokacija = :lokacija"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Postaja.getAllAvailable", query = "SELECT p FROM postaja p WHERE p.na_voljo = true")
})
public class Postaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_postaja;

    private String lokacija;

    private String ime;

    private Double moc;

    private java.sql.Time obratuje_od;

    private java.sql.Time obratuje_do;

    private Double cena;

    private Boolean na_voljo;

    @Transient
    private String porocilo;

    @ManyToOne
    @JoinColumn(name = "ID_uporabnik")
    private Uporabnik uporabnik;

    public Integer getId_postaja() {
        return id_postaja;
    }

    public void setId_postaja(Integer id_postaja) {
        this.id_postaja = id_postaja;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Double getMoc() {
        return moc;
    }

    public void setMoc(Double moc) {
        this.moc = moc;
    }

    public Time getObratuje_od() {
        return obratuje_od;
    }

    public void setObratuje_od(Time obratuje_od) {
        this.obratuje_od = obratuje_od;
    }

    public Time getObratuje_do() {
        return obratuje_do;
    }

    public void setObratuje_do(Time obratuje_do) {
        this.obratuje_do = obratuje_do;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Boolean getNa_voljo() {
        return na_voljo;
    }

    public void setNa_voljo(Boolean na_voljo) {
        this.na_voljo = na_voljo;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public String getPorocilo() {
        return porocilo;
    }

    public void setPorocilo(String porocilo) {
        this.porocilo = porocilo;
    }

    public String toString() {
        return getId_postaja() + ":" + getCena() + ":" + getIme() + ":" + getLokacija() + ":" +
                getMoc() + ":" + getNa_voljo() + ":" + getObratuje_od() + ":" +
                getObratuje_do() + ":" + getUporabnik().getId_uporabnik();
    }
}
