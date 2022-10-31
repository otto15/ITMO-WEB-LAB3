package com.otto.lab3.service;

import com.otto.lab3.model.HitCheck;

import java.io.Serializable;

public interface AreaChecker extends Serializable {
    boolean checkIfInArea(HitCheck value);
}
