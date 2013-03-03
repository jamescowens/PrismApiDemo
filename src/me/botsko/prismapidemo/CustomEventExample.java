package me.botsko.prismapidemo;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionType;
import me.botsko.prism.events.PrismCustomPlayerActionEvent;
import me.botsko.prism.exceptions.InvalidActionException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CustomEventExample {
	
	
	
	public static void trigger( Plugin plugin, Player player ){
		
		
		/**
		 * First you need to check for access to prism core. Generally you'll
		 * want to do this in the onEnable portion of your plugin.
		 */
		Plugin _tempPrism = plugin.getServer().getPluginManager().getPlugin("Prism");
		if (_tempPrism != null) {

			/**
			 * First, you must tell Prism that you're registering a new action type.
			 * 
			 * This is normally done in your onEnable, but is here for ease of reading.
			 * 
			 * To add an action type, you must:
			 * - define a name (all lower-case, words separated by hyphens). This name will be used in lookups, etc
			 * - define the action handler the event should use. Currently, the API only supports "player" actions
			 * - define a descriptive verb prism can use in lookup chat messages for this action
			 * 
			 * Note: ALL action types that are not prism defaults must use two hyphens, usually in this format:
			 * 
			 * (plugin short name)-xxx-xxx
			 * 
			 * Every action in your application should begin with the same plugin short name, so it's easy
			 * to keep them separate.
			 * 
			 */
			try {
				Prism.getActionRegistry().registerCustomAction( plugin, new ActionType( "pr-api-demo", "player", "triggered" ) );
			} catch (InvalidActionException e) {
				// If something you register is incorrect, Prism will let you know in e.getMessage();
				e.printStackTrace();
			}
			
			
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
			PrismCustomPlayerActionEvent prismEvent = new PrismCustomPlayerActionEvent( plugin, "pr-api-demo", player, "a custom event" );
	        plugin.getServer().getPluginManager().callEvent(prismEvent);
	        
	        /**
	         * When you call the event through Bukkit, Prism will hear it. Prism will record it as an "api-demo" action type for the
	         * player, at the player's current coordinates/world.
	         * 
	         * Players can then use a:demo or a:api-demo to find the data in the logs.
	         * 
	         * Note: The User MUST add your plugin name to the Prism config, to allow your plugin to record events.
	         * prism:
	         *   api:
             *     enabled: true
             *     allowed-plugins:
             *       - PrismApiDemo
	         * 
	         * Because the action type "api-type" uses a verb of "triggered" and the event message was "a custom event",
	         * the output will be:
	         * 
	         * viveleroi triggered a custom event just now
	         * 
	         */
	        player.sendMessage("Event triggered. Try /demo lookup");
		}
	}
}