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

package pl.plajer.buildbattle.arena.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPI;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajer.buildbattle.Main;
import pl.plajer.buildbattle.arena.ArenaState;
import pl.plajer.buildbattle.arena.impl.BaseArena;
import pl.plajer.buildbattle.arena.impl.SoloArena;
import pl.plajer.buildbattle.handlers.language.LanguageManager;
import pl.plajer.buildbattle.user.User;
import pl.plajerlair.commonsbox.string.StringFormatUtils;

/**
 * @author Plajer
 * <p>
 * Created at 11.01.2019
 */
public class ScoreboardManager {

  private static Main plugin = JavaPlugin.getPlugin(Main.class);
  private Map<String, List<String>> scoreboardContents = new HashMap<>();
  private List<Scoreboard> scoreboards = new ArrayList<>();
  private String boardTitle = plugin.getChatManager().colorMessage("Scoreboard.Title");
  private BaseArena arena;

  public ScoreboardManager(BaseArena arena) {
    this.arena = arena;
    for (ArenaState state : ArenaState.values()) {
      //not registering RESTARTING state and registering IN_GAME and ENDING later
      if (state == ArenaState.RESTARTING || state == ArenaState.IN_GAME || state == ArenaState.ENDING) {
        continue;
      }
      //todo migrator
      List<String> lines = LanguageManager.getLanguageList("Scoreboard.Content." + state.getFormattedName());
      scoreboardContents.put(state.getFormattedName(), lines);
    }
    for (BaseArena.ArenaType type : BaseArena.ArenaType.values()) {
      List<String> playing = LanguageManager.getLanguageList("Scoreboard.Content.Playing-States." + type.getPrefix());
      List<String> ending = LanguageManager.getLanguageList("Scoreboard.Content.Ending-States." + type.getPrefix());
      //todo locale
      scoreboardContents.put(ArenaState.IN_GAME.getFormattedName() + "_" + type.getPrefix(), playing);
      scoreboardContents.put(ArenaState.ENDING.getFormattedName() + "_" + type.getPrefix(), ending);
    }
  }

  /**
   * Creates arena scoreboard for target user
   *
   * @param user user that represents game player
   * @see User
   */
  public void createScoreboard(User user) {
    Scoreboard scoreboard = ScoreboardLib.createScoreboard(user.getPlayer()).setHandler(new ScoreboardHandler() {
      @Override
      public String getTitle(Player player) {
        return boardTitle;
      }

      @Override
      public List<Entry> getEntries(Player player) {
        return formatScoreboard(user);
      }
    });
    scoreboard.activate();
    scoreboards.add(scoreboard);
  }

  /**
   * Removes scoreboard of user
   *
   * @param user user that represents game player
   * @see User
   */
  public void removeScoreboard(User user) {
    for (Scoreboard board : scoreboards) {
      if (board.getHolder().equals(user.getPlayer())) {
        scoreboards.remove(board);
        board.deactivate();
        return;
      }
    }
  }

  /**
   * Forces all scoreboards to deactivate.
   */
  public void stopAllScoreboards() {
    for (Scoreboard board : scoreboards) {
      board.deactivate();
    }
    scoreboards.clear();
  }

  public List<Entry> formatScoreboard(User user) {
    EntryBuilder builder = new EntryBuilder();
    List<String> lines = scoreboardContents.get(arena.getArenaState().getFormattedName());
    if (arena.getArenaState() == ArenaState.IN_GAME || arena.getArenaState() == ArenaState.ENDING) {
      lines = scoreboardContents.get(arena.getArenaState().getFormattedName() + "_" + arena.getArenaType().getPrefix());
    }
    for (String line : lines) {
      builder.next(formatScoreboardLine(line, user));
    }
    return builder.build();
  }

  public String formatScoreboardLine(String string, User user) {
    Player player = user.getPlayer();
    String returnString = string;
    returnString = StringUtils.replace(returnString, "%PLAYERS%", Integer.toString(arena.getPlayers().size()));
    returnString = StringUtils.replace(returnString, "%PLAYER%", player.getName());
    if (((SoloArena) arena).isThemeVoteTime()) {
      returnString = StringUtils.replace(returnString, "%THEME%", plugin.getChatManager().colorMessage("In-Game.No-Theme-Yet"));
    } else {
      returnString = StringUtils.replace(returnString, "%THEME%", arena.getTheme());
    }
    returnString = StringUtils.replace(returnString, "%MIN_PLAYERS%", Integer.toString(arena.getMinimumPlayers()));
    returnString = StringUtils.replace(returnString, "%MAX_PLAYERS%", Integer.toString(arena.getMaximumPlayers()));
    returnString = StringUtils.replace(returnString, "%TIMER%", Integer.toString(arena.getTimer()));
    //todo its the same
    returnString = StringUtils.replace(returnString, "%TIME_LEFT%", Long.toString(arena.getTimer()));
    returnString = StringUtils.replace(returnString, "%FORMATTED_TIME_LEFT%", StringFormatUtils.formatIntoMMSS(arena.getTimer()));
    returnString = StringUtils.replace(returnString, "%ARENA_ID%", arena.getID());
    returnString = StringUtils.replace(returnString, "%MAPNAME%", arena.getMapName());
    if (!((SoloArena) arena).isThemeVoteTime()) {
      if (arena.getArenaType() == BaseArena.ArenaType.TEAM && arena.getPlotManager().getPlot(player) != null) {
        if (arena.getPlotManager().getPlot(player).getOwners().size() == 2) {
          if (arena.getPlotManager().getPlot(player).getOwners().get(0).equals(player)) {
            returnString = StringUtils.replace(returnString, "%TEAMMATE%", arena.getPlotManager().getPlot(player).getOwners().get(1).getName());
          } else {
            returnString = StringUtils.replace(returnString, "%TEAMMATE%", arena.getPlotManager().getPlot(player).getOwners().get(0).getName());
          }
        } else {
          returnString = StringUtils.replace(returnString, "%TEAMMATE%", plugin.getChatManager().colorMessage("In-Game.Nobody"));
        }
      }
    } else {
      returnString = StringUtils.replace(returnString, "%TEAMMATE%", plugin.getChatManager().colorMessage("In-Game.Nobody"));
    }
    if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      returnString = PlaceholderAPI.setPlaceholders(player, returnString);
    }
    returnString = plugin.getChatManager().colorRawMessage(returnString);
    return returnString;
  }

  public String getBoardTitle() {
    return boardTitle;
  }

  public Main getPlugin() {
    return plugin;
  }

}
