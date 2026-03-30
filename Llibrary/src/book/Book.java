package book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Book implements Comparable<Book>{
    //书的ID-编号
    private int bookId;
    //书名
    private String bookName;
    //作者
    private String author;
    //类别
    private String category;
    //出版年份
    private int publishYear;
    //借阅状态
    private boolean isBorrowed;
    //借阅次数
    private int borrowedCount;
    //上架时间
    private LocalDate shelDate;

    public Book() {

    }

    public Book(String bookName, String author, String category, int publishYear, LocalDate shelDate) {
        this.bookName = bookName;
        this.author = author;
        this.category = category;
        this.publishYear = publishYear;
        this.shelDate = shelDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public LocalDate getShelDate() {
        return shelDate;
    }

    public void setShelDate(LocalDate shelDate) {
        this.shelDate = shelDate;
    }
    //对借阅次数 每次 自增1 自减1的
    public void incrementBorrowCount() {
        this.borrowedCount++;
    }

    public void decreaseBorrowCount() {
        this.borrowedCount--;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", publishYear=" + publishYear +
                ", isBorrowed=" + isBorrowed +
                ", borrowedCount=" + borrowedCount +
                ", shelDate=" + shelDate +
                '}';
    }

    public String toJSON() {

        StringBuilder json = new StringBuilder();

        json.append(bookId).append(",");
        json.append(bookName).append(",");
        json.append(author).append(",");
        json.append(category).append(",");
        json.append(publishYear).append(",");
        json.append(isBorrowed).append(",");
        json.append(borrowedCount).append(",");
        json.append(shelDate != null ? shelDate.format(DateTimeFormatter.ISO_LOCAL_DATE) : "null");
        return json.toString();

    }

    @Override
    public int compareTo(Book o) {
        return o.getBorrowedCount() - this.getBorrowedCount();
    }
}
