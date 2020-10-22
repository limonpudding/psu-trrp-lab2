package ru.psu.martyshenko.trrp.lab2.consumer.service;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import ru.psu.martyshenko.trrp.lab2.consumer.datasource.DataSourcePostgres;
import ru.psu.martyshenko.trrp.lab2.pg.Sequences;
import ru.psu.martyshenko.trrp.lab2.pg.tables.daos.CourseDao;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.Course;
import ru.psu.martyshenko.trrp.lab2.pg.tables.records.CourseRecord;

import java.sql.Connection;
import java.util.List;

import static org.jooq.SQLDialect.POSTGRES;
import static ru.psu.martyshenko.trrp.lab2.pg.tables.Course.COURSE;

public class CourseService {

    DSLContext context;
    private CourseDao dao;

    public CourseService() {
        Connection connection = DataSourcePostgres.getInstance().getConnection();
        context = DSL.using(connection, POSTGRES);
        dao = new CourseDao();
        Configuration configuration = new DefaultConfiguration().set(connection).set(POSTGRES);
        dao.setConfiguration(configuration);
    }

    // Если уже есть полностью идентичная запись, то возвращает ID найденной записи
    public Integer insert(Course course) {
        CourseRecord teacherRecord = context.selectFrom(COURSE)
                .where(COURSE.COURSE_NAME.eq(course.getCourseName()))
                .and(COURSE.COURSE_TOTAL_HOURS.eq(course.getCourseTotalHours()))
                .and(COURSE.TEACHER_ID.eq(course.getTeacherId())).fetchOne();
        if (teacherRecord != null) {
            return (Integer)teacherRecord.get(0);
        } else {
            Integer id = context.nextval(Sequences.COURSE_COURSE_ID_SEQ);
            course.setCourseId(id);
            dao.insert(course);
            return id;
        }
    }

    public List<Course> getAll() {
        return context.selectFrom(COURSE).fetchInto(Course.class);
    }
}
