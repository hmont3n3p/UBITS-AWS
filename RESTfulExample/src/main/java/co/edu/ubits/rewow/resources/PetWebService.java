/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.ubits.rewow.resources;

/**
 *
 * @author HMONTENEGRO
 */
import co.edu.ubits.rewow.database.PetJpaController;
import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.Pet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pets")
public class PetWebService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReWowPersistenceUNIT");
    PetJpaController controller = new PetJpaController(emf);

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Pet getPet(@PathParam("id") String id) {
        return controller.findPet(Integer.parseInt(id));

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPet(Pet pet) {
        controller.create(pet);
        return Response.status(200).entity("Registro creado exitosamente.").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deletePet(@PathParam("id") String id) {
        try {
            controller.destroy(Integer.parseInt(id));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PetWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).entity("No fue posible eliminar el registro.").build();
        }
        return Response.status(200).entity("Registro eliminado exitosamente").build();
    }

    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Pet> getAllPets() {
        return controller.findPetEntities();
    }
}
