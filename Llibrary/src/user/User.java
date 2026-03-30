package user;


public abstract class User {

    private String name;

    private int userID;

    private String role;


    public User(String name, int userID, String role) {
        this.name = name;
        this.userID = userID;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return 返回 具体选择了什么操作
     * 1 2 3 4 5 6 ......
     */
    public abstract int display();

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID=" + userID +
                ", role='" + role + '\'' +
                '}';
    }
}
