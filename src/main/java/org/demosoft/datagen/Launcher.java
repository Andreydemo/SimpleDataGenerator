package org.demosoft.datagen;

import org.demosoft.datagen.model.Customer;
import org.demosoft.datagen.model.OrderItem;
import org.demosoft.datagen.model.Product;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class Launcher {

    private static Random random = new Random();


    public static void main(String[] args) {
        Map<Integer, Customer> customerIndex = Customer.generateCustomerMap(random);
        System.out.println(customerIndex);
        String customers = customerIndex.entrySet().stream().map(e -> e.getValue().toString()).collect(Collectors.joining(System.lineSeparator()));
        String customerHeader = "CUST_ID,NAME" + System.lineSeparator();
        write(Paths.get("customers.csv"), customerHeader + customers);
        Map<Integer, Product> integerProductMap = Product.generateProductMap();
        System.out.println(integerProductMap);
        String product = integerProductMap.entrySet().stream().map(e -> e.getValue().toString()).collect(Collectors.joining(System.lineSeparator()));
        String productHeader = "PRODUCT_ID,PRODUCT_NAME,GROUP" + System.lineSeparator();
        write(Paths.get("products.csv"), productHeader + product);
        Map<Integer, Integer> groupToMaxPrice = new HashMap<>();
        groupToMaxPrice.put(0, 20);
        groupToMaxPrice.put(1, 1000);
        groupToMaxPrice.put(2, 10);
        Map<Integer, Integer> groupToMaxCount = new HashMap<>();
        groupToMaxCount.put(0, 500);
        groupToMaxCount.put(1, 50);
        groupToMaxCount.put(2, 1000);

        List<OrderItem> orderItems = new OrderItemBuilder(customerIndex, integerProductMap, groupToMaxPrice, groupToMaxCount, 20, 100).genOrders();

        String orderItemsContent = orderItems.stream().map(OrderItem::toString).collect(Collectors.joining(System.lineSeparator()));

        String orderItemsHeader = "CUST_ID,DATE,PRODUCT_ID,ORDER_ID,PRICE,DISCOUNT,COUNT" + System.lineSeparator();
        write(Paths.get("orderItmes.csv"), orderItemsHeader + orderItemsContent);

    }

    private static void write(Path path, String collect) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(collect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
