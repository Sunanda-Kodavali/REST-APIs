package com.example.book_store.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="inventory")
public class Inventory {

    @Id
    @Column(name = "book_id")
    private Long bookId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;

    private LocalDate restockDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getRestockDate() {
        return restockDate;
    }

    public void setRestockDate(LocalDate restockDate) {
        this.restockDate = restockDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return quantity == inventory.quantity && Objects.equals(bookId, inventory.bookId) && Objects.equals(book, inventory.book) && Objects.equals(restockDate, inventory.restockDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, book, quantity, restockDate);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "bookId=" + bookId +
                ", book=" + book +
                ", quantity=" + quantity +
                ", restockDate=" + restockDate +
                '}';
    }
}
