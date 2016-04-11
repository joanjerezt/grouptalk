package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.GroupCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/10/15.
 */
public interface GroupDAO {
    Group createGroup(String userid, String groupname) throws SQLException;
    Group getGroupById(String id) throws SQLException;
    GroupCollection getGroups() throws SQLException;
    void joinGroup(String userid, String groupid) throws SQLException;
    boolean deleteGroup(String id) throws SQLException;
    void leaveGroup (String userid, String groupid) throws SQLException;
    boolean isSuscribed(String userid, String groupid) throws SQLException;
}