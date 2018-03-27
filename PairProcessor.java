import ecs100.*;
import java.awt.Color;
import java.io.*;
import java.util.*;
import javax.swing.JColorChooser;
/**
 * Write a description of class PairProcessor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * intakes a string of data (from messages or other) through "askString" methods.
 * allows user to chose a file
 * writes string to a file, without rewriting previous file contents.
 * reads a file, creates objects for each pair of letters in the file and each letter.
 * places all letter pairs and letters into appropriate arraylists.
 * for each count of a letter pair read from the file the array list should increase in value by one
 */
public class PairProcessor
{
    // instance variables - replace the example below with your own
    private ArrayList<Pair> pairs = new ArrayList<Pair>();    // the list of letter pairs
    private ArrayList<Letter> letters = new ArrayList<Letter>(); // the list of Letters
    private ArrayList<Word> words = new ArrayList<Word>();// the list of words
    private String fileName;
    private int maxCount; //largest frequency of any pair
    private double stdv; //standard deviation of pair frequencies
    private double mean;

    /**
     * Constructor for objects of class PairProcessor
     */
    public PairProcessor()
    {
        UI.addButton("Input Text",this::writeToFile);//writes text to file to be read and analysed
        UI.addButton("Choose File",this::fileselect);// chooses file to write to or read from
        UI.addButton("New File", this::createFile);
        UI.addButton("analyse",this::analyse);//analysis tool
        // initialise instance variables
        UI.addButton("List Data",this::dataMenu);
        UI.addButton("clean",this::clean);
        //UI.addButton("List data statistics", this::listDataStats);//incomplete, finds mean, max and stdv currently,
        // UI.addButton("List Relevant", this::listDataNice);//incomplete, finds mean, max and stdv currently,// should list only certain data
        UI.addButton("graph Frequency Distribution ",this::GraphFreq);
        UI.addButton("list Letters by magnitude", this::listLettersOrder);
        UI.addButton("list pairs by magnitude", this::listPairsOrder);
        UI.addButton("retrieve pairs by contents", this::retrievePair);
    }
    public void dataMenu(){//uses written commands to lower number of buttons
        UI.println("Select data to display:");
        UI.println("'words' for words by frequency, 'sigWords' for only the most frequent words ");
        UI.println("'pairs' for pairs and letters, 'stats' for pair data statistics");
        String dataType = UI.askString("enter data:");
        if(dataType.equalsIgnoreCase("words")){
        this.listWordData();
        }
        else if(dataType.equalsIgnoreCase("sigwords")){
        this.listSigWordData();
        }
        else if(dataType.equalsIgnoreCase("pairs")){
        this.listData();
        }
        else if(dataType.equalsIgnoreCase("Stats")){
        this.listDataStats();
        }
        
        
    
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void fileselect()
    {
        UI.println("select file to read from/write to");
        fileName=UIFileChooser.open();
        UI.println(fileName);
        // sets filename field to name of selected file.
    }

    public void createFile(){
        String NFName = UI.askString("New file name?");
        File newFile = new File(NFName);
        fileName = NFName;

    }

    public void writeToFile(){
        int numprint = 0;
        try{
            PrintStream out = new PrintStream(fileName);
            ArrayList<String> text = UI.askStrings("write text here");
            for(String s:text){ 
                out.println(s);
                numprint++;
            }
            UI.println(numprint + " strings successfully printed to " +fileName);
        }catch(IOException e){UI.println("error: "+e);}

    }

    public void analyse(){
        try{ 
            int countwords = 0;
            Scanner scan = new Scanner(new File(fileName));
            while(scan.hasNext()){
                String s = scan.next();
                toPairs(s);
                addWord(s);
            }

            UI.println(words.size() + "words counted");

        }catch(Exception e){UI.println("error: "+e);}
        UI.println(letters.size()+"letters found");
    }

    public void toPairs(String s){
        int lngth = s.length();
        //UI.println(lngth);
        int lcount = 1;
        while(lcount<=(lngth-1)){
            boolean existsa = false;// converts string to pairs of char
            char char1a = Character.toLowerCase(s.charAt(lcount-1));

            char char2a = Character.toLowerCase(s.charAt(lcount));
            //failed attempt to convert characters to string did not account for the 
            //inequality between a character and the corresponding string
            for(Pair p:pairs){
                if(char1a==p.getFirst()){
                    if(char2a==p.getLast()){
                        existsa=true;
                        p.raiseCount(1);
                    }
                }

                //checks if pair exists, raises count if yes, creates new pair if no

            }
            if(existsa!=true){
                Pair p1= new Pair(char1a,char2a);
                pairs.add(p1);
                //UI.println("pair added"); bug test
            }
            //UI.println(existsa);
            lcount++;
        }

        lcount=1;
        while(lcount<=(lngth)){//converts to letter objects
            boolean existsb = false;
            char char1 = Character.toLowerCase(s.charAt(lcount-1));

            // String charL =Character.toString(char1);

            // changed all Strings to Char arrays in an attempt to fix 
            //issues with converting char to strings.
            /**for(Letter l:letters){
            UI.println(l.returnLetter()); bug test
            }*/
            for(Letter l: letters){
                char l1 = l.returnLetter();

                if(l1==char1){
                    existsb=true;
                    l.increaseCount();
                    //UI.println("true"); bug test
                }
            }
            if(existsb!=true){
                Letter l = new Letter(char1);
                letters.add(l);
                // UI.println("false"); bug test
            }

            lcount++;
        }

    }

    public void addWord(String s){//adds a new word object if the word does not exist and raises count of current object if it does
        boolean exists = false;
        String S=s.replaceAll("[\\W]","");//ensures only letters are counted as part of words.
        for(Word w:words){
            if(S.equals(w.returnWord())){
                w.raiseCount(1);
                exists=true;
            }

        }
        if(exists==false){
            Word w2= new Word(S);
            words.add(w2);
        }
        //UI.println(s+"  "+S); method tester

    }

    public void listData(){

        for(Letter l:letters){
            char cc = l.returnLetter();
            int cn = l.getCount();
            UI.println(cc+"  " + cn);

        }

        for(Pair p:pairs){
            char char1=p.getFirst();
            char char2=p.getLast();
            int ct = p.getCount();
            //UI.println(java.lang.Character.hashCode(char1));
            // UI.println(java.lang.Character.hashCode(char2)); bug locator
            UI.println(char1+""+char2+"  "+ct);
        }

        UI.println(pairs.size()+" pairs");
        int counto=1;
        /*for(Letter l:letters){
        UI.println(l.returnLetter()+" " +counto);
        counto++; //checks each letters value
        }*/
    }

    public void listWordData(){//lists words in order of frequency
        int maxCount=0;
        for(Word w:words){
            if(w.returnCount()>maxCount){
                maxCount=w.returnCount();
            }
        }
        while(maxCount>0){
            for(Word w:words){
                if(w.returnCount()==maxCount){
                    UI.println(w.returnWord()+"  "+maxCount);

                }
            }
            maxCount-=1;

        }
    
    }
    public void listSigWordData(){//lists top 25% of frequencies of words
     int maxCount=0;
        for(Word w:words){
            if(w.returnCount()>maxCount){
                maxCount=w.returnCount();
            }
        }
      int minDisplayCount = (int)(maxCount*0.75);//lower boundary of numbers displayed
         while(maxCount>=(minDisplayCount)){
            for(Word w:words){
                if(w.returnCount()==maxCount){
                    UI.println(w.returnWord()+"  "+maxCount);

                }
            }
            maxCount-=1;

        }
    }

    /* cleans data of any pairs that are the same letters in the wrong order, and certain punctuation*/

    public void clean(){
        boolean pairsExist=false;
        for(Pair p:pairs){

            if(p!=null){
                char char1a=p.getFirst();
                char char2a = p.getLast();
                for(Pair p2:pairs){
                    if(p2!=null&&pairsExist!=true){
                        char char1b= p2.getFirst();
                        char char2b = p2.getLast();
                        if(char1a==char2b&&char1b==char2a){

                            p.raiseCount(p2.getCount());
                            pairs.remove(p2);
                            // p2.raiseCount(-(p2.getCount()));

                            // UI.println("pair identified");
                            pairsExist=true;

                            //return true;

                            break;
                            //issues with program recognising redundancy but only removing first repeated pair
                            //issues not solved by marking the second of the double ups with a count of zero 
                            //then removing all marked pairs in a separate loop
                            //plan to solve problem by calling method each time until no redundancies are detected.
                        }
                    }
                    else{UI.println("null");}

                }

                if(pairsExist){break;}
            } else{                        UI.println("null");}
        }

        /*
        ?boolean redundancy = true;
        while(redundancy==true){
        redundancy=false;
        for(Pair p:pairs){
        if(p.getCount()==0){
        pairs.remove(p);
        redundancy=true;
        UI.println("pair removed");
        } attempt to fix problem with only one pair being removed

        }**/

        if(pairsExist){

            //UI.println("one pair identified, looping");
            this.clean();
        }
        else{UI.println("finished redundancy check");
            this.punClean();
        }

        //error fixed by having each loop break upon discovering a pair, without completing their tasks.
        //error was therefore not identified
    }

    public void punClean(){
        //checks if a character is a letter and
        //removes Letter objects that do not contain roman alphabet letters

        boolean punctExists = false;
        for(Letter l:letters){
            char letter1=l.returnLetter();
            if(!Character.isLetter(letter1)){
                letters.remove(l);
                // UI.println("punctuation identified and removed");
                punctExists=true;
                break;
            }
        } 
        if(punctExists){
            // UI.println("looping");
            this.punClean();
        }
        else{UI.println("finished letters clean");
            this.punClean2();}
        //calls punClean2 to clean pairs
    }

    public void punClean2(){
        //checks for punctuation in pairs and removes those that contain punctuation
        boolean punctExists = false;
        for(Pair p:pairs){
            char letter1=p.getFirst();
            char letter2=p.getLast();
            if(!Character.isLetter(letter1)||!Character.isLetter(letter2)){
                punctExists =true;
                //UI.println("punctuation identified and pair removed");
                pairs.remove(p);
                break;

            }

        } 
        if(punctExists){
            // UI.println("looping");
            this.punClean2();
        }
        else{UI.println("finished pairs clean");}

    }

    public void listDataStats(){
        //list data within a certain range of maximum count of a pair
        //establishes which pairs are relevant and which are outliers.
        
        /**should also list statistics for letters and words*/
        int maxMag =0;
        int totalCount =0;
        double countSquare = 0;

        for(Pair p:pairs){
            int mag = p.getCount();
            totalCount+=p.getCount();//finds mean, max and total.
            if(mag>=maxMag){
                maxMag=mag;

            }
        }
        mean=(double)(totalCount/pairs.size());
        UI.println("mean frequency of pairs present"+mean);
        UI.println("max frequency of pairs present "+maxMag);
        maxCount=maxMag;
        for(Pair p:pairs){
            double displacement =(double)((p.getCount()-mean)*(p.getCount()-mean));
            countSquare+=(displacement);//to find stawndard deviation.
        }
        stdv=countSquare/(totalCount-1);
        UI.println("standard deviation of frequency of pairs " + stdv);
        UI.println(pairs.size()+" pairs");
        UI.println(letters.size()+" Letters");

    }

    public void listDataNice(){
        //lists significant data
        this.listDataStats();
        if((2*stdv)<=maxCount){
            for(Pair p:pairs){

            }
        }
        else{this.listData();}
    }

    public void listLettersOrder(){
        int count = 0; 
        int max =0;
        for(Letter l:letters){
            if(l.getCount()>max){
                max=l.getCount();
            }
        }
        while(count<=max){
            for(Letter l:letters){
                if(l.getCount()==count){
                    UI.println(l.returnLetter()+" "+l.getCount());
                }
            }
            count++;
        }

    }

    public void listPairsOrder(){
        int count = 0; 
        int max =0;
        for(Pair p:pairs){
            if(p.getCount()>max){
                max=p.getCount();
            }
        }//finds max for second loop
        while(count<=max){
            for(Pair p:pairs){
                if(p.getCount()==count){
                    UI.println(p.getFirst()+""+p.getLast()+"  "+p.getCount());
                }//prints pairs from least to most common
            }
            count++;
        }

    }

    public void retrievePair(){
        int max =0;
        for(Pair p:pairs){
            if(p.getCount()>max){
                max=p.getCount();
            }
        }
        String Sfirst = UI.askString("first character? '~' for n/a");
        String Ssecond = UI.askString("second character? '~' for n/a");
        char first = Sfirst.charAt(0);
        char second =Ssecond.charAt(0);
        int count = 0;
        if(Sfirst.equals("~")&&Ssecond.equals("~")){
            this.listPairsOrder();
        }
        if(Ssecond.equals("~")){
            count=0;
            while(count<=max){
                for(Pair p:pairs){
                    if(p.getFirst()==first||p.getLast()==first){
                        if(p.getCount()==count){
                            UI.println(p.getFirst()+""+p.getLast()+"  "+p.getCount());
                        }
                    }
                }
                count++;
            }

        }
        else if(Sfirst.equals("~")){
            count=0;
            while(count<=max){
                for(Pair p:pairs){
                    if(p.getLast()==second||p.getFirst()==second){
                        if(p.getCount()==count){
                            UI.println(p.getFirst()+""+p.getLast()+"  "+p.getCount());
                        }
                    }
                }
                count++;
            }

        }
        else{
            count=0;
            while(count<=max){
                for(Pair p:pairs){
                    if((p.getFirst()==first&&p.getLast()==second)||(p.getLast()==first&&p.getFirst()==second)){

                        if(p.getCount()==count){
                            UI.println(p.getFirst()+""+p.getLast()+"  "+p.getCount());
                        }

                    }
                }
                count++;
            }

        }

    }

    public void GraphFreq(){
        //graphs frequency of each count.

        UI.clearGraphics();
        this.listDataStats();
        double count = 0;
        double freqMax = 0;
        double countAtMax=0;
        while(count<=maxCount){
            int freq=0;
            for(Pair p:pairs){
                if(count==p.getCount()){
                    freq++;//raises frequency of this count by one and replaces maxCount if necesary.

                }

            }
            if(freq>freqMax){
                freqMax=freq;
                countAtMax=count;
            }
            count++;

        }//checks most frequent count.
        UI.println(countAtMax+" max with " + freqMax);
        UI.drawString("number of occurences of pairs at each count",10,10);
        double gLeft = 60;
        double gBase = 400;
        double gHeight = 350;
        double gWidth = 700;

        double bWidth = 20;
        double bScale = maxCount/40;
        int bScale2 = (int)(Math.log(bScale)/Math.log(2));
        double gScale = gHeight/(freqMax*((Math.log(bScale)/Math.log(2))));//eventually bscale will be replaced by log 2 of bscale
        UI.drawLine(gLeft, gBase-gHeight, gLeft, gBase);
        UI.drawLine(gLeft, gBase, gLeft+gWidth, gBase);//draws baselines
        double markings = 1;
        UI.println(bScale+"scale");
        UI.println(maxCount);
        count=0;

        while(markings<=8){

            UI.drawLine(gLeft,gBase-markings*gHeight/8 ,gLeft-15,gBase-markings*gHeight/8 );
            UI.drawString(""+(markings/8)*(freqMax*bScale2),gLeft-45,gBase-markings*gHeight/8);//labels axis
            //UI.drawLine(gLeft,gBase-markings*gHeight/8 ,gLeft-15,gBase-markings*gHeight/8 );
            markings++;//marks vertical axis.
        }
        while(count<=40){
            int freq=0;
            for(Pair p:pairs){
                if(p.getCount()>((count-1)*bScale)&&p.getCount()<=count*bScale){
                    freq++;//raises frequency of this count by one and replaces maxCount if necesary.

                }

            }
            UI.setColor(Color.pink);

            UI.fillRect(gLeft+(count-1)*bWidth, gBase-(freq*gScale), bWidth, freq*gScale);
            UI.setColor(Color.black);
            UI.drawRect(gLeft+(count-1)*bWidth, gBase-(freq*gScale), bWidth, freq*gScale);
            UI.drawString(""+(int)((count-1)*bScale),gLeft+(count-1)*bWidth, gBase+15);
            //UI.println(count);
            count+=1;
        }
        int freq=0;

        for(Pair p:pairs){
            if(p.getCount()>=40*bScale){
                freq++;//raises frequency of this count by one and replaces maxCount if necesary.

            }

        }

        UI.setColor(Color.pink);
        UI.fillRect(gLeft+(40)*bWidth, gBase-(freq*gScale), bWidth, freq*gScale);
        UI.setColor(Color.black);
        UI.drawRect(gLeft+(40)*bWidth, gBase-(freq*gScale), bWidth, freq*gScale);
        UI.drawString(""+(int)((count-1)*bScale)+"+",gLeft+(count-1)*bWidth, gBase+15);
        //UI.println(count);

    }

}
