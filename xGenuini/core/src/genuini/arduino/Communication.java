/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.arduino;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class to communicate with Arduino
 * @since 22/12/2016
 * @author jeremy
 */
public class Communication {
    /**************************************************************************/
    private SerialPort serialPort;
    private static CommPortIdentifier portId;
    private static Enumeration portList;
    /* A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent*/
    private BufferedReader input;
    //The output stream to the port
    private OutputStream outputStream;
    //SerialTestObject
    SerialTest serialTest = new SerialTest();
    /**************************************************************************/
    
    /**
     * Initialisation of the communication
     * @since 22/12/2016
     * @author jeremy
     */
    public Communication(){
        try {
            serialPort = serialTest.initialize();
        } catch (UnobtainableComPortException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Close the communication
     * @since 22/12/2016
     * @author jeremy
     */
    public void close(){
        if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
	}
        serialTest.close();
    }
    /**
     * Write in the Arduino.
     *
     * @since 22/12/2016
     * @author jeremy
     */
    public void arduinoWrite(String message) {
        String test;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals("COM3")) {//NEED TO MODIFY LATER
                    try {
                        serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
                        try {
                            outputStream = serialPort.getOutputStream();
                            try {
                                serialPort.setSerialPortParams(9600,
                                        SerialPort.DATABITS_8,
                                        SerialPort.STOPBITS_1,
                                        SerialPort.PARITY_NONE);
                                try {
                                    outputStream.write(message.getBytes());
                                    System.out.println(message);

                                    outputStream.close();
                                    serialPort.close();

                                } catch (IOException e) {
                                    System.out.println("*********************************");
                                    System.out.println("err3");
                                    System.out.println("*********************************");
                                }
                            } catch (UnsupportedCommOperationException e) {
                                System.out.println("*********************************");
                                System.out.println("err2");
                                System.out.println("*********************************");
                            }
                        } catch (IOException e) {
                            System.out.println("*********************************");
                            System.out.println("err1");
                            System.out.println("*********************************");
                        }
                    } catch (PortInUseException e) {
                        System.out.println("*********************************");
                        System.out.println("err");
                        System.out.println("*********************************");
                    }

                }
            }
        }
    }
}
