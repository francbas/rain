package org.francescobasile.rain5.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.francescobasile.rain5.domain.Corso;
import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.domain.StudenteManager;
import org.francescobasile.rain5.service.CorsoService;
import org.francescobasile.rain5.service.StudenteService;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.util.List;

@Named
@RequestScoped
public class PianoDiStudiViewController {

    @Inject
    private CorsoService corsoService;
    @Inject
    private StudenteService studenteService;
    private StudenteManager studenteManager;
    private PianoDiStudiDto pianoDiStudiDto;

    private Studente currentStudent;
    private List<Corso> corsiDisponibili;

    private CommandButton submitButton;
    private boolean rendered;

    @PostConstruct
    public void init() {
        //TODO:sostituire studente con quello effettivamente in sessione loggato
        studenteManager = new StudenteManager(studenteService.findById(2L));
        currentStudent = studenteManager.getStudente();
        corsiDisponibili = corsoService.getAll();
        corsiDisponibili.removeAll(currentStudent.getPianoDiStudi());
//        corsiDisponibili = corsoService.getAll().stream().filter(corso -> !currentPds.contains(corso)).collect(Collectors.toList());
        pianoDiStudiDto = new PianoDiStudiDto(corsiDisponibili);
        rendered = !corsiDisponibili.isEmpty();
    }

    public void registra() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Corso> selectedCorsiObj = pianoDiStudiDto.extractSelectedCorsiObj();
        try {
            studenteManager.creaPianoDiStudi(selectedCorsiObj);
            studenteService.update(this.currentStudent);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrazione PdS", "Errore!!"));
            return;
        }
        corsiDisponibili.removeAll(currentStudent.getPianoDiStudi());
        pianoDiStudiDto = new PianoDiStudiDto(corsiDisponibili);
//        rendered = !corsiDisponibili.isEmpty();
//        submitButton.setRendered(rendered);
//        context.renderResponse();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione PdS", "Corso inserito con successo"));
    }

    public boolean updateRenderedElement() {
        rendered = !corsiDisponibili.isEmpty();
        submitButton.setRendered(rendered);
        return rendered;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public Studente getCurrentStudent() {
        return currentStudent;
    }

    public List<Corso> getCorsiDisponibili() {
        return corsiDisponibili;
    }

    public PianoDiStudiDto getPianoDiStudiDto() {
        return pianoDiStudiDto;
    }

    public CommandButton getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(CommandButton submitButton) {
        this.submitButton = submitButton;
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
