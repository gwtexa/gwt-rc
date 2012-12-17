package com.google.rc.client.ui.common;


/**
 * Event that is changing the domain model.
 * It may come from UI or from other source (like proxy/sync model or other model)
 * 
 * DmEv or Command must define:
 * -is it for execution on client only?
 * -if destined for server, can it be delayed? (or online mode only)
 * -what is timeout/urgency for the command?
 * 
 */
public class DmEv extends Ev {
	
}
