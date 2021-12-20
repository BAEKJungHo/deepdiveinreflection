package reflection.study.reflectionapi.code;

import org.springframework.lang.NonNull;

public class User {

    private String firstName = "JungHo";
    private static String lastName = "BAEK";
    private static final String fullName = "BAEK JungHo";

    public int age = 28;

    @NonNull
    protected int birthdate = 19940502;

    public User() {

    }

    public User(String firstName, int age, int birthdate) {
        this.firstName = firstName;
        this.age = age;
        this.birthdate = birthdate;
    }

    public void printFirstName() {
        System.out.println(firstName);
    }

    public void printLatName() {
        System.out.println(lastName);
    }

    public int getAge() {
        return age;
    }

}
