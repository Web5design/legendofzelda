package engine.entity.item;

import java.awt.Graphics2D;

import engine.Game;
import engine.entity.AbstractSimpleEntity;
import engine.map.tiled.MetaTilesNumber;

public abstract class AbstractItem extends AbstractSimpleEntity {

	protected boolean consumed;
	
	protected boolean mustTouch; // if true link must physically touch the item to obtain. i.e. can't use boomerang/sword
	
	protected boolean disappearAfterConsume;
	
	protected boolean walkable;
	
	protected boolean justDropped;
	
	protected long droppedTime;
	
	public AbstractItem() {
		super();
		consumed = false;
		mustTouch = false;
		walkable = true;
		disappearAfterConsume = true;
		justDropped = false;
		locate(0, 0);
	}
	
	@Override
	public void handle() {
		if(justDropped) {
			if(Game.clock.elapsedMillis() - droppedTime > 250) {
				justDropped = false;
			}
		}
		if(walkable) {
			if(rectangleCollide(game.link())) {
				consume();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g, x() + game.map().offset().x(), y() + game.map().offset().y());
	}
	
	public abstract void consume();
	
	public boolean consumed() {
		return consumed;
	}
	
	public boolean disappearAfterConsume() {
		return disappearAfterConsume;
	}
	
	public boolean walkable() {
		return walkable;
	}
	
	public boolean mustTouch() {
		return mustTouch;
	}
	
	public void justDropped() {
		justDropped = true;
		droppedTime = Game.clock.elapsedMillis();
	}
	
	public boolean isJustDropped() {
		return justDropped;
	}

}
