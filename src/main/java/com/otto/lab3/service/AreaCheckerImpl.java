package com.otto.lab3.service;

import com.otto.lab3.model.HitCheck;
import lombok.Data;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.Objects;

@ManagedBean
@ApplicationScoped
@Data
public class AreaCheckerImpl implements AreaChecker {

    private static final double REC_A1 = 0;
    private static final double REC_A2 = 0.5;
    private static final double REC_B1 = 0;
    private static final double REC_B2 = -1;

    private static final double RAD = 0.5;

    private static final double TRIANGLE_Y = 0;
    private static final double TRIANGLE_X = 0;
    private static final double TRIANGLE_B = 1;

    @Override
    public boolean checkIfInArea(HitCheck hitCheck) {
        Objects.requireNonNull(hitCheck);
        return check(hitCheck.getX(), hitCheck.getY(), hitCheck.getR());
    }

    public boolean check(double x, double y, double r) {
        return checkIfInTriangle(x, y, r) || checkIfInQuarterOfCircle(x, y, r) || checkIfInRectangle(x, y, r);
    }

    private boolean checkIfInTriangle(double x, double y, double r) {
        return x <= TRIANGLE_X * r && y >= TRIANGLE_Y * r && (y - x - r * TRIANGLE_B) <= 0;
    }

    private boolean checkIfInQuarterOfCircle(double x, double y, double r) {
        return x <= 0 && y <= 0 && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r * RAD, 2));
    }

    private boolean checkIfInRectangle(double x, double y, double r) {
        return x >= REC_A1 * r && x <= REC_A2 * r && y >= REC_B2 * r && y <= REC_B1 * r;
    }

}
