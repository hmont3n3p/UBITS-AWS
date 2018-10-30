package co.edu.ubits.rewow.resources;

import co.edu.ubits.rewow.database.ClientJpaController;
import co.edu.ubits.rewow.database.MedicalRecordController;
import co.edu.ubits.rewow.database.PetJpaController;
import co.edu.ubits.rewow.database.VaccinationsJpaController;
import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.MedicalRecord;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("medical-records/")
public class MedicalRecordService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReWowPersistenceUNIT");
    ClientJpaController controller1 = new ClientJpaController(emf);
    VaccinationsJpaController controller2 = new VaccinationsJpaController(emf);
    PetJpaController controller3 = new PetJpaController(emf);
    MedicalRecordController controller = new MedicalRecordController(emf);

    @DELETE
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response destroyMedicalRecord(MedicalRecord medicalRecord) {
        try {
            controller.destroy(medicalRecord.getClient().getIdClient(), medicalRecord.getVaccination().getIdVaccinations(), medicalRecord.getPet().getIdPet());

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClientWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).entity("No fue posible eliminar el registro.").build();
        }
        return Response.status(200).entity("Registro eliminado exitosamente").build();
    }

    @DELETE
    @Path("/{idClient}/{Idvaccination}/{IdPet}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteMedicalRecord(@PathParam("idClient") String idClient, @PathParam("Idvaccination") String Idvaccination, @PathParam("IdPet") String IdPet) {
        try {

            controller.destroy(new Integer(idClient), new Integer(Idvaccination), new Integer(IdPet));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClientWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).entity("No fue posible eliminar el registro.").build();
        }
        return Response.status(200).entity("Registro eliminado exitosamente").build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editMedicalRecord(MedicalRecord medicalRecord) {
        try {

            controller.edit(medicalRecord);

            return Response.status(200).entity("Registro editado exitosamente.").build();

        } catch (Exception e) {
            return Response.status(500).entity("Message:" + e.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{idClient}/{Idvaccination}/{IdPet}")
    public Response createMedicalRecord(@PathParam("idClient") String idClient, @PathParam("Idvaccination") String Idvaccination, @PathParam("IdPet") String IdPet) {
        try {

            controller.create(new Integer(idClient), new Integer(Idvaccination), new Integer(IdPet));

            return Response.status(200).entity("Registro creado exitosamente.").build();

        } catch (Exception e) {
            return Response.status(500).entity("Message:" + e.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createMedicalRecord(MedicalRecord medicalRecord) {
        try {

            controller.create(medicalRecord);

            return Response.status(200).entity("Registro creado exitosamente.").build();

        } catch (Exception e) {
            return Response.status(500).entity("Message:" + e.getMessage()).build();
        }
    }

    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MedicalRecord> getAllMedicalRecords() {
        return controller.findMedicalRecordEntities();
    }

    @GET
    @Path("/query")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public MedicalRecord getMedicalRecord(@DefaultValue("0") @QueryParam("client") int client,
            @DefaultValue("0") @QueryParam("vaccination") int vaccination,
            @DefaultValue("0") @QueryParam("pet") int pet) {

        return controller.findMedicalRecord(client, vaccination, pet);

    }

    @GET
    @Path("/{idClient}/{Idvaccination}/{IdPet}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public MedicalRecord getMedicalRecord(@PathParam("idClient") String idClient, @PathParam("Idvaccination") String Idvaccination, @PathParam("IdPet") String IdPet) {

        return controller.findMedicalRecord(new Integer(idClient), new Integer(Idvaccination), new Integer(Idvaccination));

    }

}
