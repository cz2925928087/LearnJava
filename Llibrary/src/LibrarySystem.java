import user.ProxyUser;
import user.User;
import user.factory.AdminUserFactory;
import user.factory.IUserFactory;
import user.factory.NormalUserFactory;

import java.util.Scanner;


public class LibrarySystem {

    public static void main(String[] args) {

        // 工厂模式
        IUserFactory adminUserFactory = new AdminUserFactory();
        User admin = adminUserFactory.createUser("瑾桉", 1);

        IUserFactory normalUserFactory = new NormalUserFactory();
        User normal1 = normalUserFactory.createUser("瑾", 2);

        User normal2 = normalUserFactory.createUser("白", 3);

        //代理模式
        ProxyUser proxyUserAdmin = new ProxyUser(admin);
        ProxyUser proxyUserNormal1 = new ProxyUser(normal1);
        ProxyUser proxyUserNormal2 = new ProxyUser(normal2);

        LibrarySystem librarySystem = new LibrarySystem();

        ProxyUser currentUser = librarySystem.selectProxyRole(proxyUserAdmin,proxyUserNormal1,proxyUserNormal2);

        while (true) {
            int choice = currentUser.display();
            currentUser.handleOperation(choice);
        }
    }

    public  ProxyUser selectProxyRole(ProxyUser proxyUserAdmin, ProxyUser proxyUserNormal1,ProxyUser proxyUserNormal2) {

        System.out.println("选择角色进行登录：");
        System.out.println("1.管理员(瑾桉)\n2.普通用户(瑾)\n3.普通用户(白)\n4.退出系统");
        ProxyUser currentUser = null;

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                currentUser = proxyUserAdmin;
                break;
            case 2:
                currentUser = proxyUserNormal1;
                break;
            case 3:
                currentUser = proxyUserNormal2;
                break;
            case 4:
                System.exit(0);
                System.out.println("退出系统了...");
                break;
            default:
                break;
        }
        return currentUser;
    }
}
