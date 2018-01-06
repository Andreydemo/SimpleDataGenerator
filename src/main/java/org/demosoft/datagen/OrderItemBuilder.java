package org.demosoft.datagen;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.demosoft.datagen.model.Customer;
import org.demosoft.datagen.model.OrderItem;
import org.demosoft.datagen.model.Product;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class OrderItemBuilder {

    public static final Random random = new Random();

    final Map<Integer, Customer> customerMap;
    final Map<Integer, Product> productMap;
    final Map<Integer, Integer> groupToMaxPrice;
    final Map<Integer, Integer> groupToMaxCount;
    final Integer maxItemPerCustomer;
    final Integer maxOrdersPerCustomer;
    public static final int YEAR_SEC = 60 * 60 * 24 * 365;

    public OrderItemBuilder(Map<Integer, Customer> customerMap,
                            Map<Integer, Product> productMap,
                            Map<Integer, Integer> groupToMaxPrice,
                            Map<Integer, Integer> groupToMaxCount,
                            Integer maxItemPerCustomer,
                            Integer maxOrdersPerCustomer) {
        this.customerMap = customerMap;
        this.productMap = productMap;
        this.groupToMaxPrice = groupToMaxPrice;
        this.groupToMaxCount = groupToMaxCount;
        this.maxItemPerCustomer = maxItemPerCustomer;
        this.maxOrdersPerCustomer = maxOrdersPerCustomer;
    }

    public List<OrderItem> genOrders() {

        List<Tuple2<LocalDateTime, List<OrderItem>>> collect = customerMap.entrySet().stream()
                .map(e -> this.genOrdersForCustomer(e.getValue())).flatMap(Collection::stream)
                .sorted(Comparator.comparing(Tuple2::_1))
                .collect(Collectors.toList());
        final int[] orderId = {0};
        return collect.stream().flatMap(order -> {
            order._2.forEach(item -> item.orderId = orderId[0]);
            orderId[0]++;
            return order._2.stream();
        }).collect(Collectors.toList());
    }

    private List<Tuple2<LocalDateTime, List<OrderItem>>> genOrdersForCustomer(Customer customer) {
        int ordersCount = 1 + random.nextInt(maxOrdersPerCustomer);
        List<Tuple2<LocalDateTime, List<OrderItem>>> result = new ArrayList<>();
        for (int i = 0; i < ordersCount; i++) {
            LocalDateTime date = randomDate();
            Tuple2<LocalDateTime, List<OrderItem>> of = Tuple.of(date, genOrder(customer, date));
            result.add(of);
        }

        return result;
    }

    private List<OrderItem> genOrder(Customer customer, LocalDateTime dateTime) {
        int itemsCount = 1 + random.nextInt(maxItemPerCustomer);
        List<OrderItem> result = new ArrayList<>();
        for (int i = 0; i < itemsCount; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.customerId = customer.id;
            int productId = random.nextInt(productMap.entrySet().size());
            Product product = productMap.get(productId);
            orderItem.productId = productId;
            orderItem.date = dateTime;
            int group = product.getGroup();
            Integer maxPrice = groupToMaxPrice.get(group);
            orderItem.price = maxPrice / 2 + random.nextInt(maxPrice / 2);
            orderItem.discount = (int) ((double) orderItem.price / 100 * random.nextInt(100));
            orderItem.count = 1 + random.nextInt(groupToMaxCount.get(group));
            result.add(orderItem);
        }
        return result;
    }

    private LocalDateTime randomDate() {
        return LocalDateTime.now().minusSeconds((long) random.nextInt(2 * YEAR_SEC));// +- 2 years;
    }
}
