package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.List;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlShoppingCartDao(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        // Implement logic here
        return new ShoppingCart(); // Example return
    }

    @Override
    public void addProduct(int userId, int productId)
    {
        // Implement add logic here
    }

    @Override
    public void update(int userId, int productId, int quantity)
    {
        // Implement update logic here
    }

    @Override
    public void clear(int userId)
    {
        // Implement clear logic here
    }
}
