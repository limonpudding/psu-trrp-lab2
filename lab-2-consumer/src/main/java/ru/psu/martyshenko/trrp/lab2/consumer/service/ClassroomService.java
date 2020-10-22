package ru.psu.martyshenko.trrp.lab2.consumer.service;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import ru.psu.martyshenko.trrp.lab2.consumer.datasource.DataSourcePostgres;
import ru.psu.martyshenko.trrp.lab2.pg.Sequences;
import ru.psu.martyshenko.trrp.lab2.pg.tables.daos.ClassroomDao;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.Classroom;
import ru.psu.martyshenko.trrp.lab2.pg.tables.records.ClassroomRecord;

import java.sql.Connection;
import java.util.List;

import static org.jooq.SQLDialect.POSTGRES;
import static ru.psu.martyshenko.trrp.lab2.pg.tables.Classroom.CLASSROOM;

public class ClassroomService {

    DSLContext context;
    private ClassroomDao dao;

    public ClassroomService() {
        Connection connection = DataSourcePostgres.getInstance().getConnection();
        context = DSL.using(connection, POSTGRES);
        dao = new ClassroomDao();
        Configuration configuration = new DefaultConfiguration().set(connection).set(POSTGRES);
        dao.setConfiguration(configuration);
    }

    // Если уже есть полностью идентичная запись, то возвращает ID найденной записи
    public Integer insert(Classroom classroom) {
        ClassroomRecord classroomRecord = context.selectFrom(CLASSROOM)
                .where(CLASSROOM.BUILDING.eq(classroom.getBuilding()))
                .and(CLASSROOM.CLASSROOM_NUMBER.eq(classroom.getClassroomNumber())).fetchOne();
        if (classroomRecord != null) {
            return (Integer)classroomRecord.get(0);
        } else {
            Integer id = context.nextval(Sequences.CLASSROOM_CLASSROOM_ID_SEQ);
            classroom.setClassroomId(id);
            dao.insert(classroom);
            return id;
        }
    }

    public List<Classroom> getAll() {
        return context.selectFrom(CLASSROOM).fetchInto(Classroom.class);
    }
}
