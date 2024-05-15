package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.francescobasile.rain5.domain.Corso;
import org.francescobasile.rain5.service.CorsoService;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CorsoViewController {
    @Inject
    CorsoService corsoService;
    private CorsoDto corsoDto;
    private Corso corso;
    private List<Corso> corsiAll;
    private final String l_id = "Id: ";
    private final String l_nome = "Nome: ";
    private final String l_docente = "Docente: ";
    private final String l_anno = "Anno: ";

    @PostConstruct
    public void init() {
        //TODO: inizializzare lista corsi esistenti per le precedenze
        corsiAll = new ArrayList<>();
//        corsiPrecedenze.add(new Corso("corso01", "docente01", 2024));
        corsiAll = corsoService.getAll();
        corsoDto = new CorsoDto(corsoService);
    }

    public void register() {
        FacesContext context = FacesContext.getCurrentInstance();
        Corso corso = corsoDto.extractCorso();
        try {
            corsoService.create(corso);
        } catch (RuntimeException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrazione Corso", e.getMessage()));
            return;
        }
        corsiAll.add(corso);
        this.corso = corso;
        //TODO: testare - dovrebbe essere inutile refresh dal DB
        this.corsiAll = corsoService.getAll();
        this.corsoDto = new CorsoDto(corsoService);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione Corso", "Corso registrato con successo"));
        //TODO: aggiornare lista corsi precedenze per includere il corso appena creato
    }

    public List<Corso> getCorsiAll() {
        return corsiAll;
    }

    public CorsoDto getCorsoDto() {
        return corsoDto;
    }

    public void setCorsoDto(CorsoDto corsoDto) {
        this.corsoDto = corsoDto;
    }

    public Corso getCorso() {
        return corso;
    }

    public String getL_id() {
        return l_id;
    }

    public String getL_nome() {
        return l_nome;
    }

    public String getL_docente() {
        return l_docente;
    }

    public String getL_anno() {
        return l_anno;
    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            Corso corso = (Corso) event.getData();
            if (corso.getPrecedenze() == null) {
//                corso.setOrders(orderService.getOrders((int) (Math.random() * 10)));
            }
        }
    }
}
