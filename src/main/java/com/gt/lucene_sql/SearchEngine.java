package com.gt.lucene_sql;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchEngine {

	private static final String	INDEX_DIRECTORY	= "indexDirectory";
	public static final String	FIELD_PATH		= "path";

	public static void buildIndex( ) throws IOException {
		Directory dir = FSDirectory.open( Paths.get( INDEX_DIRECTORY ) );
		Analyzer analyzer = new StandardAnalyzer( );
		IndexWriterConfig iwc = new IndexWriterConfig( analyzer );
		IndexWriter writer = new IndexWriter( dir, iwc );

		List< Book > books = BookDB.readBooks( );
		System.out.println( "creating index" );
		for ( Book book: books ) {
			for ( Book.Chapter chapter: book.chapters ) {
				Document doc = new Document( );
				//				System.out.println("indexing "+chapter);
				doc.add( new TextField( "book_name", book.book_name, Field.Store.YES ) );
				doc.add( new TextField( "title", chapter.title, Field.Store.YES ) );
				doc.add( new IntField( "book_id", book.book_id, Field.Store.YES ) );
				doc.add( new IntField( "chapter_id", chapter.chapter_id, Field.Store.YES ) );
				writer.addDocument( doc );
			}
		}
		writer.close( );
	}

	public static void searchIndex( String[] queryStrings ) throws IOException, ParseException {

		IndexReader reader = DirectoryReader.open( FSDirectory.open( Paths.get( INDEX_DIRECTORY ) ) );
		IndexSearcher searcher = new IndexSearcher( reader );

		QueryParser parser = new MultiFieldQueryParser( new String[] { "book_name", "title" }, new StandardAnalyzer( ) );
		for ( String queryString: queryStrings ) {
			System.out.println( "\nSearching for: " + queryString );
			Query query = parser.parse( queryString );

			TopDocs results = searcher.search( query, 10 );
			ScoreDoc[] hits = results.scoreDocs;

			System.out.println( "Found " + hits.length + " hits." );
			for ( int i = 0; i < hits.length; ++i ) {

				Document document = searcher.doc( hits[ i ].doc );
				int book_id = Integer.parseInt( document.get( "book_id" ) );
				int chapter_id = Integer.parseInt( document.get( "chapter_id" ) );
				String title = document.get( "title" );
				String book_name = document.get( "book_name" );
				showInfo( book_id, chapter_id, title, book_name );
			}
		}
	}

	public static void showInfo( int book_id, int chapter_id, String title, String book_name ) {
		Book.Chapter chapter = BookDB.readChapter( chapter_id, book_id );
		System.out.println( "Book : " + book_name );
		System.out.println( "Chapter Title : " + title );
		System.out.println( "Content : " + chapter.content );

	}

	public static void main( String[] args ) throws Exception {
		buildIndex( );
		searchIndex( new String[] { "computer interface", "user interface", "hello", "language", "interface" } );
	}
}
