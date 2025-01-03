package com.jmmarao.workshopjavafxjdbc.models.daos.impl;

import com.jmmarao.workshopjavafxjdbc.config.database.DataBaseConfig;
import com.jmmarao.workshopjavafxjdbc.exceptions.DbException;
import com.jmmarao.workshopjavafxjdbc.models.daos.SellerDAO;
import com.jmmarao.workshopjavafxjdbc.models.entities.Department;
import com.jmmarao.workshopjavafxjdbc.models.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAOJDBC implements SellerDAO {

    private Connection connection;

    public SellerDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement preparedStatement = null;
        String sqlQuery =
                "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int idGenerated = resultSet.getInt(1);
                    seller.setId(idGenerated);
                }
                DataBaseConfig.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement preparedStatement = null;
        String sqlQuery =
                "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                        "WHERE Id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());

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
        String sqlQuery =
                "DELETE FROM seller WHERE Id = ?";

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
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery =
                "SELECT seller.*, dept.Name as DeptName " +
                        "FROM seller " +
                        "JOIN department dept ON seller.DepartmentId = dept.Id " +
                        "WHERE seller.Id = ?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                Seller seller = instantiateSeller(resultSet, department);
                return seller;
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
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery =
                "SELECT seller.*, dept.Name as DeptName " +
                        "FROM seller " +
                        "JOIN department dept ON seller.DepartmentId = dept.Id " +
                        "ORDER BY seller.Name";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (resultSet.next()) {
                Department departmentValue = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (departmentValue == null) {
                    departmentValue = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), departmentValue);
                }

                Seller seller = instantiateSeller(resultSet, departmentValue);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
            DataBaseConfig.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery =
                "SELECT seller.*, dept.Name as DeptName " +
                        "FROM seller " +
                        "JOIN department dept ON seller.DepartmentId = dept.Id " +
                        "WHERE dept.Id = ? " +
                        "ORDER BY seller.Name";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (resultSet.next()) {
                Department departmentValue = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (departmentValue == null) {
                    departmentValue = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), departmentValue);
                }

                Seller seller = instantiateSeller(resultSet, departmentValue);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DataBaseConfig.closeStatement(preparedStatement);
            DataBaseConfig.closeResultSet(resultSet);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("DepartmentId"),
                resultSet.getString("DeptName")
        );
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        return new Seller(
                resultSet.getInt("Id"),
                resultSet.getString("Name"),
                resultSet.getString("Email"),
                new Date(resultSet.getTimestamp("BirthDate").getTime()),
                resultSet.getDouble("BaseSalary"),
                department
        );
    }
}
