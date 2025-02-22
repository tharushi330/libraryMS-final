package edu.icet.librarymanagmentsystem.service.custome;

import edu.icet.librarymanagmentsystem.dto.Book;
import edu.icet.librarymanagmentsystem.dto.BorrowRecords;
import edu.icet.librarymanagmentsystem.dto.Member;
import edu.icet.librarymanagmentsystem.service.SuperService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowService extends SuperService {
    Member searchMemberById(String memberId) throws SQLException;

    Book searchBookById(String bookId) throws SQLException;

    boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException;
}
