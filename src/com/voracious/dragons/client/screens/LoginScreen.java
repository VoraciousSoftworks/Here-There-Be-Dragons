package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.graphics.ui.Text;
import com.voracious.dragons.client.graphics.ui.TextBox;
import com.voracious.dragons.client.utils.InputHandler;

public class LoginScreen extends Screen {
	private Sprite background;
	private Button login, register;
	private TextBox usernameTextBox, passwordTextBox, server;
	private Text userLabel, passLabel, serverLabel;
	private int hasFocus = 0;
	
	private static boolean isLoggingIn;
	private static boolean hasLoggedIn = false;
	private static boolean isRegistering;
	private static String username, password;
	
	private static Logger logger = Logger.getLogger(LoginScreen.class);
	
	public LoginScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		background = new Sprite("/mainMenuBackground.png");
		isLoggingIn = false;
		
		userLabel = new Text("Username: ", 10, Game.HEIGHT - 70);
		passLabel = new Text("Password: ", 10, Game.HEIGHT - 45);
		serverLabel = new Text("Server: ", 10, Game.HEIGHT - 95);
		
		usernameTextBox = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 73);
		usernameTextBox.setWidth(150);
		passwordTextBox = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 48, true);
		passwordTextBox.setWidth(150);
		passwordTextBox.setDrawCaret(false);
		server = new TextBox("localhost:35580", 10 + userLabel.getWidth(), Game.HEIGHT - 98);
		server.setWidth(150);
		server.setDrawCaret(false);
		
		login = new Button("Login", usernameTextBox.getWidth() + usernameTextBox.getX() + 5, usernameTextBox.getY());
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Logging in...");
				isRegistering = false;
				authenticate();
			}
		});
		
		register = new Button("register", passwordTextBox.getWidth() + passwordTextBox.getX() + 5, passwordTextBox.getY());
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Registering...");
				isRegistering = true;
				authenticate();
			}
		});
	}
	
	@Override
	public void start(){
		InputHandler.registerScreen(this);
	}
	
	@Override
	public void stop(){
		InputHandler.deregisterScreen(this);
	}

	
	synchronized
	private void authenticate() {
		if(!isLoggingIn){
			isLoggingIn = true;
			
			//TODO: better parsing that takes into account incorrect formatting
			String serverstr = server.getText();
			int colloc = serverstr.indexOf(':');
			Game.connect(serverstr.substring(0, colloc), Integer.parseInt(serverstr.substring(colloc + 1)));
			
			username = this.usernameTextBox.getText();
			password = this.passwordTextBox.getText();
		}
	}
	
	synchronized
	public static String getUsername(){
		String temp = username;
		username = "";
		return temp;
	}
	
	synchronized
	public static String getPassword(){
		String temp = password;
		username = "";
		return temp;
	}
	
	public static boolean hasLoggedIn(){
		return hasLoggedIn;
	}
	
	public static boolean isRegistering(){
		return isRegistering;
	}
	
	public static void onSuccess() {
		hasLoggedIn = true;
		Game.setCurrentScreen(new MainMenuScreen());
	}
	
	public static void onFailure(String message){
		logger.warn("Login failed: " + message);
		isLoggingIn = false;
	}
	
	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
		g.setColor(new Color(0xCCCCCCCC, true));
		g.fillRect(5, Game.HEIGHT - 105, 325, 87);
		userLabel.draw(g);
		passLabel.draw(g);
		serverLabel.draw(g);
		usernameTextBox.draw(g);
		passwordTextBox.draw(g);
		server.draw(g);
		login.draw(g);
		register.draw(g);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			hasFocus = (hasFocus + 1)%3;
			usernameTextBox.setDrawCaret(hasFocus == 0);
			passwordTextBox.setDrawCaret(hasFocus == 1);
			server.setDrawCaret(hasFocus == 2);
		}else{
			switch(hasFocus){
			case 0:
				usernameTextBox.keyReleased(e);
				break;
			case 1:
				passwordTextBox.keyReleased(e);
				break;
			case 2:
				server.keyReleased(e);
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		
		login.mouseClicked(x, y);
		register.mouseClicked(x, y);
		
		if(usernameTextBox.contains(x, y)){
			usernameTextBox.mouseClicked(x, y);
			hasFocus = 0;
			usernameTextBox.setDrawCaret(true);
			passwordTextBox.setDrawCaret(false);
			server.setDrawCaret(false);
		}else if(passwordTextBox.contains(x, y)){
			passwordTextBox.mouseClicked(x, y);
			hasFocus = 1;
			usernameTextBox.setDrawCaret(false);
			passwordTextBox.setDrawCaret(true);
			server.setDrawCaret(false);
		}else if(server.contains(x, y)){
			server.mouseClicked(x, y);
			hasFocus = 2;
			usernameTextBox.setDrawCaret(false);
			passwordTextBox.setDrawCaret(false);
			server.setDrawCaret(true);
		}
	}

	@Override
	public void tick() {
		switch(hasFocus){
		case 0:
			usernameTextBox.tick();
			break;
		case 1:
			passwordTextBox.tick();
			break;
		case 2:
			server.tick();
			break;
		}
	}
}
