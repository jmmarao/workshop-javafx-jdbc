package com.jmmarao.workshopjavafxjdbc.services;


import com.jmmarao.workshopjavafxjdbc.models.daos.FactoryDAO;
import com.jmmarao.workshopjavafxjdbc.models.daos.SellerDAO;
import com.jmmarao.workshopjavafxjdbc.models.entities.Seller;

import java.util.List;

public class SellerService {

    private final SellerDAO dao = FactoryDAO.createSellerDAO();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller seller) {
        if (seller.getId() == null)
            dao.insert(seller);
        else
            dao.update(seller);
    }

    public void remove(Seller seller) {
        dao.deleteById(seller.getId());
    }
}
