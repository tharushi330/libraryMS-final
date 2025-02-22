package edu.icet.librarymanagmentsystem.dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Book {
    private String bookId;
    private String isbn;
    private String bookTitle;
    private String author;
    private Integer categoryId;
    private String availabilityStatus;
}