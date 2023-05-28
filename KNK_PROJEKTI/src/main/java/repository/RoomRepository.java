package repository;

import repository.Interface.RoomRepositoryInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.roomData;
import services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRepository implements RoomRepositoryInterface {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @Override
    public ObservableList<roomData> getAllRooms() throws SQLException {
        ObservableList<roomData> listData = FXCollections.observableArrayList();

        String insertData = "SELECT * FROM room";

        connect = ConnectionUtil.getConnection();

        try {
            roomData roomD;

            prepare = connect.prepareStatement(insertData);
            result = prepare.executeQuery();

            while (result.next()) {
                roomD = new roomData(result.getString("object"),
                        result.getString("room"),
                        result.getString("status"));

                listData.add(roomD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    @Override
    public void addRoom(roomData room) throws SQLException {
        String insertData = "INSERT INTO room (object, room) VALUES (?, ?)";
        connect = ConnectionUtil.getConnection();

        try {
            String object = room.getObject();
            String roomName = room.getRoom();

            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, object);
            prepare.setString(2, roomName);

            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateRoom(roomData roomData) throws SQLException {
        String updateQuery = "UPDATE room SET status = ? WHERE room = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, roomData.getStatus());
            statement.setString(2, roomData.getRoom());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean checkRoomExists(String object, String room) {
        String selectQuery = "SELECT id FROM room WHERE OBJECT = ? AND ROOM = ?";

        try (PreparedStatement statement = connect.prepareStatement(selectQuery)) {
            statement.setString(1, object);
            statement.setString(2, room);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteRoom(String room) throws SQLException {
        String deleteData = "DELETE FROM room WHERE room = ?";

        try (Connection connect = ConnectionUtil.getConnection();
             PreparedStatement prepare = connect.prepareStatement(deleteData)) {

            prepare.setString(1, room);
            prepare.executeUpdate();
        }
    }
}
