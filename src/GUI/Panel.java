package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.EventListenerList;
import javax.swing.text.StyledEditorKit;

import Program.Program;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1297533983693579436L;
	private Program program;
	private EventListenerList listenerlist = new EventListenerList();
	public static File file;
	private JTextField findfield;
	private JComboBox<String> serormov;

	public Panel(File folder) {
		Dimension size = getPreferredSize();
		size.width = 400;
		setPreferredSize(size);
		program = new Program(folder);

		String[] combocontent = { "Movies & Episodes", "Movies", "Episodes" };
		serormov = new JComboBox<String>(combocontent);
		serormov.setSelectedIndex(0);
		serormov.setBackground(Color.WHITE);

		final JLabel amount = new JLabel();
		try {
			program.update();
			amount.setText(program.getTotal(serormov.getSelectedItem()
					.toString()));
		} catch (IOException e1) {
			System.out.println("bajs");
		}
		final JLabel dest = new JLabel(program.getDestination());

		findfield = new JTextField("What movie/episode are you looking for?");

		final JFileChooser openchooser = new JFileChooser(
				"C:\\Users\\M@sk\\Desktop");
		final JFileChooser savechooser = new JFileChooser(
				"C:\\Users\\M@sk\\Desktop");
		openchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		savechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		JButton directory = new JButton("Choose directory");
		JButton random = new JButton("Randomize "
				+ serormov.getSelectedItem().toString());
		JButton printall = new JButton("Save all "
				+ serormov.getSelectedItem().toString() + " to file");
		JButton printtextarea = new JButton("Save search result to file");
		JButton help = new JButton("Help");

		findfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					program.update();
					amount.setText(program.getTotal(serormov.getSelectedItem()
							.toString()));
					dest.setText(program.getDestination());
				} catch (IOException e1) {
				}
				PanelEvent films = new PanelEvent(this, program.find(
						findfield.getText(),
						serormov.getSelectedItem().toString()).toString());
				PanelEvent paths = new PanelEvent(this, program.where(
						findfield.getText()).toString());
				filmEvent(films);
				pathEvent(paths);
				findfield.setText("");

			}

		});

		findfield.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				findfield.setText("");
			}
		});

		serormov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amount.setText(program.getTotal(serormov.getSelectedItem()
						.toString()));
				dest.setText(program.getDestination());

			}

		});

		directory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = openchooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					program.setDestination(openchooser.getSelectedFile()
							.getAbsolutePath());
					try {
						program.update();
					} catch (IOException e1) {
					}
					amount.setText(program.getTotal(serormov.getSelectedItem()
							.toString()));
					dest.setText(program.getDestination());

				}
			}
		});
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = program.random(serormov.getSelectedItem()
						.toString());
				PanelEvent film = new PanelEvent(this, s[0]);
				PanelEvent path = new PanelEvent(this, s[1]);
				filmEvent(film);
				pathEvent(path);
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Help");
				JEditorPane ta = new JTextPane();
				JScrollPane scroll = new JScrollPane(ta);
				ta.setEditorKit(new StyledEditorKit());
				ta.setEditable(false);
				ta.setText("This program is created in purpose for simple movie and Tv-shows episodes searching.\n"
						+ "It lets the user scan through specified directories (e.g. external hard drives) for movies and Tv-shows episodes , displaying the result and opening a selected movie or Tv-show episode in the default application associated with the file.\n"
						+ "A user can also search for a specific movie or Tv-show episode.\n\n"
						+ "Layout description:\n"
						+ "1. Randomize:\n"
						+ "This button is used if you are uncertain of which movie or Tv-show episode you want to watch/find.\n"
						+ "The randomized movie/episode will appear in the Result-field.\n\n"
						+ "2. The search field:\n"
						+ "This field is used to search for a/several specific movie(s)/episode(s) in the choosen directory.\n"
						+ "The search result will contain every movie/episode that contains the entered name.\n"
						+ "To display all movies/episodes in the choosen directory, simply press the ENTER-button.\n"
						+ "If the search was unsuccessful or the directory doesn't contain the searched movie(s)/episode(s), an appropriate messege will be displayed in the Result-field.\n"
						+ "The search result will appear in the Result-field.\n\n"
						+ "4. Choose Movies & Episodes or one of them separately:\n"
						+ "In this dropdown-menu a user can choose to search for Movies and Episodes or just Episodes respectively Movies"
						+ "3. Choose directory:\n"
						+ "This button allows the user to specify which directory the program should search for movies/episodes.\n"
						+ "To specify a directory, navigate in the directory chooser, mark/highlight the wanted directory and click Open.\n\n"
						+ "4. Save search result to file:\n"
						+ "This button enables the user save a .txt-file containing only the search result displayed in the \"Result\"-field.\n"
						+ "To save a specific search result, navigate to where the file should be saved, name the file and click Save.\n\n"
						+ "5. Save all movies/episodes to file:\n"
						+ "This button will always save all the movies/episodes in the specefied directory in a .txt-file.\n"
						+ "To save all the movies/episodes in the specefied directory, do as in 4.\n\n"
						+ "6. Amount of files-label:\n"
						+ "This label will display the amount of movies/episodes contained in the specified directory.\n\n"
						+ "7. Directory label:\n"
						+ "This label displays the current directory the program is using.\n"
						+ "A user can acces the current folder by double-clicking this label.\n\n"
						+ "8. The Result-field:\n"
						+ "This field will display the result of a search, if the directory doesn't contain the searched movie/s an appropriate messege will be displayed.\n"
						+ "A user can open a file by double-clicking one movie-name/episode-name in this field. The opened file will be the file corresponding to the name that was selected/double-clicked in this field and will be opened in the default application associated with the file. \n"
						+ "By right-clicking on a movie-name/episode-name a user can access the folder which contains the selected/right-clicked movie-name/episode-name \n\n"
						+ "This program is created from scratch by Max Åberg.");
				frame.getContentPane().add(scroll, BorderLayout.CENTER);
				frame.setVisible(true);
				ta.setSelectionStart(0);
				ta.setSelectionEnd(0);
				frame.setSize(600, 600);
				frame.setLocationRelativeTo(null);

			}
		});

		printall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = savechooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					file = savechooser.getSelectedFile();
					try {
						program.print(file.getAbsolutePath() + ".txt", serormov
								.getSelectedItem().toString());
					} catch (IOException e1) {
					}

				}
			}
		});
		printtextarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = savechooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					file = savechooser.getSelectedFile();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(
								file.getAbsolutePath() + ".txt"));
						Frame.getTextArea().write(bw);
						bw.write(program.getTotal(serormov.getSelectedItem()
								.toString()));

						bw.close();
					} catch (IOException e1) {
					}

				}
			}
		});
		dest.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					try {
						Desktop.getDesktop().open(new File(dest.getText()));
					} catch (IOException e1) {
					}
				}
			}
		});
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.weighty = 0.01;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		add(random, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		add(serormov, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		add(findfield, gc);

		gc.gridx = 0;
		gc.gridy = 3;
		add(directory, gc);

		gc.gridx = 0;
		gc.gridy = 4;
		add(printtextarea, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		add(printall, gc);

		gc.gridx = 0;
		gc.gridy = 6;
		add(help, gc);

		gc.gridx = 0;
		gc.gridy = 7;
		add(amount, gc);

		gc.gridx = 0;
		gc.gridy = 8;
		add(dest, gc);
		program.find(findfield.getText(), serormov.getSelectedItem().toString());
	}

	public void filmEvent(PanelEvent e) {
		Object[] listeners = listenerlist.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == Listener.class) {
				((Listener) listeners[i + 1]).filmEvent(e);
			}
		}
	}

	public void pathEvent(PanelEvent e) {
		Object[] listeners = listenerlist.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == Listener.class) {
				((Listener) listeners[i + 1]).pathEvent(e);
			}
		}
	}

	public void addListener(Listener listener) {
		listenerlist.add(Listener.class, listener);
	}

	public void removeListener(Listener listener) {
		listenerlist.remove(Listener.class, listener);
	}

}
