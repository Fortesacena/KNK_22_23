package models;


import java.util.Arrays;

public class roomData {
    private String object;
    private String room;
    private String status;

    private Integer id;

    public roomData(String object, String room,String status){
        this.object = object;
        this.room = room;
        this.status = status;

    }


    public String getObject(){
        return object;
    }

    public String getRoom(){
        return room;
    }

    public void setRoom(String newRoom) {
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String newType) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}