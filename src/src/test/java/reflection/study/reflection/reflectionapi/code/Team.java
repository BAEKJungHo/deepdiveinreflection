package reflection.study.reflection.reflectionapi.code;

public class Team {

    public static String name = "TECHVU";
    private int maxUserCount = 10;
    int createdAt = 2021;
    protected String leader = "JungHo";

    public Team() {
    }

    public Team(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    private void printName() {
        System.out.println(name);
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
