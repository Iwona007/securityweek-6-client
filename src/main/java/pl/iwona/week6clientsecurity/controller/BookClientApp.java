package pl.iwona.week6clientsecurity.controller;

import org.springframework.stereotype.Controller;
import pl.iwona.week6clientsecurity.service.BookService;
import pl.iwona.week6clientsecurity.service.Token;

@Controller
public class BookClientApp {

    private BookService bookService;
    private Token token;

    public BookClientApp(BookService bookService, Token token) {
        this.bookService = bookService;
        this.token = token;

        bookService.postObject();
        bookService.getObject();
    }
}
