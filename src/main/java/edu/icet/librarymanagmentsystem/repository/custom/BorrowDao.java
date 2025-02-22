package edu.icet.librarymanagmentsystem.repository.custom;

import edu.icet.librarymanagmentsystem.dto.Book;
import edu.icet.librarymanagmentsystem.dto.BorrowRecords;
import edu.icet.librarymanagmentsystem.dto.Fine;
import edu.icet.librarymanagmentsystem.entity.BookEntity;
import edu.icet.librarymanagmentsystem.entity.BorrowRecordsEntity;
import edu.icet.librarymanagmentsystem.entity.FineEntity;
import edu.icet.librarymanagmentsystem.entity.MemberEntity;
import edu.icet.librarymanagmentsystem.repository.CrudDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowDao extends CrudDao<BookEntity,String> {
    BookEntity searchBookById(String bookId) throws SQLException;

    MemberEntity searchById(String memberId) throws SQLException;

    boolean saveTheRecord(BorrowRecordsEntity borrowRecord, Connection connection) throws SQLException;

    String getLastBorrowId(Connection connection) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowRecordsEntity> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowRecordsEntity getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean updateBorrowRecordAsReturned(String selectedBookId, LocalDate today, Connection connection);

    String getBorrowIdByBookId(String selectedBookId, Connection connection) throws SQLException;

    boolean saveFine(FineEntity fine, Connection connection) throws SQLException;
}
