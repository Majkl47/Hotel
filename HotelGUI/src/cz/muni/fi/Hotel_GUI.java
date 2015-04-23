package cz.muni.fi;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Hotel_GUI {

	private JFrame frame;
	private JTable table;
	private JTable table_ShowAll;
	NewRegistration nr;
	Search s;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hotel_GUI window = new Hotel_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Hotel_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 835, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 793, 542);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewRegistration = new JButton("New Registration");
		btnNewRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nr=new NewRegistration();
				nr.setVisible(true);
			}
		});
		btnNewRegistration.setBounds(23, 72, 137, 51);
		panel.add(btnNewRegistration);
		
		JLabel lblRegistrationSystem = new JLabel("Registration System");
		lblRegistrationSystem.setBounds(327, 5, 241, 29);
		lblRegistrationSystem.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		panel.add(lblRegistrationSystem);
		
		JButton btnSeach = new JButton("Seach");
		btnSeach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s= new Search();
				s.setVisible(true);
			}
		});
		btnSeach.setBounds(217, 72, 137, 51);
		panel.add(btnSeach);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(418, 72, 137, 51);
		panel.add(btnDelete);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.setBounds(615, 72, 137, 51);
		panel.add(btnShowAll);
		
		table_ShowAll = new JTable();
		table_ShowAll.setBounds(23, 223, 729, 242);
		panel.add(table_ShowAll);
		
		
	}

}
