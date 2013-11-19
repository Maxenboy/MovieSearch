package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

import Program.Program;

public class Frame extends JFrame {
	private static final long serialVersionUID = -4834733242800540564L;
	private Panel panel;
	private static JTextArea textarea;
	private static JScrollPane scroll;
	private Program program;

	public Frame(String title) {
		super(title);
		File folder = null;
		JFileChooser openchooser = new JFileChooser("C:\\Users\\M@sk\\Desktop");
		openchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = openchooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			folder = new File(openchooser.getSelectedFile().getAbsolutePath());

		}
		// Layout
		setLayout(new BorderLayout());

		// Components
		textarea = new JTextArea();
		textarea.setBorder(BorderFactory.createTitledBorder("Result"));
		textarea.setEditable(false);
		program = new Program(folder);

		scroll = new JScrollPane(textarea);
		panel = new Panel(folder);

		panel.addListener(new Listener() {
			public void filmEvent(PanelEvent e) {
				textarea.setText("");
				textarea.setText(e.getText());
				textarea.setSelectionStart(0);
				textarea.setSelectionEnd(0);
			}

			public void pathEvent(PanelEvent e) {
			}
		});
		textarea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					int offset = textarea.viewToModel(e.getPoint());
					try {
						int rowStart = Utilities.getRowStart(textarea, offset);
						int rowEnd = Utilities.getRowEnd(textarea, offset);
						String selectedline = textarea.getText().substring(
								rowStart, rowEnd);
						for (String s : program.getClickPath()) {
							if (s.contains(selectedline)) {
								Desktop.getDesktop().open(new File(s));
							}
						}

					} catch (IOException e1) {
					} catch (BadLocationException e2) {
					}

				} else if (SwingUtilities.isRightMouseButton(e)) {
					int offset = textarea.viewToModel(e.getPoint());
					try {
						int rowStart = Utilities.getRowStart(textarea, offset);
						int rowEnd = Utilities.getRowEnd(textarea, offset);
						String selectedline = textarea.getText().substring(
								rowStart, rowEnd);

						for (String path : program.getClickPath()) {
							if (path.contains(selectedline)) {
								File f = new File(path);
								try {
									Desktop.getDesktop()
											.open(f.getParentFile());
								} catch (IOException e1) {
									e1.printStackTrace();
								}

							}
						}

					} catch (BadLocationException e1) {
					}

				}
			}
		});
		// Adding
		Container c = getContentPane();
		c.add(scroll, BorderLayout.CENTER);
		c.add(panel, BorderLayout.WEST);

	}

	public static JTextArea getTextArea() {
		return textarea;
	}

}
