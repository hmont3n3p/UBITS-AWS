
package co.edu.ubits.rewow.database;

import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.Vaccinations;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class VaccinationsJpaController implements Serializable {

    public VaccinationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vaccinations vaccinations) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vaccinations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vaccinations vaccinations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vaccinations = em.merge(vaccinations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vaccinations.getIdVaccinations();
                if (findVaccinations(id) == null) {
                    throw new NonexistentEntityException("The vaccinations with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vaccinations vaccinations;
            try {
                vaccinations = em.getReference(Vaccinations.class, id);
                vaccinations.getIdVaccinations();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vaccinations with id " + id + " no longer exists.", enfe);
            }
            em.remove(vaccinations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vaccinations> findVaccinationsEntities() {
        return findVaccinationsEntities(true, -1, -1);
    }

    public List<Vaccinations> findVaccinationsEntities(int maxResults, int firstResult) {
        return findVaccinationsEntities(false, maxResults, firstResult);
    }

    private List<Vaccinations> findVaccinationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vaccinations.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vaccinations findVaccinations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vaccinations.class, id);
        } finally {
            em.close();
        }
    }

    public int getVaccinationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vaccinations> rt = cq.from(Vaccinations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
