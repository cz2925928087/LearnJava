package user;

import book.Book;
import book.Library;
import utils.ScannerSingleton;

import java.time.LocalDate;
import java.util.Scanner;


public class AdminUser extends User{

    //public Scanner scanner = new Scanner(System.in);
    public Scanner scanner;

    public Library library;

    public AdminUser(String name, int userID) {
        super(name, userID, "管理员");
        scanner = ScannerSingleton.getScannerSingleton();
        library = Library.getLibrary();
    }

    @Override
    public int display() {
        System.out.println("管理员 " + this.getName() + " 的操作菜单:");
        System.out.println("1. 查找图书");
        System.out.println("2. 打印所有的图书");
        System.out.println("3. 退出系统");
        System.out.println("4. 上架图书");
        System.out.println("5. 修改图书");
        System.out.println("6. 下架图书");
        System.out.println("7. 统计借阅次数");
        System.out.println("8. 查看最后欢迎的前K本书");
        System.out.println("9. 查看库存状态");
        System.out.println("10. 检查超过一年未下架的图书");
        System.out.println("请选择你的操作：");
        return scanner.nextInt();
    }


    //其他操作方法
    //上架图书
    public void addBook() {
        System.out.println("Admin类addBook方法执行了");
        scanner.nextLine();

        System.out.println("请输入书名：");
        String bookName = scanner.nextLine();

        System.out.println("请输入作者：");
        String author = scanner.nextLine();

        System.out.println("请输入类别：");
        String category = scanner.nextLine();

        System.out.println("请输入出版年份：");
        int year = scanner.nextInt();

        LocalDate shelfDate = LocalDate.now();

        Book book = new Book(bookName,author,category,year,shelfDate);

        library.addBook(book);
    }

    //图书修改 支持修改书名 作者 类别
    public void updateBook() {
        //1. 先展示一下目前的所有书籍
        library.displayBooks();
        System.out.println("请输入你要修改的图书的id:");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        Book book = library.searchById(bookId);
        if(book == null) {
            System.out.println("没有你要找的这本ID的书："+bookId);
            return;
        }

        System.out.println("当前书名：" + book.getBookName());
        System.out.println("请输入新的书名：");
        String newBookName = scanner.nextLine();  // 输入新的书名
        System.out.println("当前作者：" + book.getAuthor());
        System.out.println("请输入新的作者：");
        String newAuthor = scanner.nextLine();  // 输入新的作者
        System.out.println("当前类别：" + book.getCategory());
        System.out.println("请输入新的类别：");
        String newCategory = scanner.nextLine();  // 输入新的类别


       book.setBookName(newBookName);
        book.setAuthor(newAuthor);
        book.setCategory(newCategory);

        library.updateBook(book);
    }

    //删除书籍
    public void removeBook() {

        //1.展示一下所有的图书
        library.displayBooks();

        System.out.println("请输入要删除的图书的ID：");
        int bookId = scanner.nextInt();
        scanner.nextLine();  // 吞掉换行符


        Book removeBook = library.searchById(bookId);
        if(removeBook == null) {
            System.out.println("没有你要删除的图书");
            return;
        }

        library.removeBook(bookId);

        System.out.println("图书："+removeBook.getBookName()+" 已经被删除！");
    }

    //统计每本书的借阅次数
    public void borrowCount() {
        library.borrowCount();
    }

    //查询最受欢迎的前n本书
    public void generateBook() {
        System.out.println("请输入你要查看的最受欢迎的前K本书,注意k值不能超过："+library.bookCount);

        int k = scanner.nextInt();

        if(k <= 0 || k > library.bookCount) {
            System.out.println("没有最受欢迎的前"+k+"本书！");
            return;
        }
        library.generateBook(k);

    }

    //查看库存状态
    public void checkInventoryStatus() {
        library.checkInventoryStatus();
    }

    //并移除上架超过一年的图书
    public void checkAndRemoveOldBooks() {
        library.checkAndRemoveOldBooks();
    }

    public void exit() {
        //业务代码的实现
        System.out.println("退出系统");
        System.exit(0);
    }
}
