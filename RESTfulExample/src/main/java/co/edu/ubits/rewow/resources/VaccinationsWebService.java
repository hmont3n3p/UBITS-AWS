package co.edu.ubits.rewow.resources;

import co.edu.ubits.rewow.database.VaccinationsJpaController;
import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.Vaccinations;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vaccinations")
public class VaccinationsWebService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReWowPersistenceUNIT");
    VaccinationsJpaController controller = new VaccinationsJpaController(emf);

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vaccinations getVaccination(@PathParam("id") String id) {
        return controller.findVaccinations(Integer.parseInt(id));

    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editVaccination(Vaccinations vaccination) {
        try {

            controller.edit(vaccination);

            return Response.status(200).entity("Registro editado exitosamente.").build();

        } catch (Exception e) {
            return Response.status(500).entity("Message:" + e.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createVaccination(Vaccinations vaccination) {
        controller.create(vaccination);
        return Response.status(200).entity("Registro creado exitosamente.").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response destroyVaccination(@PathParam("id") String id) {
        try {
            controller.destroy(Integer.parseInt(id));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VaccinationsWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).entity("No fue posible eliminar el registro.").build();
        }
        return Response.status(200).entity("Registro eliminado exitosamente").build();
    }

    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vaccinations> getAllVaccinations() {
        return controller.findVaccinationsEntities();
    }
}
