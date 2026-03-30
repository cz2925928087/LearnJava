package constant;


public class Constant {

    //定义 全部书籍 存储的文件
    public static final String ALL_BOOK_FILE_NAME = "allbooks.txt";

    public static final String BORROWED_BOOK_FILE_NAME = "borrowedbook.txt";

    //定义 默认的 书架的容量
    public static final int CAPACITY = 5;

    //最多借阅的图书数量[目前代表的是 所有人的 ]
    public static final int BORROW_BOOK_MAX_NUM = 5;


    //-------------------管理员相关操作管理----------------
    //查找图书
    public static final int SEARCH_BOOK = 1;
    //显示图书
    public static final int DISPLAY_BOOK = 2;
    //退出系统
    public static final int EXIT = 3;
    //上架图书
    public static final int ADD_BOOK = 4;
    //更新图书
    public static final int UPDATE_BOOK = 5;
    //删除图书
    public static final int REMOVE_BOOK = 6;
    //查看图书的借阅次数
    public static final int BORROWED_BOOK_COUNT = 7;
    //查看受欢迎的图书
    public static final int GENERATE_BOOK = 8;
    //查看库存状态
    public static final int CHECK_INVENTORY_STATUS = 9;
    //移除上架超过1年的书籍
    public static final int CHECK_AND_REMOVE_OLD_BOOK = 10;



    //-------------------普通用户相关操作管理----------------

    //借阅图书
    public static final int BORROWED_BOOK = 4;

    //归还图书
    public static final int RETURN_BOOK = 5;

    //查看个人借阅情况
    public static final int VIEW_BORROW_HISTORY_BOOK = 6;

}
