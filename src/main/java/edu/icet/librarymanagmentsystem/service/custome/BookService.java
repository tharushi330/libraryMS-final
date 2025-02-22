package edu.icet.librarymanagmentsystem.service.custome;

import edu.icet.librarymanagmentsystem.dto.Book;
import edu.icet.librarymanagmentsystem.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BookService extends SuperService {
    ArrayList<String> getAllCategoryIds() throws SQLException;

    boolean addBook(Book book) throws SQLException;

    String showtheId();

    List<Book> getAllBooks() throws SQLException;

    boolean deleteMember(String bookId) throws SQLException;

    boolean updateBook(Book book) throws SQLException;

    Book searchBookById(String bookId) throws SQLException;
}
