package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;

public class BossKey extends AbstractItem {
	
	private AbstractSound sound;
	
	public BossKey() {
		this(0, 0);
	}
	
	public BossKey(int x, int y) {
		super();
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(357), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_item");
	}
	
	@Override
	public void consume() {
		consumed = true;
		game.link().bossKey(true);
		sound.play();
	}

}
