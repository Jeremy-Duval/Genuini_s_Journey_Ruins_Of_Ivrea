/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package essai;
/**
 *
 * @author Valentin
 */
public class Essai {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String OS = System.getProperty("os.name");
        
        if(OS.contains("Windows")){
            System.out.println("Essai validé pour Windows");
        }else if(OS.contains("Linux")){
            System.out.println("Essai validé pour Linux");
        }else if(OS.contains("Mac")){
            System.out.println("Essai validé pour Mac");
        }
    }
    
}
