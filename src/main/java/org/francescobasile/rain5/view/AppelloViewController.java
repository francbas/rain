package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.francescobasile.rain5.domain.Appello;
import org.francescobasile.rain5.service.AppelloService;
import org.francescobasile.rain5.service.CorsoService;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.util.List;

@Named
@RequestScoped
public class AppelloViewController {
    @Inject
    private CorsoService corsoService;
    @Inject
    private AppelloService appelloService;
    private AppelloDto appelloDto;
    private Appello latestAppello;
    private List<Appello> appelliAll;

    @PostConstruct
    public void init() {
        this.appelloDto = new AppelloDto(corsoService);
        appelliAll = appelloService.getAll();
    }

    public void registra() {
        FacesContext context = FacesContext.getCurrentInstance();
        Appello appello;
        try {
            appello = appelloService.create(appelloDto.extractAppello());
        } catch (Exception e) {
            this.latestAppello = null;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrazione Appello", e.getMessage()));
            return;
        }
        this.latestAppello = appello;
        appelliAll.add(appello);
        appelloDto = new AppelloDto(corsoService);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione Appello", "registrazione avvenuta con successo"));
    }

    public AppelloDto getAppelloDto() {
        return appelloDto;
    }

    public Appello getLatestAppello() {
        return latestAppello;
    }

    public List<Appello> getAppelliAll() {
        return appelliAll;
    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Appello appello = (Appello) event.getData();
            if (appello.getIscritti() == null) {
//                corso.setOrders(orderService.getOrders((int) (Math.random() * 10)));
            }
        }
    }
}
