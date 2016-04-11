package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Sting;
import edu.upc.eetac.dsa.grouptalk.entity.StingCollection;

import java.sql.*;

/**
 * Created by juan on 30/09/15.
 */
public class StingDAOImpl implements StingDAO {

    @Override
    public Sting createSting(String userid, String subject, String content, String groupid, String threadid) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                id = rs.getString(1);
                threadid = rs.getString(1);
            }
            else{
                throw new SQLException();
            }
            stmt.close();

            stmt = connection.prepareStatement(StingDAOQuery.CREATE_STING);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, content);
            stmt.setString(5, groupid);
            stmt.executeUpdate();

            stmt = connection.prepareStatement(StingDAOQuery.CREATE_THREAD);
            stmt.setString(1,threadid);
            stmt.setString(2,id);
            stmt.setString(3, userid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getStingById(id);
    }

    @Override
    public Sting getStingById(String id) throws SQLException
    {
        Sting sting = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.GET_STING_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sting = new Sting();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setSubject(rs.getString("subject"));
                sting.setContent(rs.getString("content"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return sting;
    }

    @Override
    public StingCollection getStingsByThread(String thread) throws SQLException {
        StingCollection stingCollection = new StingCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.GET_STINGS_BY_THREAD);


            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Sting sting = new Sting();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setSubject(rs.getString("subject"));
                stingCollection.getStings().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }

    @Override
    public StingCollection getStingsByGroup(String groupid) throws SQLException {
        StingCollection stingCollection = new StingCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.GET_STINGS);


            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Sting sting = new Sting();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setSubject(rs.getString("subject"));
                stingCollection.getStings().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }

    @Override
    public boolean firstpost(String id) throws SQLException{
        Sting sting = null;
        boolean response = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.CHECK_FIRST_POST);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                response = rs.getBoolean(1);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return response;
    }

    @Override
    public Sting updateSting(String id, String content) throws SQLException {
        Sting sting = null;

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.UPDATE_STING);
            stmt.setString(1, sting.getSubject());
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                sting = getStingById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return sting;
    }

    @Override
    public boolean deleteSting(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.DELETE_STING);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean deleteThread(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.DELETE_THREAD);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        }
        catch (SQLException e){
            throw e;
        }
        finally{
            if(stmt!=null) stmt.close();
            if(connection!=null) connection.close();
        }
    }
}