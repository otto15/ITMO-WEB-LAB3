package com.otto.lab3.repository;

import com.otto.lab3.db.DBConnector;
import com.otto.lab3.service.dto.HitCheckDTO;
import com.otto.lab3.util.SessionIDGetter;
import lombok.Data;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static com.otto.lab3.jooq.Tables.HIT_CHECKS;

@ManagedBean
@ApplicationScoped
@Data
public class JooqHitCheckRepository implements HitCheckRepository {

    @ManagedProperty("#{dBConnector}")
    private DBConnector dbConnector;

    @ManagedProperty("#{sessionIDGetter}")
    private SessionIDGetter sessionIDGetter;

    @Override
    public Integer saveAndReturnId(HitCheckDTO hitCheck) {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext dsl = DSL.using(connection, SQLDialect.POSTGRES);
            FacesContext.getCurrentInstance().getExternalContext().log(hitCheck.toString());
            return dsl.insertInto(HIT_CHECKS)
                    .set(dsl.newRecord(HIT_CHECKS, hitCheck))
                    .returning(HIT_CHECKS.ID)
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error inserting hit check"))
                    .get(HIT_CHECKS.ID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            context.delete(HIT_CHECKS).where(HIT_CHECKS.SESSION_ID.eq(
                    sessionIDGetter.getSessionId())).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HitCheckDTO> findAll() {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            return context.select()
                    .from(HIT_CHECKS)
                    .where(HIT_CHECKS.SESSION_ID.eq(
                            sessionIDGetter.getSessionId()))
                    .fetch()
                    .into(HitCheckDTO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int getHitChecksTotalCount() {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext dsl = DSL.using(connection, SQLDialect.POSTGRES);
            return dsl.fetchCount(HIT_CHECKS, HIT_CHECKS.SESSION_ID.eq(sessionIDGetter.getSessionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<HitCheckDTO> getLimitedHitChecks(int first, int pageSize) {
        try (Connection connection = dbConnector.getConnection()) {
            FacesContext.getCurrentInstance().getExternalContext().log(String.format("set %d %d", first, pageSize));
            DSLContext dsl = DSL.using(connection, SQLDialect.POSTGRES);
            return dsl.select().from(HIT_CHECKS)
                    .where(HIT_CHECKS.SESSION_ID.eq(sessionIDGetter.getSessionId()))
                    .orderBy(HIT_CHECKS.CALLING_DATE.asc())
                    .offset(first)
                    .limit(pageSize)
                    .fetch()
                    .into(HitCheckDTO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
