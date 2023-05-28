package br.com.rest.services;

import br.com.rest.controllers.BookController;
import br.com.rest.data.vo.v1.BookVO;
import br.com.rest.mapper.ModelMapperAdapter;
import br.com.rest.mapper.custom.PersonMapper;
import br.com.rest.model.Book;
import br.com.rest.repositories.BookRepository;
import expections.RequiredObjectsIsNullException;
import expections.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PersonMapper mapper;

    public BookVO findById(Long id) throws Exception {
        logger.info("Finding one book!");

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No records found for this ID"));

        BookVO vo = ModelMapperAdapter.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public List<BookVO> findAll() {
        logger.info("Finding all people");
        var books = ModelMapperAdapter.parseListObject(bookRepository.findAll(), BookVO.class);
        books
                .forEach(p -> {
                    try {
                        p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel());
                    } catch (Exception e) {
                        throw new ResourceNotFoundException("No records found");
                    }
                });
        return books;
    }

    public BookVO createBook(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectsIsNullException();

        logger.info("Creating a book");
        var entity = ModelMapperAdapter.parseObject(book, Book.class);
        var vo = ModelMapperAdapter.parseObject(bookRepository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());

        return vo;
    }

    public BookVO updateBook(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectsIsNullException();

        logger.info("Creating a book");
        var entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = ModelMapperAdapter.parseObject(bookRepository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());

        return vo;
    }

    public void deleteBook(Long id) {
        logger.info("Deleting a book");
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(entity);
    }
}
