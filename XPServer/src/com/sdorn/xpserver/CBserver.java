/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.xpserver;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import org.apache.log4j.Logger;
import com.sdo.utils.properties.PropertyLoader;
import io.netty.handler.ssl.SslContext;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;
import ws.wamp.jawampa.transport.netty.SimpleWampWebsocketListener;

/**
 *
 * @author siegf
 */
public class CBserver implements IMessage {

	private static final Logger LOG = Logger.getLogger(CBserver.class.getName());
	public static final String DIR = System.getProperty("user.dir");
	public static final String PREFIX = CBserver.class.getCanonicalName();
	public static final String PROPS = "config/cbserver";
	public static final Properties PROPERTIES = PropertyLoader.loadProperties(PROPS, PREFIX);
	private static final IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
	private static final ExecutorService executor = Executors.newSingleThreadExecutor();
	private static final Scheduler rxScheduler = Schedulers.from(executor);

	private String url = "ws://127.0.0.1:8081/ws";
	private String realm = "realm1";
	private URI serverUri;
	private String s_uri;
	private WampRouter router;
	private SimpleWampWebsocketListener server;
	private SslContext sslContext;
	private ActionImpl action;
	private WampClient client;
	private UdpServer udpserver;

	public CBserver() {
		setUrl(PROPERTIES.getProperty(PREFIX + ".url"));
		setRealm(PROPERTIES.getProperty(PREFIX + ".realm"));
		setS_uri(PROPERTIES.getProperty(PREFIX + ".serveruri"));
	}

	public CBserver(String url, String realm) {
		this.url = url;
		this.realm = realm;

	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			new CBserver().run();
		} else {
			new CBserver(args[0], args[1]).run();
		}
	}

	void run() {
		try {

			startRouter();
			udpserver = new UdpServer(this);
			udpserver.start();
			WampClientBuilder builder = new WampClientBuilder();
			builder.withConnectorProvider(connectorProvider)
				.withUri(url)
				.withRealm(realm)
				.withInfiniteReconnects()
				.withReconnectInterval(1, TimeUnit.SECONDS);

			client = builder.build();
			action = new ActionImpl(client, rxScheduler);

		} catch (ApplicationError ex) {
			LOG.error(ex.getMessage(), ex);
			return;
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
		}

		client.statusChanged()
			.observeOn(rxScheduler)
			.subscribe(action, new Action1<Throwable>() {
			
				@Override
			public void call(Throwable t) {
				LOG.info("Session ended with error " + t);
			}
		}, new Action0() {
			@Override
			public void call() {
				LOG.info("Session ended normally");
			}
		});
		
		client.open();

		System.out.println("Shutting down");
		udpserver.interrupt();
		action.closeSubscriptions();
		client.close();
		executor.shutdown();
		router.close().toBlocking().last();
		server.stop();

		try {
			client.getTerminationFuture().get();
		} catch (Exception e) {
		}

	}

	public void startRouter() {
		try {
			WampRouterBuilder routerBuilder = new WampRouterBuilder();
			LOG.info("Try to start router with Url: " + url);
			try {
				routerBuilder.addRealm(realm);
				router = routerBuilder.build();
			} catch (ApplicationError e1) {
				LOG.error(e1.getMessage(), e1);
				return;
			}

			serverUri = URI.create(getS_uri());
			SSLContext sslcontext = null;
			server = new SimpleWampWebsocketListener(router, serverUri, sslContext);

			server.start();

		} catch (ApplicationError ex) {
			LOG.error(ex.getMessage(), ex);
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getS_uri() {
		return s_uri;
	}

	public void setS_uri(String s_uri) {
		this.s_uri = s_uri;
	}

	@Override
	public void MessageEvent(Message message) {
		// TODO Auto-generated method stub

	}

}
