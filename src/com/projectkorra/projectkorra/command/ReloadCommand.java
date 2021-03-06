package com.projectkorra.projectkorra.command;

import com.projectkorra.projectkorra.GeneralMethods;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import me.StevenLawson.ProjectKorra.ProjectKorraHandler;

/**
 * Executor for /bending reload. Extends {@link PKCommand}.
 */
public class ReloadCommand extends PKCommand {

    public ReloadCommand() {
        super("reload", "/bending reload", "This command will reload the Bending config file.", new String[]{"reload", "r"});
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 0)) {
            return;
        }
        if (!ProjectKorraHandler.isSuperAdmin(sender)) {
            return;
        }
        GeneralMethods.reloadPlugin();
        sender.sendMessage(ChatColor.AQUA + "Bending config reloaded.");
    }

}
