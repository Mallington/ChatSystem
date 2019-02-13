/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.List;

/**
 *
 * @author mathew
 */
public class ParameterParser {
    private List<Parameter> consoleParameters;
    public ParameterParser (List<Parameter> parameters){
        
    }
    
    public Parameter getID(String ID){
        for(Parameter p: consoleParameters){
            
            if(p.getID().equals(ID)){
                return p;
            }
        }
        return null;
    }
    
    
    
    public boolean parseParameters(String[] arguments){
      return false;  
    }
}
