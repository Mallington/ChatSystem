package messaging.system;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUtils {

    final static int DEFAULT_BOX_SIZE = 78;

    public void displayError(String title, String body) {
        List<String> msgs = new ArrayList<String>();

        msgs.add("[Woops]"); msgs.add(title);
        msgs.add("-"); msgs.add(body);

        System.out.println(printMessageBox(msgs));

    }

    public void printConsole(String consoleMessage) {
        List<String> msg = new ArrayList<String>();
        msg.add("[Console]"); msg.add(consoleMessage);
        System.out.println(printMessageBox(msg));
    }

    public static String printMessageBox(String flatString, int width){
        List<String> single = new ArrayList<String>();
        single.add(flatString);
        return printMessageBox(single, width);
    }

    public static String printMessageBox(String flatString){
        return printMessageBox(flatString, DEFAULT_BOX_SIZE);
    }
    public static String printMessageBox(List<String> messages){
        return printMessageBox(messages, DEFAULT_BOX_SIZE);
    }
    public static String printMessageBox(List<String> messages, int width){
        List<String> formatted = trimCarriageReturns(messages);
        formatted = trimLineLengths(formatted, width);

        width+=2;
        String append = "";

        append+=(fillln(width,'■'));

        for(String s : formatted) append+=(printMiddleln(s , width, ' ', '║'));

        append+=(fill(width,'═', '╚','╝'));

        return append;
    }

    private static String fill(int amount, char filler, char start, char end){
        String append = "";
        for(int i =0; i< amount; i++){
            if(i ==0){
                append+=start;
            } else if (i== amount-1){
                append+=end;
            } else{
                append += filler;
            }
        }
        return append;
    }
    private static String fillln(int amount, char filler){
        return fillln(amount, filler, filler, filler);
    }
    private static String fillln(int amount, char filler, char start, char end){
        return fill(amount, filler, start, end) +'\n';
    }
    private static String printMiddle(String toPrint, int width, char filler, char sides){
        String append = "";
        if(toPrint ==null) toPrint = "null";
        for(int i =0; i<=width; i++){
            if(i==0 || i == (width)) {
                append+= sides;
            }

            else if(toPrint!=null &&i== (width/2) - (toPrint.length()/2)) {
                for(char c : toPrint.toCharArray()) {
                    append+=c;
                    i++;
                }
            }

            else{
                append+=filler;
            }
        }
        return append;
    }

    private static String printMiddleln(String toPrint, int width, char filler, char sides){
        return (printMiddle(toPrint, width, filler, sides) + '\n');
    }

    private static List<String> trimCarriageReturns(List<String> strings){
        List<String> lineFormatted = new ArrayList<String>();

        for(String st: strings) {
            String append ="";
            for(char c : st.toCharArray()) {
                if(c == '\n') {
                    lineFormatted.add(append);
                    append = "";
                }
                else{
                    append+=c;
                }
            }
            if (append.length()>0) lineFormatted.add(append);
        }
        return lineFormatted;
    }

    private static List<String> trimLineLengths(List<String> strings, int maxSize){
        List<String> formatted = new ArrayList<String>();

        for(String message: strings){
            if(message!=null) {
                while (message.length() > maxSize) {
                    formatted.add(message.substring(0, maxSize));
                    message = message.substring(maxSize, message.length());
                }
                if (message.length() > 0) formatted.add(message);
            }
        }
        return formatted;
    }
}
