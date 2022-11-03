package com.otto.lab3.service.converter;

import com.otto.lab3.model.HitCheck;
import com.otto.lab3.service.dto.HitCheckDTO;
import com.otto.lab3.util.SessionIDGetter;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.time.ZoneOffset;

@ManagedBean
@ApplicationScoped
@Getter
@Setter
public class HitCheckDTOConverter implements Converter<HitCheckDTO, HitCheck> {

    @ManagedProperty("#{sessionIDGetter}")
    private SessionIDGetter sessionIDGetter;

    @Override
    public HitCheckDTO map(HitCheck hitCheck) {
        HitCheckDTO dto = new HitCheckDTO();

        dto.setX(hitCheck.getX());
        dto.setY(hitCheck.getY());
        dto.setR(hitCheck.getR());
        dto.setCallingDate(hitCheck.getCallingDate().atOffset(ZoneOffset.UTC));
        dto.setExecutionTime(hitCheck.getExecutionTime());
        dto.setHitStatus(hitCheck.isInArea());
        dto.setSessionId(sessionIDGetter.getSessionId());

        return dto;
    }

    @Override
    public HitCheck unmap(HitCheckDTO dto) {
        HitCheck hitCheck = new HitCheck();

        hitCheck.setX(dto.getX());
        hitCheck.setY(dto.getY());
        hitCheck.setR(dto.getR());
        hitCheck.setCallingDate(dto.getCallingDate().toInstant());
        hitCheck.setExecutionTime(dto.getExecutionTime());
        hitCheck.setInArea(dto.isHitStatus());

        return hitCheck;
    }

}
