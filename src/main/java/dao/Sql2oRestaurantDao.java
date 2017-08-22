package dao;

import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Sql2oRestaurantDao implements RestaurantDao{
    private final Sql2o sql2o;

    public Sql2oRestaurantDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (name, address, zipcode, phone, website, email, image) VALUES (:name, :address, :zipcode, :phone, :website, :email, :image)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", restaurant.getName())
                    .addParameter("address", restaurant.getAddress())
                    .addParameter("zipcode", restaurant.getZipcode())
                    .addParameter("phone", restaurant.getPhone())
                    .addParameter("website", restaurant.getWebsite())
                    .addParameter("email", restaurant.getEmail())
                    .addParameter("image", restaurant.getImage())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("ADDRESS", "address")
                    .addColumnMapping("ZIPCODE", "zipcode")
                    .addColumnMapping("PHONE", "phone")
                    .addColumnMapping("WEBSITE", "website")
                    .addColumnMapping("EMAIL", "email")
                    .addColumnMapping("IMAGE", "image")
                    .executeUpdate()
                    .getKey();
            restaurant.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Restaurant> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM restaurants")
                    .executeAndFetch(Restaurant.class);
        }
    }

    @Override
    public Restaurant findById(int id) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM restaurants WHERE id = :id")
            .addParameter("id", id)
            .executeAndFetchFirst(Restaurant.class);
        }
    }

    @Override
    public void update(int id, String name, String address, String zipcode, String phone, String website, String email, String image) {
        try(Connection con = sql2o.open()){
            con.createQuery("UPDATE restaurants SET (id, name, address, zipcode, phone, website, email, image) = (:id, :name, :address, :zipcode, :phone, :website, :email, :image) WHERE id = :id")
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("address", address)
                    .addParameter("zipcode", zipcode)
                    .addParameter("phone", phone)
                    .addParameter("website", website)
                    .addParameter("email", email)
                    .addParameter("image", image)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE FROM restaurants WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}