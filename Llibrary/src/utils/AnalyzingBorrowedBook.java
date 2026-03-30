package utils;

import book.PairOfUidAndBookId;
import com.bit.utils.FileUtils;

import java.io.IOException;


public class AnalyzingBorrowedBook {


    public void storeObject(PairOfUidAndBookId[] pairOfUidAndBookIds, String filename) throws IOException {

        //先遍历pairOfUidAndBookIds数组当中不为空的数据
        int booksUseLen = 0;
        for (int i = 0; i < pairOfUidAndBookIds.length; i++) {
            if(pairOfUidAndBookIds[i] != null) {
                booksUseLen++;
            }
        }

        StringBuilder jsonArray = new StringBuilder();

        for (int i = 0; i < booksUseLen; i++) {
            if(pairOfUidAndBookIds[i] != null) {
                jsonArray.append(pairOfUidAndBookIds[i].toJson());
                if (i != booksUseLen-1) {
                    jsonArray.append("\n");
                }
            }
        }

        FileUtils.writeFile(jsonArray.toString(),filename);
    }


    public PairOfUidAndBookId[] loadObject(String filename) throws IOException {
        //从文件读取数据
        String content = FileUtils.readFile(filename);

        if (content == null || content.isEmpty()) {
            System.out.println("已借阅书籍列表无数据，表示没有用户借阅过书籍");
            return null;
        }


        String[] JsonStrings = content.split("\n");

        PairOfUidAndBookId[] pairOfUidAndBookIds
                = new PairOfUidAndBookId[JsonStrings.length];


        for (int i = 0; i < JsonStrings.length; i++) {

            PairOfUidAndBookId pairOfUidAndBookId = new PairOfUidAndBookId();
            //1 1001/将String转换为int的数字标号为书的id
            String[] uidAndBookIds = JsonStrings[i].split(",");
            pairOfUidAndBookId.setUserId(Integer.parseInt(uidAndBookIds[0]));
            pairOfUidAndBookId.setBookId(Integer.parseInt(uidAndBookIds[1]));

            pairOfUidAndBookIds[i] = pairOfUidAndBookId;
        }



        return pairOfUidAndBookIds;
    }

}
