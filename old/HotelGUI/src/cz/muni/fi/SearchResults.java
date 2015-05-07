package cz.muni.fi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;

public class SearchResults extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchResults frame = new SearchResults();
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
	public SearchResults() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 694, 388);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchResults = new JLabel("Search Results");
		lblSearchResults.setBounds(273, 5, 130, 25);
		lblSearchResults.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblSearchResults);
		
		table = new JTable();
		table.setBounds(12, 56, 670, 319);
		panel.add(table);
	}

}
