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

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        String sql = """
            SELECT sc.product_id, sc.quantity, 
                   p.name, p.price, p.category_id, p.description, p.color,
                   p.stock, p.featured, p.image_url
            FROM shopping_cart sc
            INNER JOIN products p ON p.product_id = sc.product_id
            WHERE sc.user_id = ?
            """;

        List<ShoppingCartItem> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getInt("category_id"),
                    rs.getString("description"),
                    rs.getString("color"),
                    rs.getInt("stock"),
                    rs.getBoolean("featured"),
                    rs.getString("image_url")
            );

            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(rs.getInt("quantity"));

            return item;
        }, userId);

        ShoppingCart cart = new ShoppingCart();
        for (ShoppingCartItem item : items)
        {
            cart.add(item);
        }

        return cart;
    }

    // Remember to update the rest and try not to fall asleep again lmao
}
