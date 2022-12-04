package com.otto.lab3.repository;

import com.otto.lab3.model.HitCheck;
import com.otto.lab3.service.dto.HitCheckDTO;

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

    private final Map<String, List<HitCheckDTO>> hitChecks = new HashMap<>();

    @Override
    public Integer saveAndReturnId(HitCheckDTO hitCheck) {
        hitChecks.putIfAbsent(getSessionId(), new ArrayList<>());
        hitChecks.get(getSessionId()).add(hitCheck);
        return 1;
    }

    @Override
    public void deleteAll() {
        hitChecks.remove(getSessionId());
    }

    @Override
    public List<HitCheckDTO> findAll() {
        hitChecks.putIfAbsent(getSessionId(), new ArrayList<>());
        return hitChecks.get(getSessionId());
    }

    @Override
    public int getHitChecksTotalCount() {
        return 0;
    }

    @Override
    public List<HitCheckDTO> getLimitedHitChecks(int first, int pageSize) {
        return null;
    }

    public String getSessionId() {

        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);

        return session.getId();
    }
}
