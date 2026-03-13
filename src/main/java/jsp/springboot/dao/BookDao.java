package jsp.springboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.Book;
import jsp.springboot.repository.BookRepository;

@Repository
public class BookDao {

	@Autowired
	private BookRepository bookRepository;
	
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	public List<Book> saveAllBooks(List<Book> books) {
		return bookRepository.saveAll(books);
	}
	
	public Optional<Book> getBookById(Integer id) {
		return bookRepository.findById(id);
	}
	public Book updateBookById(Integer id,Book book) {
		return bookRepository.save(book);
	}
	
}
