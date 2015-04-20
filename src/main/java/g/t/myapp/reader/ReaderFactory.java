package g.t.myapp.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReaderFactory {

	public static DocumentReader getReader( Path file ) throws Exception {

		if ( !Files.isRegularFile( file ) ) {
			throw new IOException( "Not a valid file " + file );
		}

		DocType type = DocType.getType( file.getFileName( ).toString( ) );

		if ( type == null ) {
			throw new Exception( "File Type couldn't be identified " + file );
		}

		DocumentReader reader = null;

		switch ( type ) {
		case DOC:
			reader = new MSWordFileReader( file );
			break;
		case DOCX:
			break;

		case XLS:
			reader = new MSExcelFileReader( file );
			break;
		case XLSX:
			break;

		case RTF:
			break;
		case ODT:
			break;
		case PDF:
			reader = new PDFFileReader( file );
			break;
		case TIFF:
			break;
		case TXT:
			reader = new TextFileReader( file );
			break;
		case XML:
			reader = new XMLFileReader( file );
			break;
		default:
			reader = null;
			break;

		}

		if ( reader == null ) {
			throw new Exception( "Not implemented Type " + type );
		}

		return reader;

	}

}
