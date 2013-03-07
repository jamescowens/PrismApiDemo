package me.botsko.prismapidemo.customevent;

import me.botsko.prism.events.PrismCustomPlayerActionEvent;
import me.botsko.prismapidemo.PrismApiDemo;

import org.bukkit.entity.Player;

public class CustomEventExample {
	
	
	
	public static void trigger( PrismApiDemo plugin, Player player ){
		
		if ( plugin.getPrism() != null) {
			
			
			/**
			 * Throughout your application you can now trigger custom events that Prism will
			 * listen to. Here's an example:
			 * 
			 * Create any prism custom event (currently only supports CustomPlayerAction, if you work with blocks/entities
			 * you should likely be using Bukkit's existing events)
			 * 
			 * You need to define:
			 * - The action type the event belongs to
			 * - The player involved
			 * - A message about what happened.
			 * 
			 */
			PrismCustomPlayerActionEvent prismEvent = new PrismCustomPlayerActionEvent( plugin, "pr-api-demo", player, null );
	        plugin.getServer().getPluginManager().callEvent(prismEvent);
	        
	        
	        /**
	         * When you call the event through Bukkit, Prism will hear it. Prism will record it as an "pr-api-demo" action type for the
	         * player, at the player's current coordinates/world.
	         * 
	         * Players can then use a:demo or a:pr-api-demo to find the data in the logs.
	         * 
	         * Because the action uses a verb of "triggered" and the event message was "a custom event",
	         * the output will be:
	         * 
	         * viveleroi triggered my custom handler just now
	         * 
	         */
	        player.sendMessage("Event triggered. Try /demo lookup");
		}
	}
}