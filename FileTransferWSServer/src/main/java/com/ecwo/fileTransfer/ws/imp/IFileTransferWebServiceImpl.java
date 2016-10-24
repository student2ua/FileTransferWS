package com.ecwo.fileTransfer.ws.imp;

import com.ecwo.fileTransfer.ws.IFileTransferWebService;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: tor
 * Date: 20.10.16
 * Time: 17:58
 */
@WebService(endpointInterface = "com.ecwo.fileTransfer.ws.IFileTransferWebService")
public class IFileTransferWebServiceImpl implements IFileTransferWebService {
    @Override
    public DataHandler getFile(String path, Long pk) {
//        final InputStream inputStream = IFileTransferWebServiceImpl.class.getResourceAsStream("/reclamaWin10.JPG");
        final String file = IFileTransferWebServiceImpl.class.getResource("/reclamaWin10.JPG").getFile();
        javax.activation.DataSource source = new FileDataSource(file);
//        javax.activation.DataSource source = new ByteArrayDataSource(getBytesFromInputStream(inputStream), HttpRequest.JPEG_MIME_TYPE);
        return new DataHandler(source);
    }

    @Override
    public Image getPhoto(String path, Long pk) {
        try {
            //усложним
            final InputStream inputStream = IFileTransferWebServiceImpl.class.getResourceAsStream("/reclamaWin10.JPG");
            final Image jpegImage = getJPEGImage(getBytesFromInputStream(inputStream), false);
            return jpegImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//    @WebResult(name ="getFilesInfo", targetNamespace="http://imp.ws.fileTransfer.ecwo.com/" )
    @Override
    public String[] getFilesInfo(String path) {
//        return new ArrayList<String>(Arrays.asList(path + "_test1", path + "_test2"));
        return new String[]{path + "_test1", path + "_test2"};
    }

    private static byte[] getBytesFromInputStream(InputStream is) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1; )
                os.write(buffer, 0, len);
            os.flush();

            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) try {
                os.close();
            } catch (IOException ignore) {
            }
        }
        return new byte[0];
    }

    private Image getJPEGImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream

        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) param.setSourceSubsampling(4, 4, 0, 0);
        return reader.read(0, param);
    }
}
