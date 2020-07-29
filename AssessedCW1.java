// Qingyu WANG 20124865 v2

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// interface
interface Task
{
    void taskCompleter(String str);
    String[] strToArr(String str);
    void counter(String[] word, int[] count, String[] strArr);
    int taskHelper(int[] count);
}

public class AssessedCW1
{
    public static void main(String[] args)
    {
        String filePath = "Test.txt";
        String originalStr = AssessedCW1.fileReader(filePath);

        // task1
        String lowerCaseStr = originalStr.toLowerCase();
        Task1 task1 = new Task1();
        task1.taskCompleter(lowerCaseStr);

        // task2
        Task1 task2 = new Task2();
        task2.taskCompleter(originalStr);
    }

    private static String fileReader(String filePath)
    {
        String fileToStr = "";

        // exception handling
        try(FileInputStream file = new FileInputStream(filePath))
        {
            fileToStr = new String(Files.readAllBytes(Paths.get(filePath)));
            file.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        return fileToStr;
    }
}

class Task1 implements Task // interface
{
    public void taskCompleter(String str)
    {
        String[] strArr = strToArr(str);

        String[] word = new String[1024];
        int[] count = new int[1024];

        counter(word, count, strArr);

        int maxId = taskHelper(count);
        System.out.println(word[maxId] + " " + count[maxId]);
    }

    public String[] strToArr(String str) {
        return str.split("[^a-z|A-Z]+");
    }

    public void counter(String[] word, int[] count, String[] strArr)
    {
        // method local inner class
        class innerCounter { 
            public void countAsArr(){
                for(int i = 0; i < strArr.length; i++)
                {
                    int flag = 0;
                    int j = 0;
                    for( ; j < word.length; j++)
                    {
                        if(strArr[i].equals(word[j]))
                        {
                            flag = 1;
                            count[j] = count[j] + 1;
                            break;
                        }
                        if(word[j] == null)
                        {
                            break;
                        }
                    }
                    if(flag == 0)
                    {
                        word[j] = strArr[i];
                        count[j] = 1;
                    }
                }
            }
        }
        innerCounter counter = new innerCounter();
        counter.countAsArr();
    }

    public int taskHelper(int[] count) 
    {
        int max = count[0];
        int position = 0;
        for(int i = 1; i < count.length; i++)
        {
            if(count[i] == 0)
            {
                break;
            }
            if(max < count[i])
            {
                max = count[i];
                position = i;
            }
        }
        return position;
    }
}

class Task2 extends Task1
{
    // polymorphism (override)
    public void taskCompleter(String str)
    {
        String[] strArr = strToArr(str);

        String[] word = new String[1024];
        int[] count = new int[1024];

        counter(word, count, strArr);

        String[] onceStr = taskHelper(word, count);
        for(int i = 0; i < onceStr.length; i++)
        {
            if(onceStr[i] == null)
            {
                break;
            }
            System.out.print(onceStr[i] + " ");
        }
    }

    // polymorphism (override)
    public String[] taskHelper(String[] word, int[] count)
    {
        String[] onceArr = new String[1024];
        int j = 0;

        for(int i = 0; i < count.length; i++)
        {
            if(word[i] == null)
            {
                break;
            }
            if(count[i] == 1)
            {
                onceArr[j] = word[i];
                j++;
            }
        }
        return onceArr;
    }
}