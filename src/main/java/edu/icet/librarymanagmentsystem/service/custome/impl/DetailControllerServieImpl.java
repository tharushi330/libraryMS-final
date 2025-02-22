package edu.icet.librarymanagmentsystem.service.custome.impl;

import edu.icet.librarymanagmentsystem.repository.DaoFactory;
import edu.icet.librarymanagmentsystem.repository.custom.DetailDao;
import edu.icet.librarymanagmentsystem.service.custome.DetailControllerService;
import edu.icet.librarymanagmentsystem.util.DaoType;

public class DetailControllerServieImpl implements DetailControllerService {

    DetailDao detailDao= DaoFactory.getInstance().getDaoType(DaoType.DETAILSCONTROLLER);

    private static DetailControllerServieImpl instance;

    private DetailControllerServieImpl(){};

    public static DetailControllerServieImpl getInstance(){
        return instance==null?new DetailControllerServieImpl():instance;
    }

    @Override
    public Integer getCustomerCount() {
        return detailDao.getCustomerCount();
    }

    @Override
    public Integer getBooksCount() {
        return detailDao.getBookCount();
    }

    @Override
    public Double getFineCount() {
        return detailDao.getFineCount();
    }

    @Override
    public Integer getOverDueBooksCount() {
        return detailDao.getOverDueBooksCount();
    }

    @Override
    public Integer getUnpaidFineCount() {
        return detailDao.getUnpaidFineCount();
    }
}
