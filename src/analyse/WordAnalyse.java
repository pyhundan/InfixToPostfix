package analyse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
class Toke {
    //public static int no=0;

    int number;
    String word;
    String type;
    int line;

    public Toke(int n, int l)
    {
        this.number=n;
        this.line=l;
    }

    @Override
    public String toString() {
        return number+"+"+word+"+"+type+'\n';
    }
}
//词法分析器
public class WordAnalyse {

    public LinkedList<Toke> tokes;
    public String source;

    int no;
    int line;

    final static int NUMBER=1;
    final static int CHAR=2;
    final static int START=0;

    public static HashSet<String> reserer=new HashSet<>();


    public WordAnalyse(String s){

        source=s;
        tokes =new LinkedList<>();
        no=0;
        line=1;
    }

    public int getWhat(char c)
    {
        if(c<='9'&&c>='0')
            return NUMBER;
        else return CHAR;
    }

    public List<Toke> analyse()
    {
        int index=0;
        int state=0;
        String temp="";
        while(index<source.length())
        {
            char cur=source.charAt(index);
            if (state==START) {
                state=getWhat(cur);
            }
            switch (state) {
                case NUMBER:
                    if ((cur>='0'&&cur<='9')||cur=='.') {
                        temp=temp+cur;
                        cur=source.charAt(index++);
                        continue;
                    }
                    else{
                        Toke t=new Toke(no++,line);
                        t.word=temp;
                        t.type="number";
                        tokes.addLast(t);
                        temp="";
                        state=START;
                    }
                    break;
                case CHAR:
                    switch (cur) {
                        case '\n':
                            line++;
                            index++;
                            state=START;
                            break;
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                        case '(':
                        case ')':
                            Toke t=new Toke(no++,line);
                            temp=temp+cur;
                            t.type="character";
                            t.word=temp;
                            tokes.addLast(t);
                            state=START;
                            temp="";
                            index++;
                            break;
                        default:
                            index++;
                            state=START;
                            break;
                    }
                    break;
            }
        }
        return tokes;
    }
    //测试
    public static void main(String args[]) {
        StringBuffer all = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
            String line = reader.readLine();
            while (line != null) {
                all.append(line);
                all.append('\n');
                line = reader.readLine();
            }
            WordAnalyse w = new WordAnalyse("1--2-3"+"\n");
           // WordAnalyse w = new WordAnalyse(all.toString());
            w.analyse();
            System.out.print(w.source);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(w.tokes);
        } catch (Exception e) {

        }
    }
}
