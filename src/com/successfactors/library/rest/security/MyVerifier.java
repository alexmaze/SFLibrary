package com.successfactors.library.rest.security;

import org.restlet.security.SecretVerifier;

public class MyVerifier extends SecretVerifier {

	@Override
	public boolean verify(String arg0, char[] arg1)
			throws IllegalArgumentException {
		
		return true;
//		boolean ret = false;
//		
//		String id = arg0;
//		String pw = new String(arg1);
//		
//		try {
//			Connection conn;
//			PreparedStatement stmt;
//			ResultSet res = null;
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//			conn = DriverManager.getConnection(DBInfo.DB_URL_MA, DBInfo.DB_USER, DBInfo.DB_PASSWORD);
//
//			String sql = "select * from alluser " + "WHERE userName = ? ";
//			
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, id);
//			res = stmt.executeQuery();
//			
//			while (res.next()) {
//				String pp = res.getString("userPassword");
//				if (pw.equals(pp)) {
//					ret = true;
//				}
//			}
//			
//			res.close();
//		} catch (Exception ex) {
//			System.out.println("ERRo:" + ex.toString());
//		}
//		
//		return ret;
        
	}

}
