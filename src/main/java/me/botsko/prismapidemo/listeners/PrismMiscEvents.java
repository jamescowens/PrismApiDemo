package me.botsko.prismapidemo.listeners;

import me.botsko.prism.events.PrismBlocksDrainEvent;
import me.botsko.prism.events.PrismBlocksExtinguishEvent;
import me.botsko.prism.events.PrismBlocksRollbackEvent;
import me.botsko.prismapidemo.PrismApiDemo;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PrismMiscEvents implements Listener {
	
	
	PrismApiDemo plugin;
	
	
	/**
	 * 
	 * @param plugin
	 */
	public PrismMiscEvents( PrismApiDemo plugin ){
		this.plugin = plugin;
	}

	
	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPrismBlocksRollbackEvent(final PrismBlocksRollbackEvent event){
		String msg = "Captured Prism rollback event - by" + event.onBehalfOf() + " - " + event.getBlockStateChanges().size() + " blocks changed: " + event.getQueryParameters().getOriginalCommand();
		for(Player pl : plugin.getServer().getOnlinePlayers()){
			pl.sendMessage( msg );
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPrismBlocksDrainEvent(final PrismBlocksDrainEvent event){
		String msg = "Captured Prism drain event. r:" + event.getRadius() + " - " + event.getBlockStateChanges().size() + " blocks changed";
		for(Player pl : plugin.getServer().getOnlinePlayers()){
			pl.sendMessage( msg );
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPrismBlocksExtinguishEvent(final PrismBlocksExtinguishEvent event){
		String msg = "Captured Prism extinguish event. r:" + event.getRadius() + " - " + event.getBlockStateChanges().size() + " blocks changed";
		for(Player pl : plugin.getServer().getOnlinePlayers()){
			pl.sendMessage( msg );
		}
	}
}