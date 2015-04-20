package g.t.myapp;

public class LuceneAppTest {

	static String	indexPath	= "D:\\test\\luceneTest\\index";
	static String	docsPath	= "D:\\test\\luceneTest\\docs";

	public static void main( String[] args ) throws Exception {

		Indexer ind = new Indexer( indexPath, docsPath );
		ind.doIndex( );

		try (Searcher search = new Searcher( indexPath, "computer" )) {
			search.doSearch( );

			//			search.setSearchQuery( "apple" ).doSearch( );
			//			search.setSearchQuery( "computer" ).doSearch( );
			//			search.setSearchQuery( "hacker" ).doSearch( );

		}
	}
}
