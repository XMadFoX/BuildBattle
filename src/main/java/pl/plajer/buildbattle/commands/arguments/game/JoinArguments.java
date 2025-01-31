/*
 * BuildBattle - Ultimate building competition minigame
 * Copyright (C) 2019  Plajer's Lair - maintained by Plajer and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.buildbattle.commands.arguments.game;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.plajer.buildbattle.ConfigPreferences;
import pl.plajer.buildbattle.arena.ArenaManager;
import pl.plajer.buildbattle.arena.ArenaRegistry;
import pl.plajer.buildbattle.arena.ArenaState;
import pl.plajer.buildbattle.arena.impl.BaseArena;
import pl.plajer.buildbattle.commands.arguments.ArgumentsRegistry;
import pl.plajer.buildbattle.commands.arguments.data.CommandArgument;

/**
 * @author Plajer
 * <p>
 * Created at 03.12.2018
 */
public class JoinArguments {

  public JoinArguments(ArgumentsRegistry registry) {
    //join argument
    registry.mapArgument("buildbattle", new CommandArgument("join", "", CommandArgument.ExecutorType.PLAYER) {
      @Override
      public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
          sender.sendMessage(registry.getPlugin().getChatManager().colorMessage("Commands.Type-Arena-Name"));
          return;
        }
        if (ArenaRegistry.getArena(((Player) sender)) != null) {
          sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("In-Game.Already-Playing"));
          return;
        }
        for (BaseArena arena : ArenaRegistry.getArenas()) {
          if (args[1].equalsIgnoreCase(arena.getID())) {
            if (arena.getPlayers().size() >= arena.getMaximumPlayers()) {
              sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("Commands.Arena-Is-Full"));
            } else if (arena.getArenaState() == ArenaState.IN_GAME) {
              sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("Commands.Arena-Started"));
            } else {
              ArenaManager.joinAttempt((Player) sender, arena);
            }
            return;
          }
        }
        sender.sendMessage(registry.getPlugin().getChatManager().colorMessage("Commands.No-Arena-Like-That"));
      }
    });

    //random join argument, register only for multi arena
    if (!registry.getPlugin().getConfigPreferences().getOption(ConfigPreferences.Option.BUNGEE_ENABLED)) {
      registry.mapArgument("buildbattle", new CommandArgument("randomjoin", "", CommandArgument.ExecutorType.PLAYER) {
        @Override
        public void execute(CommandSender sender, String[] args) {
          if (ArenaRegistry.getArena(((Player) sender)) != null) {
            sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("In-Game.Already-Playing"));
            return;
          }
          if (args.length == 1) {
            sender.sendMessage(registry.getPlugin().getChatManager().colorMessage("Commands.Invalid-Args"));
            return;
          }
          //todo gtb case
          switch (args[1].toLowerCase()) {
            case "solo":
            case "team":
              BaseArena.ArenaType type = BaseArena.ArenaType.valueOf(args[1].toUpperCase());
              for (BaseArena arena : ArenaRegistry.getArenas()) {
                if (arena.getArenaType() == type) {
                  if (arena.getArenaState() == ArenaState.STARTING || arena.getArenaState() == ArenaState.WAITING_FOR_PLAYERS) {
                    ArenaManager.joinAttempt((Player) sender, arena);
                    return;
                  }
                }
              }
              sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("Commands.No-Free-Arenas"));
              return;
            default:
              sender.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("Commands.Invalid-Args"));
          }
        }
      });
    }
  }

}
