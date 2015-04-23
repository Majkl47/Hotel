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

public class Search extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	SearchResults sr;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
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
	public Search() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 696, 415);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblFindGuest = new JLabel("Find Guest");
		lblFindGuest.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFindGuest.setBounds(12, 156, 167, 46);
		panel.add(lblFindGuest);
		
		textField = new JTextField();
		textField.setBounds(12, 229, 284, 50);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(12, 181, 200, 50);
		panel.add(lblName);
		
		JLabel lblFindRoom = new JLabel("Find Room");
		lblFindRoom.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFindRoom.setBounds(12, 284, 167, 46);
		panel.add(lblFindRoom);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 356, 284, 46);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblRoomNumber = new JLabel("Room Number");
		lblRoomNumber.setBounds(12, 314, 200, 50);
		panel.add(lblRoomNumber);
		
		JLabel lblFindRegistration = new JLabel("Find Registration");
		lblFindRegistration.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFindRegistration.setBounds(12, 15, 167, 46);
		panel.add(lblFindRegistration);
		
		JLabel lblGuestName = new JLabel("Guest Name");
		lblGuestName.setBounds(12, 52, 200, 50);
		panel.add(lblGuestName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(12, 94, 284, 46);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(335, 94, 284, 46);
		panel.add(textField_3);
		
		JLabel lblRoomNumber_1 = new JLabel("Room Number");
		lblRoomNumber_1.setBounds(335, 52, 200, 50);
		panel.add(lblRoomNumber_1);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sr=new SearchResults();
				sr.setVisible(true);
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(500, 297, 145, 46);
		panel.add(btnNewButton);
	}

}
