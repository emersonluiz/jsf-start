package br.com.emerson.repository;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name="connectionFactory")
@ApplicationScoped
public class ConnectionFactory implements Serializable {

    private static final long serialVersionUID = -1789617151687873770L;

    public static EntityManagerFactory factory = null;
    private EntityManager em;

    /**
     * The constructor method will create or use an already open connection.
     */
    public ConnectionFactory() {

        if (factory != null) {
            if (factory.isOpen()) {
                em = factory.createEntityManager();
            } else {
                factory = Persistence.createEntityManagerFactory("person");
                em = factory.createEntityManager();
            }
        } else {
            factory = Persistence.createEntityManagerFactory("person");
            em = factory.createEntityManager();
        }

    }

    /**
     * Method that will provide a connection.
     * 
     * @return - EntityManager
     */
    public EntityManager getConnection() {
        return em;
    }

    /**
     * Method to close the connection.
     */
    public void close() {
        this.em.close();
        factory.close();
        factory = null;
    }

}
