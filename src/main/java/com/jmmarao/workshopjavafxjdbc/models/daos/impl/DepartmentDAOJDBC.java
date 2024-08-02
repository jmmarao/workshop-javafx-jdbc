package com.jmmarao.workshopjavafxjdbc.models.daos.impl;

import com.jmmarao.workshopjavafxjdbc.config.database.DataBaseConfig;
import com.jmmarao.workshopjavafxjdbc.exceptions.DbException;
import com.jmmarao.workshopjavafxjdbc.models.daos.DepartmentDAO;
import com.jmmarao.workshopjavafxjdbc.models.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOJDBC implements DepartmentDAO {

    private final Connection connection;

    public DepartmentDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO department (Name) VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, department.getName());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int idGenerated = resultSet.getInt(1);
                department.setId(idGenerated);
            }
            DataBaseConfig.closeResultSet(resultSet);
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement preparedStatement = null;
        String sqlQuery = "UPDATE department SET Name = ? WHERE Id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        String sqlQuery = "DELETE FROM department WHERE Id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT dept.* FROM department dept WHERE dept.Id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
            }
            return null;
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
            DataBaseConfig.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT dept.* FROM department dept ORDER BY dept.Name";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                departments.add(instantiateDepartment(resultSet));
            }
            return departments;
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
            DataBaseConfig.closeResultSet(resultSet);
        }
    }

    @Override
    public Department findByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT dept.* FROM department dept WHERE dept.Name = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
            }
            return null;
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
            DataBaseConfig.closeResultSet(resultSet);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("Id"),
                resultSet.getString("Name")
        );
    }
}
