package listener;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        try {
            ServletContext context = contextEvent.getServletContext();
            MongoClient mongo = new MongoClient(
                    context.getInitParameter("MONGODB_HOST"),
                    Integer.parseInt(context.getInitParameter("MONGODB_PORT")));
            System.out.println("MongoClient initialized successfully");
            contextEvent.getServletContext().setAttribute("MONGO_CLIENT", mongo);
        } catch (UnknownHostException e) {
            throw new RuntimeException("MongoClient init failed");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        MongoClient mongo = (MongoClient) contextEvent.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }

}
