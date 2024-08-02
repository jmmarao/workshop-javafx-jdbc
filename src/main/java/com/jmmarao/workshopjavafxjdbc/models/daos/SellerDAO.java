package com.jmmarao.workshopjavafxjdbc.models.daos;

import com.jmmarao.workshopjavafxjdbc.models.entities.Department;
import com.jmmarao.workshopjavafxjdbc.models.entities.Seller;

import java.util.List;

public interface SellerDAO {
    void insert(Seller seller);

    void update(Seller seller);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();

    List<Seller> findByDepartment(Department department);
}
