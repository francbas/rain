package org.francescobasile.rain5.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.francescobasile.rain5.domain.Corso;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PianoDiStudiDtoTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    private List<Long> selectedCorsi;
    private List<Corso> corsiDisponibili;

    @BeforeAll
    static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("appelliDb-pu-nonjta");
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();

        corsiDisponibili = entityManager.createQuery("select c from Corso c where c.id >=2", Corso.class).getResultList();
        selectedCorsi = new ArrayList<>(Arrays.asList(2L, 3L));
    }

    @Test
    void extractSelectedCorsiObj() {
        PianoDiStudiDto pianoDiStudiDto = new PianoDiStudiDto(corsiDisponibili);
        pianoDiStudiDto.setCorsiSelectedId(selectedCorsi);
        List<Corso> pds = pianoDiStudiDto.extractSelectedCorsiObj();

        assertEquals(pds.size(), 2);
        assertEquals(pds.get(0).getNome(), "Analisi2");
        assertEquals(pds.get(1).getNome(), "Matematica3");
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