import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by bijiachneg on 2017/3/2.
 */

public class Calculator {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int numOfQuestion = 1;
    static String[] question = new String[1000];

    public static int enterNumber() {
        String number = null;

        System.out.println("请输入做题数：");
        number = inputNumber();

        while (!isNumber(number)) {
            System.out.println("输入错误！请重新输入：");
            number = inputNumber();
        }

        int num = Integer.valueOf(number).intValue();
        return num;
    }

    public static boolean isNumber(String str){
        if (Pattern.compile("[0-9]*").matcher(str).matches()&&!str.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    public static String inputNumber(){
        String number="";
        try {
            number = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number.replace(" ","");
    }


    public static void question(int number){
        String[] trueanswer = new String[number];
        String[] answer = new String[number];
        int rightnumber = 0 ;
        int flag = number;

        System.out.println("输入exit可以结束做题");

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        for(int i=0;i<number;i++){
            trueanswer[i] = setQuestion();
            answer[i] = inputNumber();
            while (!isAnswer(answer[i])){
                System.out.println("输入错误！请重新输入：");
                answer[i] = inputNumber();
            }
            if (answer[i].equals("exit")){
                flag = i+1;
                break;
            }
            if (trueanswer[i].equals(answer[i])){
                rightnumber++;
            }

        }
        System.out.println("共做"+flag+"道题，正确："+rightnumber+",错误："+(flag-rightnumber)+
                "，正确率："+numberFormat.format((float)rightnumber/(float)flag*100)+"%^");

        for (int i=0;i<flag;i++){
            System.out.println("第"+(i+1)+"题答案："+trueanswer[i]);
        }
    }

    public static boolean isAnswer(String str){
        if (str.substring(str.indexOf("/")+1).equals("0")&&!str.equals("0")){
            return false;
        }
        else if (!str.equals("/")&&!str.equals("-")&&Pattern.compile("[-/0-9]*").matcher(str).matches()||str.equals("exit")){
            return true;
        }
        else{
            return false;
        }
    }

    public static String setQuestion(){
        int symbol;
        int[] number1 = new int[2];
        int[] number2 = new int[2];
        String answer = null;

        symbol = randomNumber(1,4);

        number1 = setModel(randomNumber(1,2));
        number2 = setModel(randomNumber(1,2));

        answer = count(number1,number2,symbol);
        return answer;
    }


    public static int randomNumber (int min ,int max){
        Random random = new Random();
        int randomnum;
        randomnum = random.nextInt(max)%(max-min+1)+min;
        return randomnum;
    }
    public static int[] randomInt(){
        int[] number = new int[2];
        number[1] = 1;
        number[0] = randomNumber(0,100);
        return number;
    }
    public static int[] randomFraction(){
        int[] number = new int[2];
        number[1] = randomNumber(2,100);
        number[0] = randomNumber(1,number[1]);
        return number;
    }

    public static int[] setModel(int model){
        int[] number = new int[2];
        switch (model){
            case 1:
                number = randomInt();
                break;
            case 2:
                number = randomFraction();
                break;
            default:
                break;
        }
        return number;
    }

    public static String count(int number1[],int number2[],int symbol){
        int[] answer = new int[2];

        switch (symbol){
            case 1:
                answer[0] =(number1[0]*number2[1])+(number2[0]*number1[1]);
                answer[1] = number1[1]*number2[1];
                outputQuestion(number1,number2,"+");
                break;
            case 2:
                answer[0] =(number1[0]*number2[1])-(number2[0]*number1[1]);
                answer[1] = number1[1]*number2[1];
                outputQuestion(number1,number2,"-");
                break;
            case 3:
                answer[0] = number1[0]*number2[0];
                answer[1] = number1[1]*number2[1];
                outputQuestion(number1,number2,"×");
                break;
            case 4:
                while(number2[0]==0){
                    number2 = randomInt();
                }
                answer[0] = number1[0]*number2[1];
                answer[1] = number1[1]*number2[0];
                outputQuestion(number1,number2,"÷");
                break;
            default:
                break;
        }
        answer = simplification(answer);
        return isInt(answer);
    }
    public static void outputQuestion(int number1[],int number2[],String str){
        String[] num = new String[2];

        number1 = simplification(number1);
        number2 = simplification(number2);

        num[0] = isInt(number1);
        num[1] = isInt(number2);

//        if (isQuestionExist(num,str)){
//            setQuestion();
//            return;
//        }

        System.out.print(num[0]+" "+str+" "+num[1]+" =");
    }

    public static boolean isQuestionExist(String number[],String symbol){
        for (int i=0;i<numOfQuestion;i++){
            if (question[i].equals(number[1]+symbol+number[2])){
                return true;
            }
            else {
                question[i] = number[1]+symbol+number[2];
                numOfQuestion++;
                return false;
            }
        }
        return false;
    }

    public static int[] simplification(int number[]){
        int gcd = gcd(number[0],number[1]);

        number[0] = number[0]/gcd;
        number[1] = number[1]/gcd;

        return number;
    }

    public static int gcd(int x,int y){
        while (x!=0){
            int temp = y%x;
            y = x;
            x = temp;
        }
        return y;
    }

    public static String isInt(int number[]){
        String num;

        if(number[1]==1){
            num = String.valueOf(number[0]);
        }
        else {
            num = String.valueOf(number[0])+"/"+String.valueOf(number[1]);
        }

        return  num;
    }

    public static void main(String[] args){
        int number;
        number=enterNumber();
        question(number);
    }
}