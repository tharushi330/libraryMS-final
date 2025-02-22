package edu.icet.librarymanagmentsystem.service.custome.impl;

import edu.icet.librarymanagmentsystem.dbconnection.DBConnection;
import edu.icet.librarymanagmentsystem.dto.Book;
import edu.icet.librarymanagmentsystem.dto.BorrowRecords;
import edu.icet.librarymanagmentsystem.dto.Fine;
import edu.icet.librarymanagmentsystem.dto.Member;
import edu.icet.librarymanagmentsystem.entity.BookEntity;
import edu.icet.librarymanagmentsystem.entity.BorrowRecordsEntity;
import edu.icet.librarymanagmentsystem.entity.FineEntity;
import edu.icet.librarymanagmentsystem.entity.MemberEntity;
import edu.icet.librarymanagmentsystem.repository.DaoFactory;
import edu.icet.librarymanagmentsystem.repository.custom.BookDao;
import edu.icet.librarymanagmentsystem.repository.custom.BorrowDao;
import edu.icet.librarymanagmentsystem.service.custome.BorrowService;
import edu.icet.librarymanagmentsystem.util.DaoType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {

    BorrowDao borrowDao = DaoFactory.getInstance().getDaoType(DaoType.BORROW);
    BookDao bookDao=DaoFactory.getInstance().getDaoType(DaoType.BOOK);

    private static BorrowServiceImpl instance;

    private BorrowServiceImpl() {
    }

    public static BorrowServiceImpl getInstance() {
        return instance == null ? new BorrowServiceImpl() : instance;
    }

    @Override
    public Member searchMemberById(String memberId) throws SQLException {
        MemberEntity memberEntity = borrowDao.searchById(memberId);
        if (memberEntity == null) {
            return null;
        }
        return new Member(memberEntity.getId(), memberEntity.getName(), memberEntity.getContact(), memberEntity.getDate());

    }

    @Override
    public Book searchBookById(String bookId) throws SQLException {
        BookEntity bookEntity = borrowDao.searchBookById(bookId);
        if (bookEntity == null) {
            return null;
        }
        return new Book(bookEntity.getBookId(), bookEntity.getIsbn(), bookEntity.getBookTitle(),
                bookEntity.getAuthor(), bookEntity.getCategoryId(), bookEntity.getAvailabilityStatus());
    }

    @Override
    public boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            String borrowId = generateNextBorrowId();

            BorrowRecordsEntity borrowRecord = new BorrowRecordsEntity(
                    borrowId,
                    memberId,
                    bookId,
                    borrowDate,
                    returnDate,
                    null, // DateGiven is null
                    false // isReturned is false by default
            );
            boolean isBorrowRecordSaved = borrowDao.saveTheRecord(borrowRecord, connection);

            // Step 3: Update the book's availability status to 'Borrowed'
            boolean isBookAvailabilityUpdated = bookDao.updateAvailabilityStatus(bookId, "Borrowed", connection);

            if (isBorrowRecordSaved && isBookAvailabilityUpdated) {
                connection.commit(); // Commit transaction
                return true;
            } else {
                connection.rollback(); // Rollback transaction
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Rollback transaction on error
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // Reset auto-commit
                connection.close(); // Close connection
            }
        }
    }

    @Override
    public boolean hasUnreturnedBooks(String memberId) throws SQLException {
        return borrowDao.hasUnreturnedBooks(memberId);
    }

    @Override
    public List<BorrowRecords> getBorrowRecordsByMemberId(String memberId) throws SQLException {
        List<BorrowRecordsEntity> borrowRecordsByMemberId = borrowDao.getBorrowRecordsByMemberId(memberId);
        List<BorrowRecords> borrowRecordsList = new ArrayList<>();

        for (BorrowRecordsEntity entity : borrowRecordsByMemberId) {
            BorrowRecords record = new BorrowRecords();
            record.setBorrowId(entity.getBorrowId());
            record.setMemberId(entity.getMemberId());
            record.setBookId(entity.getBookId());
            record.setBorrowDate(entity.getBorrowDate());
            record.setReturnDate(entity.getReturnDate());

            borrowRecordsList.add(record);
        }
        return borrowRecordsList;
    }

    @Override
    public BorrowRecords getBorrowRecordByBookId(String selectedBookId) throws SQLException {
        BorrowRecordsEntity borrowRecordByBookId = borrowDao.getBorrowRecordByBookId(selectedBookId);

        if (borrowRecordByBookId == null) {
            return null;
        }

        BorrowRecords record = new BorrowRecords();
        record.setBorrowId(borrowRecordByBookId.getBorrowId());
        record.setMemberId(borrowRecordByBookId.getMemberId());
        record.setBookId(borrowRecordByBookId.getBookId());
        record.setBorrowDate(borrowRecordByBookId.getBorrowDate());
        record.setReturnDate(borrowRecordByBookId.getReturnDate());

        return record;
    }

    @Override
    public boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Get the BorrowID for the selected book
            String borrowId = borrowDao.getBorrowIdByBookId(selectedBookId, connection);

            if (borrowId == null) {
                throw new SQLException("No borrow record found for the selected book!");
            }

            // Step 2: Update borrow record as returned
            boolean isBorrowUpdated = borrowDao.updateBorrowRecordAsReturned(selectedBookId, today, connection);

            // Step 3: Update book availability status to "Available"
            boolean isBookUpdated = bookDao.updateAvailabilityStatus(selectedBookId, "Available", connection);

            // Step 4: Save fine record if applicable
            boolean isFineSaved = true;
            if (fineAmount > 0) {
                FineEntity fine = new FineEntity();
                fine.setBorrowId(borrowId); // Use the fetched BorrowID
                fine.setFineAmount(BigDecimal.valueOf(fineAmount));
                fine.setPaymentDate(LocalDate.now());
                isFineSaved = borrowDao.saveFine(fine, connection);
            }


            if (isBorrowUpdated && isBookUpdated && isFineSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    private String generateNextBorrowId() throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            String lastBorrowId = borrowDao.getLastBorrowId(connection);

            if (lastBorrowId == null) {
                return "BR0001";
            } else {

                int lastNumber = Integer.parseInt(lastBorrowId.substring(2));
                int nextNumber = lastNumber + 1;


                return String.format("BR%04d", nextNumber);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }
}
