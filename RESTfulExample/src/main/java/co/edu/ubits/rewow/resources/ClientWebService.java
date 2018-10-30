package co.edu.ubits.rewow.resources;

import co.edu.ubits.rewow.database.ClientJpaController;
import co.edu.ubits.rewow.database.exceptions.NonexistentEntityException;
import co.edu.ubits.rewow.model.Client;
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

@Path("/clients")
public class ClientWebService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReWowPersistenceUNIT");
    ClientJpaController controller = new ClientJpaController(emf);

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client getClient(@PathParam("id") String id) {

        return controller.findClient(Integer.parseInt(id));

    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editClient(Client client) {
        try {

            controller.edit(client);

            return Response.status(200).entity("Registro editado exitosamente.").build();

        } catch (Exception e) {
            return Response.status(500).entity("Message:" + e.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createClient(Client client) {
        controller.create(client);
        return Response.status(200).entity("Registro creado exitosamente.").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response destroyClient(@PathParam("id") String id) {
        try {
            controller.destroy(Integer.parseInt(id));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClientWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).entity("No fue posible eliminar el registro.").build();
        }
        return Response.status(200).entity("Registro eliminado exitosamente").build();
    }

    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Client> getAllClients() {
        return controller.findClientEntities();
    }
}
