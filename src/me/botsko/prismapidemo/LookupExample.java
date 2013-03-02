package me.botsko.prismapidemo;

import java.util.List;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionsQuery;
import me.botsko.prism.actionlibs.QueryParameters;
import me.botsko.prism.actionlibs.QueryResult;
import me.botsko.prism.actions.Action;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LookupExample {
	
	
	
	public static void lookup( Plugin plugin, Player player ){
		
		
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
			 * The following params essentially replicate /prism lookup a:block-break,block-place,api-demo r:20
			 */
			QueryParameters parameters = new QueryParameters();
			
			// Unless you want a truly global lookup, set a world name
			parameters.setWorld("world");
			
			// Add many action types
			parameters.addActionType("block-break");
			parameters.addActionType("block-place");
			parameters.addActionType("pr-api-demo");
			
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

				/**
				 * Pull the actual results. There's also a getPaginatedActionResults
				 * if you wish to let Prism handle the pagination. However, you must
				 * store the QueryResult object for later use.
				 *
				 * What is returned is a List of Actions.
				 */
				List<Action> results = lookupResult.getActionResults();
				if(results != null){
					for(Action a : results){
						// An example that prints the player name and the action type.
						// full action details will be available to you here.
						player.sendMessage("Player " + a.getPlayerName() + " caused action " + a.getType().getName() + " on " + a.getNiceName() + " " + a.getAggregateCount() + " times" );
					}
				}
			} else {
				player.sendMessage( "Nothing found." );
			}
		}

		// Example output:
		// Player viveleroi caused action block-break on stone 7 times
		// Player viveleroi caused action block-place on dirt 2 times
		
	}
}