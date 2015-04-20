package g.t.myapp.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MSPowerPointFileReader extends DocumentReader {

	public MSPowerPointFileReader( Path file ) {
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

			try (PowerPointExtractor extractor = new PowerPointExtractor( fs )) {
				return extractor.getText( );

			}
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return "";
	}

}
