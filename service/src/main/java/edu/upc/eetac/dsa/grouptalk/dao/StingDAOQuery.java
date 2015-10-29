package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by juan on 30/09/15.
 */
public class StingDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_STING = "insert into stings (id, userid, subject, content) values (UNHEX(?), unhex(?), ?, ?)";
    public final static String GET_STING_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.content, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where s.id=unhex(?) and u.id=s.userid";
    // public final static String GET_STINGS = "select hex(s.id) as id, hex(s.userid) as userid, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where creation_timestamp < ? and u.id=s.userid order by creation_timestamp desc limit 5";
    public final static String GET_STINGS = "select hex(id) as id, hex(userid) as userid, subject, creation_timestamp, last_modified from stings where creation_timestamp < ? order by creation_timestamp desc limit 5";
    public final static String GET_STINGS_AFTER = "select hex(id) as id, hex(userid) as userid, subject, creation_timestamp, last_modified from stings  where creation_timestamp > ? order by creation_timestamp desc limit 5";
    public final static String UPDATE_STING = "update stings set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_STING = "delete from stings where id=unhex(?)";
}
