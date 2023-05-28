package repository.Interface;

import javafx.collections.ObservableList;
import models.roomData;
import java.sql.SQLException;

public interface RoomRepositoryInterface {
    ObservableList<roomData> getAllRooms() throws SQLException;
    void addRoom(roomData room) throws SQLException;
    void updateRoom(roomData room) throws SQLException;

    boolean checkRoomExists(String object, String room);

    public void deleteRoom(String id) throws SQLException;
}
