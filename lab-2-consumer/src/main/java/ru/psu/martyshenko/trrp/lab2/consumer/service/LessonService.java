package ru.psu.martyshenko.trrp.lab2.consumer.service;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import ru.psu.martyshenko.trrp.lab2.consumer.datasource.DataSourcePostgres;
import ru.psu.martyshenko.trrp.lab2.pg.Sequences;
import ru.psu.martyshenko.trrp.lab2.pg.tables.daos.LessonDao;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.Lesson;
import ru.psu.martyshenko.trrp.lab2.pg.tables.records.LessonRecord;

import java.sql.Connection;
import java.util.List;

import static org.jooq.SQLDialect.POSTGRES;
import static ru.psu.martyshenko.trrp.lab2.pg.tables.Lesson.LESSON;

public class LessonService {

    DSLContext context;
    private LessonDao dao;

    public LessonService() {
        Connection connection = DataSourcePostgres.getInstance().getConnection();
        context = DSL.using(connection, POSTGRES);
        dao = new LessonDao();
        Configuration configuration = new DefaultConfiguration().set(connection).set(POSTGRES);
        dao.setConfiguration(configuration);
    }

    // Если уже есть полностью идентичная запись, то возвращает ID найденной записи
    public Integer insert(Lesson lesson) {
        LessonRecord lessonRecord = context.selectFrom(LESSON)
                .where(LESSON.LESSON_DATETIME.eq(lesson.getLessonDatetime()))
                .and(LESSON.LESSON_HOURS.eq(lesson.getLessonHours()))
                .and(LESSON.COURSE_ID.eq(lesson.getCourseId()))
                .and(LESSON.CLASSROOM_ID.eq(lesson.getClassroomId())).fetchOne();
        if (lessonRecord != null) {
            return (Integer)lessonRecord.get(0);
        } else {
            Integer id = context.nextval(Sequences.LESSON_LESSON_ID_SEQ);
            lesson.setLessonId(id);
            dao.insert(lesson);
            return id;
        }
    }

    public List<Lesson> getAll() {
        return context.selectFrom(LESSON).fetchInto(Lesson.class);
    }
}
