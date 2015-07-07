import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderKing {

	public static String Images = Lode.directory.concat("/[Images]/");
	public static String Books = Lode.directory.concat("/[Books]/");
	public static String Music = Lode.directory.concat("/[Music]/");
	public static String Documents = Lode.directory.concat("/[Documents]/");
	public static String PowerPoints = Lode.directory.concat("/[PowerPoints]/");
	public static String Compressed = Lode.directory.concat("/[Compressed]/");
	public static String Installers = Lode.directory.concat("/[Installers]/");
	public static String Torrents = Lode.directory.concat("/[Torrents]/");
	public static String Videos = Lode.directory.concat("/[Videos]/");
	public static String JavaCode = Lode.directory.concat("/[Java Code]/");
	public static String PythonCode = Lode.directory.concat("/[Python Code]/");
	public static String CPPCode = Lode.directory.concat("/[C++ Code]/");
	public static String Spreadsheets = Lode.directory.concat("/[Spreadsheets]/");
	public static String HTML = Lode.directory.concat("/[HTML]/");
	public static String RM = Lode.directory.concat("/[Rainmeter]/");
	public static String ASM = Lode.directory.concat("/[Assembly Code]/");
	public static String Terminal = Lode.directory.concat("/[Terminal Code]/");
	public static String Miscellaneous = Lode.directory.concat("/[Miscellaneous]/");

	public static String imageFormat = "((.jpg$)|(.jpeg$)|(.png$)|(.gif$))";
	public static String bookFormat = "((.pdf$)|(.mobi$)|(.epub$))";
	public static String musicFormat = "((.mp3$)|(.wav$)|(.wma$)|(.m4a$)(.flac$))";
	public static String documentFormat = "((.doc$)|(.docx$)|(.txt$)|(.rtf$)|(.odt$))";
	public static String powerPointFormat = "((.ppt$)|(.pptx$))";
	public static String zipFormat = "(.zip$)|(.rar$)|(.7z$)";
	public static String installerFormat = "((.msi$)|(.exe$))";
	public static String torrentFormat = "(.torrent$)";
	public static String videoFormat = "((.mp4$)|(.flv$)|(.avi$)|(.mpeg$)|(.mpg$)|(.mpe$)|(.wmv$))";
	public static String javaFormat = "((.java$)|(.jar$))";
	public static String pythonFormat = "(.py$)";
	public static String cppFormat = "(.cpp$)|(.h$)";
	public static String spreadsheetFormat = "((.csv$)|(.xls$)|(.ods$)|(.xltx$))";
	public static String htmlFormat = "(.html$)|(.htm$)";
	public static String rmFormat = "(.rmskin$)";
	public static String asmFormat = "(.asm$)";
	public static String terminalFormat = "(.bat$)|(.cmd$)|(.sh$)";

	public static String[] Directories = { Images, Books, Music, Documents,
			PowerPoints, Compressed, Installers, Torrents, Videos, JavaCode,
			PythonCode, CPPCode, Spreadsheets, HTML, RM, ASM, Terminal,
			Miscellaneous };

	public static String[] Formats = { imageFormat, bookFormat, musicFormat,
			documentFormat, powerPointFormat, zipFormat, installerFormat,
			torrentFormat, videoFormat, javaFormat, pythonFormat, cppFormat,
			spreadsheetFormat, htmlFormat, rmFormat, asmFormat, terminalFormat };

	private static void createDirectories() {
		for (String d : Directories) {
			File f = new File(d);
			f.mkdir();
		}
	}

	public static void ScanDirectory(String directory) throws IOException {

		File path = new File(Lode.directory);
		File[] files = path.listFiles();
		createDirectories();

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

		String fileName = f.getName();
		Path source = Paths.get(f.toURI());
		Path target = Paths.get(Directories[i].concat(fileName));
		
		try {
			Files.move(source, target);
		} catch (FileAlreadyExistsException e) {
			System.out.println("File already exists.");
			handleDuplicates(source, fileName, i);
		}
	}

	public static void handleDuplicates(Path source, String fileName, int i) throws IOException {
		
		String increment = "(1)";
		fileName = increment.concat(fileName);
		Path target = Paths.get(Directories[i].concat(fileName));
		
		try {
			Files.move(source, target);
		} catch (FileAlreadyExistsException e) {
			handleDuplicates(source, fileName, i);
		}
	}

}
