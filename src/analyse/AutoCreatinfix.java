package analyse;

import java.io.File;
import java.util.Random;

public class AutoCreatinfix {

    public AutoCreatinfix(){}
    //生成随机带括号的表达式
    public  String Creatinfix() {
        //表达式中的数字的最少
        int leastNum = 4;
        //表达式中数字最多lastNum+mostNum
        int mostNum = 9;
        //随机生成snum个数字
        int snum = leastNum + (int) (Math.random() * mostNum);
        int[] numar;
        numar = new int[snum];
        int temp = 0;
        Random randomnumar = new Random();
        for (int i = 0; i < snum; i++) {
            //数值最大到49
            temp = randomnumar.nextInt(50);
            numar[i] = temp;
        }
        String[] stnum = new String[numar.length];
        for (int i = 0; i < numar.length; i++) {
            stnum[i] = "" + numar[i];
        }

        int index = 0;

        //表达式
        String stInfix = "";
        String[] op;
        op = new String[]{"+", "-", "*", "/"};

        //括号对数量
        int rightBracket = 0;
        int leftBracker = 0;

        //是否出现小数
        boolean hasdecimal = false;
        Random hd = new Random();
        if (hd.nextBoolean()) {
            double random = Math.random();
            int tempindex2 = (int) (Math.random() * (stnum.length));
            int tempint = Integer.parseInt(stnum[tempindex2]);
            double tempCimal = tempint + random;
            stnum[tempindex2] = String.format("%.2f", tempCimal);
        }

        //是否出现负数
        boolean hasNegative = false;
        Random hn = new Random();
        if (hn.nextBoolean()) {
            int tempindex = (int) (Math.random() * snum);
            if (stnum[tempindex] != "0") {
                stnum[tempindex] = "(-" + stnum[tempindex] + ")";
            }
        }

        //随机生成中缀表达式
        Random ra = new Random();
        while (index < snum) {
            boolean haslbefor = false;
            if (ra.nextBoolean()) {
                stInfix += "(";
                leftBracker++;
                haslbefor = true;
            }
            stInfix += stnum[index];
            if ((leftBracker > rightBracket) && !haslbefor && (ra.nextBoolean())) {
                stInfix += ")";
                rightBracket++;
            }
            Random randomop = new Random();
            int temp2 = randomop.nextInt(4);
            if (index != stnum.length - 1) {
                stInfix += op[temp2];
            }
            index++;
        }

        //补全括号对
        for (int p = rightBracket; p < leftBracker; p++) {
            stInfix += ")";
            rightBracket++;
        }
        return stInfix;
    }

    //删除多余的括号对
    public  String deleteExcBracket(String intfixExp) {
        int length = intfixExp.length();
        int index = 0;
        int lpos = 0;
        int rpos = 0;
        int temps = 0;
        String str = intfixExp;
        String tempstr = str;
        String res = "";
        int t = 0;
        while (index < tempstr.length()) {
            boolean hasop = false;
            if (tempstr.toCharArray()[index] == '(') {
                if (tempstr.toCharArray()[index + 1] == '-') {
                    index++;
                    continue;
                }
                //找到括号对的左、右括号
                lpos = index;
                rpos = index;
                temps = 1;
                for (int i = lpos + 1; i < tempstr.length(); i++) {
                    if (tempstr.toCharArray()[i] == '+' ||
                            tempstr.toCharArray()[i] == '-' ||
                            tempstr.toCharArray()[i] == '*' ||
                            tempstr.toCharArray()[i] == '/') {
                        hasop = true;
                    }
                    if (tempstr.toCharArray()[i] == '(') {
                        temps++;
                    }
                    if (tempstr.toCharArray()[i] == ')') {
                        temps--;
                    }
                    if (temps == 0) {
                        rpos = i;
                        break;
                    }
                }
                if (!hasop) {
                    StringBuilder sb = new StringBuilder(tempstr);
                    sb.replace(lpos, lpos + 1, "");
                    sb.replace(rpos - 1, rpos, "");
                    tempstr = sb.toString();
                    continue;
                }
                if (lpos == 0) {
                    if (rpos == tempstr.length() - 1) {
                        tempstr = tempstr.substring(lpos + 1, rpos);
                        continue;
                    }
                    if (tempstr.toCharArray()[rpos + 1] != '*'
                            && tempstr.toCharArray()[rpos + 1] != '/') {
                        StringBuilder sb = new StringBuilder(tempstr);
                        sb.replace(lpos, lpos + 1, "");
                        sb.replace(rpos - 1, rpos, "");
                        tempstr = sb.toString();
                        continue;
                    }
                } else {
                    if (tempstr.toCharArray()[lpos - 1] != '*'
                            && tempstr.toCharArray()[lpos - 1] != '/') {
                        if (rpos == tempstr.length() - 1) {
                            StringBuilder sb = new StringBuilder(tempstr);
                            sb.replace(lpos, lpos + 1, "");
                            sb.replace(rpos - 1, rpos, "");
                            tempstr = sb.toString();
                            continue;
                        } else {
                            if (tempstr.toCharArray()[rpos + 1] != '*'
                                    && tempstr.toCharArray()[rpos + 1] != '/') {
                                StringBuilder sb = new StringBuilder(tempstr);
                                sb.replace(lpos, lpos + 1, "");
                                sb.replace(rpos - 1, rpos, "");
                                tempstr = sb.toString();
                                continue;
                            }
                        }
                    }
                }


            }
            index++;
        }
        res = tempstr;
        return res;
    }

//    public static void main(String[] args) {
//
//        int c = 0;
//        while (c < 50) {
//            String tempstr = Creatinfix();
//            String temp = deleteExcBracket(tempstr);
//            System.out.println(temp);
//            c++;
//        }
//    }
}

