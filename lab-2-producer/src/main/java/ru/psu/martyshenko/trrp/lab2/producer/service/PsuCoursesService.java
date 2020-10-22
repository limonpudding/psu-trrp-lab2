package ru.psu.martyshenko.trrp.lab2.producer.service;

import static org.jooq.SQLDialect.*;
import static ru.psu.martyshenko.trrp.lab2.fb.tables.PsuCourses.PSU_COURSES;

import org.jooq.*;
import org.jooq.impl.*;
import ru.psu.martyshenko.trrp.lab2.producer.datasource.DataSourceFireBird;
import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;

import java.util.List;


public class PsuCoursesService {

    DSLContext context;

    public PsuCoursesService() {
        context = DSL.using(DataSourceFireBird.getInstance().getConnection(), FIREBIRD);
    }

    public List<PsuCourses> getAllCourses() {
        return context.selectFrom(PSU_COURSES).fetchInto(PsuCourses.class);
    }
}
