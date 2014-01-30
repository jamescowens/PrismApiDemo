package me.botsko.prismapidemo.customparam;

import java.util.regex.Matcher;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.botsko.prism.actionlibs.QueryParameters;
import me.botsko.prism.parameters.PrismParameterHandler;

public class GroupParameter implements PrismParameterHandler {

	
	/**
	 * 
	 */
	public void process( QueryParameters query, Matcher input, CommandSender sender ){
		// You can use custom logic to decide which changes you want to make to the query
		// params. For example, we could list all players in a permissions group
		if( !(sender instanceof Player) ){
			throw new IllegalArgumentException("Demo group param must be used by a player");
		}
		query.addPlayerName( ((Player)sender).getName() ); // @todo faking it...
	}
}