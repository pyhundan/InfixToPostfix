package analyse;


import error.CodeException;

import java.util.LinkedList;

/*
    exp -> exp {addop term}
    addop -> + \ -
    term -> term {mulop factor}
    mulop -> *|/
    factor -> (exp) |number

 */
public class StateAnalyze {

    public LinkedList<Toke> tokes;
    public Toke tok;
    public int index;
    String fail;
    public String output="";

    public StateAnalyze(LinkedList<Toke> list)
    {
        this.tokes=list;
    }
    public void Error(String a) throws CodeException
    {
        System.err.print(a);
        throw new CodeException(a);
    }
    public void match(String a) throws CodeException
    {
        if (tok.word.equals(a)||tok.type.equals(a))
        {
            if (index<tokes.size())
            {
                tok=tokes.get(index++);
            }
        }
        else
        {
            Error("wrong word! expected："+a+" actually:"+tok.word+"index:"+(index+1)+";line:"+tok.line);

        }
    }
    public void start_analyse()
    {
        index=0;
        try {
            tok= tokes.get(index++);
            exp();
        }catch (CodeException e)
        {
            fail=e.message;
        }
    }
    public void exp() throws CodeException
    {
        term();
        while(tok.word.equals("+")||tok.word.equals("-"))
        {
            String s=tok.word;
            match(tok.word);
            term();
            output+=s;
            output+=".";
        }

    }
    public void term() throws CodeException
    {
        factor();
        while (tok.word.equals("*")||tok.word.equals("/"))
        {
            String s=tok.word;
            match(tok.word);
            factor();
            output+=s;
            output+=".";
        }

    }
    public void factor() throws CodeException
    {
        char c=tok.word.charAt(0);
        if (tok.word.equals("("))
        {
            match("(");
            exp();
            match(")");
        }
        else if (tok.type.equals("number"))
        {
            int temp=Integer.parseInt(tok.word);
            String s=tok.word;
            match("number");
            output+=s;
            output+="|";
        }
        else Error("not a factor! (index:"+(index)+",line:"+tok.line+")");
    }

//    public static void main(String args[])
//    {
//        Users users=new Users("测试.txt");
//        System.out.println(users.wordAnalyse.tokes);
//        users.stateAnalyze.start_analyse();
//        System.out.println(users.stateAnalyze.output);
//    }
}
