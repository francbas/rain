package org.francescobasile.rain5.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudenteManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private StudenteManager studenteManager;
    private Studente studente;

    @BeforeAll
    static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("appelliDb-pu-nonjta");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        studente = entityManager.find(Studente.class, 1L);
        studenteManager = new StudenteManager(studente);
    }

    @Test
    void creaPianoDiStudi() {
        List<Corso> pds = new ArrayList<>();
        Corso corso1 = new Corso("corso01", "docente01", 2021);
        Corso corso2 = new Corso("corso02", "docente02", 2022);
        Corso corso3 = new Corso("corso03", "docente03", 2023);
        pds.add(corso1);
        pds.add(corso2);
        pds.add(corso3);
        studenteManager.creaPianoDiStudi(pds);

        assertEquals(studenteManager.getStudente().getPianoDiStudi().size(), 3);
        assertEquals(studenteManager.getStudente().getPianoDiStudi().get(0).getDocente(), "docente01");
        assertEquals(studenteManager.getStudente().getPianoDiStudi().get(1).getNome(), "corso02");
    }

    @Test
    void iscriveAppello() {
        Corso corso1 = new Corso("corso01", "docente01", 2021);
        Appello appello = new Appello(LocalDateTime.of(2021, 1, 1, 0, 0), corso1);
        studenteManager.iscriveAppello(appello);

        assertEquals(studenteManager.getStudente().getEsami().size(), 1);
    }

    @Test
    void cancellaAppello() {
        Corso corso1 = new Corso("corso01", "docente01", 2021);
        Appello appello = new Appello(LocalDateTime.of(2021, 1, 1, 0, 0), corso1);
        Appello appello2 = new Appello(LocalDateTime.of(2021, 1, 2, 0, 0), corso1);
        assertEquals(studenteManager.getStudente().getEsami().size(), 0);
        studenteManager.iscriveAppello(appello);
        studenteManager.iscriveAppello(appello2);
        assertEquals(studenteManager.getStudente().getEsami().size(), 2);
        studenteManager.cancellaAppello(appello);
        assertEquals(studenteManager.getStudente().getEsami().size(), 1);

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