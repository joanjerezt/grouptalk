package edu.upc.eetac.dsa.grouptalk.dao;


import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import java.sql.SQLException;

/**
 * Created by juan on 30/09/15.
 */
public interface StingDAO {
    public Sting createSting(String userid, String subject, String content, String groupid) throws SQLException;
    public Sting getStingById(String id) throws SQLException;

    public Sting updateSting(String id, String subject, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;

    public StingCollection getStingsByGroup(String groupid) throws SQLException;
}
