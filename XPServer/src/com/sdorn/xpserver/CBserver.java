/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.xpserver;

import java.net.URI;


import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;

import io.netty.handler.ssl.SslContext;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;


/**
 *
 * @author siegf
 */
public class CBserver {

    private static final Logger LOG = Logger.getLogger(CBserver.class.getName());
    private String url = "ws://127.0.0.1:8081/ws";
    private String realm = "realm1";
    private WampRouter router;
    private SimpleWampWebsocketListener server;
    SslContext sslContext;

    public CBserver() {
        
    }
    
    public CBserver(String url, String realm) {
        this.url = url;
        this.realm = realm;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {          
            new CBserver("ws://127.0.0.1:8081/ws", "realm1").run();
        } else {
            new CBserver(args[0], args[1]).run();
        }
    }
    
    
    void run(){
        startRouter();
    }
    
    public void startRouter() {
        try {
            WampRouterBuilder routerBuilder = new WampRouterBuilder();
            LOG.info("Try to start router with Url: "+url);
            try {
                routerBuilder.addRealm("realm1");
                router = routerBuilder.build();
            } catch (ApplicationError e1) {
                LOG.error(e1.getMessage(), e1);
                return;
            }
            
            URI serverUri = URI.create("ws://0.0.0.0:8081/ws");
            SSLContext sslcontext = null;
            server = new SimpleWampWebsocketListener(router, serverUri, sslContext);
           
            server.start();
            
            
        } catch (ApplicationError ex) {
            LOG.error(ex.getMessage(), ex);
        }


    }
    
}
