package com.otto.lab3.model;

import com.otto.lab3.repository.HitCheckRepository;
import com.otto.lab3.service.converter.Converter;
import com.otto.lab3.service.dto.HitCheckDTO;
import lombok.Data;
import org.primefaces.model.LazyDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ManagedBean
@RequestScoped
@Data
public class Table {

    @ManagedProperty("#{jooqHitCheckRepository}")
    private HitCheckRepository hitCheckRepository;

    @ManagedProperty("#{hitCheckDTOConverter}")
    private Converter<HitCheckDTO, HitCheck> converter;

    private List<HitCheck> hitChecks = new ArrayList<>();

    @ManagedProperty("#{tableModel}")
    private LazyDataModel<HitCheck> model;

    private Double currentR = 1d;

    private int pageSize = 5;

    public List<HitCheck> getHitChecksByR() {
        return hitChecks.stream().filter(hitCheck -> Objects.equals(hitCheck.getR(), currentR)).collect(Collectors.toList());
    }

    public void changeR(Double r) {
        currentR = r;
    }

    public void clear() {
        hitChecks.clear();
        hitCheckRepository.deleteAll();
    }

}
