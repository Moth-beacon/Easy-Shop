package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    // Get the cart for a specific user
    ShoppingCart getByUserId(int userId);

    // Add a product to the user's cart
    void addProduct(int userId, int productId);

    // Change how many of a product is in the cart
    void update(int userId, int productId, int quantity);

    // Remove everything from the user's cart
    void clear(int userId);
}
