package si.fri.prpo.storitve;

import si.fri.prpo.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UporabnikiZrno {

    @PersistenceContext(unitName = "polnilnepostaje")
    private EntityManager em;

    private List<Uporabnik> uporabniki = new ArrayList<>();

    public List<Uporabnik> getUporabniki() {

        Query q1 = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.getAll", Uporabnik.class);

        //Query q2 = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.findByUsername", Uporabnik.class);
        //q2.setParameter("uporabnisko_ime", "jeffbezos");

        uporabniki = q1.getResultList();

        return uporabniki;
    }

    public List<Uporabnik> getUporabnikiCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Uporabnik> cq = cb.createQuery(Uporabnik.class);

        Root<Uporabnik> r = cq.from(Uporabnik.class);

        cq.select(r);

        Query q = em.createQuery(cq);
        uporabniki = q.getResultList();

        return uporabniki;
    }
}
