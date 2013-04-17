package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
	private TextBox username, password;
	private Text userLabel, passLabel;
	private boolean usernameHasFocus = true;
	
	public LoginScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		
		background = new Sprite("/mainMenuBackground.png");
		InputHandler.registerScreen(this);
		
		userLabel = new Text("Username: ", 10, Game.HEIGHT - 70);
		passLabel = new Text("Password: ", 10, Game.HEIGHT - 45);
		
		username = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 73);
		username.setWidth(150);
		password = new TextBox(10 + userLabel.getWidth(), Game.HEIGHT - 48, true);
		password.setWidth(150);
		password.setDrawCaret(false);
		
		login = new Button("Login", username.getWidth() + username.getX() + 5, username.getY());
		register = new Button("register", password.getWidth() + password.getX() + 5, password.getY());
	}

	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
		g.setColor(new Color(0xCCCCCCCC, true));
		g.fillRect(5, Game.HEIGHT - 80, 325, 62);
		userLabel.draw(g);
		passLabel.draw(g);
		username.draw(g);
		password.draw(g);
		login.draw(g);
		register.draw(g);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			usernameHasFocus = !usernameHasFocus;
			username.setDrawCaret(usernameHasFocus);
			password.setDrawCaret(!usernameHasFocus);
		}else{
			if(usernameHasFocus){
				username.keyReleased(e);
			}else{
				password.keyReleased(e);
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
			usernameHasFocus = true;
			username.setDrawCaret(true);
			password.setDrawCaret(false);
		}else if(password.contains(x, y)){
			password.mouseClicked(x, y);
			usernameHasFocus = false;
			username.setDrawCaret(false);
			password.setDrawCaret(true);
		}
	}

	@Override
	public void tick() {
		if(usernameHasFocus){
			username.tick();
		}else{
			password.tick();
		}
	}
}
