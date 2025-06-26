package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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

    // methods to be implemented: getByUserId, addProduct, update, clear
}
