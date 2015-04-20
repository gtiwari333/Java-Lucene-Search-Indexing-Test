package g.t.myapp.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MSExcelFileReader extends DocumentReader {

	public MSExcelFileReader( Path file ) {
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

			try (ExcelExtractor extractor = new ExcelExtractor( fs )) {
				return extractor.getText( );

			}
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return "";
	}

}
