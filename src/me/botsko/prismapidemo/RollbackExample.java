package me.botsko.prismapidemo;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionsQuery;
import me.botsko.prism.actionlibs.QueryParameters;
import me.botsko.prism.actionlibs.QueryResult;
import me.botsko.prism.appliers.PrismProcessType;
import me.botsko.prism.appliers.Rollback;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RollbackExample {
	
	
	
	public static void rollback( Plugin plugin, Player player ){
		
		
		/**
		 * First you need to check for access to prism core. Generally you'll
		 * want to do this in the onEnable portion of your plugin.
		 */
		Plugin _tempPrism = plugin.getServer().getPluginManager().getPlugin("Prism");
		if (_tempPrism != null) {
			// It exists, so cast it.
			Prism prism = (Prism)_tempPrism;

			/**
			 * Next, you need to build a query parameter example. This
			 * class allows you to define every condition you need
			 * to properly query the database. The query is generated
			 * for you using the same methods Prism uses.
			 *
			 * Check the QueryParameters class for all possible methods.
			 * 
			 * The following params essentially replicate /prism rollback a:block-break,block-place r:20
			 */
			QueryParameters parameters = new QueryParameters();
			
			// Unless you want a truly global lookup, set a world name
			parameters.setWorld("world");
			
			// Add many action types
			parameters.addActionType("block-break");
			parameters.addActionType("block-place");
			
			// Set radius to 20
			parameters.setRadius(20);
			
			// Set min/max vectors from the current player's location - this essentially
			// triggers the radius condition (Note: you must add a radius for this to work)
			parameters.setMinMaxVectorsFromPlayerLocation( player.getLocation() );
			
			// Limit how many results you get
			parameters.setLimit(100);

			/**
			 * Next, pass the QueryParameters object to the actions query
			 * system. It will return a QueryResult object that contains
			 * information about the results.
			 */
			ActionsQuery aq = new ActionsQuery(prism);
			QueryResult lookupResult = aq.lookup( parameters );
			if(!lookupResult.getActionResults().isEmpty()){

				Rollback rb = new Rollback( prism, player, PrismProcessType.ROLLBACK, lookupResult.getActionResults(), parameters );
				rb.apply();
				
			} else {
				player.sendMessage( "Nothing found." );
			}
		}
	}
}