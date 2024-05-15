package org.francescobasile.rain5.view;

import org.francescobasile.rain5.domain.Corso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PianoDiStudiDto {

    private List<Long> corsiSelectedId;
    private final List<Corso> corsiDisponibili;

    public PianoDiStudiDto(List<Corso> corsiDisponibili) {
        this.corsiDisponibili = corsiDisponibili;
        corsiSelectedId = new ArrayList<>();
    }

    public List<Corso> extractSelectedCorsiObj() {
        return corsiDisponibili.stream().filter(corso -> corsiSelectedId.contains(corso.getId())).collect(Collectors.toList());
    }

    public void setCorsiSelectedId(List<Long> corsiSelectedId) {
        this.corsiSelectedId = corsiSelectedId;
    }

    public List<Long> getCorsiSelectedId() {
        return corsiSelectedId;
    }
}
