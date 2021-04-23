package ru.romasini.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.service.ProductService;
import ru.romasini.service.dto.ProductDto;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),//подключаемся к очереди
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/productQueue")//имя очереди
        }
)
public class JmsProductReceiver implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(JmsProductReceiver.class);

    @EJB
    private ProductService productService;


    @Override
    public void onMessage(Message message) {
        logger.info("New JMS message");

        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            try {
                productService.save((ProductDto) om.getObject());
            } catch (JMSException e) {
                logger.error("", e);
            }
        }
    }
}
