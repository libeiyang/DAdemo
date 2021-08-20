package ejbs;

import entities.UserEntity;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@jakarta.ejb.Stateful(name = "LoginBean")
public class LoginBean {

    private int user_id;
    private String phone_number;
    private String username;
    @PersistenceContext(name="DAdemoPU")
    EntityManager em;
    public LoginBean() {
    }

    public boolean login(String username, String password) {
        Query query = em.createQuery("SELECT p FROM UserEntity p WHERE p.username = :searchUsername AND p.password = :searchPassword", UserEntity.class);
        query.setParameter("searchUsername", username);
        query.setParameter("searchPassword", password);
        List<UserEntity> l = query.getResultList();
        if (l.isEmpty())
            return false;
        else {
            UserEntity user = l.get(0);
            user_id = user.getUserId();
            username = user.getUsername();
            //lastName = user.getLastname();
            this.username = username;
            return true;
        }
    }


    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
