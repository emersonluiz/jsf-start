package br.com.emerson.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 6726292179459948525L;

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private Date date;
    
    public Person() {}

    public Person(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Person(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   
}
