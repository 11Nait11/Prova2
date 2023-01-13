package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.Ad;
import it.unical.mat.progettoweb2022.model.Property;

import java.util.List;

public interface PropertyDAO {
    public List<Property> findAll();

    public Property findByPrimaryKey(Integer id);

    public void saveOrUpdate(Property property);

    public void delete(Property property);

    public void deleteByAd(Integer id);

    List<Property> findByOwner(String nickname);

    Property findByAdId(Integer adId);
}
