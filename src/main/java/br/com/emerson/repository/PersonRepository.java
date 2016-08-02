package br.com.emerson.repository;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.emerson.model.Person;

@ManagedBean(name="personRepository")
@ApplicationScoped
public class PersonRepository implements Serializable {

    private static final long serialVersionUID = -5123925098606732777L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepository.class);

    @ManagedProperty(value="#{connectionFactory}")
    ConnectionFactory connectionFactory;

    private EntityManager entityManager;

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        entityManager = connectionFactory.getConnection();
    }

    public void create(Person person) throws Exception {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            LOGGER.debug("Person created");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public void update(Person person) throws Exception {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(person);
            entityManager.getTransaction().commit();
            LOGGER.debug("Person created");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public Person findOne(int id) throws Exception {
        try {
            Person person = entityManager.find(Person.class, id);
            LOGGER.debug("Person was found");
            return person;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public List<Person> findAll() throws Exception {
        try {
            Query query = entityManager.createQuery("FROM Person");
            @SuppressWarnings("unchecked")
            List<Person> list = query.getResultList();
            LOGGER.debug("Persons listed");
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws Exception {
        try {
            Person person = findOne(id);
            entityManager.getTransaction().begin();
            entityManager.remove(person);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
