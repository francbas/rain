package org.francescobasile.rain5.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.francescobasile.rain5.domain.Appello;

import java.util.List;

@Stateless
public class AppelloService {
    @PersistenceContext(unitName = "appelliDb-pu")
    EntityManager em;

    public Appello findById(Long id) {
        return em.find(Appello.class, id);
    }

    public Appello create(Appello appello) {
        em.persist(appello);
        return appello;
    }

    public Appello update(Appello appello) {
        return em.merge(appello);
    }

    public void delete(Appello appello) {
        em.remove(appello);
    }

    public List<Appello> getAll() {
        return em.createNamedQuery("Appello.findAll", Appello.class).getResultList();
    }
}
