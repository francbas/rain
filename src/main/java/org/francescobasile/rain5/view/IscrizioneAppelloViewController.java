package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.francescobasile.rain5.domain.Appello;
import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.domain.StudenteManager;
import org.francescobasile.rain5.service.AppelloService;
import org.francescobasile.rain5.service.StudenteService;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class IscrizioneAppelloViewController {
    @Inject
    private StudenteService studenteService;
    @Inject
    private AppelloService appelloService;
    @Inject
    private SessioneViewController sessioneViewController;
    private FacesContext facesContext;

    private EsameDto esameDto;
    private List<Appello> appelliDisponibili = new ArrayList<>();
    private StudenteManager studenteManager;
    private Studente currentStudente;

    @PostConstruct
    public void init() {
        facesContext = FacesContext.getCurrentInstance();
        studenteManager = new StudenteManager(sessioneViewController.getCurrentStudente());
        currentStudente = studenteManager.getStudente();
        appelliDisponibili = appelloService.getAll();
        appelliDisponibili.removeAll(currentStudente.getEsami());
        esameDto = new EsameDto(appelliDisponibili);
    }

    public void registra() {
        Appello esame = esameDto.extractEsame();
        try {
            studenteManager.iscriveAppello(esame);
            appelloService.update(esame);
//            studenteService.update(currentStudente);
            currentStudente = studenteManager.getStudente();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore nella registrazione del esame", e.getMessage()));
            return;
        }
        appelliDisponibili.remove(esame);
        esameDto = new EsameDto(appelliDisponibili);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione Esame", "Esame registrato correttamente"));
    }

    public List<Appello> getAppelliDisponibili() {
        return appelliDisponibili;
    }

    public Studente getCurrentStudente() {
        return currentStudente;
    }

    public EsameDto getEsameDto() {
        return esameDto;
    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Appello appello = (Appello) event.getData();
            if (appello.getEsame() == null) {
//                corso.setOrders(orderService.getOrders((int) (Math.random() * 10)));
            }
        }
    }
}
