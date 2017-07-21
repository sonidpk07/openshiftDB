package com.db.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.response.User;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "Welcome to DB test";
	}

	@RequestMapping(method = RequestMethod.GET, value="/getData/{id}")
	public User getData(@PathVariable("id") String id) {
		String username = "TOSS_DLMS";
		String password = "toss_dlms";
		String url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = dukeortv97.corp.cox.com)(PORT = 1521)) (CONNECT_DATA = (SERVER =DEDICATED) (SERVICE_NAME = DTELOPS.WORLD)))";
		User user = new User();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Here");
			Connection con = DriverManager.getConnection(url, username, password);
			PreparedStatement ps = con
					.prepareStatement("select FIRSTNAME, LASTNAME, EMAIL FROM DL_USER WHERE ID = "+id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setFirstname(rs.getString(1));
				user.setLastname(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setMessage("User Exists");
			} else {
				user.setMessage("Not Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
}
