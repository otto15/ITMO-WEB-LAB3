package com.otto.lab3.model;

import com.otto.lab3.repository.HitCheckRepository;
import com.otto.lab3.service.AreaChecker;
import lombok.Data;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ManagedBean
@RequestScoped
@Data
public class HitCheck implements Serializable {

    @ManagedProperty("#{areaCheckerImpl}")
    private AreaChecker checker;

    @ManagedProperty("#{table}")
    private Table table;

    @ManagedProperty("#{jooqHitCheckRepository}")
    private HitCheckRepository hitCheckRepository;

    private Double x;
    private Double y;
    private Double r;

    private Instant callingDate = Instant.now();
    private Long executionTime;
    private boolean inArea;
    private String timezone;

    public void saveToTable() {
        inArea = checker.checkIfInArea(this);
        executionTime = System.nanoTime() - callingDate.getNano();
        if (hitCheckRepository.save(this)) {
            table.getHitChecks().add(this);
        }
    }

    public ZonedDateTime getCallingDateWithTimeZone() {
        return callingDate.atZone(ZoneId.of(timezone));
    }
}
