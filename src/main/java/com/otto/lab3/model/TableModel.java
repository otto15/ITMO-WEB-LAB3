package com.otto.lab3.model;

import com.otto.lab3.repository.HitCheckRepository;
import com.otto.lab3.service.converter.Converter;
import com.otto.lab3.service.dto.HitCheckDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ManagedBean
@RequestScoped
@Data
public class TableModel extends LazyDataModel<HitCheck> {

    @ManagedProperty("#{jooqHitCheckRepository}")
    public HitCheckRepository hitCheckRepository;

    @ManagedProperty("#{hitCheckDTOConverter}")
    private Converter<HitCheckDTO, HitCheck> converter;

    @PostConstruct
    public void construct() {
        int c = hitCheckRepository.getHitChecksTotalCount();
        FacesContext.getCurrentInstance().getExternalContext().log(String.format("%d", c));
        this.setRowCount(c);
    }


    @Override
    public List<HitCheck> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return hitCheckRepository.getLimitedHitChecks(first, pageSize).stream().map(
                hitCheckDTO -> converter.unmap(hitCheckDTO)
        ).collect(Collectors.toList());
    }
}
