package cz.muni.fi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.JButton;

public class NewRegistration extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewRegistration frame = new NewRegistration();
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
	public NewRegistration() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 802, 439);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewRegistration = new JLabel("New Registration");
		lblNewRegistration.setBounds(325, 5, 152, 25);
		lblNewRegistration.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewRegistration);
		
		JLabel lblGuestInformation = new JLabel("Guest Information");
		lblGuestInformation.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblGuestInformation.setBounds(10, 48, 200, 50);
		panel.add(lblGuestInformation);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(20, 83, 200, 50);
		panel.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(20, 122, 163, 32);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(206, 122, 163, 32);
		panel.add(textField_1);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(206, 83, 200, 50);
		panel.add(lblAddress);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(389, 122, 163, 32);
		panel.add(textField_2);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(389, 83, 200, 50);
		panel.add(lblPhone);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(590, 122, 163, 32);
		panel.add(textField_3);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(590, 83, 200, 50);
		panel.add(lblBirthday);
		
		JLabel lblRoomInformation = new JLabel("Room Information");
		lblRoomInformation.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblRoomInformation.setBounds(20, 167, 200, 50);
		panel.add(lblRoomInformation);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(20, 240, 163, 32);
		panel.add(textField_4);
		
		JLabel lblFloor = new JLabel("Floor");
		lblFloor.setBounds(20, 201, 200, 50);
		panel.add(lblFloor);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(206, 240, 163, 32);
		panel.add(textField_5);
		
		JLabel lblNumber = new JLabel("Number");
		lblNumber.setBounds(206, 201, 200, 50);
		panel.add(lblNumber);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(389, 240, 163, 32);
		panel.add(textField_6);
		
		JLabel lblCapacity = new JLabel("Capacity");
		lblCapacity.setBounds(389, 201, 200, 50);
		panel.add(lblCapacity);
		
		JLabel lblRegistrationInformation = new JLabel("Registration Information");
		lblRegistrationInformation.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblRegistrationInformation.setBounds(20, 290, 200, 50);
		panel.add(lblRegistrationInformation);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(20, 370, 163, 32);
		panel.add(textField_7);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(20, 331, 200, 50);
		panel.add(lblStartDate);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(206, 370, 163, 32);
		panel.add(textField_8);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(206, 331, 200, 50);
		panel.add(lblEndDate);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(389, 370, 163, 32);
		panel.add(textField_9);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(389, 331, 200, 50);
		panel.add(lblPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnSave.setBounds(646, 347, 144, 55);
		panel.add(btnSave);
		
	}
}
