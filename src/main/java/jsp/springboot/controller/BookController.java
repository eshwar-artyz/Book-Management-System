package jsp.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Book;
import jsp.springboot.exception.IdNotFoundException;
import jsp.springboot.exception.NoRecordAvailableException;
import jsp.springboot.repository.BookRepository;
import jsp.springboot.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookService bookservice;

    // insert one record into the db
    @PostMapping
    public ResponseEntity<ResponseStructure<Book>> saveBook(@RequestBody Book book) {
    	return bookservice.saveBook(book);
    }

    // insert multiple records into the db
    @PostMapping("/all")
    public ResponseEntity<ResponseStructure<List<Book>>> saveBooks(@RequestBody List<Book> books) {
    	return bookservice.saveAllBooks(books);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Book>> getBookById(@PathVariable Integer id) {
    	Optional<Book> opt = bookRepository.findById(id);
    	if(!opt.isEmpty()) {
    		ResponseStructure<Book> structure = new ResponseStructure<Book>();
    		structure.setStatusCode(HttpStatus.FOUND.value());
    		structure.setMessage("Book found successfully");
    		structure.setData(opt.get());
    		return new ResponseEntity<ResponseStructure<Book>>(structure,HttpStatus.FOUND);
    	}
    	else 
    		throw new IdNotFoundException("No such id exists");
    }

    // fetch all records from db
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        ResponseStructure<List<Book>> structure = new ResponseStructure<>();
        if (books.isEmpty()) {
        	throw new IdNotFoundException("No such id exists");
        }
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Books fetched successfully");
        structure.setData(books);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // update record in db
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Book>> updateBookById(
            @PathVariable Integer id,
            @RequestBody Book book) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        ResponseStructure<Book> structure = new ResponseStructure<>();

        if (!optionalBook.isEmpty()) {
            book.setId(id);
            Book updatedBook = bookRepository.save(book);
            structure.setStatusCode(HttpStatus.ACCEPTED.value());
            structure.setMessage("Book updated successfully");
            structure.setData(updatedBook);

            return new ResponseEntity<>(structure, HttpStatus.ACCEPTED);
        }

        else if(book == null) {
        	throw new NoRecordAvailableException("No such records exists");
        }
        
        else {
        	throw new IdNotFoundException("No such id exists");
        }

    }

    // delete record from db
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Book>> deleteBook(@PathVariable Integer id) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        ResponseStructure<Book> structure = new ResponseStructure<>();

        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());

            structure.setStatusCode(HttpStatus.ACCEPTED.value());
            structure.setMessage("Book deleted successfully");
            structure.setData(optionalBook.get());

            return new ResponseEntity<>(structure, HttpStatus.ACCEPTED);
        }

        else {
        	throw new IdNotFoundException("No such id exists");
        }
    }
    
    @GetMapping("/author/{author}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(@PathVariable String author) {
    	List<Book> books = bookRepository.findByAuthor(author);
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
    @GetMapping("/author/{author}/{title}")
    public ResponseEntity<ResponseStructure<Book>> getBookByAuthorAndTitle(@PathVariable String author,@PathVariable String title) {
    	Optional<Book> books = bookRepository.findByAuthorAndTitle(author,title);
    	ResponseStructure<Book> response = new ResponseStructure<>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books.get());
    		return new ResponseEntity<ResponseStructure<Book>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
    @GetMapping("/price/{price}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPriceGreaterThan(@PathVariable Double price) {
    	List<Book> books = bookRepository.findByPriceGreaterthan(price);
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
    @GetMapping("/price/{price}/{price}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPriceBetween(@PathVariable Double start,@PathVariable Double end) {
    	List<Book> books = bookRepository.findByPriceBetween(start, end);
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
    @GetMapping("/available")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByAvailability() {
    	List<Book> books = bookRepository.getBookByAvailability();
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
    @GetMapping("/genre/{genre}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByGenre(@PathVariable String genre) {
    	List<Book> books = bookRepository.getBookByGenre(genre);
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }

    @GetMapping("/publishedYear/{publishedYear}")
    public ResponseEntity<ResponseStructure<List<Book>>> getBookByPublishedYear(@PathVariable Integer publishedYear) {
    	List<Book> books = bookRepository.getBookByPublishedYear(publishedYear);
    	ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();
    	if(!books.isEmpty()) {
    		response.setStatusCode(HttpStatus.FOUND.value());
    		response.setMessage("Author is found");
    		response.setData(books);
    		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.FOUND);
    	}
    	else 
    		throw new NoRecordAvailableException("No such author");
    }
    
}
