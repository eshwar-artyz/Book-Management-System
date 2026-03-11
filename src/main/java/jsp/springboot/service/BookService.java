package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.BookDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Book;
import jsp.springboot.exception.NoRecordAvailableException;

@Service
public class BookService {

	@Autowired
	private BookDao bookDao;
	
	public ResponseEntity<ResponseStructure<Book>> saveBook(Book book) {

        ResponseStructure<Book> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Book saved successfully");
        structure.setData(bookDao.saveBook(book));

        return new ResponseEntity<>(structure, HttpStatus.CREATED);

	}
	
	public ResponseEntity<ResponseStructure<List<Book>>> saveAllBooks(List<Book> books) {

        ResponseStructure<List<Book>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Books saved successfully");
        structure.setData(bookDao.saveAllBooks(books));

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Book>> getBookById(int id) {
		
		Optional<Book> opt = bookDao.getBookById(id);
		ResponseStructure<Book> response = new ResponseStructure<Book>();
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage("Book found successfully");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Book>>(response, HttpStatus.FOUND);
		}
		else 
			throw new NoRecordAvailableException("There is no book in the given id");
	}
	
}
