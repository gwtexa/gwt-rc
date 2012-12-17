package com.google.rc.client.ui.model.dif;


/**
 * Data Interchange Format but also:
 * -data store format, something between json and rdb
 * -data caching in key-value store 
 * -parser
 * 
 * 

For now we can use RDB but need to determine how referenced records are going to be retrieved and stored
-just mimic RDB in hash with foreign keys, 
-for small datasets search/count on client by iterating and/or in memory indexing
-for large datasets search/count on server in rdbms
-key should contain class type info: value or reference. Many value types (primitives) but ultimately represented as string.
-encode json key as attributeName:hexClassId:instanceId  - in this way json contains metadata, what allows deserializing to java objects without additional information
(except custom referenceClassId)

Because apps must work over the network, as the base representation let's choose serialized data - interchange format. 
It means json with metadata + metadata mapping to particular languages (classId -> className, which also can be represented in json)
When reading json with metadata we need to define which references to resolve i.e. load in the same response
or resolve in place (in json - it requires special notation indicating that the reference is resolved, or just not resolved references 
should be empty strings or "null". Empty string should be fine because even if referenced object is empty then it is an empty object {}
If resolving in place define to which nesting level resolve references. 
Handle optional attributes and generate classes from 
Lists represent natively in json as arrays (protobuf 'repeated')
When creating objects how to generate instanceId? Must be unique so 2 options:
-use central id generator in the system. Not viable option in distributed system, communication delays, keeping high availability cost.
-use random number - not really unique. How to handle unlikely but possible collisions? Can be used by malicious user 
to perform DOS and db inconsistency attack. Need to control permissions to individual objects well. Should we include permissions in the key?


So the new thing would be: json matching rdbms foreign key structure, including metadata in json transfer objects, something like json schema, 
allows gradual, lazy building/transferring models with circular references
also allows decoupling the module responsible for caching, optimizing network transfer, predicting requests and pushing more data in response
to client than was requested (for predictive caching)
Separate concerns: transfer metadata - simple and common for languages, and language dependent metadata describing how to construct classes.
Other metadata describing how to retrieve json objects: to which level inline/resolve, which attributes not to resolve at all.

How to store long lists? In chunks. Lists


All objects immutable - it makes cache invalidating easy. Modification of the value requries generating new instanceId - but then how to handle circular references?
And how to handle stale references in other objects? Keep register of id changes?
Foreign key mapping consistency? Orphans and FK pointing to old records?

New JSON format:
-attribute names: name_classId: 'primitive value or instanceId'
-classId describes how to interpret the value (primitive int, string or reference to object of known type)
-classId may also be used for object flags like: immutable (also a hint for cachers), inlined (should contain instanceId and inlined object) - it means
that the inline object is an entity and must exist separately with identity but is inlined (denormalized) for performance
-value objects (no identity) should be always inlined?

We may need to make classId 64-bit as it may serve for schema evolution. Consecutive versions of domain model metadata (schema)
should not change classes (classes must be immutable, new schema versions add new modified classes, but the old ones are not changed). 
For backward compatibility new classes like Person132 should be able to interpret/convert previous like Person104




Instead of deleting/updating records, mark as deleted/point to successor (new value). It will preserve old value which may be useful for undo/redo, 
not as an undo mechanism but as a cache for it. The purpose is first of all to invalidate the record, successor field is optional. 

In case of mutable records, invalidating the record indicates that it needs to be reloaded. 
If memory is scarce the old value content may be wiped out (but the successor chain must remain complete). So no entry is removed, only slimed down.
Then the only stale data can be in inlined/in place objects so it must be handled by events/notifications.
Does it make sense? Does it make any difference to mutable records? Ultimately the records are mutable because the successor field can be filled.
Probably the only difference is that other objects know that this object changed (because successor is not empty) and they can update their FKs.


If we move generating random, unique ids for newly created objects to client (or generally decentralize) the new integrity and security concerns arise.
Attacker may try to create and update object under id that he is not allowed to update/remove.
It's almost impossible to guess existing ids from other clients so keeping them confidential should be enough to secure.
So it's hard to separate in this way the read and write permissions because reading implies knowledge of id which may be used to write.
It means that write must check owner (creator) and permissions/policies. 
Sample policy:
-creator does not have read, write, 'permission change' permissions so for him this security domain is a sink hole
-creator can read and write, others group can only read
Security domain - objects can be created in different security domains which determine what default policy is assigned. 
(like umask which is file mode creation mask). Who defines security policy? Is it part of the model metadata?

As a protection against malicious users we need to check collisions - check whether id proposed by client does not already exist.
Every id should have the creator (owner)


Data store client vs server. On client only key-value with simple in memory indexes for full match. For example reverse indexes:
-transaction => id of transaction
-customer surname lowercase => list of customer ids


For regex or multi-keyword search
use Lucene on the server.
For search field preload content assist and use feedback from content assist to preload results while user is still typing.

On client side we can build reverse index with common word exclusion - keywords that give too many results are rejected. 
It has its own problems in multi keyword search, so probably only full match search.  

On server side data store can be RDBMS (fast multi key search) or MongoDB (data stored in a transferrable form, no additional processing,
it makes the server side thinner)


SOLR for related document upload and indexing. "Attach document to the payment/customer" feature.

 


Separation of concerns:
-parser json to object model
-model object to json
-object model must be able to indicate unresolved references
-gwt/andro subset and full featured server version
-every object must have instanceId
-random instanceId generator for creating new entities
-json schema to java generator
-model/controller thing for creating various representations (data views?) (e.g. payment data to display in different views) - still identified by payment id?
-generate java beans with proxies Proxy<Person> etc
-generate POJO + proxy wrapper?
-find: proxy pattern


The most novel things here:
-random entity ids generated by object creator (must get asynchronous feedback from central db anyway)
-json with metadata as a way to store and transfer objects
-asynchronous UI with 2 or 3 types of events (UI and Domain)
-general principle with I/O operations: check if we are online/offline. If online, perform optimistic operation - assume it went well on server,
but be prepared that server may asynchronously cancel the operation.
-design data model for heavy immutable objects + mutable extensions
-application must have built-in support for operation flag 'can/cannot be performed in offline mode'
-command pattern - commands don't return data. No synchronous queries. Commands may expect responses (it means commands and not requestor)
 but it is handled by the separate broker/router that looks at the command and sets timeout for response if this is 'query' command. 
 Broker must implement onSuccess/onFailure like GWT but on the application level (must understand commands) and not on the platform level
 and on failure may switch application to offline mode
-online/offline mode in the global context - decide who can change the mode


For asynchronous requests we need a message broker that will take care of:
-handling failures, confirmations, resending
-GWT onFailure and onSuccess makes it easier but does not handle above - additional event type? IoEvent(Failure, Ack)


Data model definition should consist of several parts:
-static entity class model - traditional and most common, mutable/immutable
-dynamic interactions - rels - how are references updated, whether bidirectional, synchronous, immediate, deferred, intended for caching, 
entity or value object 


 */
public class DIF {
	
	
}

interface Group {
	Person[] getPersons();
	String getGroupName();
}


interface Proxy {
	boolean isResolved();
}

interface Person {
	String getName();
}
class PersonBean implements Person {
	private String name;
	
	public String getName() {
		return name;
	}
}

class PersonProxy implements Person, Proxy {
	private Person person;
	String key;
	
	public PersonProxy(String key) {
		this.key = key;
	}
	
	public String getName() {
		if(person == null) {
			//person = resolveFromCache(key); //synchronous from local cache
			if (person == null) {
				//asyncObjectRequest(key); //asynchronous
			}
		}
		return this.getClass().getSimpleName();
		//or rather return null
	}
	public boolean isResolved() {
		return person != null;
	}
}

/**
 * Characteristics of data stores and data representations in rich network apps:
 * -server db
 * -server cache
 * -server memory - avoid in order to make servers stateless
 * -transferred json
 * -client memory
 * 
 * Transfer:
 * -slow and unreliable I/O
 * -cache - requires maintenance
 * 
 * 
 * 
 */
























