package analyse;


import error.CodeException;

import java.util.LinkedList;

/*
    exp -> term {addop term}
    addop -> + \ -
    term -> Negative {mulop Negative}
    mulop -> *|/
    Nagative -> -factor|factor
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
            output+="|";
        }

    }
    public void term() throws CodeException
    {
        Negative();
        while (tok.word.equals("*")||tok.word.equals("/"))
        {
            String s=tok.word;
            match(tok.word);
            Negative();
            output+=s;
            output+="|";
        }

    }
    public void Negative() throws CodeException
    {
        if (tok.word.equals("-"))
        {
            String s=tok.word;
            match("-");
            output+=s;
            factor();
        }
        else factor();
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
            //int temp=Integer.parseInt(tok.word);
            String s=tok.word;
            match("number");
            output+=s;
            output+="|";
        }
        else Error("not a factor! (index:"+(index)+",line:"+tok.line+")");
    }

    public static void main(String args[])
    {
        Users users=new Users("test.txt");
        System.out.println(users.wordAnalyse.tokes);
        users.stateAnalyze.start_analyse();
        System.out.println(users.stateAnalyze.output);
    }
}
