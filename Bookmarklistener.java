/*
 * Author: Inchara Diwakar
 * Date: December 2nd
 * Class: Bookmarklistener
 * Description: This class implements Actionlistener interface and performs the 
 * bookmark operation of the text editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.Iterator;

public class Bookmarklistener implements ActionListener {

	public TextEditor.Textreaderwindow text;
	String menu_text = null;
	
	JMenuItem jm;

	Bookmarklistener(TextEditor.Textreaderwindow text) {
		this.text = text;
	}

	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();

		/*
		 * using action command to check for book marks added dynamically,
		 * because it lets us identify the action without specifying the object
		 * name
		 */
		switch (action) {

		/* get the name key from the treemap and highlight based on index */

		case "bookie":

			JMenuItem jmi = (JMenuItem) e.getSource();
			String keyn = jmi.getName();
			if(text.tmap.isEmpty())
			{
				return;
			}
			int cursor = text.tmap.get(keyn);
			text.textarea.setCaretPosition(cursor);
			Highlighter h1 = text.textarea.getHighlighter();
			h1.removeAllHighlights();
			try {
				h1.addHighlight(cursor, cursor + 1, DefaultHighlighter.DefaultPainter);
			} catch (BadLocationException e1) {
				System.out.println("bad location!");
			}

			break;

		default:
			break;

		}
		/* listener implementation for new book mark, generates new menu */

		if (e.getSource().equals(text.submenu_new)) {
			String tag = JOptionPane.showInputDialog("Enter tag name");
			if(tag == null || tag.length()<0)
			{
				return;
			}

			int current_pos = text.textarea.getCaretPosition();
			text.tmap.put(tag, current_pos);
			menugenerator(text.tmap);
		}

		/*
		 * listener implementation for delete book mark, deletes the book mark
		 * the pop up implementation of bookmark is in class delete. This class
		 * passes an instance to the delete class.
		 */

		else if (e.getSource().equals(text.submenu_delete)) {
			Set e_set = text.tmap.entrySet();
			Iterator it = e_set.iterator();
			text.al.clear();
			while (it.hasNext()) {
				Map.Entry mp = (Map.Entry) it.next();
				text.al.add((String) mp.getKey());
			}
			Delete obj = new Delete(this);
		}
	}

	/*
	 * Method which generates the current bookmarks. the method deletes all the
	 * tags present and looks at the current values in treemap and generates the
	 * bookmark names
	 */
	public void menugenerator(TreeMap tmap) {
		text.submenu_old.removeAll();
		Set e_set = tmap.entrySet();
		Iterator it = e_set.iterator();

		while (it.hasNext()) {
			Map.Entry mp = (Map.Entry) it.next();
			jm = new JMenuItem((String) mp.getKey());
			jm.setName((String) mp.getKey());
			jm.setActionCommand("bookie");
			text.submenu_old.add(jm);
			text.submenu_old.addSeparator();
			jm.addActionListener(this);

		}
	}
	

	
}
