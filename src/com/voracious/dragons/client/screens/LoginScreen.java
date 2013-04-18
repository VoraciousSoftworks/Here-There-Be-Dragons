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
	private TextBox username, password, server;
	private Text userLabel, passLabel, serverLabel;
	private int hasFocus = 0;
	
	private Logger logger = Logger.getLogger(LoginScreen.class);
	
	public LoginScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		background = new Sprite("/mainMenuBackground.png");
		
		userLabel = new Text("Username: ", 10, Game.HEIGHT - 70);
		passLabel = new Text("Password: ", 10, Game.HEIGHT - 45);
		serverLabel = new Text("Server: ", 10, Game.HEIGHT - 95);
		
		username = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 73);
		username.setWidth(150);
		password = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 48, true);
		password.setWidth(150);
		password.setDrawCaret(false);
		server = new TextBox("localhost:35580", 10 + userLabel.getWidth(), Game.HEIGHT - 98);
		server.setWidth(150);
		server.setDrawCaret(false);
		
		login = new Button("Login", username.getWidth() + username.getX() + 5, username.getY());
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				authenticate();
			}
		});
		
		register = new Button("register", password.getWidth() + password.getX() + 5, password.getY());
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
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

	
	private void authenticate() {
		logger.info("Logging in...");
		//TODO: better parsing that takes into account incorrect formatting
		String serverstr = server.getText();
		int colloc = serverstr.indexOf(':');
		Game.connect(serverstr.substring(0, colloc), Integer.parseInt(serverstr.substring(colloc + 1)));
		Game.getClientConnectionManager().sendMessage("Hello!");
	}
	
	private void register() {
		logger.info("Registering...");
	}

	private void onSuccess(String response) {
		Game.setCurrentScreen(new MainMenuScreen());
	}
	
	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
		g.setColor(new Color(0xCCCCCCCC, true));
		g.fillRect(5, Game.HEIGHT - 105, 325, 87);
		userLabel.draw(g);
		passLabel.draw(g);
		serverLabel.draw(g);
		username.draw(g);
		password.draw(g);
		server.draw(g);
		login.draw(g);
		register.draw(g);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			hasFocus = (hasFocus + 1)%3;
			username.setDrawCaret(hasFocus == 0);
			password.setDrawCaret(hasFocus == 1);
			server.setDrawCaret(hasFocus == 2);
		}else{
			switch(hasFocus){
			case 0:
				username.keyReleased(e);
				break;
			case 1:
				password.keyReleased(e);
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
		
		if(username.contains(x, y)){
			username.mouseClicked(x, y);
			hasFocus = 0;
			username.setDrawCaret(true);
			password.setDrawCaret(false);
			server.setDrawCaret(false);
		}else if(password.contains(x, y)){
			password.mouseClicked(x, y);
			hasFocus = 1;
			username.setDrawCaret(false);
			password.setDrawCaret(true);
			server.setDrawCaret(false);
		}else if(server.contains(x, y)){
			server.mouseClicked(x, y);
			hasFocus = 2;
			username.setDrawCaret(false);
			password.setDrawCaret(false);
			server.setDrawCaret(true);
		}
	}

	@Override
	public void tick() {
		switch(hasFocus){
		case 0:
			username.tick();
			break;
		case 1:
			password.tick();
			break;
		case 2:
			server.tick();
			break;
		}
	}
}
