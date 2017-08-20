package hbn;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            File path = new File(HibernateUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/hibernate-settings/hibernate.cfg.xml");

            Configuration configuration  = new Configuration().configure(path);
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed:\n" + ex);
        }

        return null;
    }

    public static Session getSession(){
        return HibernateUtil.sessionFactory.openSession();
    }
}
