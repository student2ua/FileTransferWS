package com.ecwo.fileTransfer.ws;

import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.awt.*;

/**
 * User: tor
 * Date: 20.10.16
 * Time: 17:51
 * http://info.javarush.ru/eGarmin/2015/03/14/%D0%92%D0%B5%D0%B1-%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81%D1%8B-%D0%A8%D0%B0%D0%B3-1-%D0%A7%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-%D0%B2%D0%B5%D0%B1-%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81-%D0%B8-%D0%BA%D0%B0%D0%BA-%D1%81-%D0%BD%D0%B8%D0%BC-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D1%82%D1%8C-.html
 * see http://docs.oracle.com/javaee/5/tutorial/doc/bnazq.html#bnazs
 * Java Class	XML Data Type
 * java.lang.String	xs:string
 * java.math.BigInteger	xs:integer
 * java.math.BigDecimal	xs:decimal
 * java.util.Calendar	xs:dateTime
 * java.util.Date	xs:dateTime
 * javax.xml.namespace.QName	xs:QName
 * java.net.URI	xs:string
 * javax.xml.datatype.XMLGregorianCalendar	xs:anySimpleType
 * javax.xml.datatype.Duration	xs:duration
 * java.lang.Object	xs:anyType
 * java.awt.Image	xs:base64Binary
 * javax.activation.DataHandler	xs:base64Binary
 * javax.xml.transform.Source	xs:base64Binary
 * java.util.UUID	xs:string
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "handlers.xml")
public interface IFileTransferWebService {
    @WebMethod
    public DataHandler getFile(@WebParam(name = "path") String path, @WebParam(name = "pk") Long pk);

    @WebMethod
    public Image getPhoto(@WebParam(name = "path") String path, @WebParam(name = "pk") Long pk);

//    public byte[] getFile(UID pk);

    @WebMethod
//    @WebResult(name = "getFilesInfo", targetNamespace = "http://imp.ws.fileTransfer.ecwo.com/")
    public String[] getFilesInfo(@WebParam(name = "path") String path);

}
