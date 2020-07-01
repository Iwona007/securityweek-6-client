package pl.iwona.week6clientsecurity.controller;

import org.springframework.stereotype.Controller;
import pl.iwona.week6clientsecurity.service.BookService;

@Controller
public class BookClientApp {

    private BookService bookService;

    public BookClientApp(BookService bookService) {
        this.bookService = bookService;

        bookService.postObject();
        bookService.getObject();
    }
}
