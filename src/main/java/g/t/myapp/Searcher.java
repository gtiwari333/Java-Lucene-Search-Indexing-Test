package g.t.myapp;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class Searcher implements Closeable
{

	/*
	 * 
	 * TODO: implement scoring, complex query search
	 * 
	 * http://www.lucenetutorial.com/advanced-topics/scoring.html
	 * 
	 * http://events.linuxfoundation.org/sites/events/files/slides/CustomLuceneQueries.pdf
	 * http://opensourceconnections.com/blog/2014/03/12/using-customscorequery-for-custom-solrlucene-scoring/
	 * http://opensourceconnections.com/blog/2014/01/20/build-your-own-custom-lucene-query-and-scorer/
	 */

	public static int		HITS_PER_PAGE				= 10;
	public static int		NUM_OF_SEARCH_PAGES			= 5;	//total 50 results when paginating
	public static int		MAX_HIT_TO_SHOW_UNPAGINATED	= 10;	//total 50 results

	private String			indexPath;
	private String			searchQuery;

	private IndexReader		reader						= null;
	private IndexSearcher	searcher					= null;

	public Searcher( String indexPath, String searchQuery ) throws IOException {
		super( );
		this.indexPath = indexPath;
		this.searchQuery = searchQuery;

		init( );

	}

	private void init( ) throws IOException {
		reader = DirectoryReader.open( FSDirectory.open( Paths.get( indexPath ) ) );
		searcher = new IndexSearcher( reader );
	}

	public String getSearchQuery( ) {
		return searchQuery;
	}

	public Searcher setSearchQuery( String searchQuery ) {
		this.searchQuery = searchQuery;
		return this;
	}

	public String getIndexPath( ) {
		return indexPath;
	}

	public void doSearch( ) throws IOException, ParseException {
		Analyzer analyzer = new StandardAnalyzer( );
		QueryParser parser = new QueryParser( Indexer.contents, analyzer );

		Query query = parser.parse( searchQuery );

		TopDocs results = searcher.search( query, MAX_HIT_TO_SHOW_UNPAGINATED );
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;

		int toShow = Math.min( numTotalHits, MAX_HIT_TO_SHOW_UNPAGINATED );

		System.out.println( "Found " + hits.length + " hits." + " , but showing only " + toShow );
		for ( int i = 0; i < toShow; ++i ) {

			Document d = searcher.doc( hits[ i ].doc );
			System.out.println( hits[i].score + " -- "+ ( i + 1 ) + ". " + d.get( Indexer.filePath ) + " , Type : \t" + d.get( Indexer.fileType ) );
		}

	}

	private void doPagingSearch( ) {

	}

	@Override
	public void close( ) throws IOException {

		if ( reader != null ) {
			reader.close( );
		}
	}

}
