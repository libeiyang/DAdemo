package ejbs;

import entities.MessageEntity;
import entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@ManagedBean(
        name = "MessageManagedBean"
)
public class MessageManagedBean {
    private List<MessageEntity> messages = new ArrayList();
    @PersistenceContext(unitName = "DAdemoPU")
    EntityManager em;

    public MessageManagedBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        if (httpSession.getAttribute("user_id") != null) {
            int userID = (Integer)httpSession.getAttribute("user_id");
            //this.messages = ((UserEntity)this.em.find(UserEntity.class, userID)).getMessages();
            Query query = this.em.createQuery("SELECT m FROM MessageEntity m", MessageEntity.class);
            List<MessageEntity> m = query.getResultList();

            this.messages = m;
        } else {
            try {
                context.getExternalContext().redirect("/DAdemo8/login.xhtml");
            } catch (IOException var5) {
                System.out.println("Logout error");
            }
        }

    }

    public List<MessageEntity> getMessages() {
        return this.messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}
