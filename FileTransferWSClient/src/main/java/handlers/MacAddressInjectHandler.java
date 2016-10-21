package handlers;

import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * User: tor
 * Date: 21.10.16
 * Time: 19:52
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/
 */
public class MacAddressInjectHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger log = Logger.getLogger(MacAddressInjectHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        log.trace("Client : handleMessage()......");

        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        //if this is a request, true for outbound messages, false for inbound
        if (isRequest) {

            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                //if no header, add one
                if (soapHeader == null) {
                    soapHeader = soapEnv.addHeader();
                }

                //get mac address
                String mac = getMACAddress();

                //add a soap header, name as "mac address"
                QName qname = new QName("http://ws.mkyong.com/", "macAddress");
                SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

                soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                soapHeaderElement.addTextNode(mac);
                soapMsg.saveChanges();

                //tracking
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                soapMsg.writeTo(baos);
                log.debug(baos);

            } catch (SOAPException e) {
                log.error(e.toString(), e);
            } catch (IOException e) {
                log.error(e.toString(), e);
            }

        }

        //continue other handler chain
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        System.out.println("Client : handleFault()......");
        return true;
    }

    @Override
    public void close(MessageContext context) {
        System.out.println("Client : close()......");
    }

    @Override
    public Set<QName> getHeaders() {
        System.out.println("Client : getHeaders()......");
        return null;
    }

    //return current client mac address
    private String getMACAddress() {

        InetAddress ip;
        StringBuilder sb = new StringBuilder();

        try {

            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            for (int i = 0; i < mac.length; i++) {

                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

            }
            System.out.println(sb.toString());

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }

        return sb.toString();
    }
}
