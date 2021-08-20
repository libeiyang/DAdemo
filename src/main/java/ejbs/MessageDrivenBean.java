package ejbs;

import entities.MessageEntity;
import entities.UserEntity;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Calendar;
import java.util.List;

@MessageDriven(
        mappedName = "jms/MyMessQueue",
        activationConfig = {@ActivationConfigProperty(
                propertyName = "type",
                propertyValue = "system"
        )}
)
public class MessageDrivenBean implements MessageListener {
    @PersistenceContext(
            name = "DAdemoPU"
    )
    private EntityManager em;

    public MessageDrivenBean() {
    }

    public void onMessage(Message message) {
        try {
            Query query = this.em.createQuery("SELECT p FROM UserEntity p", UserEntity.class);
            List<UserEntity> userEntityList = query.getResultList();
            if (!userEntityList.isEmpty()) {
                for(UserEntity u: userEntityList)
                {
                    MessageEntity messageEntity = new MessageEntity();
                    messageEntity.setMessage(message.getBody(String.class));
                    Calendar today = Calendar.getInstance();
                    messageEntity.setDate(today);
                    em.persist(messageEntity);
                    u.getMessages().add(messageEntity);
                }
            }
        } catch (JMSException ex) {
            System.out.println("MDB error");
        }

    }
}