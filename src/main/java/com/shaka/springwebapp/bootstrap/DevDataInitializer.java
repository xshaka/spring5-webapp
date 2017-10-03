package com.shaka.springwebapp.bootstrap;

import com.shaka.springwebapp.model.Author;
import com.shaka.springwebapp.model.Book;
import com.shaka.springwebapp.repository.AuthorRepository;
import com.shaka.springwebapp.repository.BookRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DevDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public DevDataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();

    }

    private void initData() {
        Author eric = new Author("Eric", "Evans");
        Book ericBook = new Book("Domain Driven Design", "1234", "Harper Collins");
        publishAuthorWork(eric, ericBook);

        Author rod = new Author("Rod", "Johnson");
        Book rodBook = new Book("J2EE Development without EJB", "23444", "Worx");
        publishAuthorWork(rod, rodBook);
    }

    private void publishAuthorWork(Author author, Book... books) {
        Arrays.stream(books).forEach(book -> {
            author.getBooks().add(book);
            book.getAuthors().add(author);
        });
        authorRepository.save(author);
    }
}
