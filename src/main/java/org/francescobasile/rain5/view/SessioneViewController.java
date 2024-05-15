package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.service.StudenteService;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

@Named
@SessionScoped
public class SessioneViewController implements Serializable {
    @Inject
    private StudenteService studenteService;
    private Studente currentStudente;

    @PostConstruct
    public void init() {
        if (Objects.isNull(currentStudente)) {
            setCurrentStudente(studenteService.findById(new Random().nextLong(6)));
        }
    }

    public Studente getCurrentStudente() {
        return currentStudente;
    }

    public void setCurrentStudente(Studente currentStudente2) {
        currentStudente = currentStudente2;
    }
}
