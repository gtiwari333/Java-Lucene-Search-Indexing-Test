package g.t.myapp.reader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public enum DocType {
	TXT, XML, DOC, PDF, DOCX, XLS, XLSX, TIFF, RTF, ODT;

	public static DocType getType( String fileName ) {
		String ext = StringUtils.upperCase( FilenameUtils.getExtension( fileName ) );

		return DocType.valueOf( ext );
	}
}
