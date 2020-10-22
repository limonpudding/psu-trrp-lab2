package ru.psu.martyshenko.trrp.lab2.fb.tables.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PsuCourses implements Serializable {

    private static final long serialVersionUID = -1462733424;

    private String        courseName;
    private Integer       courseTotalHours;
    private String        teacherName;
    private String        teacherMail;
    private Long          teacherPhone;
    private LocalDateTime lessonDatetime;
    private Integer       lessonHours;
    private String        classroomNumber;
    private String        building;
    private String        buildingAddress;

    public PsuCourses() {}

    public PsuCourses(PsuCourses value) {
        this.courseName = value.courseName;
        this.courseTotalHours = value.courseTotalHours;
        this.teacherName = value.teacherName;
        this.teacherMail = value.teacherMail;
        this.teacherPhone = value.teacherPhone;
        this.lessonDatetime = value.lessonDatetime;
        this.lessonHours = value.lessonHours;
        this.classroomNumber = value.classroomNumber;
        this.building = value.building;
        this.buildingAddress = value.buildingAddress;
    }

    public PsuCourses(
            String        courseName,
            Integer       courseTotalHours,
            String        teacherName,
            String        teacherMail,
            Long          teacherPhone,
            LocalDateTime lessonDatetime,
            Integer       lessonHours,
            String        classroomNumber,
            String        building,
            String        buildingAddress
    ) {
        this.courseName = courseName;
        this.courseTotalHours = courseTotalHours;
        this.teacherName = teacherName;
        this.teacherMail = teacherMail;
        this.teacherPhone = teacherPhone;
        this.lessonDatetime = lessonDatetime;
        this.lessonHours = lessonHours;
        this.classroomNumber = classroomNumber;
        this.building = building;
        this.buildingAddress = buildingAddress;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseTotalHours() {
        return this.courseTotalHours;
    }

    public void setCourseTotalHours(Integer courseTotalHours) {
        this.courseTotalHours = courseTotalHours;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherMail() {
        return this.teacherMail;
    }

    public void setTeacherMail(String teacherMail) {
        this.teacherMail = teacherMail;
    }

    public Long getTeacherPhone() {
        return this.teacherPhone;
    }

    public void setTeacherPhone(Long teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public LocalDateTime getLessonDatetime() {
        return this.lessonDatetime;
    }

    public void setLessonDatetime(LocalDateTime lessonDatetime) {
        this.lessonDatetime = lessonDatetime;
    }

    public Integer getLessonHours() {
        return this.lessonHours;
    }

    public void setLessonHours(Integer lessonHours) {
        this.lessonHours = lessonHours;
    }

    public String getClassroomNumber() {
        return this.classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuildingAddress() {
        return this.buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PsuCourses (");

        sb.append(courseName);
        sb.append(", ").append(courseTotalHours);
        sb.append(", ").append(teacherName);
        sb.append(", ").append(teacherMail);
        sb.append(", ").append(teacherPhone);
        sb.append(", ").append(lessonDatetime);
        sb.append(", ").append(lessonHours);
        sb.append(", ").append(classroomNumber);
        sb.append(", ").append(building);
        sb.append(", ").append(buildingAddress);

        sb.append(")");
        return sb.toString();
    }
}

