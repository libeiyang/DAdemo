package ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless(
        name = "MessageProducerBean"
)
public class MessageProducerBean {
    @Resource(
            lookup = "Mypoolfactory"
    )
    private QueueConnectionFactory connectionFactory;
    @Resource(
            lookup = "jms/MyMessQueue"
    )
    private Queue queue;

    public MessageProducerBean() {
    }

    @PostConstruct
    public void init() {
        try {
            Context context = new InitialContext();
            this.connectionFactory = (QueueConnectionFactory)context.lookup("Mypoolfactory");
            this.queue = (Queue)context.lookup("jms/MyMessQueue");
        } catch (NamingException ex) {
            System.out.println("Producer error");
        }
    }

    public void sendMessageToAll(String text) {
        JMSContext context = this.connectionFactory.createContext();
        Throwable var3 = null;

        try {
            context.createProducer().send(this.queue, text);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (context != null) {
                if (var3 != null) {
                    try {
                        context.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    context.close();
                }
            }
        }
    }


    @Schedule(
            month = "12",
            dayOfMonth = "25",
            hour = "1"
    )
    public void sendHolidayMsg() {
        String text = "Merry Christmas.See you after the holiday.";
        this.sendMessageToAll(text);
    }

}
