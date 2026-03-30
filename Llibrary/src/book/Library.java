package book;

import constant.Constant;
import utils.AnalyzingBook;
import utils.ScannerSingleton;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;

import static com.sun.org.apache.xpath.internal.compiler.Token.contains;


public class Library {
    //存储书籍的
    public Book[] books;
    //记录当前图书馆 有效的书  到底是多少本
    public int bookCount;

    private static Library library ;

    public Scanner scanner;

    private AnalyzingBook analyzingBook = new AnalyzingBook();


    private Library() {
        //读
        loadAllBook();

        scanner = ScannerSingleton.getScannerSingleton();
    }

    public static Library getLibrary() {
        if(library == null) {
            library = new Library();
        }
        return library;
    }
    //写成一个独立的方法 后期 实时的可以更新 查看 我们当前书架到底多少本书
    private void loadAllBook() {
        try {
            //1.读取文件的书籍 到 allBook 数组当中
            Book[] allBook = analyzingBook.loadObject(Constant.ALL_BOOK_FILE_NAME);
            //2.给当前书架 默认分配大小是5
            books = new Book[Constant.CAPACITY];

            if(allBook == null) {
                bookCount = 0;
            }else {
                int allBookLen = allBook.length;//10  3
                if(allBookLen > books.length) {
                    books = new Book[allBookLen];
                }

                for (int i = 0; i < allBookLen; i++) {
                    books[i] = allBook[i];
                }
                //有效的书籍
                bookCount = allBookLen;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //存储图书到文件中
    private void storeBook() {
        try {
            analyzingBook.storeObject(books,Constant.ALL_BOOK_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //共用的方法
    public void searchBook() {
        System.out.println("查找图书");

        scanner.nextLine();
        System.out.println("请输入你要查找的图书的名称：");
        String title = scanner.nextLine();


        Book book = search(title);

        if(book == null) {
            System.out.println("没有你要找的这本书，你查找的书名为："+title);
        }else {
            System.out.println("找到了你要查找的书，书的详细信息如下：");
            System.out.println(book);
        }

    }

    //根据书名查找对应书籍
    private Book search(String bookName) {
        loadAllBook();
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            if(book.getBookName().equals(bookName)) {
                //contains(bookName);//模糊查询,根据字符串中的部分关键字去查找书籍
                return book;
            }
        }
        return null;
    }


    public void displayBooks() {
        System.out.println("显示图书");
        loadAllBook();
        for (int i = 0; i < bookCount; i++) {
            System.out.println(books[i]);
        }
    }
    public void exit() {
        System.out.println("退出系统");

        System.exit(0);

    }

    public void addBook(Book book) {
        System.out.println("Libary类addBook方法执行了");

        if(bookCount >= books.length) {
            System.out.println("图书馆已满，无法上架图书");
            //扩容的 TODO 如果扩容了 下面就不要进行return了
            return;
        }
        books[bookCount] = book;
        if(bookCount == 0) {
            book.setBookId(1);
        }else {
            Book bookLast = books[bookCount-1];
            book.setBookId(bookLast.getBookId()+1);
        }
        bookCount++;

        storeBook();

        System.out.println("图书上架成功！！ 图书的名称是："+book.getBookName());
    }


    public Book searchById(int bookId) {
        loadAllBook();
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            if(book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }


    public void updateBook(Book book) {

        int bookId = book.getBookId();
        int index = searchByIdReturnIndex(bookId);
        if(index == -1) {
            System.out.println("没有你要更新的图书，此时的ID为："+bookId);
            return;
        }

        books[index] = book;
        System.out.println("修改成功");
        //把当前的更新 写会到 文件当中 保证一致性
        storeBook();
        System.out.println("书籍已被更新，文件已经写入，更新之后书籍为：");
        System.out.println(books[index]);
    }


    private int searchByIdReturnIndex(int bookId) {

        loadAllBook();

        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            if(book.getBookId() == bookId) {
                return i;
            }
        }
        return -1;
    }


    public void removeBook(int bookId) {

        int index = searchByIdReturnIndex(bookId);
        if(index == -1) {
            System.out.println("没有你要删除的图书");
            return;
        }

        for (int i = index; i < bookCount-1; i++) {
            books[i] = books[i+1];
        }



        bookCount--;
        books[bookCount] = null;

        storeBook();
    }

    public void borrowCount() {
        loadAllBook();
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            System.out.println("书籍："+book.getBookName()+" 借阅了："+book.getBorrowedCount()+"次！");
        }
    }

    public void generateBook(int k) {

        //1. 加载已有的全部的书籍
        loadAllBook();

        //2.把所有书籍放在 临时数据 进行排序
        Book[] tmp = new Book[bookCount];
        for (int i = 0; i < bookCount; i++) {
            tmp[i] = books[i];
        }
        //2.1 开始排序
        Arrays.sort(tmp);

        //3. 把前k本书拷贝到新数组   可以不定义临时数组，直接输出前K个就行
        Book[] generateBooks = new Book[k];

        for (int i = 0; i < k; i++) {
            generateBooks[i] = tmp[i];
        }

        //4.打印新数组
        System.out.println("最受欢迎书籍如下：");
        for (int i = 0; i < generateBooks.length; i++) {
            Book book = generateBooks[i];
            System.out.println("书籍ID: "+book.getBookId()+" |书名："+ book.getBookName()+" |作者："+
                    book.getAuthor()+" |借阅次数："+book.getBorrowedCount());
        }
    }


    public void checkInventoryStatus() {
        loadAllBook();
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];

            String status = "未借出";
            if(book.isBorrowed()) {
                status = "已借出";
            }
            System.out.println("书籍："+book.getBookName()+" 借阅状态：" + status);
        }
    }

    public void checkAndRemoveOldBooks() {

        loadAllBook();

        // 获取当前时间戳
        long currentTimestamp = System.currentTimeMillis();

        // 将当前时间戳转换为 LocalDate
        LocalDate currentDate = Instant.ofEpochMilli(currentTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        boolean flg = false;
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            //获取当前书籍的上架时间
            LocalDate specifiedDate = book.getShelDate();
            // 计算两个日期之间的差值（以年为单位）
            long yearsBetween = ChronoUnit.YEARS.between(specifiedDate, currentDate);
            if(yearsBetween >= 1) {
                System.out.print("图书 " + book.getBookName() + " 已经上架超过一年，是否移除？ (y/n)：");
                scanner.nextLine();
                String response = scanner.nextLine();
                //scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    //确认删除调用remove方法进行删除
                    //应该拿书籍的 ID
                    removeBook(book.getBookId());
                    i--;  // 因为后面的书已经向前移动，所以要重新检查当前索引位置
                }
                flg = true;
            }
        }
        if(!flg) {
            System.out.println("没有上架超过一年的图书！");
        }

        storeBook();
    }


    public void borrowBook(int bookId) {

        loadAllBook();

        Book book = searchById(bookId);
        book.setBorrowed(true);

        book.incrementBorrowCount();

        storeBook();

    }

    public void returnBook(int bookId) {
        loadAllBook();
        Book book = searchById(bookId);
        book.setBorrowed(false);
        book.decreaseBorrowCount();// 置为 0
        storeBook();
    }
}
