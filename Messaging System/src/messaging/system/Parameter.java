/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 *
 * @author mathew
 */
public class Parameter<t>{
    final private boolean ESSENTIAL;
    final private String IDENTIFIER;
    
    public Parameter(String identifier, boolean essential){
        ESSENTIAL = essential;
        IDENTIFIER = identifier;
    }
    
    public String getID(){
        return IDENTIFIER;
    }
    
    public boolean isEssential(){
        return ESSENTIAL;
    }
    
    
    
}
