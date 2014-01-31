package service;

import java.io.StringWriter;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Book;
import model.BookList;


public class Parser {

	public static String getJSON(Book book){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json="";
		try {
			json = ow.writeValueAsString(book);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static String getJSON(Vector<Book> lstBooks){
		StringBuffer outputStringBuffer = new StringBuffer();
		outputStringBuffer.append("{\n");
		for (Book book : lstBooks) {
			outputStringBuffer.append(getJSON(book));
		}
		outputStringBuffer.append("}");
		return outputStringBuffer.toString();
	}
	
	// not in use
	public static String getXML(Book book) {
		StringWriter sw =  new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(book, sw);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.toString();
	}
	
	public static String getXML(BookList lstBooks) {
		StringWriter sw =  new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(lstBooks, sw);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.toString();
	}
	
}
