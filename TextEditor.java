
/*
 * Author: Inchara Diwakar
 * Date: November 25th 2015
 * Class:TextEditor
 * This class contains the main function for a text file reader.The class contains an inner class Textreaderwindow 
 * which takes care of the set up of the reader. All the one time initialization happens in the constructor of the inner class.
 * The outer class's constructor creates an object of type inner class to access its methods. 
 * The action listeners are passed the instance of the inner class during their construction. The instance is passed around
 * and is initialized in the listeners constructors using "this". This way changes made to the same instance.  
 * Separate classes listen to the swing components. The serach listener performs the search operations, wordslistener counts the
 * words and so on.  
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextEditor {

	public static void main(String[] args) {
		/* creating an object of type TextFileReader */
		TextEditor swing_obj = new TextEditor();
		/* holds the application */
		swing_obj.mainloop();

	}

	/* object of type inner class */
	private Textreaderwindow window;

	/* CONSTRUCTOR: to create a new object of inner class */
	TextEditor() {
		window = new Textreaderwindow();

	}

	/* METHOD: to get blank file */
	public void mainloop() {
		window.blank_file();

	}

	/*
	 * Inner class which takes care of all the design and provides specific
	 * accessor methods to the listeners
	 */
	public class Textreaderwindow {
		public JFrame file_frame;
		public JButton open_button;
		public JButton close_button;
		public String file_name = null;
		public JTextArea textarea;
		public JLabel label;
		public String lastfileopened = null;
		public String lastfilepath = null;
		public String filename = null;
		public JMenu submenu_search;
		public JMenuItem word1;
		public JMenuItem word2;
		public JMenuItem word3;
		public JMenuItem word4;
		public JMenuItem word5;
		public JMenu submenu;
		public JMenuItem close;
		public JMenuItem exit;
		public JMenuItem count;
		public JMenuItem submenu_new;
		public JMenu submenu_old;
		public JMenuItem submenu_delete;
		public LinkedList<String> LL = new LinkedList<String>();
		public ArrayList<String> al = new ArrayList<String>();
		public TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();

		Textreaderwindow() {
			/* setting up the frame and panels with border layout */
			file_frame = new JFrame();
			label = new JLabel();
			file_frame.setLayout(new BorderLayout());

			/* Menu and menu items */
			JMenuBar menubar = new JMenuBar();
			JMenu file = new JMenu("File");
			menubar.add(file);
			JMenu tools = new JMenu("Tools");
			menubar.add(tools);
			JMenu help = new JMenu("Help");
			menubar.add(help);

			JMenuItem Open = new JMenuItem("Open");
			JMenuItem Save = new JMenuItem("Save");
			JMenuItem Saveas = new JMenuItem("Save as");
			close = new JMenuItem("Close");
			exit = new JMenuItem("Exit");
			JMenuItem about = new JMenuItem("About");

			/* adding sumenu for words */
			submenu = new JMenu("Words");
			count = new JMenuItem("Count");
			JMenuItem unique = new JMenuItem("Unique");
			submenu.add(count);
			submenu.addSeparator();
			submenu.add(unique);
			submenu.addSeparator();
			unique.addActionListener(new Wordslistener(this));

			/* adding submenu for search */
			submenu_search = new JMenu("Search");
			JMenuItem search_new = new JMenuItem("Search new..");
			submenu_search.add(search_new);

			/* adding submenu for bookmark */
			JMenu bookmark = new JMenu("Bookmark");
			submenu_new = new JMenuItem("New..");
			submenu_old = new JMenu("Old");
			submenu_delete = new JMenuItem("Delete");
			bookmark.add(submenu_new);
			bookmark.addSeparator();
			bookmark.add(submenu_old);
			bookmark.addSeparator();
			bookmark.add(submenu_delete);
			tools.add(bookmark);
			tools.addSeparator();
			tools.add(submenu);

			submenu.add(submenu_search);

			file.add(Open);
			file.addSeparator();
			file.add(Save);
			file.addSeparator();
			file.add(Saveas);
			file.addSeparator();
			file.add(close);
			file.addSeparator();
			file.add(exit);
			help.add(about);

			/* adding actionlisteners */
			Open.addActionListener(new Openlistener(this));
			Save.addActionListener(new Savelistener(this));
			about.addActionListener(new Helplistener(this));
			Saveas.addActionListener(new Saveaslistener(this));
			close.addActionListener(new Helplistener(this));
			exit.addActionListener(new Helplistener(this));
			count.addActionListener(new Wordslistener(this));
			search_new.addActionListener(new Searchlistener(this));
			submenu_new.addActionListener(new Bookmarklistener(this));
			submenu_old.addActionListener(new Bookmarklistener(this));
			submenu_delete.addActionListener(new Bookmarklistener(this));

			/* Setting up the file reader */
			textarea = new JTextArea(50, 100);
			JScrollPane scroll = new JScrollPane(textarea);
			textarea.setLineWrap(true);
			textarea.setWrapStyleWord(true);

			/*
			 * align the buttons, text area to the respective panels and then to
			 * the frame
			 */

			/* creating menu items for the last 5 words searched */
			word1 = new JMenuItem();
			word2 = new JMenuItem();
			word3 = new JMenuItem();
			word4 = new JMenuItem();
			word5 = new JMenuItem();
			submenu_search.add(word1);
			submenu_search.add(word2);
			submenu_search.add(word3);
			submenu_search.add(word4);
			submenu_search.add(word5);
			word1.setVisible(false);
			word2.setVisible(false);
			word3.setVisible(false);
			word4.setVisible(false);
			word5.setVisible(false);
			word1.addActionListener(new Searchlistener(this));
			word2.addActionListener(new Searchlistener(this));
			word3.addActionListener(new Searchlistener(this));
			word4.addActionListener(new Searchlistener(this));
			word5.addActionListener(new Searchlistener(this));

			file_frame.add(scroll, BorderLayout.CENTER);
			file_frame.add(label, BorderLayout.PAGE_END);
			file_frame.setTitle("Text Editor");
			file_frame.add(menubar, BorderLayout.BEFORE_FIRST_LINE);
			file_frame.pack();
		}

		/* method to open blank file */
		public void blank_file() {
			textarea.setText("");
			file_frame.setVisible(true);
		}

		/*
		 * METHOD: process linkedlist to maintain history of last 5 saved items
		 */

		public void add_menu() {
			for (int i = 0; i < LL.size(); i++) {
				settext(i);
			}
		}

		/* METHOD: set labels of the menu items accordingly */
		public void settext(int i) {
			int last = LL.size() - 1;
			switch (i) {
			case 0:
				word1.setText(LL.get(last));
				word1.setVisible(true);
				break;
			case 1:
				word2.setText(LL.get(last - 1));
				word2.setVisible(true);
				break;
			case 2:
				word3.setText(LL.get(last - 2));
				word3.setVisible(true);
				break;
			case 3:
				word4.setText(LL.get(last - 3));
				word4.setVisible(true);
				break;
			case 4:
				word5.setText(LL.get(last - 4));
				word5.setVisible(true);
				break;
			}

		}

		/* METHOD: to clean history-search history and boookmark history */
		public void clean_history() {
			word1.setText("");
			word2.setText("");
			word3.setText("");
			word4.setText("");
			word5.setText("");
			word1.setVisible(false);
			word2.setVisible(false);
			word3.setVisible(false);
			word4.setVisible(false);
			word5.setVisible(false);
			LL.clear();
			submenu_old.removeAll();
				tmap.clear();
				al.clear();
	
		}
		
		

	}

}
