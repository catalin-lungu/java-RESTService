package service;

import java.net.URI;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;




import com.sun.jersey.api.client.ClientResponse.Status;

import model.Book;
import model.BookList;

@Stateless
@LocalBean
@Path("/books")
public class MyService {

	@PersistenceContext(unitName="JPA", type=PersistenceContextType.TRANSACTION)
	EntityManager entityManager;
	@Context
	private UriInfo uriInfo;
 
	@GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{id}")
    public Book getBookXMLDefault(@PathParam("id") long id) {
        return entityManager.find(Book.class, id);
    }
	
	@GET
    @Produces(MediaType.APPLICATION_XML)  
    @Path("/xml/{id}")
    public Book getBookXML(@PathParam("id") long id) {
        return entityManager.find(Book.class, id);
    }
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json/{id}")
    public Book getBookJSON(@PathParam("id") long id) {
        return entityManager.find(Book.class, id);
    }
 
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBooksXML_Default() {
    	CriteriaQuery<Book> cq = entityManager.getCriteriaBuilder().createQuery(Book.class);
		cq.select(cq.from(Book.class));
		Vector<Book> lstBooks = (Vector<Book>) entityManager.createQuery(cq).getResultList();
        return Parser.getXML(new BookList(lstBooks));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml")
    public String getBooksXML() {
    	CriteriaQuery<Book> cq = entityManager.getCriteriaBuilder().createQuery(Book.class);
		cq.select(cq.from(Book.class));
		Vector<Book> lstBooks = (Vector<Book>) entityManager.createQuery(cq).getResultList();
        return Parser.getXML(new BookList(lstBooks));
    }
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public String getBooksJSON(@PathParam("id") long id) {
    	CriteriaQuery<Book> cq = entityManager.getCriteriaBuilder().createQuery(Book.class);
		cq.select(cq.from(Book.class));
		Vector<Book> lstBooks = (Vector<Book>) entityManager.createQuery(cq).getResultList();
        return Parser.getJSON(lstBooks);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createXML(Book book) {
    	book.setIdbook(getLastIndex()+1);
    	URI resurceUri = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getIdbook())).build();
        book.setUrlString(resurceUri.toString());
        entityManager.persist(book);
        entityManager.flush();
        return Response.created(resurceUri).status(Status.CREATED).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJSON(Book book) {
    	book.setIdbook(getLastIndex()+1);
        URI resurceUri = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getIdbook())).build();
        book.setUrlString(resurceUri.toString());
        entityManager.persist(book);
        entityManager.flush();
        return Response.created(resurceUri).status(Status.CREATED).build();
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addBook(@FormParam("gen") String genStr, @FormParam("isbn") String isbnStr, @FormParam("title") String titleStr){
		Book book = new Book();
		book.setGen(genStr);
		book.setIsbn(isbnStr);
		book.setTitle(titleStr);
		book.setIdbook(getLastIndex()+1);

		URI bookUri = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getIdbook())).build();
		book.setUrlString(bookUri.toString());
		
		if(entityManager.contains(book)){
			return Response.status(Status.CONFLICT).build();
		}
		
		entityManager.persist(book);
		entityManager.flush();
		return Response.created(bookUri).build();
	}
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private long getLastIndex(){
    	CriteriaBuilder qb = entityManager.getCriteriaBuilder();
    	CriteriaQuery cq = qb.createQuery();
    	Root book = cq.from(Book.class);
    	cq.select(qb.max(book.get("idbook")));
    	long l = (long) entityManager.createQuery(cq).getSingleResult();	
    	return l;
    }
    
   
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response update(Book book) {
    	
    	if(!containsID(book.getIdbook())){
			return Response.status(Status.NOT_FOUND).build();
		}
    	entityManager.merge(book);
    	
    	return Response.status(Status.OK).build();
    }
 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJSON(Book book) {
    	if(!containsID(book.getIdbook())){
			return Response.status(Status.NOT_FOUND).build();
		}
    	entityManager.merge(book);
    	
    	return Response.status(Status.OK).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateURL_ENCODED(@FormParam("gen") String genStr, @FormParam("isbn") String isbnStr, @FormParam("title") String titleStr , @FormParam("idbook") String idString) {
    	Book book = new Book();
    	try {
			book.setIdbook(Long.parseLong(idString));
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		book.setGen(genStr);
		book.setIsbn(isbnStr);
		book.setTitle(titleStr);
		URI bookUri = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getIdbook())).build();
		book.setUrlString(bookUri.toString());
		
		if(!containsID(book.getIdbook())){
			return Response.status(Status.NOT_FOUND).build();
		}
			
		entityManager.merge(book);
        
        return Response.status(Status.OK).build();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean containsID(Long id){
    	CriteriaBuilder qb = entityManager.getCriteriaBuilder();
    	CriteriaQuery cq = qb.createQuery();
    	Root book = cq.from(Book.class);
    	cq.select(qb.count(book.get("idbook"))).where(book.get("idbook").in(id));
    	Long long1 = (Long)entityManager.createQuery(cq).getSingleResult();
    	return long1>0 ? true : false;
    }
 
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        Book book = getBookXML(id);
        if(null != book) {
            entityManager.remove(book);
            return Response.status(Status.OK).build();
        }
        
        return Response.status(Status.NOT_FOUND).build();
       
    }
}
