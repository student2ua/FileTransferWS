package com.ecwo.fileTransfer.ws.handlers;

import com.ecwo.fileTransfer.ws.Log4jOutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * User: tor
 * Date: 21.10.16
 * Time: 18:53
 * http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-server-side/
 */
public class MacAddressValidatorHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger log = Logger.getLogger(MacAddressValidatorHandler.class);
    private static final Log4jOutputStream LOG_OUTPUT_STREAM = new Log4jOutputStream(log, Level.INFO);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        log.debug("Server : handleMessage()......");

        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        //for response message only, true for outbound messages, false for inbound
        if (!isRequest) {

            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                //if no header, add one
                if (soapHeader == null) {
                    soapHeader = soapEnv.addHeader();
                    //throw exception
                    generateSOAPErrMessage(soapMsg, "No SOAP header.");
                }

                //Get client mac address from SOAP header
                Iterator it = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

                //if no header block for next actor found? throw exception
                if (it == null || !it.hasNext()) {
                    generateSOAPErrMessage(soapMsg, "No header block for next actor.");
                }

                //if no mac address found? throw exception
                Node macNode = (Node) it.next();
                String macValue = (macNode == null) ? null : macNode.getValue();

                if (macValue == null) {
                    generateSOAPErrMessage(soapMsg, "No mac address in header block.");
                }

                //if mac address is not match, throw exception
                // cmd getmac /fo table /v
                //dev
                if (!"20-CF-30-62-00-A3".equals(macValue)) {
                    generateSOAPErrMessage(soapMsg, "Invalid mac address, access is denied.");
                }

                //tracking
                soapMsg.writeTo(LOG_OUTPUT_STREAM);

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

        log.info("Server : handleFault()......");

        return true;
    }

    @Override
    public void close(MessageContext context) {
        log.info("Server : close()......");
    }

    @Override
    public Set<QName> getHeaders() {
        log.info("Server : getHeaders()......");
        return null;
    }

    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
            log.error(e.toString(), e);
        }
    }
}
