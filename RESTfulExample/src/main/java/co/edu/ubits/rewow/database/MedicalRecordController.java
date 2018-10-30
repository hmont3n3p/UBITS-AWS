/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.ubits.rewow.database;

import co.edu.ubits.rewow.model.MedicalRecord;
import co.edu.ubits.rewow.model.Pet;
import co.edu.ubits.rewow.model.Vaccinations;

import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author HMONTENEGRO
 */
public class MedicalRecordController implements Serializable {

    public MedicalRecordController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void edit(MedicalRecord medicalRecord) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            new ClientJpaController(emf).edit(medicalRecord.getClient());
            new VaccinationsJpaController(emf).edit(medicalRecord.getVaccination());
            new PetJpaController(emf).edit(medicalRecord.getPet());
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer IdClient = medicalRecord.getClient().getIdClient();
                Integer Idvaccination = medicalRecord.getVaccination().getIdVaccinations();
                Integer IdPet = medicalRecord.getPet().getIdPet();
                if (findMedicalRecord(IdClient, Idvaccination, IdPet) == null) {
                    throw new NonexistentEntityException("The Medical Record with IdClient =" + IdClient + " Idvaccination = Idvaccination" + "IdPet= " + IdPet + " Idno longer exists.");
                }

            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(MedicalRecord medicalRecord) {
        EntityManager em = getEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = new ClientJpaController(emf).findClient(medicalRecord.getClient().getIdClient());
            Vaccinations vaccination = new VaccinationsJpaController(emf).findVaccinations(medicalRecord.getVaccination().getIdVaccinations());
            Pet pet = new PetJpaController(emf).findPet(medicalRecord.getPet().getIdPet());
            boolean crear = client == null && vaccination == null && pet == null;
            if (!crear) {
                int result = em.createNativeQuery("INSERT INTO `ReWow`.`MedicalRecord` (`Client_idClient`, `Vaccinations_idVaccinations`, `Pet_idPet`, `Description`) VALUES (?, ?, ?, NULL)")
                        .setParameter(1, medicalRecord.getClient().getIdClient().toString())
                        .setParameter(2, medicalRecord.getVaccination().getIdVaccinations().toString())
                        .setParameter(3, medicalRecord.getPet().getIdPet().toString()).executeUpdate();
                em.getTransaction().commit();
            } else {
                em.persist(medicalRecord.getClient());
                em.persist(medicalRecord.getVaccination());
                em.persist(medicalRecord.getPet());
                em.getTransaction().commit();

                em.getTransaction().begin();
                int result = em.createNativeQuery("INSERT INTO `ReWow`.`MedicalRecord` (`Client_idClient`, `Vaccinations_idVaccinations`, `Pet_idPet`, `Description`) VALUES (?, ?, ?, NULL)")
                        .setParameter(1, medicalRecord.getClient().getIdClient().toString())
                        .setParameter(2, medicalRecord.getVaccination().getIdVaccinations().toString())
                        .setParameter(3, medicalRecord.getPet().getIdPet().toString()).executeUpdate();
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            throw new RuntimeException("No ha sido posible insertar el registro.", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Integer clientId, Integer vaccinationId, Integer petId) {
        EntityManager em = getEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = new ClientJpaController(emf).findClient(clientId);
            Vaccinations vaccination = new VaccinationsJpaController(emf).findVaccinations(vaccinationId);
            Pet pet = new PetJpaController(emf).findPet(petId);
            boolean crear = client == null && vaccination == null && pet == null;
            if (!crear) {
                int result = em.createNativeQuery("INSERT INTO `ReWow`.`MedicalRecord` (`Client_idClient`, `Vaccinations_idVaccinations`, `Pet_idPet`, `Description`) VALUES (?, ?, ?, NULL)")
                        .setParameter(1, clientId)
                        .setParameter(2, vaccinationId)
                        .setParameter(3, petId).executeUpdate();
                em.getTransaction().commit();
            } else {
                em.persist(clientId);
                em.persist(vaccinationId);
                em.persist(petId);
                em.getTransaction().commit();

                em.getTransaction().begin();
                int result = em.createNativeQuery("INSERT INTO `ReWow`.`MedicalRecord` (`Client_idClient`, `Vaccinations_idVaccinations`, `Pet_idPet`, `Description`) VALUES (?, ?, ?, NULL)")
                        .setParameter(1, clientId)
                        .setParameter(2, vaccinationId)
                        .setParameter(3, petId).executeUpdate();
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            throw new RuntimeException("No ha sido eliminar insertar el registro.", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicalRecord> findMedicalRecordEntities() {
        return findMedicalRecordEntities(true, -1, -1);
    }

    private List<MedicalRecord> findMedicalRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {

            Query q = em.createNativeQuery("SELECT  Client_idClient, Vaccinations_idVaccinations, Pet_idPet, Description  FROM ReWow.MedicalRecord");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            Iterator iterator = q.getResultList().iterator();
            List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
            while (iterator.hasNext()) {
                Object[] tuple = (Object[]) iterator.next();
                Client client = new ClientJpaController(emf).findClient(new Integer(tuple[0].toString()));
                Vaccinations vaccination = new VaccinationsJpaController(emf).findVaccinations(new Integer(tuple[1].toString()));
                Pet pet = new PetJpaController(emf).findPet(new Integer(tuple[2].toString()));
                medicalRecords.add(new MedicalRecord(client, vaccination, pet));

            }

            return medicalRecords;
        } finally {
            em.close();
        }
    }

    public void destroy(Integer clientId, Integer vaccinationId, Integer petId) throws NonexistentEntityException {

        EntityManager em = getEntityManager();
        try {
            em = getEntityManager();
            MedicalRecord medicalRecord = findMedicalRecord(clientId, vaccinationId, petId);
            boolean borrar = medicalRecord == null || medicalRecord.getClient() == null || medicalRecord.getVaccination() == null || medicalRecord.getPet() == null;
            if (!borrar) {

                try {
                    em.getTransaction().begin();
                    int result = em.createNativeQuery("DELETE FROM `ReWow`.`MedicalRecord`  WHERE "
                            + "`Client_idClient`= ? AND "
                            + "`Vaccinations_idVaccinations` = ? AND"
                            + " `Pet_idPet` = ? ")
                            .setParameter(1, medicalRecord.getClient().getIdClient().toString())
                            .setParameter(2, medicalRecord.getVaccination().getIdVaccinations().toString())
                            .setParameter(3, medicalRecord.getPet().getIdPet().toString()).executeUpdate();
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    throw new NonexistentEntityException("The Medical Record with IdClient =" + medicalRecord.getClient().getIdClient() + " Idvaccination =" + medicalRecord.getVaccination().getIdVaccinations() + " IdPet= " + medicalRecord.getPet().getIdPet() + " Idno longer exists.");
                }
                em.getTransaction().begin();
                new ClientJpaController(emf).destroy(clientId);
                new VaccinationsJpaController(emf).destroy(vaccinationId);
                new PetJpaController(emf).destroy(petId);
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            throw new RuntimeException("No ha sido eliminar insertar el registro.", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public MedicalRecord findMedicalRecord(Integer clientId, Integer vaccinationId, Integer petId) {
        EntityManager em = getEntityManager();
        try {

            Query q = em.createNativeQuery("SELECT  Client_idClient, Vaccinations_idVaccinations, Pet_idPet, Description  FROM ReWow.MedicalRecord where Client_idClient= ?  and Vaccinations_idVaccinations= ? and Pet_idPet= ?");
            q.setParameter(1, clientId == 0 ? "Client_idClient" : clientId);
            q.setParameter(2, vaccinationId == 0 ? "Vaccinations_idVaccinations" : vaccinationId);
            q.setParameter(3, petId == 0 ? "Pet_idPet" : petId);
           
            
            q.setMaxResults(1);

            Iterator iterator = q.getResultList().iterator();
            List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
            while (iterator.hasNext()) {
                Object[] tuple = (Object[]) iterator.next();
                Client client = new ClientJpaController(emf).findClient(new Integer(tuple[0].toString()));
                Vaccinations vaccination = new VaccinationsJpaController(emf).findVaccinations(new Integer(tuple[1].toString()));
                Pet pet = new PetJpaController(emf).findPet(new Integer(tuple[2].toString()));
                medicalRecords.add(new MedicalRecord(client, vaccination, pet));

            }
            if (medicalRecords.isEmpty()) {
                return new MedicalRecord();
            }

            return medicalRecords.get(0);
        } finally {
            em.close();
        }
    }

}
