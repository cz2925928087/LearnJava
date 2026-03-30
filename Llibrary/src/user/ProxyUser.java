package user;

import book.Library;
import utils.PermissionException;
import constant.Constant;


public class ProxyUser {

    private User realUser;

    private Library library ;

    public ProxyUser(User user) {
        this.realUser = user;
        library = Library.getLibrary();
    }

    public User getRealUser() {
        return realUser;
    }

    public int display() {
        return this.realUser.display();
    }

    //--------------------------------管理员相关方法------------------------------//

    private void checkRealUserWhetherAdminUser(String msg) {
        if(!(this.realUser instanceof AdminUser)) {
            throw new PermissionException(msg);
        }
    }
    //添加书籍操作
    public void addBook() {
        System.out.println("代理类addBook方法执行了");
        try {
            checkRealUserWhetherAdminUser("普通用户没有权限添加书籍...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).addBook();
    }

    //更新书籍操作
    public void updateBook() {
        System.out.println("代理类updateBook方法执行了");
        try {
            checkRealUserWhetherAdminUser("普通用户没有权限更新书籍...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).updateBook();
    }

    //移除图书
    public void removeBook() {
        System.out.println("代理类removeBook方法执行了");

        try {
            checkRealUserWhetherAdminUser("普通用户没有权限删除书籍...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).removeBook();

    }

    //查看图书的借阅次数
    public void borrowCount( ) {
        System.out.println("代理类borrowCount方法执行了");

        try {
            checkRealUserWhetherAdminUser("普通用户没有权限查看借阅次数...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).borrowCount();
    }

    //查看最受欢迎的前K本书
    public void generateBook() {
        System.out.println("代理类generateBook方法执行了");

        try {
            checkRealUserWhetherAdminUser("普通用户没有权限查看最受欢迎的前K本书...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).generateBook();
    }

    //查看库存状态
    public void checkInventoryStatus() {
        System.out.println("代理类checkInventoryStatus方法执行了");


        try {
            checkRealUserWhetherAdminUser("普通用户没有权限查看库存状态...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).checkInventoryStatus();


    }


    //移除上架超过1年的书籍
    public void checkAndRemoveOldBooks() {
        System.out.println("代理类checkAndRemoveOldBooks方法执行了");

        try {
            checkRealUserWhetherAdminUser("普通用户没有权限查看库存状态...");
        }catch (PermissionException e) {
            e.printStackTrace();
        }

        ((AdminUser)(this.realUser)).checkAndRemoveOldBooks();
    }

    //--------------------------------普通用户相关方法------------------------------//

    private void checkRealUserWhetherNormalUser(String exceptionMessage) {
        if(!(realUser instanceof NormalUser)){
            throw new PermissionException(exceptionMessage);
        }
    }

    //借阅图书
    public void borrowBook() {
        System.out.println("代理类borrowBook方法执行了");
        checkRealUserWhetherNormalUser("管理员不能借阅图书，请换做普通用户来借阅图书...");

        ((NormalUser)(this.realUser)).borrowBook();
    }

    //归还图书
    public void returnBook() {
        System.out.println("代理类returnBook方法执行了");
        checkRealUserWhetherNormalUser("管理员请以普通用户的方式归还图书");
        ((NormalUser) realUser).returnBook();
    }


    //查看个人借阅情况
    public void viewBorrowHistory() {
        System.out.println("代理类viewBorrowHistory方法执行了");


        checkRealUserWhetherNormalUser("管理员请以普通用户的方式归还图书");
        ((NormalUser) realUser).viewBorrowHistory();

    }


    public void handleOperation(int choice) {

        if(this.realUser instanceof AdminUser) {
            switch (choice) {
                case Constant.SEARCH_BOOK:
                    library.searchBook();
                    break;
                case Constant.DISPLAY_BOOK:
                    library.displayBooks();
                    break;
                case Constant.EXIT:
                    library.exit();
                    break;
                case Constant.ADD_BOOK:
                    addBook();
                    break;
                case Constant.UPDATE_BOOK:
                    updateBook();
                    break;
                case Constant.REMOVE_BOOK:
                    removeBook();
                    break;
                case Constant.BORROWED_BOOK_COUNT:
                    borrowCount();
                    break;
                case Constant.GENERATE_BOOK:
                    generateBook();
                    break;
                case Constant.CHECK_INVENTORY_STATUS:
                    checkInventoryStatus();
                    break;
                case Constant.CHECK_AND_REMOVE_OLD_BOOK:
                    checkAndRemoveOldBooks();
                default:
                    break;
            }
        }else {
            switch (choice) {
                case Constant.SEARCH_BOOK:
                    library.searchBook();
                    break;
                case Constant.DISPLAY_BOOK:
                    library.displayBooks();
                    break;
                case Constant.EXIT:
                    library.exit();
                    break;
                case Constant.BORROWED_BOOK:
                    borrowBook();
                    break;
                case Constant.RETURN_BOOK:
                    returnBook();
                    break;
                case Constant.VIEW_BORROW_HISTORY_BOOK:
                    viewBorrowHistory();
                    break;
            }
        }

    }
}
