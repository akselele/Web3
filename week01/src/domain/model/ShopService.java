package domain.model;

import db.PersonDb;
import db.PersonDbInSQL;

import java.util.List;

public class ShopService {
    private PersonDb personDb = new PersonDbInSQL();


    public Person getPerson(String personId) {
        return getPersonDb().get(personId);
    }

    public List<Person> getAll() {
        return getPersonDb().getAll();
    }

    public void add(Person person) {
        getPersonDb().add(person);
    }

    public void updatePersons(Person person) {
        getPersonDb().update(person);
    }

    public void deletePerson(String id) {
        getPersonDb().delete(id);
    }

    private PersonDb getPersonDb() {
        return personDb;
    }
}
