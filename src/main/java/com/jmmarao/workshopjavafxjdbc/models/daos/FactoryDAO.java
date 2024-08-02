package com.jmmarao.workshopjavafxjdbc.models.daos;

import com.jmmarao.workshopjavafxjdbc.config.database.DataBaseConfig;
import com.jmmarao.workshopjavafxjdbc.models.daos.impl.DepartmentDAOJDBC;
import com.jmmarao.workshopjavafxjdbc.models.daos.impl.SellerDAOJDBC;

public class FactoryDAO {
    public static SellerDAO createSellerDAO() {
        return new SellerDAOJDBC(DataBaseConfig.getConnection());
    }

    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentDAOJDBC(DataBaseConfig.getConnection());
    }
}
