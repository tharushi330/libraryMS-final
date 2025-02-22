package edu.icet.librarymanagmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor

public class BorrowRecordsEntity {
    private String borrowId;
    private String memberId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dateGiven;
    private boolean isReturned;

}
