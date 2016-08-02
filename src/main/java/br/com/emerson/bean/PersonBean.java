package br.com.emerson.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.emerson.model.Person;
import br.com.emerson.repository.PersonRepository;

@ManagedBean(name="personBean")
@SessionScoped
public class PersonBean implements Serializable {
    
    private static final long serialVersionUID = 6087193637790434703L;

    @ManagedProperty(value="#{personRepository}")
    PersonRepository personRepository;

    private List<Person> persons;
    private Person person;

    public PersonBean() {
        startPerson();
    }
    
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void listing() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("listagem.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            StringBuffer message = verify();
            if (message.length() > 0) {
                throw new Exception(message.toString());
            }
            if (personRepository != null) {
                if (person.getId() != 0) {
                    personRepository.create(person);
                } else {
                    personRepository.update(person);
                }
            }

            person = new Person();
            FacesContext.getCurrentInstance().getExternalContext().redirect("listagem.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
        }
    }

    public Person getPerson() {
        return person;
    }

    public boolean loadCreate() {
        try {
            startPerson();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadEdit(int id) {
        try {
            person = personRepository.findOne(id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("editar.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        try {
            startPerson();
            persons = personRepository.findAll();
            return persons;
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(int id) {
        try {
            personRepository.delete(id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listagem.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuffer verify() {
        StringBuffer string = new StringBuffer();
        if (person.getName().equals(null) || person.getName().equals("")) {
            string.append("Nome é nulo");
        }
        if (person.getDate() == null) {
            if (string.length() > 0) {
                string.append(" e ");
            }
            string.append("Data é nula");
        }
        return string;
    }

    private void startPerson() {
        person = new Person();
        person.setId(0);
    }
}
