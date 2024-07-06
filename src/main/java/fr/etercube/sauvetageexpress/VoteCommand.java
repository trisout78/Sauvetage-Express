package fr.etercube.sauvetageexpress;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class VoteCommand implements CommandExecutor {
    static HashMap<String, Integer> votes = new HashMap<>();
    static HashSet<UUID> votedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Main.vote) {
            sender.sendMessage("Il n'est pas possible de voter pour le moment.");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can vote.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            if (votedPlayers.contains(player.getUniqueId())) {
                player.sendMessage("Vous avez déjà voté.");
                return true;
            }
            String playerName = args[0];
            votes.put(playerName, votes.getOrDefault(playerName, 0) + 1);
            votedPlayers.add(player.getUniqueId());
            player.sendMessage("Vous avez voté pour: " + playerName);
            return true;
        }
        return false;
    }

    public static boolean isVoteValid() {
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        return totalVotes >= (onlinePlayers / 2.0);
    }

    public static String getWinner() {
        if (!isVoteValid()) {
            votes.clear();
            votedPlayers.clear();
            return null;
        }
        String winner = null;
        int maxVotes = 0;
        for (String key : votes.keySet()) {
            int voteCount = votes.get(key);
            if (voteCount > maxVotes) {
                maxVotes = voteCount;
                winner = key;
            }
        }
        votes.clear();
        votedPlayers.clear();
        return winner;
    }
}