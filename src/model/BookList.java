package model;

import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

 @XmlRootElement   
 public class BookList {
	
	@XmlElement
	Vector<Book> books = new Vector<Book>() ;
	
	public BookList(){
		
	}
	
	public BookList(Vector<Book> lstBooks) {
		this.books = lstBooks;
	}

	public Vector<Book> getBooks() {
		return books;
	}

	public void setBooks(Vector<Book> books) {
		this.books = books;
	}
	
	
}