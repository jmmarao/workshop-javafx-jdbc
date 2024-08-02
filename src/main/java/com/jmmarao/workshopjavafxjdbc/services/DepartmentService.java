package com.jmmarao.workshopjavafxjdbc.services;


import com.jmmarao.workshopjavafxjdbc.models.daos.DepartmentDAO;
import com.jmmarao.workshopjavafxjdbc.models.daos.FactoryDAO;
import com.jmmarao.workshopjavafxjdbc.models.entities.Department;

import java.util.List;

public class DepartmentService {

    private final DepartmentDAO dao = FactoryDAO.createDepartmentDAO();

    public List<Department> findAll() {
        return dao.findAll();
    }
}
