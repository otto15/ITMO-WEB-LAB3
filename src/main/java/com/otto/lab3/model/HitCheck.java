package com.otto.lab3.model;

import com.otto.lab3.service.dto.HitCheckDTO;
import com.otto.lab3.repository.HitCheckRepository;
import com.otto.lab3.service.AreaChecker;
import com.otto.lab3.service.converter.Converter;
import lombok.Data;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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

    @ManagedProperty("#{hitCheckDTOConverter}")
    private Converter<HitCheckDTO, HitCheck> converter;

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
        executionTime = System.currentTimeMillis() - callingDate.toEpochMilli();
        saveTimezone(timezone);
        hitCheckRepository.saveAndReturnId(
                converter.map(this));
    }

    public ZonedDateTime getCallingDateWithTimeZone() {
        return callingDate.atZone(ZoneId.of(getSessionTimezone()));
    }

    public void saveTimezone(String timezone) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("timezone", timezone);
    }

    public String getSessionTimezone() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().putIfAbsent("timezone", "Europe/London");
        return (String) context.getExternalContext().getSessionMap().get("timezone");
    }
}
