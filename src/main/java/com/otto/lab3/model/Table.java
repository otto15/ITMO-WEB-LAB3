package com.otto.lab3.model;

import com.otto.lab3.repository.HitCheckRepository;
import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ManagedBean(eager = true)
@SessionScoped
@Data
public class Table {

    @ManagedProperty("#{hitCheckRepositoryImpl}")
    private HitCheckRepository hitCheckRepository;

    private List<HitCheck> hitChecks = new ArrayList<>();

    private Double currentR = 1d;

    public List<HitCheck> getHitChecksByR() {
        return hitChecks.stream().filter(hitCheck -> Objects.equals(hitCheck.getR(), currentR)).collect(Collectors.toList());
    }

    public void changeR(Double r) {
        currentR = r;
    }

    public void clear() {
        hitCheckRepository.deleteAll();
    }

    public List<HitCheck> getAllRows() {
        hitChecks = hitCheckRepository.findAll();
        return hitChecks;
    }

}
