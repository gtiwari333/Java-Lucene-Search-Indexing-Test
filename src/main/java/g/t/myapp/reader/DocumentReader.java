package g.t.myapp.reader;

import java.nio.file.Path;

public abstract class DocumentReader {

	protected Path	file;

	public DocumentReader( Path file ) {
		this.file = file;
	}

	public abstract String readString( );

	public abstract String getFileType( );
}
