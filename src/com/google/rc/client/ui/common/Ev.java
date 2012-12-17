package com.google.rc.client.ui.common;

/**
 * Generic event. The base class for UiEv and DmEv
 * The difference between UiEv and DmEv is that UiEv is directly destined to local viewers and reaction to UiEv is immediate
 * while DvEv goes to content providers and reaction may be after a delay needed to refresh model from server.
 * 
 * Third possible event type is coming from the split of DmEv into local model DmEv (LocalDmEv) and centrally persisted model DmEv (RemoteDmEv). 
 * RemoteDmEv not necessarily brings model change - it may carry acknowledge only.
 * 
 * Data store with asynchronous RemoteDmEv notifications must take into account:
 * -send events only to users with valid session
 * -route events according to interest/subscription rules
 * -security - make sure events are not routed to malicious users
 * 
 * 
 */
public class Ev {

}
