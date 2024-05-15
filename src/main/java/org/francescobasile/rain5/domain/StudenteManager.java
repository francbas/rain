package org.francescobasile.rain5.domain;

import java.util.List;
import java.util.Objects;


public class StudenteManager {

    private Studente studente;

    public StudenteManager(Studente studente) {
        this.studente = studente;
    }

    public void creaPianoDiStudi(List<Corso> listaCorsi) {
        listaCorsi.forEach(this.studente::addCorso);
    }

    public void iscriveAppello(Appello appello) {
        appello.iscrivi(this.studente);
    }

    public void cancellaAppello(Appello appello) {
        appello.disiscrivi(this.studente);
    }

    public Studente getStudente() {
        return studente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudenteManager that)) return false;
        return Objects.equals(getStudente(), that.getStudente());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getStudente());
    }
}
