package com.jmmarao.workshopjavafxjdbc.services;

import com.jmmarao.workshopjavafxjdbc.models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll() {
        List<Department> departmentList = new ArrayList<>();

        departmentList.add(new Department(1, "Books"));
        departmentList.add(new Department(2, "Computers"));
        departmentList.add(new Department(3, "Eletronics"));

        return departmentList;
    }
}
