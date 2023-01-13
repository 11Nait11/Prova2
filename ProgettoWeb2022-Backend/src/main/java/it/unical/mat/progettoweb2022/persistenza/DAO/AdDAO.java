package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.Ad;

import java.util.List;

public interface AdDAO {
    public List<Ad> findAll();

    public Ad findByPrimaryKey(Integer id);
    public int findLastId();

    public Boolean saveOrUpdate(Ad ad);

    public Boolean delete(Ad ad);
}
