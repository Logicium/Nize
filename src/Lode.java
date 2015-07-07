import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Lode implements Serializable {

	private static final long serialVersionUID = -2318802564540428385L;
	public static long printSpeed = 20;
	public static Vector<String> directories = new Vector<String>();
	public static Lode l = new Lode();

	public Lode() {
	}

	public static void main(String[] args) throws Exception {
		retrieveLodeText();
	}

	public static void retrieveSerialData() throws Exception {
		try {
			l = (Lode) SerializationUtil.deserialize("LodeData.ser");
			adUsusum();
		} catch (IOException e) {
			SetupLode();
		}
	}
	
	private static void SetupLode() throws Exception {
		boolean recursive = false;
		// Recursive: recursive = true, dirArg++;
		System.out.println("Select a directory: ");
		String directory = (new ChooseFile().getFile().getAbsolutePath().replace("\\", "/"));
		Path dir = Paths.get(directory);
		l.directories.add(directory);
		System.out.println(l.directories.size());
		SaveLodeText();
	}
	
	public static void serialize() throws IOException, InterruptedException{
		SerializationUtil.serialize(l, "LodeData.ser");
		System.out.println("Serializaton complete.");	
	}
	
	public static void retrieveLodeText() throws Exception {
		FileReader fr;
		try {
			fr = new FileReader("LodeText.txt");
			Scanner input = new Scanner(fr);
			while (input.hasNext()) {
				l.directories.add(input.nextLine());
			}
			System.out.println("Lode complete.");
			adUsusum();
			
		} catch (FileNotFoundException e) {
			SetupLode();
		}
	}
	
	public static void SaveLodeText() throws Exception {
		PrintWriter P = new PrintWriter("LodeText.txt");
		for(String d: directories){
			P.println(d);
		}
		P.close();
	}

	public static void adUsusum() throws IOException, InterruptedException{
		System.out.println("Welcome back!");
		System.out.println(l.directories.size());
		for(String d : l.directories){
			FolderOrganizer fo = new FolderOrganizer(d);
			WatchDirectory wd = new WatchDirectory(Paths.get(d),false,fo);
			fo.ScanDirectory();
			wd.processEvents();
		}
	}
}
