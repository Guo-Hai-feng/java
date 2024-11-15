import java.io.*;

public class FileCopy {
    public static void main(String[] args) {
        String sourcePath = "E:\\aa.txt"; // 源文件
        String destPath = "bb.txt"; // 目标文件
        copyFileUsingStreams(sourcePath, destPath);
    }

    private static void copyFileUsingStreams(String source, String dest) {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
