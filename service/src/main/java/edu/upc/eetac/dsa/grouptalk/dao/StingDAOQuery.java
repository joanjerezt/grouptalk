package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by juan on 30/09/15.
 */
public class StingDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_STING = "insert into stings (id, userid, subject, content, groupid) values (UNHEX(?), unhex(?), ?, ?,unhex(?))";
    public final static String GET_STING_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.content, s.subject, s.creation_timestamp, s.last_modified, u.id from stings s, users u where s.id=unhex(?) and u.id=s.userid";
    public final static String GET_STINGS = "select hex(id) as id, hex(userid) as userid, subject as subject from stings where groupid=unhex(?)";
    public final static String UPDATE_STING = "update stings set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_STING = "delete from stings where id=unhex(?)";
    public final static String CREATE_THREAD="insert into thread (id, stingid) values (UNHEX(?), unhex(?))";
    public final static String DELETE_THREAD="delete from thread where id=unhex(?)";
    public final static String CHECK_FIRST_POST="SELECT CASE WHEN EXISTS(select hex(id) as stingid, hex(userid) as userid, hex(id) as threadid from thread where stingid!=unhex(?) and threadid=unhex(?))" +
            "THEN CAST(0 AS BIT)" +
            "ELSE CAST(1 AS BIT) END";
    public static final String GET_STINGS_BY_THREAD = "select hex(s.id) as id, hex(s.userid) as userid, s.content, s.subject, s.creation_timestamp, s.last_modified, t.id from stings s, thread t where t.id=unhex(?)";
}
