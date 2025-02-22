package edu.icet.librarymanagmentsystem.service.custome.impl;

import edu.icet.librarymanagmentsystem.dbconnection.DBConnection;
import edu.icet.librarymanagmentsystem.entity.UserEntity;
import edu.icet.librarymanagmentsystem.repository.DaoFactory;
import edu.icet.librarymanagmentsystem.repository.custom.LoginDao;
import edu.icet.librarymanagmentsystem.service.custome.LoginService;
import edu.icet.librarymanagmentsystem.util.DaoType;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {

    LoginDao loginDao= DaoFactory.getInstance().getDaoType(DaoType.LOGIN);

    private static LoginServiceImpl instance;

    private LoginServiceImpl(){

    }

    public static LoginServiceImpl getInstance(){
        return instance == null ? instance=new LoginServiceImpl() : instance;
    }

    @Override
    public boolean authenticateUser(String email, String password) throws SQLException {
        UserEntity userEntity = loginDao.authenticateUser(email, password);
        if (userEntity != null) {
            return true;
        }
        return false;
    }

}
