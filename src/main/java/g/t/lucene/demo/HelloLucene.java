package g.t.lucene.demo;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class HelloLucene {

	public static void main( String[] args ) throws IOException, ParseException {
		// 0. Specify the analyzer for tokenizing text.
		//    The same analyzer should be used for indexing and searching
		Analyzer analyzer = new StandardAnalyzer( );

		// 1. create the index
		Directory index = new RAMDirectory( );

		IndexWriterConfig config = new IndexWriterConfig( analyzer );

		IndexWriter w = new IndexWriter( index, config );
		addDoc( w, "Lucene in Action", "193398817", "" );
		addDoc( w, "Lucene for Dummies", "55320055Z", "Discover the Lucene full-text search library lucene is an open-source Java full-text search library which makes it easy to add search functionality to an application or website.  The goal of Lucene Tutorial.com is to provide a gentle introduction into Lucene. First-time Visitors  If this is your first-time here, you most probably want to go straight to the 5 minute introduction to Lucene." );
		addDoc( w, "Managing Gigabytes", "55063554A", "This is 9900333 a simple content, nothing interesting" );
		addDoc( w, "The Art of Computer Science", "9900333", "Lucene lucene lucene" );
		w.close( );

		// 2. query
		String querystr = args.length > 0 ? args[ 0 ] : "lucene";

		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		//		Query q = new QueryParser( "title", analyzer ).parse( querystr );
		Query q = new MultiFieldQueryParser( new String[] { "isbn", "content" }, analyzer ).parse( querystr );

		// 3. search
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open( index );
		IndexSearcher searcher = new IndexSearcher( reader );

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search( q, 5 * hitsPerPage );
		ScoreDoc[] hits = results.scoreDocs;

		// 4. display results
		System.out.println( "Found " + hits.length + " hits." );
		for ( int i = 0; i < hits.length; ++i ) {
			int docId = hits[ i ].doc;
			Document d = searcher.doc( docId );
			System.out.println( ( i + 1 ) + ". " + d.get( "isbn" ) + "\t" + d.get( "title" ) );
		}

		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close( );
	}

	private static void addDoc( IndexWriter w, String title, String isbn, String content ) throws IOException {
		Document doc = new Document( );
		doc.add( new TextField( "title", title, Field.Store.YES ) );

		// use a string field for isbn because we don't want it tokenized
		doc.add( new StringField( "isbn", isbn, Field.Store.YES ) );
		doc.add( new TextField( "content", content, Field.Store.NO ) );

		w.addDocument( doc );
	}
}
