package org.francescobasile.rain5.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nome", "docente", "anno"})
        }
)
@NamedQueries({
        @NamedQuery(name = "Corso.findAll", query = "select c from  Corso c"),
        @NamedQuery(name = "Corso.findByAnno", query = "select c from Corso c where c.anno = :anno")
})
public class Corso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String docente;
    private int anno;
    @OneToMany
    private List<Corso> precedenze = new ArrayList<>();
    @OneToMany(mappedBy = "esame")
    private List<Appello> appelli = new ArrayList<>();

    public Corso() {
        precedenze = new ArrayList<>();
        appelli = new ArrayList<>();
    }

    public Corso(String nome, String docente, int anno) {
        this.nome = nome;
        this.docente = docente;
        this.anno = anno;
    }

    public void addCorso(Corso corso) {
        if (!precedenze.contains(corso)) {
            precedenze.add(corso);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Corso corso)) return false;
        return Objects.equals(getId(), corso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public void removeCorso(Corso corso) {
        precedenze.remove(corso);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocente() {
        return docente;
    }

    public int getAnno() {
        return anno;
    }

    public List<Corso> getPrecedenze() {
        return precedenze;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public List<Appello> getAppelli() {
        return appelli;
    }

    public void addAppelli(Appello appello) {
        appelli.add(appello);
    }

    public void removeAppelli(Appello appello) {
        appelli.remove(appello);
    }
}
