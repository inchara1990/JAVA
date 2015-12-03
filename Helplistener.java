/*
 * Author: Inchara Diwakar
 * Date: November 17th
 * Class: helplistener
 * Description: This class implements Actionlistener interface and contains notepad information. It also hanf=dles exit and close
 * functionalities.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Helplistener implements ActionListener {
	private TextEditor.Textreaderwindow text;

	Helplistener(TextEditor.Textreaderwindow text) {
		this.text = text;
		
	}

	public void actionPerformed(ActionEvent e) {
		
		/*close file operation*/
		if( e.getSource().equals(text.close))
		{
			text.blank_file();
			text.label.setText(null);
			text.clean_history();
			text.lastfileopened = null;
		}
		/*exit operation*/
		else if(e.getSource().equals(text.exit))
		{
			System.exit(0);
		}
		else
		{	

		JOptionPane.showMessageDialog(null,"Text Editor-Version 1 : created by a CMU student"
				+ " as a part of the JAVA project." + " \n \n" + " You can't start the next chapter of your life "
				+ " if you keep re-reading the last one");
		}

	}

}
