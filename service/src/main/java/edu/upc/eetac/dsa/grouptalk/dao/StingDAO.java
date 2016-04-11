package edu.upc.eetac.dsa.grouptalk.dao;


import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface StingDAO {
    public Sting createSting(String userid, String subject, String content, String groupid, String threadid) throws SQLException;
    public Sting getStingById(String id) throws SQLException;
    StingCollection getStingsByThread(String thread) throws SQLException;
    Sting updateSting(String id, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;
    public StingCollection getStingsByGroup(String groupid) throws SQLException;
    boolean firstpost(String id) throws SQLException;
    boolean deleteThread(String id) throws SQLException;
}
