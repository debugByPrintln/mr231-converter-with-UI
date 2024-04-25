package org.example.searadar.desktop.service;

import org.example.searadar.database.DAO.MessageDAO;
import org.example.searadar.mr231.convert.Mr231Converter;
import ru.oogis.searadar.api.message.RadarSystemDataMessage;
import ru.oogis.searadar.api.message.SearadarStationMessage;
import ru.oogis.searadar.api.message.TrackedTargetMessage;
import ru.oogis.searadar.api.message.WaterSpeedHeadingMessage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAO();

    private final Mr231Converter converter = new Mr231Converter();

    public DefaultListModel<String> getVHWMessages(){
        DefaultListModel<String> vhwListModel = new DefaultListModel<>();
        List<SearadarStationMessage> vhwMessages = messageDAO.getAllVHWMessages();

        for (int i = 0; i < vhwMessages.size(); i++) {
            vhwListModel.addElement(vhwMessages.get(i).toString());
        }

        return vhwListModel;
    }

    public DefaultListModel<String> getTTMMessages(){
        DefaultListModel<String> ttmListModel = new DefaultListModel<>();
        List<SearadarStationMessage> ttmMessages = messageDAO.getAllTTMMessages();

        for (int i = 0; i < ttmMessages.size(); i++) {
            ttmListModel.addElement(ttmMessages.get(i).toString());
        }

        return ttmListModel;
    }

    public DefaultListModel<String> getRSDMessages(){
        DefaultListModel<String> rsdListModel = new DefaultListModel<>();
        List<SearadarStationMessage> rsdMessages = messageDAO.getAllRSDMessages();

        for (int i = 0; i < rsdMessages.size(); i++) {
            rsdListModel.addElement(rsdMessages.get(i).toString());
        }

        return rsdListModel;
    }

    public Pair<String, String> convertMessage(String text){
        List<SearadarStationMessage> stationMessages = new ArrayList<>();
        try{
            stationMessages = converter.convert(text);
        }
        catch (Exception e){
            stationMessages.add(null);
            return new Pair<>("UNKNOWN", "Wrong message type or message spelling");
        }

        if (stationMessages.get(0) instanceof WaterSpeedHeadingMessage){
            messageDAO.addVHWMessage((WaterSpeedHeadingMessage) stationMessages.get(0));
            return new Pair<>("VHW", stationMessages.get(0).toString());
        }

        else if (stationMessages.get(0) instanceof TrackedTargetMessage){
            messageDAO.addTTMMessage((TrackedTargetMessage) stationMessages.get(0));
            return new Pair<>("TTM", stationMessages.get(0).toString());
        }

        else if (stationMessages.get(0) instanceof RadarSystemDataMessage){
            messageDAO.addRSDMessage((RadarSystemDataMessage) stationMessages.get(0));
            return new Pair<>("RSD", stationMessages.get(0).toString());
        }

        else {
            return new Pair<>("UNKNOWN", "Wrong message type or message spelling");
        }
    }
}
