package org.francescobasile.rain5.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.francescobasile.rain5.domain.Studente;

import java.util.List;

@Named
@RequestScoped
public class StudenteService {
    @PersistenceContext(unitName = "appelliDb-pu")
    private EntityManager em;

    @Transactional
    public Studente create(Studente studente) throws EntityExistsException {
        if (emailExists(studente.getEmail())) {
            throw new EntityExistsException(studente.getEmail() + " already exists");
        } else {
            em.persist(studente);
            return studente;
        }
    }

    @Transactional
    public void update(Studente studente) {
        try {
            em.merge(studente);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Studente findById(Long id) {
        return em.find(Studente.class, id);
    }

    public Studente findByEmail(String email) {
        TypedQuery<Studente> query = em.createNamedQuery("Studente.findByEmail", Studente.class);
        query.setParameter("email", email);
        List<Studente> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.getFirst();
    }

    public List<Studente> findAll() {
        return em.createNamedQuery("Studente.findAll", Studente.class).getResultList();
    }

    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

}
