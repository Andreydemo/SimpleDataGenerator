package org.demosoft.datagen.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class OrderItem {

    public int customerId;
    public int productId;
    public int discount;
    public int price;
    public LocalDateTime date;
    public int orderId;
    public int count;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public String toString() {
        return customerId + "," + productId +
                "," + discount +
                "," + price +
                "," + date.format(formatter) +
                "," + orderId +
                "," + count;
    }
}
