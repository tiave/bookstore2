package fi.haagahelia.course.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.course.domain.Book;
import fi.haagahelia.course.domain.BookRepository;
import fi.haagahelia.course.domain.CategoryRepository;

@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository repository2;
	
		@RequestMapping(value="/login")
		public String login() {	
		    return "login";
		}	
		
	    @RequestMapping(value="/booklist")
	    public String studentList(Model model) {	
	        model.addAttribute("books", repository.findAll());
	        return "booklist";
	    }
	    
	    @RequestMapping(value="/books", method = RequestMethod.GET)
	    public @ResponseBody List<Book> bookListRest() {
	    	return (List<Book>) repository.findAll();
	    }
	    
	    @RequestMapping(value="/book/{id}", method = RequestMethod.GET)
	    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
	    	return repository.findById(bookId);
	    }
	    
	  
	    @RequestMapping(value = "/add")
	    public String addBook(Model model){
	    	model.addAttribute("book", new Book());
	    	model.addAttribute("categories", repository2.findAll());
	        return "addbook";
	    }     
	    
	    @RequestMapping(value = "/save", method = RequestMethod.POST)
	    public String save(Book book){
	        repository.save(book);
	        return "redirect:booklist";
	    }    
	
	    @PreAuthorize("hasAuthority('ADMIN')")
	    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	    public String deleteBook(@PathVariable("id") Long id, Model model) {
	    	repository.deleteById(id);
	        return "redirect:../booklist";
	    }     
	    
	    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	    public String editBook(@PathVariable("id") Long id, Model model) {
	    	model.addAttribute("book", repository.findById(id));
	    	model.addAttribute("categories", repository2.findAll());
	    	return "editbook";
	    }   
}
