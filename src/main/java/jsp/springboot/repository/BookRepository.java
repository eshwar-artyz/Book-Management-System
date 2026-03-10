package jsp.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.springboot.entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {
	
	List<Book> findByAuthor(String author);
	
	Optional<Book> findByAuthorAndTitle(String author,String title);
	
	List<Book> findByPriceGreaterthan(Double price);
	
	List<Book> findByPriceBetween(Double start,Double end);
	
	@Query("Select b from Book b where b.availability = true")
	List<Book> getBookByAvailability();
	
	@Query("Select b from Book b where b.genre =?1")
	List<Book> getBookByGenre(String genre);
	
	@Query("Select b from Book b where b.publishedYear = :publishedYear")
	List<Book> getBookByPublishedYear(Integer publishedYear);

}