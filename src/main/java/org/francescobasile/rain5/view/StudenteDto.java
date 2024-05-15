package org.francescobasile.rain5.view;

import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.service.StudenteService;

public class StudenteDto {

    private String nome;
    private String cognome;
    private String email;
    private String matricola;

    public StudenteDto(StudenteService studenteService) {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public Studente extractStudente() {
        return new Studente(nome, cognome, email, matricola);
    }

}
