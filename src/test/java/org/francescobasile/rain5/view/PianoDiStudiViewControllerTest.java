package org.francescobasile.rain5.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.francescobasile.rain5.domain.Corso;
import org.francescobasile.rain5.domain.Studente;
import org.francescobasile.rain5.domain.StudenteManager;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PianoDiStudiViewControllerTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private PianoDiStudiViewController pianoDiStudiViewController;

    private StudenteManager studenteManager;
    private PianoDiStudiDto pianoDiStudiDto;

    private Studente currentStudent;
    private List<Corso> corsiDisponibili;
    private ArrayList<Long> selectedCorsi;


    @BeforeAll
    static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("appelliDb-pu-nonjta");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();

        // istanza di uno studente, simula la presenza dello studente del context
        studenteManager = new StudenteManager(entityManager.find(Studente.class, 1L));
        currentStudent = studenteManager.getStudente();
        //simula presenza di un pds precedente
        studenteManager.creaPianoDiStudi(entityManager.createQuery("select c from Corso c where c.id=4", Corso.class).getResultList());
        // i corsi disponibili per il pds nuovo si estraggono da tutti i corsi presenti a catalogo meno quelli gia presenti nel pds
        corsiDisponibili = entityManager.createQuery("select c from Corso c order by c.id ASC ", Corso.class).getResultList().stream().filter(corso -> !this.currentStudent.getPianoDiStudi().contains(corso)).collect(Collectors.toList());
        // simula i corsi selezionati dallo user nel web form
        selectedCorsi = new ArrayList<>(Arrays.asList(2L, 3L));
        // istanza di oggetto DTO che gestisce la conversione tra web form ed oggetti del dominio
        pianoDiStudiDto = new PianoDiStudiDto(corsiDisponibili);
        //simula la fase di apply request values del lifecycle di Jakarta Faces
        pianoDiStudiDto.setCorsiSelectedId(selectedCorsi);

        // verifiche (presume che sia stato eseguito il file import.sql che pre-carica nel db varie tuple di oggetti Corso e Studente
        assertEquals(corsiDisponibili.size(), 3);
        assertEquals(corsiDisponibili.get(0).getNome(), "Analisi1");
        assertEquals(corsiDisponibili.get(1).getNome(), "Analisi2");
        assertEquals(corsiDisponibili.get(2).getNome(), "Matematica3");

    }

    @Test
    void registra() {
//        FacesContext context = FacesContext.getCurrentInstance();
        List<Corso> pianodiStudi = pianoDiStudiDto.extractSelectedCorsiObj();
        try {
            studenteManager.creaPianoDiStudi(pianodiStudi);
            entityManager.merge(this.currentStudent);
        } catch (Exception e) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registrazione PdS", e.getMessage()));
            return;
        }
        corsiDisponibili.removeAll(pianodiStudi);
        pianoDiStudiDto = new PianoDiStudiDto(corsiDisponibili);
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrazione PdS", "Piano di Studi inserito con successo"));

        assertEquals(currentStudent.getPianoDiStudi().size(), 3);
        assertEquals(corsiDisponibili.size(), 1);
        assertEquals(corsiDisponibili.getFirst().getNome(), "Analisi1");
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @AfterAll
    static void tearDownAll() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}