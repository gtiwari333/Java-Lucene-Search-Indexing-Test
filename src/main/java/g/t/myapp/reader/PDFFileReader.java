package g.t.myapp.reader;

import java.io.FileInputStream;
import java.nio.file.Path;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFFileReader extends DocumentReader {

	public PDFFileReader( Path file ) {
		super( file );
	}

	@Override
	public String getFileType( ) {
		return "PDF File";
	}

	@Override
	public String readString( ) {
		try {
			FileInputStream fi = new FileInputStream( file.toFile( ) );

			PDFParser parser = new PDFParser( fi );
			parser.parse( );
			COSDocument cd = parser.getDocument( );
			PDFTextStripper stripper = new PDFTextStripper( );
			return stripper.getText( new PDDocument( cd ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}
		return "";
	}

}
