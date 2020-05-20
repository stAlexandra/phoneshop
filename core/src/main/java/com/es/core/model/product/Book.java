package com.es.core.model.product;

import java.time.LocalDate;

public class Book extends Product {
    private String author;
    private String genre;
    private LocalDate dateOfPublishing;
    private String publisher;
    private Long pageCount;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(LocalDate dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }
}
