package org.demosoft.datagen.util;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Random;

/**
 * Created by andrii_korkoshko on 06.01.18.
 */
public class RandUtil {

    public static final Random random = new Random();

    public static  <T> T getRandValue(List<T> list){
        return list.get(random.nextInt(list.size()));
    }


    public static  <T> Tuple2<Integer, T> getRandValueWithIndex(List<T> list){
        int index = random.nextInt(list.size());
        return Tuple.of(index,list.get(index));
    }
}
