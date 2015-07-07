import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderOrganizer {

	public FolderOrganizer(String directory){
		this.directory = directory;
		 Images = directory.concat("/[Images]/");
		 Books = directory.concat("/[Books]/");
		 Music = directory.concat("/[Music]/");
		 Documents = directory.concat("/[Documents]/");
		 PowerPoints = directory.concat("/[PowerPoints]/");
		 Compressed = directory.concat("/[Compressed]/");
		 Installers = directory.concat("/[Installers]/");
		 Torrents = directory.concat("/[Torrents]/");
		 Videos = directory.concat("/[Videos]/");
		 JavaCode = directory.concat("/[Java Code]/");
		 PythonCode = directory.concat("/[Python Code]/");
		 CPPCode = directory.concat("/[C++ Code]/");
		 Spreadsheets = directory.concat("/[Spreadsheets]/");
		 HTML = directory.concat("/[HTML]/");
		 RM = directory.concat("/[Rainmeter]/");
		 ASM = directory.concat("/[Assembly Code]/");
		 Terminal = directory.concat("/[Terminal Code]/");
		 Miscellaneous = directory.concat("/[Miscellaneous]/");
		 Shortcuts = directory.concat("/[Shortcuts]/");
		
		 String[] temp = { Images, Books, Music, Documents,
					PowerPoints, Compressed, Installers, Torrents, Videos, JavaCode,
					PythonCode, CPPCode, Spreadsheets, HTML, RM, ASM, Terminal,
					Miscellaneous,Shortcuts };
		for(int i=0;i<18;i++){
			Directories[i]=temp[i];
		}
	}
	
	public static String directory;
	
	public static String Images;
	public static String Books;
	public static String Music;
	public static String Documents;
	public static String PowerPoints;
	public static String Compressed; 
	public static String Installers; 
	public static String Torrents; 
	public static String Videos; 
	public static String JavaCode; 
	public static String PythonCode;
	public static String CPPCode;
	public static String Spreadsheets; 
	public static String HTML; 
	public static String RM; 
	public static String ASM; 
	public static String Terminal;
	public static String Miscellaneous;
	public static String Shortcuts;

	public static String imageFormat = "((.jpg$)|(.jpeg$)|(.png$)|(.gif$)|(.tiff$)|(.psd$)|(.tga$))";
	public static String bookFormat = "((.pdf$)|(.mobi$)|(.epub$))";
	public static String musicFormat = "((.mp3$)|(.wav$)|(.wma$)|(.m4a$)(.flac$)|(.mid$))";
	public static String documentFormat = "((.doc$)|(.docx$)|(.txt$)|(.rtf$)|(.odt$))";
	public static String powerPointFormat = "((.ppt$)|(.pptx$))";
	public static String zipFormat = "(.zip$)|(.rar$)|(.7z$)|(.tar.gz$)";
	public static String installerFormat = "((.msi$)|(.exe$))";
	public static String torrentFormat = "(.torrent$)";
	public static String videoFormat = "((.mts$)|(.mp4$)|(.flv$)|(.avi$)|(.mpeg$)|(.mpg$)|(.mpe$)|(.wmv$)|(.mov$))";
	public static String javaFormat = "((.java$)|(.jar$))";
	public static String pythonFormat = "(.py$)";
	public static String cppFormat = "(.cpp$)|(.h$)";
	public static String spreadsheetFormat = "((.csv$)|(.xls$)|(.ods$)|(.xltx$)|(.xlsx$))";
	public static String htmlFormat = "(.html$)|(.htm$)";
	public static String rmFormat = "(.rmskin$)";
	public static String asmFormat = "(.asm$)";
	public static String terminalFormat = "(.bat$)|(.cmd$)|(.sh$)";
	public static String shortcutFormat = "(.lnk$)";
 
	public static String[] Directories = new String[18];
	
	public static String[] Formats = { imageFormat, bookFormat, musicFormat,
			documentFormat, powerPointFormat, zipFormat, installerFormat,
			torrentFormat, videoFormat, javaFormat, pythonFormat, cppFormat,
			spreadsheetFormat, htmlFormat, rmFormat, asmFormat, terminalFormat,shortcutFormat };

	private static void createDirectories() {
		for (String d : Directories) {
			File f = new File(d);
			f.mkdir();
		}
	}

	public static void ScanDirectory() throws IOException {

		File path = new File(directory);
		File[] files = path.listFiles();

		for (int i = 0; i < files.length; i++) {
			System.out.println("File " + i + " is: " + files[i]);
			classify(files[i]);
		}
	}

	public static void classify(File filepath) throws IOException {

		String f = filepath.toString().toLowerCase();

		for (int i = 0; i < Formats.length; i++) {
			Pattern p = Pattern.compile(Formats[i]);
			Matcher m = p.matcher(f);
			while (m.find()) {
				Organize(filepath, i);
			}
		}
	}

	private static void Organize(File f, int i) throws IOException {

		File folder = new File(Directories[i]);
		folder.mkdir();
		String fileName = f.getName();
		Path source = Paths.get(f.toURI());
		Path target = Paths.get(Directories[i].concat(fileName));
		
		try {
			Files.move(source, target);
		} catch (FileAlreadyExistsException e) {
			int increment = 1;
			System.out.println("File already exists.");
			handleDuplicates(source, fileName, i,increment);
		}
	}

	public static void handleDuplicates(Path source, String fileName, int index, int increment) throws IOException {

		String incrementString = ("("+increment+")");
		String tempFileName = incrementString.concat(fileName);
		Path target = Paths.get(Directories[index].concat(tempFileName));
		
		try {
			Files.move(source, target);
		} catch (FileAlreadyExistsException e) {
			increment++;
			handleDuplicates(source, fileName,index,increment);
		}
	}

}
