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
public class Parameter{
    final private boolean ESSENTIAL;
    final private String IDENTIFIER;
    private String value = null;
    public Parameter(String identifier, boolean essential){
        ESSENTIAL = essential;
        IDENTIFIER = identifier;
    }
    
    public String getID(){
        return IDENTIFIER;
    }
    
    public void setValue(String val){
        this.value = val;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public boolean isEssential(){
        return ESSENTIAL;
    }
    
    public boolean isEmpty(){
        return (value == null);
    }
    
    public Integer parseInteger(){
        if(value != null){
            try{
                Integer ret = Integer.parseInt(value);
                return ret;
            }
            catch(Exception e){
                System.out.println("Invalid format for parameter [-"+IDENTIFIER+"]: \""+value+"\" is not an integer.");
            }
        }
        
        return null;
    }
    
    
    
}
