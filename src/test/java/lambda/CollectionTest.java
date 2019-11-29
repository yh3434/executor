package lambda;

import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class CollectionTest {

    @Test
    public void testMapMerge() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        map.merge("1", "3", (v1, v2) -> v1 + v2);
        System.out.println(map.toString());
    }

    @Test
    public void testMapCompute() {
        //学生的集合

        List<Student> students = new ArrayList<>();
        students.add(new Student("张三", "男", 18));
        students.add(new Student("李四", "男", 20));
        students.add(new Student("韩梅梅", "女", 18));
        students.add(new Student("小红", "女", 45));

        //声明接收结果的 map
        Map<String, List<Student>> resultMap = new HashMap<>();
        for (Student student : students) {
            List<Student> s = resultMap.computeIfAbsent(student.getSex(), k -> new ArrayList<>());
            s.add(student);
        }

        System.out.println(resultMap);
    }

    @Test
    public void testStreamReduce() {
        // 求单词长度之和
        List<String> list = Arrays.asList("I", "love", "you", "too");
        Integer lengthSum = list.parallelStream().reduce(0,// 初始值　// (1)
                new BiFunction<Integer, String, Integer>() {
                    @Override
                    public Integer apply(Integer sum, String str) {
                        System.out.println("47....." + sum);
                        System.out.println("48....." + str);
                        return sum + str.length();
                    }
                }, // 累加器 // (2)
                new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer a, Integer b) {
                        System.out.println("51....." + a);
                        System.out.println("52....." + b);
                        return a + b;
                    }
                });// 部分和拼接器，并行执行时才会用到 // (3)
//        int lengthSum = stream.mapToInt(str -> str.length()).sum();
        System.out.println(lengthSum);
        System.out.println(list.parallelStream().collect(Collectors.joining(",", "{", "}")));
    }
}

class Student {
    private String name;
    private String sex;
    private Integer age;

    public Student(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
