package org.example.searadar.database.DAO;

import org.example.searadar.database.connection.ConnectionSource;
import ru.oogis.searadar.api.message.RadarSystemDataMessage;
import ru.oogis.searadar.api.message.SearadarStationMessage;
import ru.oogis.searadar.api.message.TrackedTargetMessage;
import ru.oogis.searadar.api.message.WaterSpeedHeadingMessage;
import ru.oogis.searadar.api.types.IFF;
import ru.oogis.searadar.api.types.TargetStatus;
import ru.oogis.searadar.api.types.TargetType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {
    ConnectionSource source = ConnectionSource.instance();

    public void addVHWMessage(WaterSpeedHeadingMessage vhwMessage){
        try(Connection connection = source.createConnection();
            PreparedStatement addVHW = connection.prepareStatement("INSERT INTO searadar.vhw_message " +
                    "(msgRecTime, course, courseattr, speed, speedunit) " +
                    "VALUES (?, ?, ?, ?, ?);"))
        {
            addVHW.setTimestamp(1, vhwMessage.getMsgRecTime());
            addVHW.setDouble(2, vhwMessage.getCourse());
            addVHW.setString(3, vhwMessage.getCourseAttr());
            addVHW.setDouble(4, vhwMessage.getSpeed());
            addVHW.setString(5, vhwMessage.getSpeedUnit());

            addVHW.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTTMMessage(TrackedTargetMessage ttmMessage){
        try(Connection connection = source.createConnection();
            PreparedStatement addTTM = connection.prepareStatement("INSERT INTO searadar.ttm_message " +
                    "(msgRecTime, msgtime, targetnumber, distance, bearing, course, speed, type, status, iff) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"))
        {
            addTTM.setTimestamp(1, ttmMessage.getMsgRecTime());
            addTTM.setLong(2, ttmMessage.getMsgTime());
            addTTM.setInt(3, ttmMessage.getTargetNumber());
            addTTM.setDouble(4, ttmMessage.getDistance());
            addTTM.setDouble(5, ttmMessage.getBearing());
            addTTM.setDouble(6, ttmMessage.getCourse());
            addTTM.setDouble(7, ttmMessage.getSpeed());
            addTTM.setString(8, ttmMessage.getType().toString());
            addTTM.setString(9, ttmMessage.getStatus().toString());
            addTTM.setString(10, ttmMessage.getIff().toString());

            addTTM.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRSDMessage(RadarSystemDataMessage rsdMessage){
        try(Connection connection = source.createConnection();
            PreparedStatement addRSD = connection.prepareStatement("INSERT INTO searadar.rsd_message " +
                    "(msgRecTime, initialdistance, initialbearing, movingcircleofdistance, bearing, distancefromship, bearing2, distancescale, distanceunit, displayorientation, workingmode) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"))
        {
            addRSD.setTimestamp(1, rsdMessage.getMsgRecTime());
            addRSD.setDouble(2, rsdMessage.getInitialDistance());
            addRSD.setDouble(3, rsdMessage.getInitialBearing());
            addRSD.setDouble(4, rsdMessage.getMovingCircleOfDistance());
            addRSD.setDouble(5, rsdMessage.getBearing());
            addRSD.setDouble(6, rsdMessage.getDistanceFromShip());
            addRSD.setDouble(7, rsdMessage.getBearing2());
            addRSD.setDouble(8, rsdMessage.getDistanceScale());
            addRSD.setString(9, rsdMessage.getDistanceUnit());
            addRSD.setString(10, rsdMessage.getDisplayOrientation());
            addRSD.setString(11, rsdMessage.getWorkingMode());

            addRSD.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<SearadarStationMessage>> getAllMessages(){
        Map<String, List<SearadarStationMessage>> result = new HashMap<>();

        List<SearadarStationMessage> vhwMessages = getAllVHWMessages();
        List<SearadarStationMessage> ttmMessages = getAllTTMMessages();
        List<SearadarStationMessage> rsdMessages = getAllRSDMessages();

        result.put("VHW", vhwMessages);
        result.put("TTM", ttmMessages);
        result.put("RSD", rsdMessages);

        return result;
    }

    public List<SearadarStationMessage> getAllVHWMessages(){
        List<SearadarStationMessage> result = new ArrayList<>();
        try(Connection connection = source.createConnection();
            PreparedStatement getAllMessages = connection.prepareStatement("SELECT * FROM searadar.vhw_message;"))
        {
            ResultSet resultSet = getAllMessages.executeQuery();

            while (resultSet.next()){
                result.add(getVHWMessageFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<SearadarStationMessage> getAllTTMMessages(){
        List<SearadarStationMessage> result = new ArrayList<>();
        try(Connection connection = source.createConnection();
            PreparedStatement getAllMessages = connection.prepareStatement("SELECT * FROM searadar.ttm_message;"))
        {
            ResultSet resultSet = getAllMessages.executeQuery();

            while (resultSet.next()){
                result.add(getTTMMessageFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<SearadarStationMessage> getAllRSDMessages(){
        List<SearadarStationMessage> result = new ArrayList<>();
        try(Connection connection = source.createConnection();
            PreparedStatement getAllMessages = connection.prepareStatement("SELECT * FROM searadar.rsd_message;"))
        {
            ResultSet resultSet = getAllMessages.executeQuery();

            while (resultSet.next()){
                result.add(getRSDMessageFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private WaterSpeedHeadingMessage getVHWMessageFromResultSet(ResultSet resultSet) throws SQLException {
        WaterSpeedHeadingMessage msg = new WaterSpeedHeadingMessage();
        msg.setMsgRecTime(resultSet.getTimestamp(2));
        msg.setCourse(resultSet.getDouble(3));
        msg.setCourseAttr(resultSet.getString(4));
        msg.setSpeed(resultSet.getDouble(5));
        msg.setSpeedUnit(resultSet.getString(6));
        return msg;
    }

    private TrackedTargetMessage getTTMMessageFromResultSet(ResultSet resultSet) throws SQLException {
        TrackedTargetMessage msg = new TrackedTargetMessage();
        msg.setMsgRecTime(resultSet.getTimestamp(2));
        msg.setMsgTime(resultSet.getLong(3));
        msg.setTargetNumber(resultSet.getInt(4));
        msg.setDistance(resultSet.getDouble(5));
        msg.setBearing(resultSet.getDouble(6));
        msg.setCourse(resultSet.getDouble(7));
        msg.setSpeed(resultSet.getDouble(8));
        msg.setType(TargetType.valueOf(resultSet.getString(9)));
        msg.setStatus(TargetStatus.valueOf(resultSet.getString(10)));
        msg.setIff(IFF.valueOf(resultSet.getString(11)));
        return msg;
    }

    private RadarSystemDataMessage getRSDMessageFromResultSet(ResultSet resultSet) throws SQLException {
        RadarSystemDataMessage msg = new RadarSystemDataMessage();
        msg.setMsgRecTime(resultSet.getTimestamp(2));
        msg.setInitialDistance(resultSet.getDouble(3));
        msg.setInitialBearing(resultSet.getDouble(4));
        msg.setMovingCircleOfDistance(resultSet.getDouble(5));
        msg.setBearing(resultSet.getDouble(6));
        msg.setDistanceFromShip(resultSet.getDouble(7));
        msg.setBearing2(resultSet.getDouble(8));
        msg.setDistanceScale(resultSet.getDouble(9));
        msg.setDistanceUnit(resultSet.getString(10));
        msg.setDisplayOrientation(resultSet.getString(11));
        msg.setWorkingMode(resultSet.getString(12));
        return msg;
    }
}
