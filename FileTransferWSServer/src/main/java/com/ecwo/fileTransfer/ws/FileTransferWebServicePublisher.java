package com.ecwo.fileTransfer.ws;

import com.ecwo.fileTransfer.ws.imp.IFileTransferWebServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * User: tor
 * Date: 20.10.16
 * Time: 18:17
 */
public class FileTransferWebServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://dev:8181/v1610/wss/filetransfer",new IFileTransferWebServiceImpl());
    }
}
