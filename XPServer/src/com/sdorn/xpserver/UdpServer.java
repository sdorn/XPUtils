/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.xpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sdo.utils.properties.PropertyLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class UdpServer.
 *
 * @author siegf
 */
public class UdpServer extends Thread {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(UdpServer.class.getName());
	
	/** The Constant DIR. */
	public static final String DIR = System.getProperty("user.dir");
	
	/** The Constant PREFIX. */
	public static final String PREFIX = UdpServer.class.getCanonicalName();
	
	/** The Constant PROPS. */
	public static final String PROPS = "config/cbserver";
	
	/** The Constant PROPERTIES. */
	public static final Properties PROPERTIES = PropertyLoader.loadProperties(PROPS, PREFIX);
	
    /** The port. */
    private int port = 49000;
    
    /** The event. */
    private IMessage event;
    
    /** The dsocket. */
    private DatagramSocket dsocket;

    
    
    /**
     * Instantiates a new udp server.
     *
     * @param callback the callback
     */
	public UdpServer(IMessage callback) {
		setPort(Integer.parseInt(PROPERTIES.getProperty(PREFIX + ".port")));
		LOG.info("Start UDP Server, listen on :" + getPort());
		event = callback;
	}

	/**
	 * Instantiates a new udp server.
	 *
	 * @param p the p
	 * @param callback the callback
	 */
	public UdpServer(int p, IMessage callback) {
        this.port = p;
        event = callback;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {

        try {
            
            // Create a socket to listen on the port.
            dsocket = new DatagramSocket(port);

            // Create a buffer to read datagrams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            byte[] buffer = new byte[1024];

            // Create a packet to receive data into the buffer
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            //Now loop forever, waiting to receive packets and printing them.
            while (!isInterrupted()) {
                // Wait to receive a datagram
                dsocket.receive(packet);
                LOG.info("Packet Length: " + packet.getLength());
                Message[] m = parsePacket(packet);
                for (Message m1 : m) {
                    event.MessageEvent(m1);
                }

                packet.setLength(buffer.length);
            }
        } catch (Exception e) {
            interrupt();
            LOG.error(e.getMessage(), e);;
            dsocket.close();           
        }
        dsocket.close();
    }

    /**
     * Byte2 float.
     *
     * @param inData the in data
     * @param byteSwap the byte swap
     * @return the float[]
     */
    // byte2Float method - extracts floats from byte array
    public static final float[] byte2Float(byte[] inData, boolean byteSwap) {
        int j = 0, value;
        int length = inData.length / 4;
        float[] outData = new float[length];
        if (!byteSwap) {
            for (int i = 0; i < length; i++) {
                j = i * 4;
                value = (((inData[j] & 0xff) << 24)
                        + ((inData[j + 1] & 0xff) << 16)
                        + ((inData[j + 2] & 0xff) << 8)
                        + ((inData[j + 3] & 0xff) << 0));
                outData[i] = Float.intBitsToFloat(value);
            }
        } else {
            for (int i = 0; i < length; i++) {
                j = i * 4;
                value = (((inData[j + 3] & 0xff) << 24)
                        + ((inData[j + 2] & 0xff) << 16)
                        + ((inData[j + 1] & 0xff) << 8)
                        + ((inData[j] & 0xff) << 0));
                outData[i] = Float.intBitsToFloat(value);
            }
        }

        return outData;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Parses the packet.
     *
     * @param packet the packet
     * @return the message[]
     */
    public Message[] parsePacket(DatagramPacket packet) {
        int p_len = packet.getLength();
        p_len = (p_len - 23) / 36;
        Message[] m_array = new Message[p_len];
        byte[] data = packet.getData();
        // Read the firs Element
        byte[] header = Arrays.copyOfRange(data, 0, 4);
        byte[] internal = Arrays.copyOfRange(data, 4, 6);
        byte[] data_element = Arrays.copyOfRange(data, 6, 10);
        byte[] floats = Arrays.copyOfRange(data, 10, 42);
        int element = data_element[0];
        float[] f = byte2Float(floats, true);
        String Header = new String(header);
        // put first Element into Message25[]
        Message message = new Message(Header, element, f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7]);
        m_array[0] = message;  // process the others
        for (int i = 1; i < p_len; i++) {
            m_array[i] = new Message();
            int start = i*36+6;
            int end_element = start + 4;
            int end_floats = end_element + 32;
            
            data_element = Arrays.copyOfRange(data, start, end_element);
            floats = Arrays.copyOfRange(data, end_element, end_floats);
            element = data_element[0];
            f = byte2Float(floats, true);
            m_array[i].setHeader(Header);
            m_array[i].setElement(element);
            m_array[i].setF1(f[0]);
            m_array[i].setF2(f[1]);
            m_array[i].setF3(f[2]);
            m_array[i].setF4(f[3]);
            m_array[i].setF5(f[4]);
            m_array[i].setF6(f[5]);
            m_array[i].setF7(f[6]);
            m_array[i].setF8(f[7]);
            
        }
        return m_array;
    }

}
