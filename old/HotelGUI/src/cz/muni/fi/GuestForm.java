package cz.muni.fi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuestForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTable table_1;
	UpdateGuestForm updateF;
	private JTextField textField_1;
	private JTable table_2;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuestForm frame = new GuestForm();
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
	public GuestForm() {
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Guest Information");
		label.setBounds(268, 13, 200, 50);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(label);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 102, 676, 288);
		contentPane.add(tabbedPane);
		 JPanel panel = new JPanel();
		 tabbedPane.addTab("Guest", panel);
		 panel.setLayout(null);
		 
		 table = new JTable();
		 table.setBounds(12, 90, 647, 155);
		 panel.add(table);
		 
		 JLabel label_2 = new JLabel("Guest name");
		 label_2.setBounds(12, 0, 200, 50);
		 panel.add(label_2);
		 
		 textField_2 = new JTextField();
		 textField_2.setColumns(10);
		 textField_2.setBounds(12, 43, 157, 30);
		 panel.add(textField_2);
		 
		 JButton button_1 = new JButton("Find guest");
		 button_1.setBounds(271, 46, 97, 25);
		 panel.add(button_1);
		 JPanel panel_1 = new JPanel();
		 tabbedPane.addTab("Update", panel_1);
		 panel_1.setLayout(null);
		 
		 JLabel lblGuestName = new JLabel("Guest name");
		 lblGuestName.setBounds(12, 0, 200, 50);
		 panel_1.add(lblGuestName);
		 
		 textField = new JTextField();
		 textField.setBounds(12, 43, 157, 30);
		 panel_1.add(textField);
		 textField.setColumns(10);
		 
		 table_1 = new JTable();
		 table_1.setBounds(12, 98, 647, 91);
		 panel_1.add(table_1);
		 
		 JButton btnNewButton = new JButton("Find guest");
		 btnNewButton.setBounds(271, 46, 97, 25);
		 panel_1.add(btnNewButton);
		 
		 JButton btnNewButton_1 = new JButton("Update");
		 btnNewButton_1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateF= new UpdateGuestForm();
		 		updateF.setVisible(true);

		 	}
		 });
		 btnNewButton_1.setBounds(543, 209, 97, 25);
		 panel_1.add(btnNewButton_1);
		 JPanel panel_2 = new JPanel();
		 tabbedPane.addTab("Delete ", panel_2);
		 panel_2.setLayout(null);
		 
		 JLabel label_1 = new JLabel("Guest name");
		 label_1.setBounds(12, 13, 200, 50);
		 panel_2.add(label_1);
		 
		 textField_1 = new JTextField();
		 textField_1.setColumns(10);
		 textField_1.setBounds(12, 56, 157, 30);
		 panel_2.add(textField_1);
		 
		 JButton button = new JButton("Find guest");
		 button.setBounds(271, 59, 97, 25);
		 panel_2.add(button);
		 
		 table_2 = new JTable();
		 table_2.setBounds(12, 109, 647, 91);
		 panel_2.add(table_2);
		 
		 JButton btnDelete = new JButton("Delete");
		 btnDelete.setBounds(543, 220, 97, 25);
		 panel_2.add(btnDelete);
		
	}
}
