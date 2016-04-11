package com.ksu.mantis.appmanager;

import com.ksu.mantis.model.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ksu on 03.04.2016.
 */
public class DbHelper {

    private final SessionFactory sessionFactory;
    private final ApplicationManager app;

    public DbHelper(ApplicationManager app) {
        this.app = app;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Set<UserData> users(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserData> result = session.createQuery(String.format("from UserData where username !='%s'", app.getProperty("web.adminLogin"))).list();
        session.getTransaction().commit();
        session.close();
        return new HashSet<UserData>(result);
    }


}
