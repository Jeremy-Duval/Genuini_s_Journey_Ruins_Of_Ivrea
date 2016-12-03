/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.arduino;

/**
 *
 * @author Valentin
 */
public class UnobtainableComPortException extends Exception{
    public UnobtainableComPortException(){
        
    }
    
    @Override
    public String getMessage(){
        return "False ComPort";
    }
}
