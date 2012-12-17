package com.google.rc.shared;

import java.sql.Connection;
import java.util.Date;

public class Snippets {

	static interface CloseableProvider<C extends AutoCloseable> {
		public C create();
	}
	
	
	public static void main(String[] args) {
		//Sample use of Fun and Fun1
		CloseableProvider<Connection> connProvider = new CloseableProvider<Connection>() {
			Connection conn;
			public Connection create() { return conn; };
		};
		Date d = withSession(connProvider, new Fun1<Date, Connection>() {
			public Date call(Connection conn) throws RuntimeException {
				//in practice get date from connection
				//Connection => Date
				return new Date();
			}
		});
	}
	
	
	public static <T, A1 extends AutoCloseable> T withSession(CloseableProvider<A1> provider, Fun1<T, A1> fun) throws RuntimeException {
		A1 conn = provider.create();
		T result = fun.call(conn); //throws RuntimeException
		try {	
			conn.close();
		} catch (Exception e) {
			//log
		}
		return result;
	}
}
