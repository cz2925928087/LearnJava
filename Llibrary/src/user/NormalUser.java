package user;

import book.Book;
import book.Library;
import book.PairOfUidAndBookId;
import constant.Constant;
import utils.AnalyzingBorrowedBook;
import utils.ScannerSingleton;

import java.io.IOException;
import java.util.Scanner;


public class NormalUser extends User{

    public Scanner scanner ;

    public Library library;

    //存储图书借阅信息
    private PairOfUidAndBookId[] pairOfUidAndBookIds;

    // 当前书籍的借阅量
    private int borrowedCount;

    private final AnalyzingBorrowedBook analyzingBorrowedBook = new AnalyzingBorrowedBook();



    public NormalUser(String name, int userID) {
        super(name, userID, "普通用户");

        loadBorrowedBook();

        scanner = ScannerSingleton.getScannerSingleton();
        library = Library.getLibrary();

    }


    private void loadBorrowedBook() {
        PairOfUidAndBookId[] allBorrowedBook;

        try {
            //1.先加载文件当中的借阅信息
            allBorrowedBook = analyzingBorrowedBook.loadObject(Constant.BORROWED_BOOK_FILE_NAME);

            //2. 默认已借阅的图书数组大小为BORROW_BOOK_MAX_NUM，这里也可以定义到常量类
            pairOfUidAndBookIds = new PairOfUidAndBookId[Constant.BORROW_BOOK_MAX_NUM];



            //3.没有读取到已借阅的图书信息
            if (allBorrowedBook== null) {
                borrowedCount = 0;
            } else {
                //4. 查看实际读取到的数组长度是多少？ 3   ->5   10
                int allBorrowedBookLen= allBorrowedBook.length;


                //5. 如果读取到了10本书被借阅 但是当前borrowedBooks数组长度小于10
                if (allBorrowedBookLen > pairOfUidAndBookIds.length) {
                    //6. 按照实际情况进行分配数组内存
                    pairOfUidAndBookIds = new PairOfUidAndBookId[allBorrowedBookLen];
                }


                //7.把数据拷贝回到 已借阅图书信息的数组当中
                for (int i = 0; i < allBorrowedBookLen; i++) {
                    pairOfUidAndBookIds[i] = allBorrowedBook[i];
                }

                //8.更新当前实际借阅书籍的书籍数量
                borrowedCount = allBorrowedBookLen;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void storeBorrowedBook() {
        try {
            analyzingBorrowedBook.storeObject(pairOfUidAndBookIds, Constant.BORROWED_BOOK_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public int display() {
        System.out.println("普通用户 " + this.getName() + " 的操作菜单:");
        System.out.println("1. 查找图书");
        System.out.println("2. 打印所有的图书");
        System.out.println("3. 退出系统");
        System.out.println("4. 借阅图书");
        System.out.println("5. 归还图书");
        System.out.println("6. 查看当前个人借阅情况");
        System.out.println("请选择你的操作：");
        return scanner.nextInt();
    }

    //借阅图书
    public void borrowBook() {

        scanner.nextLine();

        System.out.println("请输入你要借阅图书的书籍ID：");

        int bookId = scanner.nextInt();
        scanner.nextLine();

        if(library.bookCount == 0) {
            System.out.println("图书馆没有书籍可以借阅....");
            return;
        }

        loadBorrowedBook();

        //1. 判断图书在图书馆当中 是否是存在的
        Book book = library.searchById(bookId);

        if(book == null) {
            System.out.println("没有你借阅的图书，书籍ID为："+bookId);
            return;
        }

        //2.开始准备借阅图书
        for (int i = 0; i < borrowedCount; i++) {
            PairOfUidAndBookId pairOfUidAndBookIds1 = pairOfUidAndBookIds[i];

            if(pairOfUidAndBookIds1.getBookId() ==book.getBookId() ) {
                //getUserID当前用户的ID
                if(pairOfUidAndBookIds1.getUserId() == getUserID()) {
                    System.out.println("这本书是你自己已经借阅过的！");
                    return;
                }else {
                    System.out.println("这本书是别人已经借阅过的！");
                    return;
                }
            }
        }
        library.borrowBook(bookId);
        //这里要写入  借阅书籍的txt


        PairOfUidAndBookId pairOfUidAndBookId = new PairOfUidAndBookId(getUserID(),book.getBookId());


        pairOfUidAndBookIds[borrowedCount] = pairOfUidAndBookId;


        borrowedCount++;

        storeBorrowedBook();

        System.out.println("借阅书籍成功！！！");

    }


    //归还图书
    public void returnBook() {
        loadBorrowedBook();

        if (borrowedCount == 0) {
            System.out.println("目前没有用户借阅过书籍");
            return;
        }

        scanner.nextLine();
        System.out.println("请输入你要归还图书的id：");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        //判断要借阅的书 是否是已经被自己借阅过了
        Book book = library.searchById(bookId);
        if(book == null) {
            System.out.println("没有该id的相关书籍："+bookId);
            return;
        }

        for (int i = 0; i < borrowedCount; i++) {
            //如果2本书的ID，则认为是同一本书
            if (pairOfUidAndBookIds[i].getBookId()==book.getBookId()) {
                //借阅用户也一样
                if (getUserID() == pairOfUidAndBookIds[i].getUserId()) {
                    library.returnBook(bookId);
                    System.out.println("图书 '" + book.getBookName()  + "' 已成功归还。");
                    // 用最后一本替换归还的书
                    pairOfUidAndBookIds[i] = pairOfUidAndBookIds[borrowedCount - 1];
                    // 清空最后一个
                    pairOfUidAndBookIds[borrowedCount - 1] = null;
                    borrowedCount--;
                    storeBorrowedBook();
                }else{
                    System.out.println("该书籍不是你借阅的书籍，不能归还："+book.getBookName());
                }
                return;
            }
        }

    }

    // 查看个人借阅情况
    public void viewBorrowHistory() {
        //读取当前借阅所有用户的借阅书籍的情况
        loadBorrowedBook();

        System.out.println("您的借阅情况如下：");

        if (borrowedCount == 0) {
            System.out.println("目前没有借阅记录.....");
        } else {
            boolean flg = false;

            for (int i = 0; i < borrowedCount; i++) {
                //这里只能查看属于自己借阅的情况
                //用户ID相同的情况下，使用书籍ID查询书籍
                if(pairOfUidAndBookIds[i].getUserId() == getUserID()) {
                    flg = true;
                    Book book = library.searchById(pairOfUidAndBookIds[i].getBookId());
                    System.out.println(book);
                }
            }
            if(!flg) {
                System.out.println("你没有借阅过书籍！");
            }
        }
    }
}
