package com.gt.lucene_sql;

import java.util.List;

public class tests {
	public static void main(String[] args) {
		List<Book> books= BookDB.readBooks();
		for (Book book : books) {
			System.out.println(book.toString());
		}
	}
}
