package com.example.a1_Intro;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    @GetMapping("/")

    public ResponseEntity<List<Book>> index() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("book", "are you sure");

        List<Book> books = new ArrayList<>();
        books.add(new Book("dd", "dd", "dd"));
        books.add(new Book("cc", "dd", "dd"));

        /*return new ResponseEntity<>(books, headers, HttpStatus.OK);*/
        return ResponseEntity.ok().headers(headers).body(books);
    }
}
