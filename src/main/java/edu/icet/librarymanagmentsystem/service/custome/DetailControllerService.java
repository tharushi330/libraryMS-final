package edu.icet.librarymanagmentsystem.service.custome;

import edu.icet.librarymanagmentsystem.service.SuperService;

public interface DetailControllerService extends SuperService {
    Integer getCustomerCount();

    Integer getBooksCount();

    Double getFineCount();

    Integer getOverDueBooksCount();

    Integer getUnpaidFineCount();
}
