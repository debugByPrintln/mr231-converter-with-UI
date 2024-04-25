package org.example.searadar.desktop.UI;

import org.example.searadar.desktop.service.MessageService;
import org.example.searadar.desktop.service.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppFrame {
    MessageService messageService = new MessageService();

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Mr231 converter");

        frame.setSize(1240, 720);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultListModel<String> vhwListModel = messageService.getVHWMessages();

        JList<String> vhwList = new JList<>(vhwListModel);
        JScrollPane vhwScroll = new JScrollPane(vhwList);
        JLabel vhwLabel = new JLabel("VHW messages (ex. $RAVHW,115.6,T,,,46.0,N,,*71)");

        vhwLabel.setBounds(10, 10, 700, 20);
        vhwScroll.setBounds(10, 30, 1200, 140);

        DefaultListModel<String> ttmListModel = messageService.getTTMMessages();

        JList<String> ttmList = new JList<>(ttmListModel);
        JScrollPane ttmScroll = new JScrollPane(ttmList);
        JLabel ttmLabel = new JLabel("TTM messages (ex. $RATTM,23,13.88,137.2,T,63.8,094.3,T,9.2,79.4,N,b,T,,783344,А*42)");

        ttmLabel.setBounds(10, 180, 700, 20);
        ttmScroll.setBounds(10, 200, 1200, 140);

        DefaultListModel<String> rsdListModel = messageService.getRSDMessages();

        JList<String> rsdList = new JList<>(rsdListModel);
        JScrollPane rsdScroll = new JScrollPane(rsdList);
        JLabel rsdLabel = new JLabel("RSD messages (ex. $RARSD,36.5,331.4,8.4,320.6,,,,,11.6,185.3,96.0,N,N,S*33)");

        rsdLabel.setBounds(10, 350, 700, 20);
        rsdScroll.setBounds(10, 370, 1200, 140);

        JTextField inputField = new JTextField();
        JLabel inputLabel = new JLabel("Type message:");
        JButton submitButton = new JButton("Save message");

        inputLabel.setBounds(10, 520, 200, 20);
        inputField.setBounds(10, 540, 600, 100);
        submitButton.setBounds(660, 560, 175, 50);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    Pair<String, String> converted = messageService.convertMessage(text);
                    if (converted.getKey().equals("VHW")){
                        vhwListModel.addElement(converted.getValue());
                        inputField.setText("");
                    }
                    else if (converted.getKey().equals("TTM")) {
                        ttmListModel.addElement(converted.getValue());
                        inputField.setText("");
                    }
                    else if (converted.getKey().equals("RSD")){
                        rsdListModel.addElement(converted.getValue());
                        inputField.setText("");
                    }
                    else{
                        inputField.setText(converted.getValue());
                    }
                }
            }
        });

        JPanel contentPanel = new JPanel(null);
        frame.setContentPane(contentPanel);

        contentPanel.add(vhwLabel);
        contentPanel.add(vhwScroll);
        contentPanel.add(ttmLabel);
        contentPanel.add(ttmScroll);
        contentPanel.add(rsdLabel);
        contentPanel.add(rsdScroll);
        contentPanel.add(inputField);
        contentPanel.add(inputLabel);
        contentPanel.add(submitButton);

        frame.setVisible(true);
    }
}
