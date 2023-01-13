package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.Image;
import it.unical.mat.progettoweb2022.model.Property;

import java.util.List;

public interface ImageDAO {
    public List<Image> findAll();

    public Image findByPrimaryKey(Integer id);

    public void saveOrUpdate(Image image);

    public void delete(Image image);

    public List<Image> findByAdId(Integer id);
}
