/*
 * Author: Inchara Diwakar
 * Date: November 25th
 * Class: Openlistener
 * Description: This class implements Actionlistener interface and performs the file open operation
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Openlistener implements ActionListener {

	private TextEditor.Textreaderwindow text;

	Openlistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	public void actionPerformed(ActionEvent e) {
		/* intialize variables */
		File file = null;
		BufferedReader reader = null;
		JFileChooser fileExplorer = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileExplorer.setDialogTitle("Open");
		fileExplorer.setFileFilter(filter);

		/* open dialog for the user to choose file */
		int returnval = fileExplorer.showOpenDialog(fileExplorer);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			file = fileExplorer.getSelectedFile();
		}

		else {
			return;
		}
		text.filename = fileExplorer.getSelectedFile().getName();

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {

			JOptionPane.showMessageDialog(text.file_frame, "File not found");
			return;
		}

		/* read text from the file */
		String line;
		text.textarea.setText("");
		try {
			while ((line = reader.readLine()) != null) {
				text.textarea.append(line);
				text.textarea.append("\n");
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		/* clear history only if a new file is opened */

		if (!text.filename.equals(text.lastfileopened)) {
			text.clean_history();
		}

		/* store last file opened information */
		text.lastfileopened = text.filename;
		text.lastfilepath = file.getAbsolutePath();
		text.label.setText((text.filename.contains("txt")) ? text.filename : text.filename + ".txt");

	}

}
