package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.Review;

import java.util.List;

public interface ReviewDAO {

    public List<Review> findAll();

    public Review findByPrimaryKey(Integer id);

    public List<Review> findByAdId(Integer id);

    public boolean saveOrUpdate(Review review);

    public boolean delete(Integer reviewId);

}
