package me.botsko.prismapidemo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PrismApiDemo extends JavaPlugin {
	
	
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
    			
    			if(args[0].equalsIgnoreCase("custom")){
    				CustomEventExample.trigger( this, player );
    			}
    			
    			return true;
    		}
    	}
    	return false; 
    }
}