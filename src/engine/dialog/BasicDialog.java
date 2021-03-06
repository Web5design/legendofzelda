package engine.dialog;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Game;
import game.zelda.Buttons;

public class BasicDialog extends AbstractDialog {

	public BasicDialog(String text, int x, int y, int width, int height, int textSpeed) {
		super(text, x, y, width, height, textSpeed);
	}
	
	public BasicDialog(String[] text, int x, int y, int width, int height, int textSpeed) {
		super(text, x, y, width, height, textSpeed);
	}
	
	
    private void drawString(Graphics2D g, String text, int x, int y) {
    	int i = 0;
        for (String line : text.split("\n")) {
        	if(i > 0) {
        		y += g.getFontMetrics().getHeight();
        	}
            g.drawString(line, x, y);
            i++;
        }
    }

	@Override
	public void trigger() {
		happened = true;
		Game.clock.pause();
		int originalTextSpeed = textSpeed;
		while(!finished) {
			if(phase == 1) {
				if(!textTyped.playing()) {
					textTyped.play();
				}
				if(Game.clock.systemElapsedMillis() - lastDraw > textSpeed) {	
					lastDraw = Game.clock.systemElapsedMillis();
					Graphics2D g = game.screen().bufferedImage().createGraphics();				
					game.screen().draw(g, 0, 0);
					if(game.zoom() > 1) {
						g.scale(game.zoom(), game.zoom());
					}
					g.setColor(Color.GRAY);
					g.fillRect(x, y, width, height);
					g.setColor(Color.BLACK);
					g.fillRect(x + 2, y + 2, width - 4, height - 4);

					g.setFont(Game.fonts.get("menu_small"));
					g.setColor(Color.WHITE);

					drawString(g, pages[page].substring(0, currentChar), x + 5, y + 15);

					g.dispose();
					game.screenPanel().repaint();
					
					if(currentChar >= pages[page].length()) {
						textTyped.stop();
						phase = 2;
						game.sleep(200);
						pageFinished.play();
					}
					currentChar++;
				}
				if(Game.keyboard.isKeyPressed(Buttons.ITEM_A)) {
					textSpeed = originalTextSpeed / 2;
				} else {
					textSpeed = originalTextSpeed;
				}
			} else if(phase == 2) {
				while(!Game.keyboard.isKeyPressed(Buttons.ITEM_A) &&
						!Game.keyboard.isKeyPressed(Buttons.ITEM_B) &&
						!Game.keyboard.isKeyPressed(Buttons.START)) {
					if(page >= pages.length) {
						finished = true;
						game.sleep(150);
					} else {
						page++;
						currentChar = 0;
						phase = 1;
					}
				}
			} 
		}
		Game.clock.start();
	}

	@Override
	public boolean ready() {
		return true;
	}

}
