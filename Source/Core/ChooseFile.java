package Core;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChooseFile {
	
	private JFrame frame;
	
	public ChooseFile() {

		frame = new JFrame();
		frame.setVisible(true);
		BringToFront();
	}

	public File getFile() {
		JFileChooser fc = new JFileChooser("Select Folder to Organize:");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
			frame.setVisible(false);
			return fc.getSelectedFile();
		} else {
			System.out.println("Next time select a folder.");
			System.exit(1);
		}
		return null;
	}

	private void BringToFront() {
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.setExtendedState(JFrame.NORMAL);
	}

}