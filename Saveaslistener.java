
/*
 * Author: Inchara Diwakar
 * Date: November 25th
 * Class: Openlistener
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

public class Saveaslistener implements ActionListener {
    /*method parameters*/
	private TextEditor.Textreaderwindow text;
	File file = null;
	BufferedWriter fw = null;
	BufferedReader reader = null;
	

	Saveaslistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	public void actionPerformed(ActionEvent e) {

		JFileChooser filesaver = new JFileChooser();
		/*filesaver.setCurrentDirectory(new File(TextEditor.DEFAULT_PATH));*/
		filesaver.setDialogTitle("Save As");
        
		/* dialog box for saving the file*/
		int returnval = filesaver.showSaveDialog(null);
        
		/* write file*/
		if (returnval == JFileChooser.APPROVE_OPTION) {
			file = filesaver.getSelectedFile();
			write_file();
    
		} else if (returnval == 999) {
			write_file();
		} else {
			return;
		}

	}
    
	/* write file method*/
	void write_file() {
		/* read the text from textarea and write it to the new fle*/
		text.filename = file.getName();
		String string = text.textarea.getText();
		StringReader stringReader = new StringReader(string);
		try {
			if  (text.filename.contains("txt")) {
				fw = new BufferedWriter(new FileWriter(file));
			} else {
				fw = new BufferedWriter(new FileWriter(file + ".txt"));
			}
			reader = new BufferedReader(stringReader);
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				fw.write(line);
				fw.write("\n");
			}

			reader.close();
			fw.close();
            /* store last file opened information*/
			text.lastfileopened=text.filename;
			text.label.setText((text.filename.contains("txt")? text.filename : text.filename + ".txt") + " succesfully saved");
			text.lastfilepath = file.getAbsolutePath();

		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}
}
