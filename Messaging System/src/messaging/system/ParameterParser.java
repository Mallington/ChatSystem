/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class ParameterParser {

    private List<Parameter> consoleParameters;

    public ParameterParser(List<Parameter> parameters) {
        consoleParameters = parameters;
    }
     public ParameterParser() {
         consoleParameters = new ArrayList<Parameter>();
     }

     public ParameterParser add(Parameter p){
         consoleParameters.add(p);
         return this;
     }
     
    private boolean setParamByID(String paramID, String value) {

        for (Parameter param : consoleParameters) {
            if (param.getID().equals(paramID)) {
                param.setValue(value);
                return true;
            }
        }
        //ID was not in parameter list
        return false;
    }

    public Parameter getID(String ID) {
        for (Parameter p : consoleParameters) {
            if (p.getID().equals(ID)) {
                return p;
            }
        }
        return null;
    }

    private boolean paramsFufilled() {
        boolean emptyEssential = false;

        for (Parameter p : consoleParameters) {
            if (p.isEssential() && p.isEmpty()) {
                System.out.println("Missing value for parameter: " + p.getID());
                emptyEssential = true;
            }
        }
        return !emptyEssential;
    }

    private List<String> cleanUpArguments(String[] arguments) {
        List<String> validArguments = new ArrayList<String>();
        boolean expectingID = true;

        for (String arg : arguments) {
            boolean isIDParam = arg.matches("-([A-Za-z])([A-Za-z0-9])*");

            if (isIDParam && expectingID) {
                validArguments.add(arg.substring(1));
                expectingID = false;
            } else if (expectingID) {
                System.out.println("Ignoring unexpected token: " + arg);
            } else if (isIDParam) {

                System.out.println("Ignoring unmatched value for: " + validArguments.remove(validArguments.size() - 1));
                validArguments.add(arg.substring(1));
            } else {
                validArguments.add(arg);
                expectingID = true;
            }
        }
        return validArguments;
    }

    private void addParameters(List<String> validArguments) {
        for (int i = 0; i < validArguments.size(); i += 2) {
            if (!setParamByID(validArguments.get(i), validArguments.get(i + 1))) {
                System.out.println("Ignoring unknown parameter ID: " + validArguments.get(i));
            }
        }
    }

    public boolean parseParameters(String[] arguments) {

        List<String> validArguments = cleanUpArguments(arguments);
        addParameters(validArguments);

        return paramsFufilled();
    }
}
