package ru.romasini;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class EjbClient {
    public static void main(String[] args) throws NamingException, IOException {
        Context context = createInitialContext();
        String jndiServiceName = "ejb:/first-jsf-app/ProductServiceImpl!ru.romasini.ProductServiceRemote";
        ProductServiceRemote productServiceRemote = (ProductServiceRemote) context.lookup(jndiServiceName);
        productServiceRemote.
                findAllRemote().
                forEach(productDto -> System.out.println(productDto.getId() + " / " + productDto.getName()));
    }

    public static Context createInitialContext() throws IOException, NamingException{
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
