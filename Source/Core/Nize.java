package Core;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("static-access")
public class Nize implements Serializable {

	private static final long serialVersionUID = -2318802564540428385L;
	public static long printSpeed = 20;
	public static Vector<String> directories = new Vector<String>();
	public static Nize l = new Nize();

	public Nize() {
	}

	public static void main(String[] args) throws Exception {
		retrieveNizeText();
	}
	
	public static void retrieveNizeText() throws Exception {
		FileReader fr;
		Scanner input = null;
		try {
			fr = new FileReader("LodeText.txt");
			input = new Scanner(fr);
			while (input.hasNext()) {
				l.directories.add(input.nextLine());
			}
			System.out.println("Lode complete.");
			adUsusum();
			
		} catch (FileNotFoundException e) {
			SetupLode();
		}
		finally{
			if(input!=null) input.close();
		}
	}
	
	
	private static void SetupLode() throws Exception {
		//boolean recursive = false;
		// Recursive: recursive = true, dirArg++;
		System.out.println("Select a directory: ");
		String directory = (new ChooseFile().getFile().getAbsolutePath().replace("\\", "/"));
		//Path dir = Paths.get(directory);
		l.directories.add(directory);
		System.out.println(l.directories.size());
		//SaveLodeText(); Don't save, as to use for 'one-time' purposes
		organizeDirectory(directory);
		
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
	
	public static void organizeDirectory(String d) throws IOException{
		FolderOrganizer fo = new FolderOrganizer(d);
		fo.ScanDirectory();
	}
	
	public static void serialize() throws IOException, InterruptedException{
		SerializationUtil.serialize(l, "NizeData.ser");
		System.out.println("Serializaton complete.");	
	}
	
	public static void retrieveSerialData() throws Exception {
		try {
			l = (Nize) SerializationUtil.deserialize("NizeData.ser");
			adUsusum();
		} catch (IOException e) {
			SetupLode();
		}
	}

}
