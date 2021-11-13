package si.fri.prpo.api;

import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Termin;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.dtos.CenaPolnjenjaDTO;
import si.fri.prpo.storitve.dtos.DodajTerminDTO;
import si.fri.prpo.storitve.dtos.PreveriNaVoljoDTO;
import si.fri.prpo.storitve.zrna.PostajeZrno;
import si.fri.prpo.storitve.zrna.TerminiZrno;
import si.fri.prpo.storitve.zrna.UporabnikiZrno;
import si.fri.prpo.storitve.zrna.UpravljanjePolnilnihPostajZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;
    @Inject
    private PostajeZrno postajeZrno;
    @Inject
    private TerminiZrno terminiZrno;
    @Inject
    private UpravljanjePolnilnihPostajZrno upravljanjePolnilnihPostajZrno;

    private List<Uporabnik> uporabniki;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter w = resp.getWriter();

        Uporabnik u1 = new Uporabnik();
        u1.setUporabnisko_ime("elonmusk");
        u1.setTip("lastnik");

        Uporabnik u2 = new Uporabnik();
        u2.setUporabnisko_ime("billgates");
        u2.setTip("uporabnik");

        Uporabnik u3 = new Uporabnik();
        u3.setUporabnisko_ime("jeffbezos");
        u3.setTip("uporabnik");

        uporabnikiZrno.addUporabnik(u1);
        uporabnikiZrno.addUporabnik(u2);
        uporabnikiZrno.addUporabnik(u3);

        uporabniki = uporabnikiZrno.getUporabniki();

        for(int i = 0; i < uporabniki.size(); i++) {
            w.println(uporabniki.get(i).toString());
        }

        Uporabnik u4 = new Uporabnik();
        u4.setUporabnisko_ime("chucknorris");
        u4.setTip("chucknorris");

        w.println("-------------------------------------");

        uporabnikiZrno.addUporabnik(u4);

        uporabniki = uporabnikiZrno.getUporabniki();

        for(int i = 0; i < uporabniki.size(); i++) {
            w.println(uporabniki.get(i).toString());
        }

        w.println("-------------------------------------");

        CenaPolnjenjaDTO cenaPolnjenjaDTO = new CenaPolnjenjaDTO();
        // current + 30 mins
        Timestamp termin_od = new Timestamp(System.currentTimeMillis() + 1800000);
        // current + 2 hours
        Timestamp termin_do = new Timestamp(System.currentTimeMillis() + 7200000);

        cenaPolnjenjaDTO.setTermin_od(termin_od);
        cenaPolnjenjaDTO.setTermin_do(termin_do);
        cenaPolnjenjaDTO.setCena(0.15);

        Double cena = upravljanjePolnilnihPostajZrno.cenaPolnjenja(cenaPolnjenjaDTO);

        w.println("Cena polnjenja za 2 uri pri ceni 0.15 euro je " + cena + " euro.");

        w.println("-------------------------------------");
        w.println("Termini pred vstavljanjem:");

        List<Termin> termini = terminiZrno.getTermini();
        for(int i = 0; i < termini.size(); i++) {
            w.println(termini.get(i).toString());
        }

        w.println("-------------------------------------");

        // priprava podatkov
        Postaja postaja = new Postaja();
        postaja.setId_postaja(1);
        postaja.setUporabnik(u1);
        postaja.setLokacija("Večna pot 113");
        postaja.setIme("TYPE 2");
        postaja.setMoc(22.0);
        Time time_od = Time.valueOf("00:00:00");
        Time time_do = Time.valueOf("23:59:59");
        postaja.setObratuje_od(time_od);
        postaja.setObratuje_do(time_do);
        postaja.setCena(0.15);
        postaja.setNa_voljo(true);
        postajeZrno.addPostaja(postaja);
        // konec priprave

        DodajTerminDTO dodajTerminDTO = new DodajTerminDTO();
        dodajTerminDTO.setId_uporabnik(4);
        dodajTerminDTO.setId_postaja(1);
        dodajTerminDTO.setTermin_od(termin_od);
        dodajTerminDTO.setTermin_do(termin_do);

        if(upravljanjePolnilnihPostajZrno.dodajTermin(dodajTerminDTO)) {
            w.println("Uspesno dodan termin.");
        } else {
            w.println("Napaka pri dodajanju termina.");
        }

        w.println("-------------------------------------");
        w.println("Termini po vstavljanju:");

        termini = terminiZrno.getTermini();

        for(int i = 0; i < termini.size(); i++) {
            w.println(termini.get(i).toString());
        }

        w.println("-------------------------------------");

        PreveriNaVoljoDTO preveriNaVoljoDTO = new PreveriNaVoljoDTO();

        termin_od = new Timestamp(System.currentTimeMillis() - 7200000);
        termin_do = new Timestamp(System.currentTimeMillis() + 7200000);

        preveriNaVoljoDTO.setTermin_od(termin_od);
        preveriNaVoljoDTO.setTermin_do(termin_do);

        // vrne true, ker se termin trenutno "izvaja"
        if(upravljanjePolnilnihPostajZrno.preveriNaVoljo(preveriNaVoljoDTO)) {
            w.println("Polnilnica trenutno ni na voljo.");
            // posodobimo zapis v bazi ??
        }

        // vrne false, ker je termin čez eno uros
        termin_od = new Timestamp(System.currentTimeMillis() + 3600000);
        preveriNaVoljoDTO.setTermin_od(termin_od);

        if(!upravljanjePolnilnihPostajZrno.preveriNaVoljo(preveriNaVoljoDTO)) {
            w.println("Polnilnica je na voljo.");
        }
    }
}