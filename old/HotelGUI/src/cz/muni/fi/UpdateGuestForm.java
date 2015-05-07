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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateGuestForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateGuestForm frame = new UpdateGuestForm();
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
	public UpdateGuestForm() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateGuestInfo = new JLabel("Update Guest Info");
		lblUpdateGuestInfo.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblUpdateGuestInfo.setBounds(218, 13, 200, 50);
		contentPane.add(lblUpdateGuestInfo);
		
		JLabel lblGuestName = new JLabel("Guest name");
		lblGuestName.setBounds(10, 59, 200, 50);
		contentPane.add(lblGuestName);
		
		textField = new JTextField();
		textField.setBounds(10, 108, 160, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 190, 160, 33);
		contentPane.add(textField_1);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(10, 141, 200, 50);
		contentPane.add(lblPhone);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(316, 108, 160, 33);
		contentPane.add(textField_2);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(316, 59, 200, 50);
		contentPane.add(lblBirthday);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(316, 190, 160, 33);
		contentPane.add(textField_3);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(316, 141, 200, 50);
		contentPane.add(lblAddress);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(441, 280, 97, 25);
		contentPane.add(btnNewButton);
	}

}
