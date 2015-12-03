/*
 * Author: Inchara Diwakar
 * Date: November 25th 2015
 * Class:TextEditor
 * This class implements the search functionality of the text editor. It searches for the user inputed string in the 
 * document. It also stores the history of the most recently searched strings.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

public class Searchlistener implements ActionListener {
    
	/* ArrayList to hold search elements*/
	ArrayList<Integer> index = new ArrayList<Integer>();
	
	private TextEditor.Textreaderwindow text;
	String[] str = null;
	String s_search = null;
	int ind = 0;
	private final int size = 5;
	static int cn;

	Searchlistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	public void actionPerformed(ActionEvent e) {
/* To enable search of the last five saved items*/
		if (e.getSource().equals(text.word1)) {
			s_search = text.word1.getText();
		} else if (e.getSource().equals(text.word2)) {
			s_search = text.word2.getText();
		} else if (e.getSource().equals(text.word3)) {
			s_search = text.word3.getText();
		} else if (e.getSource().equals(text.word4)) {
			s_search = text.word4.getText();
		} else if (e.getSource().equals(text.word5)) {
			s_search = text.word5.getText();
		} else

		s_search = JOptionPane.showInputDialog("search");
		if( s_search==null)
		{
			return;
		}
		

		/* handling the search element*/
	    handle_search(s_search);

	    /* highlighting the search occurrences in the file*/
		highlight();
		
        /* maintaining history*/
		maintain_history();

		index.clear();
	}

	/* METHOD- Handle search functionality */

	void handle_search(String s_search) {

		if ((!s_search.equals(null)) && (!s_search.equals(""))) {
			int len = s_search.length();

			if ((s_search.length() > 0)) {
				/* read the text area into a document */
				Document d = text.textarea.getDocument();
				String doc_bit = null;
				for (int i = 0; i < d.getLength();) {
					/* get the first bit from the document */
					try {
						doc_bit = d.getText(i, 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}

					for (int j = 0; j < len;) {
						/*
						 * get the first bit of the search string. If the bit
						 * matches, match the next bit of the document and the
						 * search string. else break from the loop.If the entire
						 * search string matches add the index to the ArrayList
						 */
						String search_bit = s_search.substring(j, j + 1);
						if (!doc_bit.equals(search_bit)) {
							i++;
							break;
						} else {
							j++;
							try {
								doc_bit = d.getText(++i, 1);
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							}

							if (j == s_search.length()) {
								index.add(i);
							}
						}
					}

				}
			}
		}

	}

	/* METHOD : highlight search patterns */

	void highlight() {

		if ((!s_search.equals(null)) && (!s_search.equals(""))) {
			/* Highlighter to highlight matched strings in the doc */

			Highlighter h = text.textarea.getHighlighter();
			h.removeAllHighlights();

			/* set label of the reader with the findings */
			text.label.setText(
					 s_search + ":" + index.size() + " occurences ");

			for (int i = 0; i < index.size(); i++) {
				/* highlight the text */
				try {
					h.addHighlight(index.get(i) - s_search.length(), index.get(i), DefaultHighlighter.DefaultPainter);
				} catch (BadLocationException e1) {
					System.out.println("bad location!");
				}

			}
		}

	}

	/* METHOD : Maintain history of search words */

	void maintain_history() {
		/* adding the search words to linked list for history*/
		
		while (index.size() != 0 && !text.LL.contains(s_search)) {
			if (text.LL.size() == size) {
				text.LL.removeLast();
			}
        
			text.LL.addFirst(s_search);
			text.add_menu();
		}
	}

}
