package org.francescobasile.rain5.view;

import org.francescobasile.rain5.domain.Appello;
import org.francescobasile.rain5.domain.Corso;
import org.francescobasile.rain5.service.CorsoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppelloDto {

    private final CorsoService corsoService;
    private List<Corso> esamiAll;
    private Long esameId;
    private LocalDateTime data;

    public AppelloDto(CorsoService corsoService) {
        this.corsoService = corsoService;
        esamiAll = new ArrayList<>();
        esamiAll = corsoService.getAll();
    }

    public Appello extractAppello() {
//        LocalDateTime dataAppello = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Corso esame = corsoService.findById(esameId);
        return new Appello(this.data, esame);
    }

    public Long getEsameId() {
        return esameId;
    }

    public void setEsameId(Long esameId) {
        this.esameId = esameId;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<Corso> getEsamiAll() {
        return esamiAll;
    }

    public void setEsamiAll(List<Corso> esamiAll) {
        this.esamiAll = esamiAll;
    }
}
