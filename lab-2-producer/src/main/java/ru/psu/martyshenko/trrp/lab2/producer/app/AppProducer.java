package ru.psu.martyshenko.trrp.lab2.producer.app;

import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;
import ru.psu.martyshenko.trrp.lab2.producer.sender.Sender;
import ru.psu.martyshenko.trrp.lab2.producer.sender.SenderMQ;
import ru.psu.martyshenko.trrp.lab2.producer.service.PsuCoursesService;

public class AppProducer {

    public static void main(String[] args) {
        PsuCoursesService psuCoursesService = new PsuCoursesService();
        Sender sender = new SenderMQ();
        for (PsuCourses psuCourses:psuCoursesService.getAllCourses()) {
            sender.sendRow(psuCourses);
        }
    }
}
