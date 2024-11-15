import java.io.File;

public class FileTree {
    public static void main(String[] args) {
        File root = new File("E:\\note\\java\\实验"); // 替换为你的路径
        printDirectoryTree(root, 0);
    }

    private static void printDirectoryTree(File file, int level) {
        if (file.isDirectory()) {
            System.out.println("  ".repeat(level) + "[" + file.getName() + "]");
            for (File subFile : file.listFiles()) {
                printDirectoryTree(subFile, level + 1);
            }
        } else {
            System.out.println("  ".repeat(level) + file.getName());
        }
    }
}


