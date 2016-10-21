
import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * User: tor
 * Date: 21.10.16
 * Time: 21:25
 * без генерации классов, используем либу
 */
public class FileTransferWebServiceExampleClient {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://dev:8181/v1610/wss/filetransfer?wsdl");
        // Параметры следующего конструктора смотрим в самом первом теге WSDL описания - definitions
        // 1-ый аргумент смотрим в атрибуте targetNamespace
        // 2-ой аргумент смотрим в атрибуте name
        QName qName = new QName("http://imp.ws.fileTransfer.ecwo.com/", "IFileTransferWebServiceImplService");
        Service service = Service.create(url, qName);
        IFileTransferWebService transferWebService = service.getPort(IFileTransferWebService.class);

        //---- logging
      /*  BindingProvider bindProv = (BindingProvider) transferWebService;
        java.util.List<Handler> handlers = bindProv.getBinding().getHandlerChain();
        handlers.add(new FTServiceLogHandler());
        bindProv.getBinding().setHandlerChain(handlers);*/
        //----

        System.out.println(".getPhoto = " + transferWebService.getPhoto("boo", 1L));
        System.out.println("getFilesInfo(\"boo\") = " + Arrays.toString(transferWebService.getFilesInfo("qb"));
        final DataHandler qb = transferWebService.getFile("qb", 1l);
        System.out.println("getFile.ContentType = " + qb.getContentType());
        try {
            System.out.println("getFile.Content = " + qb.getContent());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
