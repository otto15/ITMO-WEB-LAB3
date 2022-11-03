package com.otto.lab3.repository;

import com.otto.lab3.service.dto.HitCheckDTO;

import java.util.List;

public interface HitCheckRepository {
    Integer saveAndReturnId(HitCheckDTO hitCheck);
    void deleteAll();
    List<HitCheckDTO> findAll();
}
