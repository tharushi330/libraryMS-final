package edu.icet.librarymanagmentsystem.service.custome;

import edu.icet.librarymanagmentsystem.dto.Member;
import edu.icet.librarymanagmentsystem.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface MemberService extends SuperService {

    boolean addMember(Member member) throws SQLException;
    List<Member> getAllMembers() throws SQLException;
    String showtheId() throws  SQLException;
    Member searchMemberById(String memberId) throws SQLException;
    boolean deleteMember(String memberId) throws SQLException;
    boolean updateMember(Member member) throws SQLException;

}
