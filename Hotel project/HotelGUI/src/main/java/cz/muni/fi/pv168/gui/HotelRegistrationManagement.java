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
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michal
 */
public class HotelRegistrationManagement extends javax.swing.JFrame {

    private List<Registration> registrationList;
    private RegistrationManager registrationManager;
    private int previousRowCount;
    private DefaultTableModel model;

    /**
     * Creates new form HotelRegistrationManagement
     */
    public HotelRegistrationManagement() {
        initComponents();
        model = new DefaultTableModel(new Object[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        },
                new String[]{
                    "Room number", "Guest name", "Start date", "End date", "Price"
                });

        table.setModel(model);
        registrationManager = HotelMain.getRegistrationManager();
    }

    private class MySwingWorker extends SwingWorker<String, String> {

        @Override
        protected String doInBackground() throws DatabaseException {
            registrationList = registrationManager.getAllRegistrations();
            return "Retrieving all registrations";
        }

        @Override
        protected void done() {
            publish("Registrations retrieved");
            while (table.getModel().getRowCount() < registrationList.size()) {
                model.addRow(new Object[]{null, null, null});
            }

            showActual();
            previousRowCount = table.getRowCount();
        }

        @Override
        protected void process(List<String> chunks) {
            System.out.println(chunks.toString() + "\n");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        comboBox = new javax.swing.JComboBox();
        textField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Room number", "Guest name", "Start date", "End date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        jButton1.setText("Create new");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("Show all");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Room number", "Guest name", "Start date", "End date", "Price" }));

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton3))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearTable() {
        for (int i = 0; i < previousRowCount; i++) {
            table.getModel().setValueAt("", i, 0);
            table.getModel().setValueAt("", i, 1);
            table.getModel().setValueAt("", i, 2);
            table.getModel().setValueAt("", i, 3);
            table.getModel().setValueAt("", i, 4);
        }
    }

    private void showActual() {
        for (int i = 0; i < registrationList.size(); i++) {
            table.getModel().setValueAt(registrationList.get(i).getRoom().getNumber(), i, 0);
            table.getModel().setValueAt(registrationList.get(i).getGuest().getName(), i, 1);
            table.getModel().setValueAt(HotelUtils.convertDateToString(registrationList.get(i).getStartDate()), i, 2);
            table.getModel().setValueAt(HotelUtils.convertDateToString(registrationList.get(i).getEndDate()), i, 3);
            table.getModel().setValueAt(registrationList.get(i).getPrice(), i, 4);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new HotelAddRegistration().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (table.getSelectedRowCount() > 0 && registrationList.size() > 0) {
            int[] idList = table.getSelectedRows();
            previousRowCount = table.getRowCount();
            for (int i = idList.length - 1; i >= 0; i--) {
                if (idList[i] < registrationList.size()) {
                    registrationManager.deleteRegistration(registrationList.get(idList[i]).getId());
                    registrationList.remove(idList[i]);
                }
            }
            clearTable();
            showActual();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        clearTable();
        MySwingWorker worker = new MySwingWorker();
        worker.execute();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearTable();

        RoomManager roomManager = HotelMain.getRoomManager();
        GuestManager guestManager = HotelMain.getGuestManager();
        switch (comboBox.getSelectedIndex()) {
            case 0:
                try {
                    Integer.parseInt(textField.getText());
                } catch (NumberFormatException ex) {
                    return;
                }
                try {
                    List<Room> rooms = roomManager.getRoomByNumber(Integer.parseInt(textField.getText()));
                    if (!rooms.isEmpty()) {
                        registrationList = registrationManager.getRegistrationForRoom(rooms.get(0));
                    } else {
                        registrationList.removeAll(registrationList);
                    }
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 1:
                try {
                    List<Guest> guests = guestManager.getGuestsByName(textField.getText());
                    registrationList.removeAll(registrationList);
                    for (int i = 0; i < guests.size(); i++) {
                        registrationList.addAll(registrationManager.getRegistrationForGuest(guests.get(i)));
                    }
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 2:
                try {
                    registrationList = registrationManager.getRegistrationsByStartDate(HotelUtils.convertStringToDate(textField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 3:
                try {
                    registrationList = registrationManager.getRegistrationsByEndDate(HotelUtils.convertStringToDate(textField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
            case 4:
                try {
                    registrationList = registrationManager.getRegistrationsByPrice(Double.parseDouble(textField.getText()));
                } catch (DatabaseException ex) {
                    //exception
                }
                break;
        }
        showActual();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (table.getSelectedRowCount() == 1 && table.getSelectedRow() < registrationList.size()) {
            HotelEditRegistration edit = new HotelEditRegistration();
            edit.setRegistrationId(registrationList.get(table.getSelectedRow()).getId());
            edit.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(HotelRegistrationManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HotelRegistrationManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HotelRegistrationManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HotelRegistrationManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HotelRegistrationManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables
}
