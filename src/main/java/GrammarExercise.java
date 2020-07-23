import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrammarExercise {
    public static void main(String[] args) {
        //需要从命令行读入
        String firstWordList = "";
        String secondWordList = "";

        //命令行输入
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input word list1: ");
        firstWordList = scanner.next();
        System.out.print("Please input word list2: ");
        secondWordList = scanner.next();

        List<String> result = findCommonWordsWithSpace(firstWordList,secondWordList);
        System.out.print(result);
        //按要求输出到命令行

    }

    public static List<String> findCommonWordsWithSpace(String firstWordList, String secondWordList) {
        //在这编写实现代码
        List firstArr = Arrays.asList(firstWordList.split(","));
        List secondArr = Arrays.asList(secondWordList.split(","));

        //正则表达式判断是否所有的单词形式都正确
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Predicate checkWord = word -> pattern.matcher((String) word).matches();
        if (firstArr.stream().allMatch(checkWord) && secondArr.stream().allMatch(checkWord) ) {
            Set commonWords = new HashSet();
            List outputWords = new ArrayList();
            //用Stream模拟两层for循环
            firstArr.stream().forEach(
                    word1 -> {
                        String w1 = (String) word1;
                        //如果outputWords里没有w1则添加
                        if (! outputWords.stream().anyMatch(
                                outputWord -> ((String) outputWord).toLowerCase().equals(w1.toLowerCase())
                        )) {
                            outputWords.add(w1);
                        }
                        //第二层循环
                        secondArr.stream().forEach(
                            word2 ->{
                                String w2 = (String) word2;
                                //判断两个单词是否一样，忽略大小写
                                if (w1.toLowerCase().equals(w2.toLowerCase())) {
                                    commonWords.add(w1);
                                }
                                //如果outputWords里没有w2则添加
                                if (! outputWords.stream().anyMatch(
                                        outputWord -> ((String) outputWord).toLowerCase().equals(w2.toLowerCase())
                                )) {
                                    outputWords.add(w2);
                                }
                            }
                    );}
            );
            //commonWords中的每一个单词添加space
            List commonWordsList = new ArrayList();
            commonWords.stream().forEach(
                word -> {
                    commonWordsList.add(((String) word).replaceAll("", " ").trim().toUpperCase());
                }
            );

            //给输出单词排序
            outputWords.stream().sorted().forEach(
                    word -> System.out.print(word + " ")
            );
            System.out.println();

            return commonWordsList;
        } else {
            throw new RuntimeException("input not valid");
        }
    }
}
