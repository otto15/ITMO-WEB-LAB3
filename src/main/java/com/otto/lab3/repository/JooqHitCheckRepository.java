package com.otto.lab3.repository;

import com.otto.lab3.db.DBConnector;
import com.otto.lab3.jooq.tables.HitChecks;
import com.otto.lab3.jooq.tables.records.HitChecksRecord;
import com.otto.lab3.model.HitCheck;
import lombok.Data;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


import static com.otto.lab3.jooq.Tables.HIT_CHECKS;

@ManagedBean
@ApplicationScoped
@Data
public class JooqHitCheckRepository implements HitCheckRepository {

    @ManagedProperty("#{dBConnector}")
    private DBConnector dbConnector;

    @Override
    public boolean save(HitCheck hitCheck) {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            HitChecksRecord hitChecksRecord = context.newRecord(HitChecks.HIT_CHECKS);

            hitChecksRecord.setX(hitCheck.getX());
            hitChecksRecord.setY(hitCheck.getY());
            hitChecksRecord.setR(hitCheck.getR());
            hitChecksRecord.setHitStatus(hitCheck.isInArea());
            hitChecksRecord.setExecutionTime(hitCheck.getExecutionTime());
            hitChecksRecord.setCallingDate(hitCheck.getCallingDate().atOffset(ZoneOffset.UTC));
            hitChecksRecord.setSessionId(getSessionId());

            return hitChecksRecord.store() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            context.delete(HIT_CHECKS).where(HIT_CHECKS.SESSION_ID.eq(getSessionId())).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HitCheck> findAll() {
        try (Connection connection = dbConnector.getConnection()) {
            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            Result<Record> result = context.select()
                    .from(HIT_CHECKS)
                    .where(HIT_CHECKS.SESSION_ID.eq(getSessionId()))
                    .fetch();

            List<HitCheck> hitChecks = new ArrayList<>();

            result.forEach(record -> {
                HitCheck hitCheck = new HitCheck();
                hitCheck.setX(record.getValue(HIT_CHECKS.X));
                hitCheck.setY(record.getValue(HIT_CHECKS.Y));
                hitCheck.setR(record.getValue(HIT_CHECKS.R));
                hitCheck.setCallingDate(record.getValue(HIT_CHECKS.CALLING_DATE).toInstant());
                hitCheck.setExecutionTime(record.getValue(HIT_CHECKS.EXECUTION_TIME));
                hitCheck.setInArea(record.getValue(HIT_CHECKS.HIT_STATUS));

                hitChecks.add(hitCheck);
            });

            return hitChecks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String getSessionId() {

        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        return session.getId();
    }
}
