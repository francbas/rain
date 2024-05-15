package org.francescobasile.rain5.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"data", "ESAME_ID"}),})
@NamedQueries({@NamedQuery(name = "Appello.findAll", query = "select a from Appello a"),})
public class Appello implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;
    @ManyToOne
    private Corso esame;
    @ManyToMany(mappedBy = "esami", cascade = CascadeType.ALL)
    @JoinTable(name = "APPELLO_STUDENTE_ISCRITTO", joinColumns = {@JoinColumn(name = "Appello_ID")}, inverseJoinColumns = {@JoinColumn(name = "Studente_ID")})
    private List<Studente> iscritti;
    @ManyToMany
    @JoinTable(name = "APPELLO_STUDENTE_PROMOSSO", joinColumns = {@JoinColumn(name = "Appello_ID")}, inverseJoinColumns = {@JoinColumn(name = "Studente_ID")})
    private List<Studente> promossi;

    public Appello() {
        iscritti = new ArrayList<>();
        promossi = new ArrayList<>();
    }

    public Appello(LocalDateTime data, Corso esame) {
        this();
        this.data = data;
        this.esame = esame;
    }

    public Long getId() {
        return id;
    }

    public Corso getEsame() {
        return esame;
    }

    public void setEsame(Corso esame) {
        this.esame = esame;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<Studente> getIscritti() {
        return iscritti;
    }

    public void iscrivi(Studente studente) {
        if (iscritti.contains(studente))
            throw new IllegalArgumentException("Studente " + studente.getNome() + " già iscritto");
        if (!studente.puoIscriversi(this))
            throw new IllegalArgumentException("Studente " + studente.getNome() + " non puo iscriversi");
        iscritti.add(studente);
        studente.addEsame(this);
    }

    public void disiscrivi(Studente studente) {
        iscritti.remove(studente);
        studente.removeEsame(this);
        if (!iscritti.contains(studente))
            throw new RuntimeException("Studente " + studente.getCognome() + " non risulta iscritto");
    }

    public void promuovi(Studente studente) {
        promossi.add(studente);
        studente.promosso(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appello appello)) return false;
        return Objects.equals(getId(), appello.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
