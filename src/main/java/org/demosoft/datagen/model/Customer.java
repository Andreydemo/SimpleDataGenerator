package org.demosoft.datagen.model;

import org.demosoft.datagen.util.RandUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.demosoft.datagen.util.RandUtil.getRandValue;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class Customer {

    public static List<String> names = Arrays.asList(
            "Andrii",
            "Ivan",
            "Petr",
            "Anatolii",
            "Sergey",
            "Vitalii",
            "Maksim",
            "Volodimir",
            "Myroslav",
            "Vladislav",
            "Boris",
            "Mykola");
    public static List<String> lastNames = Arrays.asList(
                    "Ivanov",
                    "Korkoshko",
                    "Poveduk",
                    "Dosiak",
                    "Mumladze",
                    "Glavchev",
                    "Fedorchuk",
                    "Klichko",
                    "Poroshenko",
                    "Putin",
                    "Simonenko",
                    "Yereskovskii");
    public int id;
    public  String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Map<Integer, Customer> generateCustomerMap(Random random) {
        Set<String> fullNames = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            fullNames.add(genRandomFullName(random));
        }
        final int[] custId = {0};
        return fullNames.stream()
                .map(name -> new Customer(custId[0]++,name))
                .collect(Collectors.toMap(Customer::getId, Function.identity()));
    }

    public static String genRandomFullName(Random random) {
        return getRandValue(names) + " " + getRandValue(lastNames);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ",\"" + name + "\"";
    }
}
