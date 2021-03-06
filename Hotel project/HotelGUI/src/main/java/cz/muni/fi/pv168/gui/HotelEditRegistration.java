/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.gui;

import cz.muni.fi.pv168.hotel.DatabaseException;
import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.HotelUtils;
import cz.muni.fi.pv168.hotel.Registration;
import cz.muni.fi.pv168.hotel.RegistrationManager;
import cz.muni.fi.pv168.hotel.Room;
import cz.muni.fi.pv168.hotel.RoomManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michal
 */
public class HotelEditRegistration extends javax.swing.JFrame {

    private RegistrationManager registrationManager = HotelMain.getRegistrationManager();
    private List<Guest> guestList;
    private GuestManager guestManager = HotelMain.getGuestManager();
    private int guestPreviousRowCount;
    private DefaultTableModel guestModel;
    private List<Room> roomList;
    private RoomManager roomManager = HotelMain.getRoomManager();
    private int roomPreviousRowCount;
    private DefaultTableModel roomModel;

    private long registrationId;

    /**
     * Creates new form HotelEditRegistration
     */
    public HotelEditRegistration() {
        initComponents();
        guestModel = new DefaultTableModel(new Object[][]{
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
                new String[]{
                    "Name", "Address", "Phone", "Birth date"
                });

        guestTable.setModel(guestModel);

        roomModel = new DefaultTableModel(new Object[][]{
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null}
        },
                new String[]{
                    "Floor", "Number", "Capacity"
                });
        roomTable.setModel(roomModel);

    }

    public void setRegistrationId(long id) {
        this.registrationId = id;
        try {
            Registration registration = registrationManager.getRegistrationById(id);
            Guest guest = guestManager.getGuestById(registration.getGuest().getId());
            Room room = roomManager.getRoomById(registration.getRoom().getId());
            guestList = new ArrayList<>();
            guestList.add(guest);
            showActualGuest();
            roomList = new ArrayList<>();
            roomList.add(room);
            showActualRoom();
            Date startDate = registration.getStartDate();
            Date endDate = registration.getEndDate();
            startDateDay.setText(Integer.toString(startDate.getDay()));
            startDateMonth.setText(Integer.toString(startDate.getMonth()));
            startDateYear.setText(Integer.toString(startDate.getYear()));
            endDateDay.setText(Integer.toString(endDate.getDay()));
            endDateMonth.setText(Integer.toString(endDate.getMonth()));
            endDateYear.setText(Integer.toString(endDate.getYear()));
            PriceTextField.setText(Double.toString(registration.getPrice()));
       } catch (DatabaseException ex) {
            //exception
        }

    }

    private class MySwingWorker extends SwingWorker<String, String> {

        @Override
        protected String doInBackground() throws DatabaseException {
            roomList = roomManager.getAllRooms();
            return "Retrieving rooms";
        }

        @Override
        protected void done() {
            publish("Rooms retrieved");
            while (roomTable.getModel().getRowCount() < roomList.size()) {
                roomModel.addRow(new Object[]{null, null, null});
            }

            showActualRoom();
            roomPreviousRowCount = roomTable.getRowCount();
        }

        @Override
        protected void process(List<String> chunks) {
            System.out.println(chunks.toString() + "\n");
        }
    }

    private class MySwingWorker2 extends SwingWorker<String, String> {

        @Override
        protected String doInBackground() throws DatabaseException {
            guestList = guestManager.getAllGuests();
            return "Retrieving guests";
        }

        @Override
        protected void done() {
            publish("Guests retrieved");
            while (guestTable.getModel().getRowCount() < guestList.size()) {
                guestModel.addRow(new Object[]{null, null, null});
            }

            showActualGuest();
            guestPreviousRowCount = guestTable.getRowCount();
        }

        @Override
        protected void process(List<String> chunks) {
            System.out.println(chunks.toString() + "\n");
        }
    }

    private void clearTableGuest() {
        for (int i = 0; i < guestPreviousRowCount; i++) {
            guestTable.getModel().setValueAt("", i, 0);
            guestTable.getModel().setValueAt("", i, 1);
            guestTable.getModel().setValueAt("", i, 2);
            guestTable.getModel().setValueAt("", i, 3);
        }
    }

    private void showActualGuest() {
        for (int i = 0; i < guestList.size(); i++) {
            guestTable.getModel().setValueAt(guestList.get(i).getName(), i, 0);
            guestTable.getModel().setValueAt(guestList.get(i).getAddress(), i, 1);
            guestTable.getModel().setValueAt(guestList.get(i).getPhone(), i, 2);
            guestTable.getModel().setValueAt(HotelUtils.convertDateToString(guestList.get(i).getBirthDate()), i, 3);
        }
    }

    private void clearTableRoom() {
        for (int i = 0; i < roomPreviousRowCount; i++) {
            roomTable.getModel().setValueAt("", i, 0);
            roomTable.getModel().setValueAt("", i, 1);
            roomTable.getModel().setValueAt("", i, 2);
        }
    }

    private void showActualRoom() {
        for (int i = 0; i < roomList.size(); i++) {
            roomTable.getModel().setValueAt(roomList.get(i).getFloor(), i, 0);
            roomTable.getModel().setValueAt(roomList.get(i).getNumber(), i, 1);
            roomTable.getModel().setValueAt(roomList.get(i).getCapacity(), i, 2);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startDateDay = new javax.swing.JTextField();
        startDateMonth = new javax.swing.JTextField();
        startDateYear = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        guestTable = new javax.swing.JTable();
        endDateDay = new javax.swing.JTextField();
        endDateMonth = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        endDateYear = new javax.swing.JTextField();
        guestTextField = new javax.swing.JTextField();
        PriceTextField = new javax.swing.JTextField();
        guestComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        roomComboBox = new javax.swing.JComboBox();
        roomTextField = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        guestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Address", "Phone", "Birth date"
            }
        ));
        jScrollPane1.setViewportView(guestTable);

        endDateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDateDayActionPerformed(evt);
            }
        });

        endDateMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDateMonthActionPerformed(evt);
            }
        });

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        guestTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestTextFieldActionPerformed(evt);
            }
        });

        guestComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Name", "Address", "Phone", "Birth date" }));

        jLabel4.setText("Month");

        jButton5.setText("Show all");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel5.setText("Year");

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Floor", "Number", "Capacity"
            }
        ));
        jScrollPane2.setViewportView(roomTable);

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton6.setText("Show all");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        roomComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Floor", "Number", "Capacity" }));

        roomTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomTextFieldActionPerformed(evt);
            }
        });

        jButton7.setText("Search");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel6.setText("Day");

        jLabel7.setText("Month");

        jLabel8.setText("Year");

        jLabel9.setText("Price");

        jLabel10.setText("Start date");

        jLabel11.setText("End date");

        jLabel12.setText("Day");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jButton6)
                        .addGap(136, 136, 136)
                        .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(roomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jButton5)
                        .addGap(137, 137, 137)
                        .addComponent(guestComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(guestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(startDateDay)
                                .addComponent(startDateMonth, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(startDateYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10))
                        .addGap(163, 163, 163)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(endDateDay)
                                .addComponent(endDateMonth)
                                .addComponent(endDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(PriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(guestComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDateDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endDateDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endDateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void endDateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDateDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endDateDayActionPerformed

    private void endDateMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDateMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endDateMonthActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearTableGuest();
        switch (guestComboBox.getSelectedIndex()) {
            case 0:
                try {
                    guestList = guestManager.getGuestsByName(guestTextField.getText());
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 1:
                try {
                    guestList = guestManager.getGuestsByAddress(guestTextField.getText());
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 2:
                try {
                    guestList = guestManager.getGuestsByPhone(Long.parseLong(guestTextField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 3:
                try {
                    guestList = guestManager.getGuestsByBirthDate(HotelUtils.convertStringToDate(guestTextField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
        }
        showActualGuest();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void guestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guestTextFieldActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        clearTableGuest();
        MySwingWorker2 worker2 = new MySwingWorker2();
        worker2.execute();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (roomTable.getSelectedRowCount() != 1 || guestTable.getSelectedRowCount() != 1
                || roomTable.getSelectedRow() >= roomList.size() || guestTable.getSelectedRow() >= guestList.size()) {
            return;
        }

        long roomId = roomList.get(roomTable.getSelectedRow()).getId();
        long guestId = guestList.get(guestTable.getSelectedRow()).getId();

        try {
            Registration registration = new Registration(registrationId, roomManager.getRoomById(roomId), guestManager.getGuestById(guestId),
                    new Date(Integer.parseInt(startDateYear.getText()), Integer.parseInt(startDateMonth.getText()), Integer.parseInt(startDateYear.getText())),
                    new Date(Integer.parseInt(endDateYear.getText()), Integer.parseInt(endDateMonth.getText()), Integer.parseInt(endDateYear.getText())),
                    Double.parseDouble(PriceTextField.getText()));
            registrationManager.updateRegistration(registration);
            this.setVisible(false);
        } catch (DatabaseException ex) {
            //exception
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        clearTableRoom();
        MySwingWorker worker = new MySwingWorker();
        worker.execute();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void roomTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomTextFieldActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        clearTableRoom();
        switch (roomComboBox.getSelectedIndex()) {
            case 0:
                try {
                    roomList = roomManager.getRoomsByFloor(Integer.parseInt(roomTextField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 1:
                try {
                    roomList = roomManager.getRoomByNumber(Integer.parseInt(roomTextField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 2:
                try {
                    roomList = roomManager.getRoomsByCapacity(Integer.parseInt(roomTextField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
        }
        showActualRoom();
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HotelEditRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HotelEditRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HotelEditRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HotelEditRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HotelEditRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField PriceTextField;
    private javax.swing.JTextField endDateDay;
    private javax.swing.JTextField endDateMonth;
    private javax.swing.JTextField endDateYear;
    private javax.swing.JComboBox guestComboBox;
    private javax.swing.JTable guestTable;
    private javax.swing.JTextField guestTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox roomComboBox;
    private javax.swing.JTable roomTable;
    private javax.swing.JTextField roomTextField;
    private javax.swing.JTextField startDateDay;
    private javax.swing.JTextField startDateMonth;
    private javax.swing.JTextField startDateYear;
    // End of variables declaration//GEN-END:variables
}
