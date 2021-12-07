package si.fri.prpo.storitve.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.entitete.Postaja;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.storitve.anotacije.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@BeleziKlice
@RequestScoped
public class PostajeZrno {

    @PersistenceContext(unitName = "polnilnepostaje")
    private EntityManager em;

    private UUID uuid = UUID.randomUUID();
    private static Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PostConstruct
    public void init() {
        log.info("Zrno za entiteto Postaja se je ustvarilo. ID = " + this.uuid.toString());
    }

    @PreDestroy
    public void destroy() {
        log.info("Zrno za entiteto Postaja se je unicilo. ID = " + this.uuid.toString());
    }

    public List<Postaja> getPostaje() {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Postaja.getAll", Postaja.class);

        List<Postaja> postaje = q.getResultList();

        return postaje;
    }

    public List<Postaja> getPostaje(QueryParameters query) {
        return JPAUtils.queryEntities(em, Postaja.class, query);
    }

    public Long getPostajeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Postaja.class, query);
    }

    public Postaja getById(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Postaja.findById", Postaja.class);
        q.setParameter("id_postaja", id);

        Postaja p;

        try {
            p = (Postaja) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return p;
    }

    public List<Postaja> getByLocation(String location) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Postaja.findByLocation", Postaja.class);
        q.setParameter("lokacija", location);

        List<Postaja> postaje = q.getResultList();

        return postaje;
    }

    public List<Postaja> getAllAvailable(int id) {

        Query q = em.createNamedQuery("si.fri.prpo.polnilnepostaje.Postaja.getAllAvailable", Postaja.class);

        List<Postaja> postaje = q.getResultList();

        return postaje;
    }

    @Transactional
    public boolean addPostaja(Postaja p) {

        if(p != null) {
            em.persist(p);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean updatePostaja(Postaja p) {

        if(p != null) {
            em.merge(p);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean removePostaja(int id) {

        Postaja p = em.find(Postaja.class, id);

        if(p != null) {
            em.remove(p);
            return true;
        }

        return false;
    }
}
