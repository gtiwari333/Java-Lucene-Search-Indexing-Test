package g.t.myapp.reader;

import java.nio.file.Path;

public class MSXWordFileReader extends DocumentReader {

	public MSXWordFileReader( Path file ) {
		super( file );
	}

	@Override
	public String getFileType( ) {
		return "MS Word File";
	}

	@Override
	public String readString( ) {

		//		XPFFWordExtractor, XSLFPowerPointExtractor, XSSFExcelExtractor
		return "";
	}

}
