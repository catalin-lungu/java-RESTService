package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
@XmlRootElement
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idbook;

	private String gen;

	private String isbn;

	private String title;

	private String urlString;

	public Book() {
	}

	public Long getIdbook() {
		return this.idbook;
	}

	public void setIdbook(Long idbook) {
		this.idbook = idbook;
	}

	public String getGen() {
		return this.gen;
	}

	public void setGen(String gen) {
		this.gen = gen;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlString() {
		return this.urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	@Override
	public String toString() {
		
		return super.toString();
	}

}

