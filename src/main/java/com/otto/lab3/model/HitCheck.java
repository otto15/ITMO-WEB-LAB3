package com.otto.lab3.model;

import com.otto.lab3.repository.HitCheckRepository;
import com.otto.lab3.service.AreaChecker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.ZonedDateTime;

@ManagedBean
@RequestScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HitCheck implements Serializable {

    @ManagedProperty("#{areaCheckerImpl}")
    private AreaChecker checker;

    @ManagedProperty("#{hitCheckRepositoryImpl}")
    private HitCheckRepository hitCheckRepository;

    private Double x;
    private Double y;
    private Double r;

    private ZonedDateTime callingDate = ZonedDateTime.now();
    private Long executionTime;
    private boolean inArea;

    public void saveToTable() {
        inArea = checker.checkIfInArea(this);
        executionTime = System.currentTimeMillis() - callingDate.toInstant().toEpochMilli();
        hitCheckRepository.save(this);
    }

}
