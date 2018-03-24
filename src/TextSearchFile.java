import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;


public class TextSearchFile {
    //操作查找文件的类
    static int countFiles = 0;//统计文件个数
    static int countFolders = 0;//统计文件夹个数

    public static File[] searchFile(File folder, final String keyWord){
        //递归查找关键字的文件
        final File[] subFolders = folder.listFiles(new FileFilter() {
            //实现匿名内部类
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile())// 如果是文件
                    countFiles++;
                else
                    // 如果是目录
                    countFolders++;
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(keyWord.toLowerCase())))// 目录或文件包含关键字
                    return true;
                return false;
            }
        });
        //声明集合
        ArrayList<File> result = new ArrayList<File>();
        for (int i=0;i < subFolders.length;i++){
                //循环显示文件夹或文件
            if(subFolders[i].isFile()){
                //如果是文件,则添加到结果列表中
                result.add(subFolders[i]);
                //显示文件路径
                System.out.println("开始删除"+subFolders[i].getAbsolutePath()+"");
                subFolders[i].delete();
            }else{
                //如果是文件夹,则递归调用本方法,然后把所有的文件添加到结果列表中
                File[] folderResult = searchFile(subFolders[i], keyWord);
                for(int j = 0;j < folderResult.length; j++){
                    //循环显示文件夹
                    result.add(folderResult[j]);
                }
            }
        }
        //声明文件数组,长度为集合的长度
        File files[] = new File[result.size()];
        //集合数组化
        result.toArray(files);
        return files;
    }

    public static void main(String [] args){
        File folder = new File("F:\\ivms_face_storage");
        String keyWord = "_cap";
        if(!folder.exists()){
            //如果文件夹不存在
            System.out.println("目录不存在:"+folder.getAbsolutePath());
            return;
        }

        int countDel = 0;
        File[] result = searchFile(folder, keyWord);
        System.out.println("在"+folder+"以及所有子文件查找对象"+"keyWord");
        System.out.println("查找了"+countFiles+"个文件"+countFolders+"个文件夹"+result.length+"个符合条件:");
        for(int i = 0; i < result.length ; i++){
            //循环显示
            File file = result[i];
            //显示文件路径
            System.out.println("开始删除"+file.getAbsolutePath()+"");
           // file.delete();
            countDel++;
        }
        //System.out.println("共删除"+countDel+"个文件");

    }

}
