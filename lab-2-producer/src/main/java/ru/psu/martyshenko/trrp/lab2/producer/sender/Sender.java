package ru.psu.martyshenko.trrp.lab2.producer.sender;

import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;

public interface Sender {

    void sendRow(PsuCourses psuCourses);

}
