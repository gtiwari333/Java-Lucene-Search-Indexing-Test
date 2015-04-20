package com.gt.lucene_sql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDB {
	private BookDB() {
	}

	public static List<Book> readBooks() {
		List<Book> books = new ArrayList<Book>();
		try {
			ResultSet rs = DBConnection.getInstance().executeQuery("Select * from book");
			while (rs.next()) {
				Book tBook = new Book();
				tBook.book_id = rs.getInt("book_id");
				tBook.book_name = rs.getString("book_name");
				tBook.chapters = readChapters(tBook);
				// System.out.println(tBook);
				books.add(tBook);
			}
			System.out.println("Total Books " + books.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	public static List<Book.Chapter> readChapters(Book book) {
		List<Book.Chapter> chapters = new ArrayList<Book.Chapter>();
		try {
			ResultSet rs = DBConnection.getInstance().executeQuery("Select * from chapters where book_id=" + book.book_id);
			while (rs.next()) {
				Book.Chapter tChapter = book.new Chapter();
				tChapter.book_id=book.book_id;
				tChapter.chapter_id = rs.getInt("chapter_id");
				tChapter.title = rs.getString("title");
				tChapter.content = rs.getString("content");
				chapters.add(tChapter);
			}
			System.out.println("Book " + book.book_name + " has " + chapters.size() + " chapters ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chapters;
	}

	public static Book.Chapter readChapter(int chapter_id, int book_id) {
		Book.Chapter tChapter = new Book().new Chapter();
		try {
			ResultSet rs = DBConnection.getInstance().executeQuery(
					"Select * from chapters where book_id=" + book_id + " and chapter_id=" + chapter_id);
			while (rs.next()) {
				tChapter.book_id = book_id;
				tChapter.chapter_id = rs.getInt("chapter_id");
				tChapter.title = rs.getString("title");
				tChapter.content = rs.getString("content");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tChapter;
	}
}
