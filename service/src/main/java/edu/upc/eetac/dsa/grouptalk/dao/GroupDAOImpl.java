package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.GroupCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 30/10/15.
 */
public class GroupDAOImpl implements GroupDAO {
    @Override
    public Group createGroup(String userid, String groupname) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt       = null;
        String            groupid    = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                groupid = rs.getString(1);
            } else {
                throw new SQLException();
            }

            stmt = connection.prepareStatement(GroupDAOQuery.CREATE_GROUP);
            stmt.setString(1, groupid);
            stmt.setString(2, userid);
            stmt.setString(3, groupname);

            System.out.println(stmt.toString());

            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return this.getGroupById(groupid);
    }

    @Override
    public Group getGroupById(String id) throws SQLException
    {
        Group group = null;

        Connection        connection = null;
        PreparedStatement stmt       = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUP_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                group = new Group(rs.getString("id"), rs.getString("groupname"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return group;
    }

    @Override
    public GroupCollection getGroups() throws SQLException
    {

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUP);

            ResultSet rs = stmt.executeQuery();
            List<Group> groups = new ArrayList<>();
            while (rs.next()) {
                groups.add( new Group(rs.getString("id"), rs.getString("name")));
            }
            return new GroupCollection(groups);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean deleteGroup(String id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.DELETE_GROUP);
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
    public void joinGroup(String userid, String groupid) throws SQLException
    {
        Connection        connection = null;
        PreparedStatement stmt       = null;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.JOIN_GROUP);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);

            System.out.println(stmt.toString());

            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
    @Override
    public void leaveGroup(String userid, String groupid) throws SQLException
    {
        Connection        connection = null;
        PreparedStatement stmt       = null;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.LEAVE_GROUP);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);

            System.out.println(stmt.toString());

            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}