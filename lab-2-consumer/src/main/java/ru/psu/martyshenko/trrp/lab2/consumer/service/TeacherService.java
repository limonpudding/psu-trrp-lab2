package ru.psu.martyshenko.trrp.lab2.consumer.service;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import ru.psu.martyshenko.trrp.lab2.consumer.datasource.DataSourcePostgres;
import ru.psu.martyshenko.trrp.lab2.pg.Sequences;
import ru.psu.martyshenko.trrp.lab2.pg.tables.daos.TeacherDao;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.Teacher;
import ru.psu.martyshenko.trrp.lab2.pg.tables.records.TeacherRecord;

import java.sql.Connection;
import java.util.List;

import static org.jooq.SQLDialect.POSTGRES;
import static ru.psu.martyshenko.trrp.lab2.pg.tables.Teacher.TEACHER;

public class TeacherService {

    DSLContext context;
    private TeacherDao dao;

    public TeacherService() {
        Connection connection = DataSourcePostgres.getInstance().getConnection();
        context = DSL.using(connection, POSTGRES);
        dao = new TeacherDao();
        Configuration configuration = new DefaultConfiguration().set(connection).set(POSTGRES);
        dao.setConfiguration(configuration);
    }

    // Если уже есть полностью идентичная запись, то возвращает ID найденной записи
    public Integer insert(Teacher teacher) {
        TeacherRecord teacherRecord = context.selectFrom(TEACHER)
                .where(TEACHER.TEACHER_FIRST_NAME.eq(teacher.getTeacherFirstName()))
                .and(TEACHER.TEACHER_LAST_NAME.eq(teacher.getTeacherLastName()))
                .and(TEACHER.TEACHER_PATRONYMIC.eq(teacher.getTeacherPatronymic()))
                .and(TEACHER.TEACHER_MAIL.eq(teacher.getTeacherMail()))
                .and(TEACHER.TEACHER_PHONE.eq(teacher.getTeacherPhone())).fetchOne();
        if (teacherRecord != null) {
            return (Integer)teacherRecord.get(0);
        } else {
            Integer id = context.nextval(Sequences.TEACHER_TEACHER_ID_SEQ);
            teacher.setTeacherId(id);
            dao.insert(teacher);
            return id;
        }
    }

    public List<Teacher> getAll() {
        return context.selectFrom(TEACHER).fetchInto(Teacher.class);
    }
}
