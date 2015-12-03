
/*
 * Author: Inchara Diwakar
 * Date: November 25th
 * Class: Delete
 * Description: This class is a supplement to the Bookmarklistener class. 
 * When the user wants to delete the bookmark, this class sets up the pop up and also
 * handles the delete. 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Delete implements ActionListener {
	JButton delete_but;
	JButton cancel_but;
	JComboBox<String> jc;
	Bookmarklistener del;
	JFrame jfr;

	/* pop up being set up in the constructor */
	Delete(Bookmarklistener del) {
		this.del = del;
		String[] string_combo = new String[del.text.al.size()];
		string_combo = del.text.al.toArray(string_combo);

		jc = new JComboBox<String>(string_combo);
		jfr = new JFrame();
		jfr.setLayout(new BorderLayout());
		jfr.setPreferredSize(new Dimension(300, 200));
		JPanel south_panel = new JPanel();
		JPanel center_panel = new JPanel();
		JLabel label = new JLabel("Select bookmark to delete");
		delete_but = new JButton("Delete");
		cancel_but = new JButton("Cancel");
		south_panel.add(delete_but);
		south_panel.add(cancel_but);
		center_panel.add(label);
		center_panel.add(jc);
		jfr.add(center_panel, BorderLayout.CENTER);
		jfr.add(south_panel, BorderLayout.SOUTH);

		jfr.pack();
		jfr.setVisible(true);
		delete_but.addActionListener(this);
		cancel_but.addActionListener(this);

	}

	/* handle delete */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(delete_but)) {
			String value = jc.getSelectedItem().toString();
			del.text.tmap.remove(value);
			del.text.al.remove(del.text.al.indexOf(value));
			jfr.setVisible(false);
			del.menugenerator(del.text.tmap);
		}

		if (e.getSource().equals(cancel_but)) {

			jfr.setVisible(false);
		}
	}

}
