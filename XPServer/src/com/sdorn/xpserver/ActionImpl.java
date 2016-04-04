package com.sdorn.xpserver;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.sdo.utils.properties.PropertyLoader;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import ws.wamp.jawampa.WampClient;

/**
 *
 * @author siegf
 */
public class ActionImpl implements Action1<WampClient.State> {

	private static final Logger LOG = Logger.getLogger(ActionImpl.class.getName());
	public static final String DIR = System.getProperty("user.dir");
	public static final String PREFIX = ActionImpl.class.getCanonicalName();
	public static final String PROPS = "config/cbserver";
	public static final Properties PROPERTIES = PropertyLoader.loadProperties(PROPS, PREFIX);
	
    Subscription onHelloSubscription;
    Subscription xpmessage;
    private String SubString;

    WampClient client;
    Scheduler rxScheduler;
    Message mess = new Message();
    Action0 action25;

    public ActionImpl(WampClient client, Scheduler rxScheduler) {
        this.client = client;
        this.rxScheduler = rxScheduler;
        setSubString(PROPERTIES.getProperty(PREFIX + ".subscription")); 
    }

    public ActionImpl() {
    	
    }

    @Override
    public void call(WampClient.State t1) {
        LOG.info("Session status changed to " + t1);

        if (t1 instanceof WampClient.ConnectedState) {
            // SUBSCRIBE to a topic and receive events
            //onHelloSubscription = client.makeSubscription("com.sdorn.xppos", String.class)
            onHelloSubscription = client.makeSubscription(getSubString(), Message.class)
                    .observeOn(rxScheduler)
                    .subscribe(new Action1<Message>() {
                        @Override
                        public void call(Message mess) {
                        	LOG.info("event for 'xppos' received: " + mess.toString());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable e) {
                        	LOG.info("failed to subscribe 'xppos': " + e);
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                        	LOG.info("'xppos' subscription ended");
                        }
                    });

//            
        } else if (t1 instanceof WampClient.DisconnectedState) {
            closeSubscriptions();
        }
    }

    public void NewMessage(Message mess) {
       
        final Message m = new Message(mess.Header, 
                                        mess.element, 
                                        mess.f1, 
                                        mess.f2, 
                                        mess.f3, 
                                        mess.f4, 
                                        mess.f5, 
                                        mess.f6, 
                                        mess.f7,
                                        mess.f8);
       
        xpmessage = rxScheduler.createWorker().schedule(new Action0() {
            @Override
            public void call() {
                
                client.publish(getSubString(), m.toString())
                        .observeOn(rxScheduler)
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long t1) {
                            	LOG.info("published " + m.toString());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                LOG.info("Error during publishing to 'oncounter': " + e);
                            }
                        });
            }
        });
    }

    void closeSubscriptions() {
        if (onHelloSubscription != null) {
            onHelloSubscription.unsubscribe();
        }
        onHelloSubscription = null;
        if (xpmessage != null) {
            xpmessage.unsubscribe();
        }
        xpmessage = null;

    }

	public String getSubString() {
		return SubString;
	}

	public void setSubString(String subString) {
		SubString = subString;
	}
    
}
