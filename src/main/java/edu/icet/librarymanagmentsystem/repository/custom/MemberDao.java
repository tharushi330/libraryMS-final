package edu.icet.librarymanagmentsystem.repository.custom;

import edu.icet.librarymanagmentsystem.dto.Member;
import edu.icet.librarymanagmentsystem.entity.MemberEntity;
import edu.icet.librarymanagmentsystem.repository.CrudDao;

import java.sql.SQLException;

public interface MemberDao extends CrudDao<MemberEntity,String> {

    String getLastMemberID() throws SQLException;

}
