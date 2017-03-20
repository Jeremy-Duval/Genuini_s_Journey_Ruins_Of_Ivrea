/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.arduino;

import genuini.game.PreferencesManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ArduinoLink implements SerialPortEventListener {
	private SerialPort serialPort;
        /** The port we're normally going to use. */
        private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM", // Windows
	};  
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
        
        public SerialPort initialize() throws UnobtainableComPortException{

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

               
		// Enumerate system ports and try connecting to Arduino over each
                while (portId == null && portEnum.hasMoreElements()) {
                    CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                    
                    for (String portName : PORT_NAMES) {
                        if ( currPortId.getName().equals(portName) || currPortId.getName().startsWith(portName)) {
                            try {
                                // Try to connect to the Arduino on this port
                                serialPort = (SerialPort)currPortId.open(this.getClass().getName(), TIME_OUT);
                            } catch (PortInUseException ex) {
                                System.err.println(ex.getMessage());
                            }
                                portId = currPortId;
                                break;
                        }
                    }
                }
                
		if (portId == null) {
			throw new UnobtainableComPortException();
		}
                
                try{
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
                        serialPort.disableReceiveTimeout();
                        serialPort.enableReceiveThreshold(1);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
                return serialPort;
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
         * @param oEvent the event caught by the port listener
	 */
        @Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				System.out.println(inputLine);
                                //PreferencesManager.stepProgression();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones. 
	} 
        
   /*     public synchronized String serialEventString(SerialPortEvent oEvent) {
            String inputLine=null;
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
                                System.out.println("rc");
				inputLine=input.readLine();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		return inputLine;
	} */
        
        public synchronized String read(){
            String receive = null;
            try {
                receive = input.readLine();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            return receive;
        }
        
        public synchronized void write(String s){
            try {
                
                output = serialPort.getOutputStream();
                output.write((s+"\n").getBytes());
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(ArduinoLink.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        public synchronized void write(byte[] i){
            try {
                output = serialPort.getOutputStream();
                output.write(i);
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(ArduinoLink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public synchronized void essai(String s){
            System.out.println(s);
        }
        
        

}
