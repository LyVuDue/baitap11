package vn.ute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.ute.entity.*;
import vn.ute.service.IProductService_24162016;
import vn.ute.service.impl.ShoppingCartService_24162016;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

class ShoppingCartTest {
    private Product_24162016 product;
    private ShoppingCartService_24162016 service;
    private ShoppingCart_24162016 cart;

    @BeforeEach
    void setup() {
        product = new Product_24162016();
        product.setProductId(1);
        product.setProductName("Sản phẩm thử");
        product.setPrice(10.1);
        product.setStock(5);
        product.setStatus(1);
        cart = new ShoppingCart_24162016(12);
        service =
                new ShoppingCartService_24162016(
                        new IProductService_24162016() {
                            public Product_24162016 findById(int id) {
                                return id == 1 ? product : null;
                            }

                            public List<Product_24162016> findAll() {
                                return List.of(product);
                            }

                            public Map<Integer, List<Product_24162016>> groupBySeller() {
                                return Map.of();
                            }
                        });
    }

    @Test
    void repeatedAddCombinesQuantityAndUsesDecimalTotals() {
        service.add(cart, 1, 1);
        service.add(cart, 1, 2);
        assertEquals(3, cart.getItemCount());
        assertEquals(1, cart.getItems().size());
        assertEquals(new BigDecimal("30.30"), cart.getTotal());
    }

    @Test
    void invalidQuantityDoesNotChangeExistingCart() {
        service.add(cart, 1, 2);
        for (int quantity : new int[] {0, -1, 6, 1001, Integer.MAX_VALUE})
            assertThrows(
                    IllegalArgumentException.class, () -> service.setQuantity(cart, 1, quantity));
        assertEquals(2, cart.quantityOf(1));
        assertThrows(IllegalArgumentException.class, () -> service.add(cart, 1, 4));
        assertEquals(2, cart.quantityOf(1));
    }

    @Test
    void refreshUpdatesPriceButReportsReducedStock() {
        service.add(cart, 1, 3);
        product.setPrice(20.0);
        service.refresh(cart);
        assertEquals(new BigDecimal("60.00"), cart.getTotal());
        product.setStock(2);
        assertThrows(IllegalArgumentException.class, () -> service.refresh(cart));
        assertEquals(3, cart.quantityOf(1));
        service.setQuantity(cart, 1, 2);
        assertEquals(2, cart.quantityOf(1));
    }

    @Test
    void unavailableProductsCannotBeAdded() {
        assertThrows(IllegalArgumentException.class, () -> service.add(cart, 99, 1));
        product.setStatus(0);
        assertThrows(IllegalArgumentException.class, () -> service.add(cart, 1, 1));
        product.setStatus(1);
        product.setPrice(Double.NaN);
        assertThrows(IllegalArgumentException.class, () -> service.add(cart, 1, 1));
        assertTrue(cart.isEmpty());
    }

    @Test
    void removalAndClearRecalculateTotal() {
        service.add(cart, 1, 2);
        cart.remove(1);
        assertEquals(BigDecimal.ZERO, cart.getTotal());
        service.add(cart, 1, 1);
        cart.clear();
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
    }
}
