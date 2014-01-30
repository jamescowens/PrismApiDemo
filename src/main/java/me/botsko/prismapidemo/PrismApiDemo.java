package me.botsko.prismapidemo;

import java.util.regex.Pattern;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionType;
import me.botsko.prism.exceptions.InvalidActionException;
import me.botsko.prismapidemo.customevent.CustomEventExample;
import me.botsko.prismapidemo.customevent.DemoHandler;
import me.botsko.prismapidemo.customparam.GroupParameter;
import me.botsko.prismapidemo.listeners.PrismMiscEvents;
import me.botsko.prismapidemo.lookup.LookupExample;
import me.botsko.prismapidemo.manual.CustomManualActionExample;
import me.botsko.prismapidemo.restore.RestoreExample;
import me.botsko.prismapidemo.rollback.RollbackExample;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PrismApiDemo extends JavaPlugin {
	
	
	/**
	 * 
	 * This is a very basic demo plugin that provides working examples
	 * of Prism API code.
	 * 
	 * Prism 1.5 introduced an API that allows:
	 * 
	 * - Lookups
	 * - Rollbacks
	 * - Restores
	 * - Tracking your custom events
	 * - Using custom handlers for custom event lookups/rollbacks/restores
	 * - Manually giving Prism an action if the event is intercepted
	 * 
	 * A few rules Prism enforces:
	 * 
	 * #1) The User MUST add your plugin name to the Prism config, to allow your plugin to register custom events.
	 * prism:
	 *   api:
	 *     enabled: true
	 *     allowed-plugins:
	 *       - PrismApiDemo
	 * 
	 * If they do not list your plugin in the Prism config it will not work.
	 * 
	 * #2) Custom action types must follow a two-hyphen format. We strongly recommend using a shortname
	 * for your plugin as the prefix so they're all standard.
	 * 
	 * Ex: dm-spell-found is used in the plugin "DarkMythos".
	 * 
	 * #3) You should provide a configuration section in your own plugin that allows users to disable
	 * tracking of each of your events.
	 * 
	 */
	
	
	/**
	 * 
	 */
	private Prism prism;
	
	
	/**
	 * First, make sure we have access to prism core.
	 */
	public void onEnable(){
		/**
		 * First you need to check for access to prism core. Generally you'll
		 * want to do this in the onEnable portion of your plugin.
		 */
		Plugin _tempPrism = getServer().getPluginManager().getPlugin("Prism");
		if (_tempPrism != null) {
			
			prism = (Prism) _tempPrism;
			
			
			/**
			 * If you're going to use a custom handler, you need to make sure you register
			 * it with Prism - this way it will be used for your custom events instead of
			 * a generic/default one.
			 */
			try {
				Prism.getHandlerRegistry().registerCustomHandler( this, DemoHandler.class );
			} catch (InvalidActionException e) {
				e.printStackTrace();
			}
			
			
			/**
			 * You must tell Prism that you're registering a new action type.
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
				Prism.getActionRegistry().registerCustomAction( this, new ActionType( "pr-api-demo", false, true, false, "DemoHandler", "triggered" ) );
			} catch (InvalidActionException e) {
				// If something you register is incorrect, Prism will let you know in e.getMessage();
				e.printStackTrace();
			}
			
			
			/**
			 * Adds a custom parameter. Use as `pr l group:op`
			 */
			Prism.registerParameter( Pattern.compile("(group):([\\w]+)"), new GroupParameter());
		}
		
		getServer().getPluginManager().registerEvents(new PrismMiscEvents(this), this);
		
	}
	
	
	/**
	 * 
	 */
	public Prism getPrism(){
		return prism;
	}
	
	
	/**
	 * 
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("demo")){
    		
    		if( args.length > 0 && sender instanceof Player ){
    			
    			Player player = (Player) sender;
    			
    			if(args[0].equalsIgnoreCase("lookup")){
    				LookupExample.lookup( this, player );
    			}
    			
    			if(args[0].equalsIgnoreCase("rollback")){
    				RollbackExample.rollback( this, player );
    			}
    			
    			if(args[0].equalsIgnoreCase("restore")){
    				RestoreExample.restore( this, player );
    			}
    			
    			if(args[0].equalsIgnoreCase("event")){
    				CustomEventExample.trigger( this, player );
    			}
    			
    			if(args[0].equalsIgnoreCase("drop")){
    				CustomManualActionExample.trigger( this, player );
    			}
    			
    			return true;
    		} else {
    			
    			sender.sendMessage( ChatColor.GOLD + "Prism API Demo");
    			sender.sendMessage("'/demo lookup' - Replicates /prism near with the Lookup API");
    			sender.sendMessage("'/demo rollback' - Replicates /prism rollback a:block-break,block-place r:20 with the Rollback API");
    			sender.sendMessage("'/demo restore' - Replicates /prism restore a:block-break r:20 with the Restore API");
    			sender.sendMessage("'/demo drop' - Trigger a manual item-drop action.");
    			sender.sendMessage("'/demo event' - Trigger a custom 'pr-api-demo' event to record.");
    			sender.sendMessage("'/pr rb a:demo' - Demo the custom rollback handler for 'pr-api-demo' events.");
    		}
    	}
    	return false; 
    }
}