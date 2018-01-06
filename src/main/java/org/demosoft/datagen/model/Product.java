package org.demosoft.datagen.model;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.demosoft.datagen.util.RandUtil.getRandValue;
import static org.demosoft.datagen.util.RandUtil.getRandValueWithIndex;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class Product {

    static List<String> prefixes = Arrays.asList(
            "Blue",
            "Yellow",
            "Amazing",
            "Salted",
            "Sweet",
            "Disgusting",
            "Usual",
            "",
            "Fast",
            "Powerful",
            "Super",
            "Micro",
            "Nano"
    );

    static List<String> names1 = Arrays.asList(
            "Banana",
            "Orange",
            "Tomato",
            "Potato",
            "Lemon"
    );
    static List<String> names2 = Arrays.asList(
            "Computer",
            "Mouse",
            "Keyboard",
            "Monitor",
            "Laptop"
    );

    static List<String> names3 = Arrays.asList(
            "Pen",
            "Pencil",
            "Book",
            "CopyBook",
            "Notebook"
    );

    static List<List<String>> names = Arrays.asList(names1, names2, names3);

    public static Tuple2<Integer, String> getRandProduct() {
        String prefix = getRandValue(prefixes);
        Tuple2<Integer, List<String>> randValueWithIndex = getRandValueWithIndex(names);
        String name = getRandValue(randValueWithIndex._2);
        return prefix.equals("") ? Tuple.of(randValueWithIndex._1, name)
                : Tuple.of(randValueWithIndex._1, prefix + " " + name);
    }

    public static Map<Integer, Product> generateProductMap() {
        Set<Tuple2<Integer, String>> fullNames = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            fullNames.add(getRandProduct());
        }
        final int[] prodId = {0};
        return fullNames.stream()
                .map(name -> new Product(prodId[0]++, name._2, name._1))
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    public int id;
    public String name;
    public int group;

    public Product(int id, String name, int group) {
        this.id = id;
        this.name = name;
        this.group = group;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return id + ",\"" + name + "\"," + group;
    }
}
