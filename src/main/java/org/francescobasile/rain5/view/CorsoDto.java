package org.francescobasile.rain5.view;

import org.francescobasile.rain5.domain.Corso;
import org.francescobasile.rain5.service.CorsoService;

import java.util.ArrayList;
import java.util.List;

public class CorsoDto {

    private final CorsoService corsoService;
    private String nome;
    private String docente;
    private int anno;
    private List<Long> precedenzeId = new ArrayList<>();

    public CorsoDto(CorsoService corsoService) {
        this.corsoService = corsoService;
    }

    private List<Corso> convertPrecedenze() {
        List<Corso> precedenzeObj = new ArrayList<>();
        if (precedenzeId.isEmpty()) {
            return null;
        }
        for (Long id : precedenzeId) {
            Corso corso = corsoService.findById(id);
            if (corso != null) {
                precedenzeObj.add(corso);
            }
        }
        return precedenzeObj;
    }

    public Corso extractCorso() {
        Corso corso = new Corso();
        corso.setNome(nome);
        corso.setDocente(docente);
        corso.setAnno(anno);
        List<Corso> precedenzeObj = convertPrecedenze();
        if (precedenzeObj != null) {
            for (Corso corsoObj : precedenzeObj) {
                corso.addCorso(corsoObj);
            }
        }
        return corso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public List<Long> getPrecedenzeId() {
        return precedenzeId;
    }

    public void setPrecedenzeId(List<Long> precedenzeId) {
        this.precedenzeId = precedenzeId;
    }
}
