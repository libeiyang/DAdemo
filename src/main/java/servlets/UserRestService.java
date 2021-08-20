package servlets;

import jakarta.ws.rs.*;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import java.util.Iterator;
import java.util.List;

@Path("/user")
@ApplicationPath("/resources")
@Stateless
public class UserRestService extends Application{
    @PersistenceContext(
            unitName = "DAdemoPU"
    )
    EntityManager em;

    public UserRestService() {
    }

    @GET
    public String notification() {
        String notificate = "Enter a phone number to find the user";
        return notificate;
    }

    @GET
    @Path("/{username}")
    public String searchPhonenumber(@PathParam("username") String username) {
        Query query = this.em.createQuery("SELECT p FROM UserEntity p Where p.username = :searchFirst", UserEntity.class);
        query.setParameter("searchFirst", username);
        List<UserEntity> users = query.getResultList();
        if (users.isEmpty()) {
            return "This username is not exist.";
        }
        else {
            UserEntity user = users.get(0);
            return "Phone number: " + user.getPhoneNumber();
        }
    }
}