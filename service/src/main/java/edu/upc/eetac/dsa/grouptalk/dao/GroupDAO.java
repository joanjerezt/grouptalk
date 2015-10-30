package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.GroupCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/10/15.
 */
public interface GroupDAO {
    public Group createGroup(String userid, String groupname) throws SQLException;
    public Group getGroupById(String id) throws SQLException;
    public GroupCollection getGroups() throws SQLException;
    public void joinGroup(String userid, String groupid) throws SQLException;
    public boolean deleteGroup(String id) throws SQLException;
    public void leaveGroup (String userid, String groupid) throws SQLException;
}