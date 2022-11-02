package com.otto.lab3.repository;

import com.otto.lab3.model.HitCheck;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ApplicationScoped
public class HashMapHitCheckRepository implements HitCheckRepository {

    private final Map<String, List<HitCheck>> hitChecks = new HashMap<>();

    @Override
    public boolean save(HitCheck hitCheck) {
        hitChecks.putIfAbsent(getSessionId(), new ArrayList<>());
        hitChecks.get(getSessionId()).add(hitCheck);
        return true;
    }

    @Override
    public void deleteAll() {
        hitChecks.remove(getSessionId());
    }

    @Override
    public List<HitCheck> findAll() {
        hitChecks.putIfAbsent(getSessionId(), new ArrayList<>());
        return hitChecks.get(getSessionId());
    }

    public String getSessionId() {

        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);

        return session.getId();
    }
}
