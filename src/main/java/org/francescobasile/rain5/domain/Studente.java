package org.francescobasile.rain5.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"matricola"})})
@NamedQueries({@NamedQuery(name = "Studente.findAll", query = "SELECT s from Studente s"), @NamedQuery(name = "Studente.findByEmail", query = "SELECT s FROM Studente s WHERE s.email = :email")})
public class Studente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String email;
    private String matricola;
    @OneToMany
    private List<Corso> pianoDiStudi;
    @ManyToMany
    @JoinTable(name = "APPELLO_STUDENTE_ISCRITTO", joinColumns = {@JoinColumn(name = "Appello_ID")}, inverseJoinColumns = {@JoinColumn(name = "Studente_ID")})
    private List<Appello> esami;
    @ManyToMany
    @JoinTable(name = "STUDENTE_CORSO_SUPERATO", joinColumns = {@JoinColumn(name = "Studente_ID")}, inverseJoinColumns = {@JoinColumn(name = "Corso_ID")})
    private List<Corso> corsiSuperati;


    public Studente() {
        esami = new ArrayList<>();
        corsiSuperati = new ArrayList<>();
    }

    public Studente(String nome, String cognome, String email, String matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.matricola = matricola;
    }

    public boolean puoIscriversi(Appello appello) {
        return puoIscriversi(appello.getEsame());
    }

    public boolean puoIscriversi(Corso corso) {
        boolean isInPianodiStudi = pianoDiStudi.contains(corso);
        boolean isAllPrerequisitiPassed = new HashSet<>(corsiSuperati).containsAll(corso.getPrecedenze());
//        return isInPianodiStudi && isAllPrerequisitiPassed;
        return true;
    }

    public void addCorso(Corso corso) {
        if (pianoDiStudi.contains(corso)) throw new IllegalArgumentException("Corso già presente");
        pianoDiStudi.add(corso);
    }

    public void removeCorso(Corso corso) {
        if (!pianoDiStudi.contains(corso)) throw new IllegalArgumentException("Corso non trovato");
        pianoDiStudi.remove(corso);
    }

    public void addEsame(Appello appello) {
        esami.add(appello);
    }

    public void removeEsame(Appello appello) {
        if (!esami.contains(appello)) throw new IllegalArgumentException("Appello non trovato");
        esami.remove(appello);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Studente studente)) return false;
        return Objects.equals(getId(), studente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public void promosso(Appello appello) {
        corsiSuperati.add(appello.getEsame());
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

    public void setNome(String username) {
        this.nome = username;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String password) {
        this.matricola = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<Corso> getPianoDiStudi() {
        return pianoDiStudi;
    }

    public List<Appello> getEsami() {
        return esami;
    }

    public List<Corso> getCorsiSuperati() {
        return corsiSuperati;
    }
}

