package si.fri.prpo.storitve.zrna;

import si.fri.prpo.entitete.Termin;
import si.fri.prpo.storitve.anotacije.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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
public class TerminiZrno {

    @PersistenceContext(unitName = "polnilnepostaje")
    private EntityManager em;

    private UUID uuid = UUID.randomUUID();
    private static Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PostConstruct
    public void init() {
        log.info("Zrno za entiteto Termin se je ustvarilo. ID = " + this.uuid.toString());
    }

    @PreDestroy
    public void destroy() {
        log.info("Zrno za entiteto Termin se je unicilo. ID = " + this.uuid.toString());
    }

    public List<Termin> getTermini() {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Termin.getAll", Termin.class);

        List<Termin> termini = q.getResultList();

        return termini;
    }

    public Termin getById(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Termin.findById", Termin.class);
        q.setParameter("id_termin", id);

        Termin t = (Termin) q.getSingleResult();

        return t;
    }

    public List<Termin> getByUserId(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Termin.findByUserID", Termin.class);
        q.setParameter("id_uporabnik", id);

        List<Termin> termini = q.getResultList();

        return termini;
    }

    public List<Termin> getByPostajaId(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Termin.findByPostajaID", Termin.class);
        q.setParameter("id_postaja", id);

        List<Termin> termini = q.getResultList();

        return termini;
    }

    @Transactional
    public boolean addTermin(Termin t) {

        if(t != null) {
            em.persist(t);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean updateTermin(Termin t) {

        if(t != null) {
            em.merge(t);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean removeTermin(int id) {

        Termin t = em.find(Termin.class, id);

        if(t != null) {
            em.remove(t);
            return true;
        }

        return false;
    }
}
