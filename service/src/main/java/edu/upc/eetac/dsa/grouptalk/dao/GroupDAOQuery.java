package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by juan on 30/10/15.
 */
public interface GroupDAOQuery {
    String UUID = "select REPLACE(UUID(),'-','')";
    String CREATE_GROUP = "insert into groups (id, groupname) values (UNHEX(?), ?)";
    String JOIN_GROUP = "insert into group_users (userid, groupid) values (unhex(?), unhex(?))";
    String LEAVE_GROUP = "delete from group_users (userid, groupid) values (unhex(?), unhex(?))";
    String GET_GROUP_BY_ID = "select hex(id) as id, groupname FROM groups WHERE id = unhex(?)";
    String GET_GROUP = "select hex(id) as id, groupname FROM groups";
    String DELETE_GROUP = "delete from groups where id=unhex(?)";
    String IS_SUSCRIBED = "select hex(groupid) as groupid FROM group_users WHERE userid=unhex(?)";
}
