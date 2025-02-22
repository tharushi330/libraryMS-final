package edu.icet.librarymanagmentsystem.repository.custom;

import edu.icet.librarymanagmentsystem.dto.Book;
import edu.icet.librarymanagmentsystem.repository.CrudDao;

public interface DetailDao extends CrudDao<Book,String> {

    Integer getCustomerCount();

    Integer getBookCount();

    Double getFineCount();

    Integer getOverDueBooksCount();

    Integer getUnpaidFineCount();
}
