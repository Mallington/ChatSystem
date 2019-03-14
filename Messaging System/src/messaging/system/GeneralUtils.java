package messaging.system;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {

    public static void promptUserOptions(String[] args, Constants.NodeType node, String... options){
        ParameterWindow pm = new ParameterWindow("Please specify the input values:", args);

        List<String> params = new ArrayList<String>();

        for(Parameter p : pm.getUserParameters( "USE_DEFAULT_VALUE",options)){

            if(!(p.getValue().equals("") || p.getValue().equals("USE_DEFAULT_VALUE") ||p.getValue() ==null)){
                params.add(p.getID());
                params.add(p.getValue());

            }
        }

        String[] tempArgs = new String[params.size()];
        for(int i =0; i< params.size(); i++){
            tempArgs[i] = params.get(i);
        }
        Constants.updateConstants(tempArgs, node);
    }

    public static boolean resolveRunGUIParam(){
        boolean runUI;
        if(Constants.getRunGui() ==null) runUI = ConsoleUtils.getYesNoChoice("Would you like to run the GUI?");
        else runUI = Constants.getRunGui();
        return runUI;
    }
}
