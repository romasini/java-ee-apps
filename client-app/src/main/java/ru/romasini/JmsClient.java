package ru.romasini;

import ru.romasini.service.dto.ProductDto;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class JmsClient {
    public static void main(String[] args) throws IOException, NamingException {
        Context context = createInitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        JMSContext jmsContext = factory.createContext("jmsuser", "123");
        Destination dest = (Destination) context.lookup("jms/queue/productQueue");
        JMSProducer producer = jmsContext.createProducer();
        ObjectMessage om = jmsContext.createObjectMessage(new ProductDto(null, "Product from JMS", "Product from JMS",
                new BigDecimal(100), 1L, null));

        producer.send(dest, om);
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
