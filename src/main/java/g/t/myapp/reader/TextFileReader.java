package g.t.myapp.reader;

import java.nio.file.Path;
import java.util.Scanner;

public class TextFileReader extends DocumentReader {

	public TextFileReader( Path file ) {
		super( file );
	}

	@Override
	public String getFileType( ) {
		return "Text File";
	}

	@Override
	public String readString( ) {
		try {
			try (Scanner sc = new Scanner( file.toFile( ) )) {
				return sc.useDelimiter( "\\Z" ).next( );
			}

		} catch ( Exception e ) {
			e.printStackTrace( );
		}
		return "";
	}

}
