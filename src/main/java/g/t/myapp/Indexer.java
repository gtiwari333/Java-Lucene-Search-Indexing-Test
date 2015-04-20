package g.t.myapp;

import g.t.myapp.reader.DocType;
import g.t.myapp.reader.ReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	public static final String	fileName	= "fileName";
	public static final String	filePath	= "filePath";
	public static final String	modified	= "modified";
	public static final String	fileType	= "fileType";
	public static final String	contents	= "contents";

	private String				indexPath;
	private String				documentPath;
	private boolean				updateIndex	= false;

	/**
	 * updateIndex is false by default
	 * 
	 * @param indexPath
	 * @param documentPath
	 */
	public Indexer( String indexPath, String documentPath ) {
		this.indexPath = indexPath;
		this.documentPath = documentPath;
	}

	/**
	 * @param indexPath
	 * @param documentPath
	 * @param updateIndex
	 */
	public Indexer( String indexPath, String documentPath, boolean updateIndex ) {
		this( indexPath, documentPath );
		this.updateIndex = updateIndex;
	}

	public void doIndex( ) {
		final Path docDir = Paths.get( documentPath );
		if ( !Files.isReadable( docDir ) ) {
			System.out.println( "Document directory '" + docDir.toAbsolutePath( ) + "' does not exist or is not readable, please check the path" );
			return;
		}

		Date start = new Date( );
		try {
			System.out.println( "Indexing to directory '" + indexPath + "'..." );

			Directory dir = FSDirectory.open( Paths.get( indexPath ) );
			Analyzer analyzer = new StandardAnalyzer( );
			IndexWriterConfig iwc = new IndexWriterConfig( analyzer );

			if ( !updateIndex ) {

				/*
				 * Create a new index in the directory, removing any previously indexed documents:
				 */
				iwc.setOpenMode( OpenMode.CREATE );
			} else {
				/*
				 *  Add new documents to an existing index:
				 */
				iwc.setOpenMode( OpenMode.CREATE_OR_APPEND );
			}

			/*
			 *  Optional: for better indexing performance, if you are indexing many documents, increase the RAM buffer.  
			 *  
			 *  But if you do this, increase the max heap size to the JVM (eg add -Xmx512m or -Xmx1g):
			 */
			// iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter( dir, iwc );
			indexDocs( writer, docDir );

			/*
			 *  NOTE: if you want to maximize search performance, you can optionally call forceMerge here.  
			 *  This can be a terribly costly operation, so generally it's only worth it when your index is relatively static (ie you're done adding documents to it): 			 
			 * 
			 */
			// writer.forceMerge(1);

			writer.close( );

			Date end = new Date( );
			System.out.println( end.getTime( ) - start.getTime( ) + " total milliseconds" );

		} catch ( IOException e ) {
			System.out.println( " caught a " + e.getClass( ) + "\n with message: " + e.getMessage( ) );
		}
	}

	private void indexDocs( final IndexWriter writer, Path path ) throws IOException {

		if ( path.toFile( ).isDirectory( ) ) {
			File[] files = path.toFile( ).listFiles( );
			for ( int i = 0; i < files.length; i++ )
				indexDocs( writer, files[ i ].toPath( ) );
		} else {
			indexDoc( writer, path );
		}

	}

	private void indexDoc( IndexWriter writer, Path path ) throws IOException {

		Long lastModified = Files.getLastModifiedTime( path ).toMillis( );
		String docText = "";
		try {
			docText = ReaderFactory.getReader( path ).readString( );
//			System.out.println( docText );
		} catch ( Exception e ) {
			System.out.println( "Indexer.indexDoc() " + e.getMessage( ) );
			System.out.println( "Skipping file " + path );
			return;
		}

		try (InputStream stream = Files.newInputStream( path )) {
			Document doc = new Document( );

			doc.add( new StringField( filePath, path.toString( ), Field.Store.YES ) );
			doc.add( new StringField( fileType, DocType.getType( path.getFileName( ).toString( ) ).name( ), Field.Store.YES ) );

			doc.add( new LongField( modified, lastModified, Field.Store.NO ) );

			doc.add( new TextField( contents, docText, Field.Store.NO ) );

			if ( writer.getConfig( ).getOpenMode( ) == OpenMode.CREATE ) {
				System.out.println( "adding " + path );
				writer.addDocument( doc );
			} else {
				System.out.println( "updating " + path );
				writer.updateDocument( new Term( "path", path.toString( ) ), doc );
			}
		}
	}

	public String getDocumentPath( ) {
		return documentPath;
	}

	public void setDocumentPath( String documentPath ) {
		this.documentPath = documentPath;
	}

	public String getIndexPath( ) {
		return indexPath;
	}

	public boolean isUpdateIndex( ) {
		return updateIndex;
	}

}
