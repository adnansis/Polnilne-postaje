package si.fri.prpo.entitete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "uporabnik")
@NamedQueries(value = {
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Uporabnik.getAll", query = "SELECT u FROM uporabnik u"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Uporabnik.findById", query = "SELECT u FROM uporabnik u WHERE u.id_uporabnik = :id_uporabnik"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Uporabnik.findByUsername", query = "SELECT u FROM uporabnik u WHERE u.uporabnisko_ime = :uporabnisko_ime"),
        @NamedQuery(name = "si.fri.prpo.polnilnepostaje.Uporabnik.findByType", query = "SELECT u FROM uporabnik u WHERE u.tip = :tip")
})
public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_uporabnik;

    private String tip;

    private String uporabnisko_ime;

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Postaja> postaje = new ArrayList<>();

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Termin> rezervacije = new ArrayList<>();

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Termin> termini = new ArrayList<>();

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
    }

    public List<Postaja> getPostaje() {
        return postaje;
    }

    public void setPostaje(List<Postaja> postaje) {
        this.postaje = postaje;
    }

    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getId_uporabnik() {
        return id_uporabnik;
    }

    public void setId_uporabnik(Integer id_uporabnik) {
        this.id_uporabnik = id_uporabnik;
    }

    public String toString() {
        return getId_uporabnik() + ":" + getTip() + ":" + getUporabnisko_ime();
    }
}
