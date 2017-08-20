package hbn;

import org.hibernate.Session;

import java.util.List;

public class Requests {

    public static void add(Object o){
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        session.save(o);
        session.flush();
        session.getTransaction().commit();

        session.close();
    }

    public static void delete(Object o) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();

        session.close();
    }

    public static List getAllFromTable(Class aClass){
        Session session = HibernateUtil.getSession();
        List list = session.createCriteria(aClass).list();
        session.close();

        return list;
    }

    public static Object getFromId(Class aClass, int id){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Object result = session.get(aClass, id);
        session.getTransaction().commit();
        session.close();

        return result;
    }
}
