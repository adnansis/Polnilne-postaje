package si.fri.prpo.storitve.zrna;

import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.anotacije.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@BeleziKlice
@RequestScoped
public class UporabnikiZrno {

    @PersistenceContext(unitName = "polnilnepostaje")
    private EntityManager em;

    private UUID uuid = UUID.randomUUID();
    private static Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PostConstruct
    public void init() {
        log.info("Zrno za entiteto Uporabnik se je ustvarilo. ID = " + this.uuid.toString());
    }

    @PreDestroy
    public void destroy() {
        log.info("Zrno za entiteto Uporabnik se je unicilo. ID = " + this.uuid.toString());
    }

    public List<Uporabnik> getUporabniki() {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.getAll", Uporabnik.class);

        List<Uporabnik> uporabniki = q.getResultList();

        return uporabniki;
    }

    public Uporabnik getById(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.findById", Uporabnik.class);
        q.setParameter("id_uporabnik", id);

        Uporabnik u = (Uporabnik) q.getSingleResult();

        return u;
    }

    public Uporabnik getByUsername(String username) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.findByUsername", Uporabnik.class);
        q.setParameter("uporabnisko_ime", username);

        Uporabnik u = (Uporabnik) q.getSingleResult();

        return u;
    }

    public List<Uporabnik> getByType(String type) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Uporabnik.findByType", Uporabnik.class);
        q.setParameter("tip", type);

        List<Uporabnik> uporabniki = q.getResultList();

        return uporabniki;
    }

    @Transactional
    public boolean addUporabnik(Uporabnik u) {

        if(u != null) {
            em.persist(u);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean updateUporabnik(Uporabnik u) {

        if(u != null) {
            em.merge(u);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean removeUporabnik(int id) {

        Uporabnik u = em.find(Uporabnik.class, id);

        if(u != null) {
            em.remove(u);
            return true;
        }

        return false;
    }
}
