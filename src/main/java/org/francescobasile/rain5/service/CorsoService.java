package org.francescobasile.rain5.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.francescobasile.rain5.domain.Corso;

import java.util.List;

@Named
@RequestScoped
public class CorsoService {
    @PersistenceContext(unitName = "appelliDb-pu")
    private EntityManager em;

    @Transactional
    public Corso create(Corso corso) {
        em.persist(corso);
//        try {
//        } catch (RuntimeException e) {
        //TODO:sostituire con una classe di eccezione che implementa Interfaccia di Eccezione customizzata
//            throw new RuntimeException("Corso already exists");
//        }
        return corso;
    }

    public Corso findById(Long id) {
        return em.find(Corso.class, id);
    }


    public List<Corso> getAll() {
        return em.createNamedQuery("Corso.findAll", Corso.class).getResultList();
    }
}
