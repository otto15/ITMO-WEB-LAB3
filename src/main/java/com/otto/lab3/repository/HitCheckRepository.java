package com.otto.lab3.repository;

import com.otto.lab3.model.HitCheck;
import java.util.List;

public interface HitCheckRepository {
    void save(HitCheck hitCheck);
    void deleteAll();
    List<HitCheck> findAll();
}
