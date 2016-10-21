package handlers;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.util.Set;

/**
 * User: tor
 * Date: 20.10.16
 * Time: 18:40
 * http://java.globinch.com/enterprise-java/web-services/jax-ws/logging-tracing-web-service-xml-request-response-jax-ws/
 */
public class FTServiceLogHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage message= context.getMessage();
        boolean isOutboundMessage=  (Boolean)context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if(isOutboundMessage){
            System.out.println("OUTBOUND MESSAGE\n");

        }else{
            System.out.println("INBOUND MESSAGE\n");
        }
        try {
            message.writeTo(System.out);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;}

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        SOAPMessage message= context.getMessage();
        try {
            message.writeTo(System.out);
        } catch (SOAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close(MessageContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
