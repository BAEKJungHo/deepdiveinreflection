package reflection.study.reflection.reflectionapi.code;

public class Team {

    public static String name = "TECHVU";
    private int maxUserCount = 10;

    public Team() {
    }

    public Team(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public void printName() {
        System.out.println(name);
    }

}
