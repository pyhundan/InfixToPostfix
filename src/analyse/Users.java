package analyse;

import java.io.BufferedReader;
import java.io.FileReader;


import java.io.BufferedReader;
import java.io.FileReader;

public class Users {

    public WordAnalyse wordAnalyse;
    public StateAnalyze stateAnalyze;
    public String name;
    public Users(String filepath)
    {
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(filepath));
            StringBuffer stringBuffer=new StringBuffer();
            String line=bufferedReader.readLine();
            while(line!=null)
            {
                stringBuffer.append(line+'\n');
                line=bufferedReader.readLine();
            }
            name=filepath;
            wordAnalyse=new WordAnalyse(stringBuffer.toString());
            wordAnalyse.analyse();
            stateAnalyze=new StateAnalyze(wordAnalyse.tokes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Users(){}

}
