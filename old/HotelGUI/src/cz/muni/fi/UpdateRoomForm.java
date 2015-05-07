package cz.muni.fi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class UpdateRoomForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateRoomForm frame = new UpdateRoomForm();
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
	public UpdateRoomForm() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 659, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateRoomInformation = new JLabel("Update Room Information");
		lblUpdateRoomInformation.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblUpdateRoomInformation.setBounds(259, 22, 279, 50);
		contentPane.add(lblUpdateRoomInformation);
		
		JLabel lblRoomNumber = new JLabel("Room number");
		lblRoomNumber.setBounds(12, 90, 200, 50);
		contentPane.add(lblRoomNumber);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(12, 133, 157, 30);
		contentPane.add(textField);
		
		JLabel lblRoomFloor = new JLabel("Floor");
		lblRoomFloor.setBounds(12, 186, 200, 50);
		contentPane.add(lblRoomFloor);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(12, 229, 157, 30);
		contentPane.add(textField_1);
		
		JLabel lblCapacity = new JLabel("Capacity");
		lblCapacity.setBounds(269, 90, 200, 50);
		contentPane.add(lblCapacity);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(269, 133, 157, 30);
		contentPane.add(textField_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(294, 232, 97, 25);
		contentPane.add(btnUpdate);
	}

}
