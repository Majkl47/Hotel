package cz.muni.fi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JPanel panel_1;
	private JTextField textField_1;
	private JTable table_1;
	private JPanel panel_2;
	private JTextField textField_2;
	private JTable table_2;
	UpdateRoomForm updateR;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomForm frame = new RoomForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RoomForm() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRoomInformation = new JLabel("Room Information");
		lblRoomInformation.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblRoomInformation.setBounds(277, 13, 200, 50);
		contentPane.add(lblRoomInformation);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 88, 704, 276);
		contentPane.add(tabbedPane);
		
		 JPanel panel = new JPanel();
		 tabbedPane.addTab("Create Room", panel);
		 panel.setLayout(null);
		 
		 JLabel label = new JLabel("Room number");
		 label.setBounds(12, 19, 200, 50);
		 panel.add(label);
		 
		 textField_3 = new JTextField();
		 textField_3.setColumns(10);
		 textField_3.setBounds(12, 62, 157, 30);
		 panel.add(textField_3);
		 
		 JLabel label_1 = new JLabel("Floor");
		 label_1.setBounds(12, 115, 200, 50);
		 panel.add(label_1);
		 
		 textField_4 = new JTextField();
		 textField_4.setColumns(10);
		 textField_4.setBounds(12, 158, 157, 30);
		 panel.add(textField_4);
		 
		 textField_5 = new JTextField();
		 textField_5.setColumns(10);
		 textField_5.setBounds(269, 62, 157, 30);
		 panel.add(textField_5);
		 
		 JLabel label_2 = new JLabel("Capacity");
		 label_2.setBounds(269, 19, 200, 50);
		 panel.add(label_2);
		 
		 JButton btnSave = new JButton("Save");
		 btnSave.setBounds(570, 161, 97, 25);
		 panel.add(btnSave);
		 
		 JLabel label_3 = new JLabel("Update Room Information");
		 label_3.setFont(new Font("Tahoma", Font.PLAIN, 19));
		 label_3.setBounds(259, -44, 279, 50);
		 panel.add(label_3);
		 
		 JPanel panel1 = new JPanel();
		 tabbedPane.addTab("Room", panel1);
		 panel1.setLayout(null);
		 
		 textField = new JTextField();
		 textField.setColumns(10);
		 textField.setBounds(10, 43, 157, 30);
		 panel1.add(textField);
		 
		 JLabel lblRoomNumber = new JLabel("Room number");
		 lblRoomNumber.setBounds(10, 0, 200, 50);
		 panel1.add(lblRoomNumber);
		 
		 JButton btnFindRoom = new JButton("Find room");
		 btnFindRoom.setBounds(269, 46, 97, 25);
		 panel1.add(btnFindRoom);
		 
		 table = new JTable();
		 table.setBounds(10, 90, 647, 155);
		 panel1.add(table);
		 
		
		 panel_1 = new JPanel();
		 tabbedPane.addTab("Update", panel_1);
		 panel_1.setLayout(null);
		 
		 JLabel lblRoomNumber_1 = new JLabel("Room number");
		 lblRoomNumber_1.setBounds(10, 10, 200, 50);
		 panel_1.add(lblRoomNumber_1);
		 
		 textField_1 = new JTextField();
		 textField_1.setColumns(10);
		 textField_1.setBounds(10, 53, 157, 30);
		 panel_1.add(textField_1);
		 
		 JButton btnFindRoom_1 = new JButton("Find room");
		 btnFindRoom_1.setBounds(269, 56, 97, 25);
		 panel_1.add(btnFindRoom_1);
		 
		 table_1 = new JTable();
		 table_1.setBounds(10, 108, 647, 91);
		 panel_1.add(table_1);
		 
		 JButton btnUpdateRoom = new JButton("Update");
		 btnUpdateRoom.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateR=new UpdateRoomForm();
		 		updateR.setVisible(true);
		 	}
		 });
		 btnUpdateRoom.setBounds(560, 212, 97, 25);
		 panel_1.add(btnUpdateRoom);
		 
		 
		 panel_2 = new JPanel();
		 tabbedPane.addTab("Delete", panel_2);
		 panel_2.setLayout(null);
		 
		 JLabel lblRoomNumber_2 = new JLabel("Room number");
		 lblRoomNumber_2.setBounds(10, 10, 200, 50);
		 panel_2.add(lblRoomNumber_2);
		 
		 textField_2 = new JTextField();
		 textField_2.setColumns(10);
		 textField_2.setBounds(10, 53, 157, 30);
		 panel_2.add(textField_2);
		 
		 JButton btnFindRoom_2 = new JButton("Find room");
		 btnFindRoom_2.setBounds(269, 56, 97, 25);
		 panel_2.add(btnFindRoom_2);
		 
		 table_2 = new JTable();
		 table_2.setBounds(10, 106, 647, 91);
		 panel_2.add(table_2);
		 
		 JButton button_1 = new JButton("Delete");
		 button_1.setBounds(560, 208, 97, 25);
		 panel_2.add(button_1);
	}

}
