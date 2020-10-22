package ru.psu.martyshenko.trrp.lab2.consumer.service;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import ru.psu.martyshenko.trrp.lab2.consumer.datasource.DataSourcePostgres;
import ru.psu.martyshenko.trrp.lab2.pg.tables.daos.BuildingDao;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.Building;

import java.sql.Connection;
import java.util.List;

import static org.jooq.SQLDialect.POSTGRES;
import static ru.psu.martyshenko.trrp.lab2.pg.tables.Building.BUILDING;

public class BuildingService {

    DSLContext context;
    private BuildingDao dao;

    public BuildingService() {
        Connection connection = DataSourcePostgres.getInstance().getConnection();
        context = DSL.using(connection, POSTGRES);
        dao = new BuildingDao();
        Configuration configuration = new DefaultConfiguration().set(connection).set(POSTGRES);
        dao.setConfiguration(configuration);
    }

    public void insert(Building building) {
        if (!dao.existsById(building.getBuilding())) {
            dao.insert(building);
        }
    }

    public List<Building> getAll() {
        return context.selectFrom(BUILDING).fetchInto(Building.class);
    }
}
