package edu.icet.librarymanagmentsystem.entity;


import lombok.*;
import org.checkerframework.checker.units.qual.A;


@NoArgsConstructor
@Data
@ToString
@Getter
@Setter
@AllArgsConstructor

public class BookEntity {
    private String bookId;
    private String isbn;
    private String bookTitle;
    private String author;
    private Integer categoryId;
    private String availabilityStatus;
}