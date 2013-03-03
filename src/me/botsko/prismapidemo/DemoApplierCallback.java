package me.botsko.prismapidemo;

import me.botsko.prism.appliers.ApplierCallback;
import me.botsko.prism.appliers.ApplierResult;
import me.botsko.prism.appliers.PrismProcessType;

import org.bukkit.command.CommandSender;

public class DemoApplierCallback implements ApplierCallback {
		
		
	/**
	 * Prism runs all world block changes through a repeating sync task
	 * in 1000 block changes at a time. You can define a callback
	 * that will be fired after the entire process has been applied.
	 * 
	 * Prism will give you an ApplierResult object that contains
	 * the number of changes applied, skipped, if it's a preview or not,
	 * the process type, and a collection of block state changes.
	 * 
	 * Prism's default callback is responsible for sending the chat messages
	 * about the finished process.
	 */
	public void handle( CommandSender sender, ApplierResult result ){
		
		// If it was a rollback
		if(result.getProcessType().equals(PrismProcessType.ROLLBACK)){
			// If it's not a preview
			if(!result.isPreview()){
				// Tell the player
				sender.sendMessage( "Rollback with Prism API complete - " + result.getChangesApplied() + " reversals." );
			}
		}
		// If it was a restore
		if(result.getProcessType().equals(PrismProcessType.RESTORE)){
			// If it's not a preview
			if(!result.isPreview()){
				// Tell the player
				sender.sendMessage( "Restore with Prism API complete - " + result.getChangesApplied() + " changes re-applied." );
			}
		}
	}
}
