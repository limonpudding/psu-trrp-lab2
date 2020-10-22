package ru.psu.martyshenko.trrp.lab2.consumer.app;

import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;
import ru.psu.martyshenko.trrp.lab2.consumer.service.*;
import ru.psu.martyshenko.trrp.lab2.pg.tables.pojos.*;

public class TablesConverter {

    BuildingService buildingService;
    ClassroomService classroomService;
    TeacherService teacherService;
    CourseService courseService;
    LessonService lessonService;

    public TablesConverter() {
        buildingService = new BuildingService();
        classroomService = new ClassroomService();
        teacherService = new TeacherService();
        courseService = new CourseService();
        lessonService = new LessonService();
    }

    public void convert(PsuCourses row) {

        Integer classroomId = null;
        Integer teacherId = null;
        Integer courseId = null;
        saveBuilding(row);
        classroomId = saveClassroom(row);
        teacherId = saveTeacher(row);
        courseId = saveCourse(row, teacherId);
        saveLesson(row, courseId, classroomId);

    }


    public void saveBuilding(PsuCourses InputRowPsuCources) {
        Building building = new Building(InputRowPsuCources.getBuilding(), InputRowPsuCources.getBuildingAddress());
        buildingService.insert(building);
    }

    public Integer saveClassroom(PsuCourses InputRowPsuCources) {
        Classroom classroom = new Classroom(null, InputRowPsuCources.getClassroomNumber(), InputRowPsuCources.getBuilding());
        Integer classroomId = classroomService.insert(classroom);
        return classroomId;
    }

    public Integer saveTeacher(PsuCourses InputRowPsuCources) {
        String[] fio = InputRowPsuCources.getTeacherName().split(" ");
        Teacher teacher = new Teacher(null, fio[0], fio[1], fio[2], InputRowPsuCources.getTeacherMail(), InputRowPsuCources.getTeacherPhone());
        Integer teacherId = teacherService.insert(teacher);
        return teacherId;
    }

    public Integer saveCourse(PsuCourses InputRowPsuCources, Integer teacherId) {
        Course course = new Course(null, InputRowPsuCources.getCourseName(), InputRowPsuCources.getCourseTotalHours(), teacherId);
        Integer courseId = courseService.insert(course);
        return courseId;
    }

    public void saveLesson(PsuCourses InputRowPsuCources, Integer courseId, Integer classroomId) {
        Lesson lesson = new Lesson(null, InputRowPsuCources.getLessonDatetime(), InputRowPsuCources.getLessonHours(), courseId, classroomId);
        lessonService.insert(lesson);
    }
}
