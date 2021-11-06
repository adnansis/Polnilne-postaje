package si.fri.prpo.api;

import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.UporabnikiZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;
    private List<Uporabnik> uporabniki;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter w = resp.getWriter();

        w.println("Vsi uporabniki z uporabo NamedQuery:");
        w.println();

        uporabniki = uporabnikiZrno.getUporabniki();

        w.println("id_uporabnik:tip:uporabnisko_ime");
        for(int i = 0; i < uporabniki.size(); i++) {
            w.println(uporabniki.get(i).toString());
        }

        w.println();
        w.println("Vsi uporabniki z uporabo Criteria API:");
        w.println();

        uporabniki = uporabnikiZrno.getUporabnikiCriteriaAPI();

        w.println("id_uporabnik:tip:uporabnisko_ime");
        for(int i = 0; i < uporabniki.size(); i++) {
            w.println(uporabniki.get(i).toString());
        }
    }
}