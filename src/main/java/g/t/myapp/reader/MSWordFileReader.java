package g.t.myapp.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MSWordFileReader extends DocumentReader {

	public MSWordFileReader( Path file ) {
		super( file );
	}

	@Override
	public String getFileType( ) {
		return "MS Word File";
	}

	@Override
	public String readString( ) {

		try {
			POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream( file.toFile( ) ) );

			try (WordExtractor extractor = new WordExtractor( fs )) {
				return extractor.getText( );

			}
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return "";
	}

}
