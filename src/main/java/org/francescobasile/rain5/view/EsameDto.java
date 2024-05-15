package org.francescobasile.rain5.view;

import org.francescobasile.rain5.domain.Appello;

import java.util.List;

public class EsameDto {

    private Long selectedId;
    private final List<Appello> appelliDisponibili;

    public EsameDto(List<Appello> appelliDisponibili) {
        this.appelliDisponibili = appelliDisponibili;
    }

    public Appello extractEsame() {
        return appelliDisponibili.stream().filter(appello -> selectedId.equals(appello.getId())).findFirst().orElse(null);
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }
}
