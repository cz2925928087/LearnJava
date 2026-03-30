package utils;

import book.Book;
import com.bit.utils.FileUtils;
import constant.Constant;

import java.io.IOException;
import java.time.LocalDate;


public class AnalyzingBook {

    public void storeObject(Book[] books, String filename) throws IOException {
        //有效的书籍 -  4
        int booksUseLen = 0;

        for (int i = 0; i < books.length; i++) {
            if(books[i] != null) {
                booksUseLen++;
            }
        }

        StringBuilder jsonArray = new StringBuilder();
        for (int i = 0; i < booksUseLen; i++) {

            if(books[i] != null) {
                jsonArray.append(books[i].toJSON());

                if (i != booksUseLen-1) {
                    //一本书籍完成后以\n进行分割
                    jsonArray.append("\n");
                }
            }
        }
        //把内存当中 存储的数据 写到磁盘上
        FileUtils.writeFile(jsonArray.toString(),filename);
    }

    //Book对象 1. 解析出来 一个一个的Book对象
    public Book[] loadObject(String filename) throws IOException {
        //从文件读取数据
        //1. 从磁盘的文件当中读取数据，读到内存当中
        String content = FileUtils.readFile(filename);

        if (content == null || content.isEmpty()) {
            System.out.println("图书馆为空！！！！");
            return null;
        }

        //2. 使用\n作为分隔符进行字符串分割
        String[] bookJsonStrings = content.split("\n");

        //3. 把对应字符串“组装”成书籍对象
        Book[] bookList = new Book[bookJsonStrings.length];
        for (int i = 0;i < bookJsonStrings.length;i++) {
            Book book = parseBookJson(bookJsonStrings[i]);
            bookList[i] = book;
        }

        return bookList;
    }

    //"0,java,gaobo,编程,1994...."
    private Book parseBookJson(String json) {
        String[] pairs = json.split(",");
        //Book book = new Book();
        //for (int i = 0; i < pairs.length; i++) {
        int bookId = Integer.parseInt(pairs[0]);
        String title = pairs[1];
        String author = pairs[2];
        String category = pairs[3];
        int publishYear = Integer.parseInt(pairs[4]);
        boolean isBorrowed = Boolean.parseBoolean(pairs[5]);//shift+tab    tab
        int borrowCount = Integer.parseInt(pairs[6]);
        LocalDate shelfDate = LocalDate.parse(pairs[7]);
        //}
        if (title != null && author != null && category != null && shelfDate != null) {
            Book book = new Book(title, author, category, publishYear, shelfDate);
            book.setBookId(bookId);
            book.setBorrowed(isBorrowed);
            book.setBorrowedCount(borrowCount);
            return book;
        }
        return null;
    }


    public static void main1(String[] args) {
        Book[] books = new Book[4];
        books[0] = new Book("java", "gaobo", "编程", 1994, LocalDate.of(2023, 9, 24));
        books[1] = new Book("mysql", "lisi", "编程", 1999, LocalDate.of(2024, 2, 10));
        books[2] = new Book("php", "gaobo", "编程", 2020, LocalDate.of(2023, 9, 23));
        books[3] = new Book("西游记", "吴承恩", "小说", 2024, LocalDate.of(2023, 9, 23));

        AnalyzingBook analyzingBook = new AnalyzingBook();

        try {
            analyzingBook.storeObject(books, Constant.ALL_BOOK_FILE_NAME);

            Book[] ret = analyzingBook.loadObject(Constant.ALL_BOOK_FILE_NAME);

            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
