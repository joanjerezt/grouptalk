package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by juan on 30/10/15.
 */
public interface GroupDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GROUP = "insert into groups (id, groupname) values (UNHEX(?), ?)";
    public final static String JOIN_GROUP = "insert into group_users (userid, groupid) values (unhex(?), unhex(?))";
    public final static String LEAVE_GROUP = "delete from group_users (userid, groupid) values (unhex(?), unhex(?))";
    public final static String GET_GROUP_BY_ID = "select hex(id) as id, groupname FROM groups WHERE id = unhex(?)";
    public final static String GET_GROUP = "select hex(id) as id, groupname FROM groups";
    public final static String DELETE_GROUP = "delete from groups where id=unhex(?)";
}
