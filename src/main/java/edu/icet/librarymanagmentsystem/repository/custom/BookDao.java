package edu.icet.librarymanagmentsystem.repository.custom;

import edu.icet.librarymanagmentsystem.entity.BookEntity;
import edu.icet.librarymanagmentsystem.repository.CrudDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDao extends CrudDao<BookEntity,String> {

    ArrayList<String> getAllCategoryIds() throws SQLException;

    String getLastBookID();

    boolean updateAvailabilityStatus(String bookId, String borrowed, Connection connection) throws SQLException;
}
