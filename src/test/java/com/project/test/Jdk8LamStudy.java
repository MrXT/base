package com.project.test;
import com.project.common.exception.BusinessException;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 学会stream api的写法与optional（处理空指针的写法）的用法
 */
public class Jdk8LamStudy {

    //如果user为空，或者username为空，返回"",
    @SuppressWarnings("unused")
    @Test
    public void testOptional() {
        //JDK optional的写法
        User user = new User(null, "123123", 12);
        Optional<User> opUser = Optional.ofNullable(user);
        opUser.map(u -> u.getUsername()).map(name -> name.toUpperCase()).orElseThrow(() -> new BusinessException("用户名为空"));
        // 普通写法
        if (user == null) {
            System.out.println("用户名为空");
        } else {
            if (user.getUsername() == null) {
                System.out.println("用户名为空");
            } else {
                System.out.println(user.getUsername().toUpperCase());
            }
        }
        //明显optional写法更加好看
    }

    @Test
    public void testForEach() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        //Stream stream = users.stream();
        //Stream stream = users.parallelStream();
        users.parallelStream().filter(user -> user.getAge() >= 13).forEach(user -> {
            user.setUserId(user.getUserId() + 1);
            System.out.println(user.getUserId());
        });
    }

    // peek 对每个元素执行操作并返回一个新的 Stream，
    // forEach消费了这个流后stream就关闭了
    @Test
    public void testPeek() throws InterruptedException {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        //Stream stream = users.stream();
        //Stream stream = users.parallelStream();
        Stream<User> stream = users.stream().peek(user -> System.out.println(user.getUsername()));
        stream.filter(user -> user.getAge() >= 13).forEach(user -> System.out.println(user.getUsername()));
    }

    //测试groupBy，按年龄分组
    @Test
    public void testGroupBy() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        Map<String, List<User>> map = users.stream().collect(Collectors.groupingBy(user -> user.getAge() + "___" + user.getUserId()));
        map.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    //测试partitioningBy,分成true与false
    @Test
    public void testPartitioningBy() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        Map<Boolean, List<User>> map = users.stream().collect(Collectors.partitioningBy(o -> o.getAge() == 13));
        map.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    //測試map //mapToInt求平均值,求总和，求最大，求最小
    @Test
    public void testMap() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        //Stream stream = users.stream();
        //Stream stream = users.parallelStream();
        users.parallelStream().filter(user -> user.getAge() >= 13).map(User::getAge).sorted((o1, o2) -> o2 - o1).collect(Collectors.toList()).forEach(System.out::println);
        //求平均值
        Double d = users.parallelStream().filter(user -> user.getAge() >= 13).mapToInt(User::getAge).average().getAsDouble();
        System.out.println(d);
    }

    //将一对多的结构扁平化
    @Test
    public void testFlatMap() {
        List<List<User>> users = Arrays.asList(
                Arrays.asList(new User("test1", "test1", 12), new User("test2", "test2", 13)),
                Arrays.asList(new User("test3", "test3", 14), new User("test4", "test4", 15)),
                Arrays.asList(new User("test5", "test5", 16), new User("test6", "test6", 17))
        );
        //Stream stream = users.stream();
        //Stream stream = users.parallelStream();
        users.stream().flatMap(users1 -> users1.stream()).filter(user -> user.getAge() >= 14).sorted((o1, o2) -> o2.getAge() - o1.getAge()).forEach(System.out::println);

    }

    //连接年龄大于13的用户的姓名
    @Test
    public void testMapReduce() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        //Stream stream = users.stream();
        //Stream stream = users.parallelStream();
        String result = users.stream().filter(user -> user.getAge() >= 13).map(user -> user.getUsername()).reduce(Jdk8LamStudy::concatStr).get();
        System.out.println(result);
    }

    //测试匹配
    @Test
    public void testMatch() {
        List<User> users = Arrays.asList(
                new User("test1", "test1", 12),
                new User("test2", "test2", 13),
                new User("test3", "test3", 14)
        );
        System.out.println("所有用户名都包含test:" + users.stream().allMatch(user -> user.getUsername().contains("test")));
        System.out.println("所有用户名都不包含ttest:" + users.stream().noneMatch(user -> user.getUsername().contains("ttest")));
        System.out.println("有一个用户名包含test1:" + users.stream().anyMatch(user -> user.getUsername().contains("test1")));
    }

    public static String concatStr(String a, String b) {
        return a + b;
    }


}

class User {
    private String username;

    private String userId;

    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public User() {
    }

    public User(String username, String userId, Integer age) {
        this.username = username;
        this.userId = userId;
        this.age = age;
    }

    public void setUserId(String userId) {
        this.userId = userId;

    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", age=" + age +
                '}';
    }
}
