USE online;

SELECT cartId, userId, buyDate, status, paymentMethod, totalAmount
FROM Cart
ORDER BY buyDate DESC;


SELECT c.cartId, c.status, i.productId, i.productName, i.quantity, i.unitPrice
FROM Cart c JOIN CartItem i ON i.cartId = c.cartId
ORDER BY c.buyDate DESC;
