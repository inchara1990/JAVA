/*
 * Author: Inchara Diwakar
 * Date: December 2nd
 * Class: Wordslistener
 * Description: This class implements count of words and unique words on a file which is open
 * in texteditor.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Wordslistener implements ActionListener {
	private TextEditor.Textreaderwindow text;

	Wordslistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	public void actionPerformed(ActionEvent e) {

		/* declare count variables */
		String for_count = text.textarea.getText();
		int u_count;
		ArrayList<String> uniquewords = new ArrayList<String>();
		String trim = for_count.trim();

		/* split words based on the below list of delimiters */
		String[] splitwords = trim.split("[!,~\"?:\\s]+");

		/* String[] splitwords = trim.split("\\s+"); */

		for (int i = 0; i < splitwords.length; i++) {
			splitwords[i] = splitwords[i].trim();

			if (!uniquewords.contains(splitwords[i])) {

				uniquewords.add(splitwords[i]);
			}
		}

		u_count = uniquewords.size();

		/* set label(word count or unique word count) accordingly. */

		if (e.getSource().equals(text.count)) {
			if (trim.isEmpty())
				text.label.setText("Word Count: " + 0);
			else
				text.label.setText("Word Count: " + splitwords.length);
		} else {
			text.label.setText("Unique Words: " + u_count);
		}

	}

}
