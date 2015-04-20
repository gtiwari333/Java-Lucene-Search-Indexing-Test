package com.gt.lucene_sql;

import java.util.ArrayList;
import java.util.List;

public class Book {
	int book_id;
	String book_name;
	List<Chapter> chapters;

	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", book_name=" + book_name + ", chapters=" + chapters + "]";
	}

	public Book() {
		chapters = new ArrayList<Book.Chapter>();
	}

	public class Chapter {
		int chapter_id;
		String title;
		String content;
		int book_id;
		@Override
		public String toString() {
			return "Chapter [chapter_id=" + chapter_id + ", title=" + title + ", content=" + content + ", book_id=" + book_id + "]";
		}
		
	}

}
