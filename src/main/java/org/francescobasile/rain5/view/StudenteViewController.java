package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityExistsException;
import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.service.StudenteService;

import java.util.logging.Logger;

@Named
@RequestScoped
public class StudenteViewController {

    private StudenteDto studenteDto;
    private boolean responseRendered = false;
    private Studente studente;
    private String l_id;
    private String l_nome;
    private String l_cognome;
    private String l_email;
    private String l_matricola;

    Logger logger = Logger.getLogger(StudenteViewController.class.getName());

    @Inject
    StudenteService studenteService;

    @PostConstruct
    public void init() {
        studenteDto = new StudenteDto(studenteService);
    }

    public void register() {
        Studente studente;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            studente = studenteService.create(studenteDto.extractStudente());
        } catch (EntityExistsException e) {
            this.studente = null;
            context.addMessage("response", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrazione studente", e.getMessage()));
            return;
//            throw new RuntimeException(e);
        }
        this.studente = studente;
        responseRendered = true;
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione studente", "Informazioni salvate con successo"));
        initLabels();
    }

    public void initLabels() {
        this.l_id = "Id: ";
        this.l_nome = "Nome: ";
        this.l_cognome = "Cognome: ";
        this.l_email = "Email: ";
        this.l_matricola = "Matricola: ";
    }

    public StudenteDto getStudenteDto() {
        return studenteDto;
    }

    public String getL_nome() {
        return l_nome;
    }

    public String getL_cognome() {
        return l_cognome;
    }

    public String getL_email() {
        return l_email;
    }

    public String getL_matricola() {
        return l_matricola;
    }

    public String getL_id() {
        return l_id;
    }

    public Studente getStudente() {
        return studente;
    }

    public boolean isResponseRendered() {
        return responseRendered;
    }

    public void setResponseRendered(boolean responseRendered) {
        this.responseRendered = responseRendered;
    }
}
