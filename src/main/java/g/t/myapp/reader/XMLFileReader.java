package g.t.myapp.reader;

import java.nio.file.Path;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLFileReader extends DocumentReader {

	public XMLFileReader( Path file ) {
		super( file );
	}

	@Override
	public String getFileType( ) {
		return "PDF File";
	}

	@Override
	public String readString( ) {
		final StringBuffer sb = new StringBuffer( );

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance( );
			SAXParser saxParser = factory.newSAXParser( );

			DefaultHandler handler = new DefaultHandler( ) {

				//Other event handlers (for startElement and endElement) also can be implemented similarly  
				public void characters( char ch[], int start, int length )
						throws SAXException {
					sb.append( new String( ch, start, length ) );
				}
			};

			saxParser.parse( file.toFile( ), handler );
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
		return sb.toString( );
	}

}
