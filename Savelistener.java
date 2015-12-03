
/*
 * Author: Inchara Diwakar
 * Date: November 17th
 * Class: Savelistener
 * Description: This class implements Actionlistener interface and performs the file save operation
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.JFileChooser;

public class Savelistener implements ActionListener {

	private TextEditor.Textreaderwindow text;

	Savelistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	/* method parameters */
	File file = null;
	BufferedWriter fw = null;
	BufferedReader reader = null;

	JFileChooser filesaver = new JFileChooser();
	int check = 0;
	int returnval = 999;

	public void actionPerformed(ActionEvent e) {
		/* setting default path */
		/* filesaver.setCurrentDirectory(new File(TextEditor.DEFAULT_PATH)); */
		filesaver.setDialogTitle("Save");

		/* get file method to get the file to written onto */
		get_file();

		if (returnval == JFileChooser.APPROVE_OPTION) {
			file = filesaver.getSelectedFile();
			text.filename = file.getName();
			write_file();

		} else if (returnval == 999) {
			write_file();
		} else {
			return;
		}

	}

	int get_file() {
		/*
		 * if the last file name is null, or it equals the current filename
		 * label show save dialog
		 */
		if ((text.lastfileopened == null || text.lastfileopened.isEmpty()) || (text.file_name == text.lastfileopened)) {
			returnval = filesaver.showSaveDialog(null);
		}
		/* else treat the last file as the file to be edited */
		else {
			file = new File(text.lastfilepath);
			check = 1;
			text.filename = text.lastfileopened;
		}

		return returnval;
	}

	void write_file() {
		/* get text from textarea */
		String string = text.textarea.getText();
		StringReader stringReader = new StringReader(string);
		try {
			/* append .txt if it is a new file */
			if (text.filename.contains("txt")) {
				fw = new BufferedWriter(new FileWriter(file));
			} else {
				fw = new BufferedWriter(new FileWriter(file + ".txt"));
			}

			/* read each line from textarea */
			reader = new BufferedReader(stringReader);
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				fw.write(line);
				fw.write("\n");
			}

			/* close reader, writer and set labels */
			reader.close();
			fw.close();

			/* set label accordingly */
			text.label.setText(
					(text.filename.contains("txt") ? text.filename : text.filename + ".txt") + " succesfully saved");

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
