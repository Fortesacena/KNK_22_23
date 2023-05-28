package models;

import java.awt.*;

public class Application {
    private Integer application_id;
    private Integer study_year;
    private String university;
    private String phone_no;
    private String name;
    private String lastname;
    private String gender;
    private String birth_date;
    private String city;
    private String personal_number;
    private Double average_grade;
    private String image;
    private String room;
    private Integer dormitory;

    private Color rowColor;


    public Application(Integer application_id, Integer study_year, String university, String phone_no, String name, String lastname, String gender,
                       String birth_date, String city, String personal_number, Double average_grade, String image, String room, Integer dormitory){
        this.application_id = application_id;
        this.study_year = study_year;
        this.university = university;
        this.phone_no = phone_no;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.birth_date = birth_date;
        this.city = city;
        this.personal_number = personal_number;
        this.average_grade = average_grade;
        this.image = image;
        this.room = room;
        this.dormitory = dormitory;
    }

    public Integer getStudy_year(){
        return study_year;
    }
    public static void setStudyYear(Integer studyYear) {
    }
    public String getUniversity(){
        return university;
    }
    public String getPhone_no(){
        return phone_no;
    }

    public String getName(){
        return name;
    }

    public String getLastname(){
        return lastname;
    }
    public String getGender(){
        return gender;
    }
    public String getBirth_date(){
        return birth_date;
    }
    public String getCity(){
        return city;
    }
    public String getPersonal_number(){
        return personal_number;
    }
    public Double getAverage_grade(){
        return average_grade;
    }

    public String getImage(){
        return image;
    }

    public String getRoom() {
        return room;
    }
    public Integer getDormitory() {
        return dormitory;
    }
    public Integer getApplication_id() {
        return application_id;
    }

    public Color getRowColor() {
        return rowColor;
    }

    public void setRowColor(Color rowColor) {
        this.rowColor = rowColor;
    }
}

