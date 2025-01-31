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

package pl.plajer.buildbattle.menus.themevoter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajer.buildbattle.Main;
import pl.plajer.buildbattle.api.StatsStorage;
import pl.plajer.buildbattle.arena.impl.SoloArena;
import pl.plajer.buildbattle.user.User;
import pl.plajerlair.commonsbox.minecraft.compat.XMaterial;
import pl.plajerlair.commonsbox.minecraft.item.ItemBuilder;
import pl.plajerlair.commonsbox.number.NumberUtils;

/**
 * @author Plajer
 * <p>
 * Created at 07.07.2018
 */
@Deprecated //not per player inventory, can get bugged
public class VoteMenu {

  private Main plugin = JavaPlugin.getPlugin(Main.class);
  private Inventory inventory;
  private VotePoll votePoll;
  private SoloArena arena;

  public VoteMenu(SoloArena arena) {
    this.arena = arena;
    this.inventory = Bukkit.createInventory(null, 9 * 5, plugin.getChatManager().colorMessage("Menus.Theme-Voting.Inventory-Name"));
  }

  private void setItem(ItemStack itemStack, int pos) {
    inventory.setItem(pos, itemStack);
  }

  public void resetPoll() {
    List<String> themesTotal = new ArrayList<>(plugin.getConfigPreferences().getThemes(arena.getArenaType().getPrefix()));
    //random themes order
    Collections.shuffle(themesTotal);
    List<String> randomThemes = new ArrayList<>();
    if (themesTotal.size() <= 5) {
      randomThemes.addAll(themesTotal);
    } else {
      Iterator<String> itr = themesTotal.iterator();
      int i = 0;
      while (itr.hasNext()) {
        if (i == 5) {
          break;
        }
        randomThemes.add(itr.next());
        itr.remove();
        i++;
      }
    }
    this.inventory = Bukkit.createInventory(null, 9 * (randomThemes.size() > 5 ? 5 : randomThemes.size()), plugin.getChatManager().colorMessage("Menus.Theme-Voting.Inventory-Name"));
    for (int i = 0; i < randomThemes.size(); i++) {
      setItem(new ItemBuilder(Material.SIGN)
          .name(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Theme-Item-Name").replace("%theme%", randomThemes.get(i)))
          .lore(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Theme-Item-Lore").replace("%theme%", randomThemes.get(i))
              .replace("%percent%", "0.0").replace("%time-left%", String.valueOf(arena.getTimer())).split(";"))
          .build(), i * 9);
      setItem(new ItemBuilder(XMaterial.IRON_BARS.parseItem()).build(), (i * 9) + 1);
      for (int j = 0; j < 6; j++) {
        setItem(new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE.parseItem()).build(), (i * 9) + 1 + j + 1);
      }
      setItem(new ItemBuilder(new ItemStack(Material.PAPER))
          .name(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Super-Vote-Item-Name").replace("%theme%", randomThemes.get(i)))
          .lore(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Super-Vote-Item-Lore").replace("%theme%", randomThemes.get(i)).split(";"))
          .build(), (i * 9) + 8);
    }
    votePoll = new VotePoll(arena, randomThemes);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public VotePoll getVotePoll() {
    return votePoll;
  }

  public void updateInventory(Player player) {
    int totalVotes = votePoll.getPlayerVote().size();
    int i = 0;
    User user = plugin.getUserManager().getUser(player);
    for (String theme : votePoll.getVotedThemes().keySet()) {
      double percent;
      if (Double.isNaN(votePoll.getVotedThemes().get(theme)) || votePoll.getVotedThemes().get(theme) == 0) {
        percent = 0.0;
      } else {
        percent = ((double) votePoll.getVotedThemes().get(theme) / (double) totalVotes) * 100;
      }
      ItemStack stack = new ItemBuilder(new ItemStack(Material.SIGN))
          .name(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Theme-Item-Name").replace("%theme%", theme))
          .lore(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Theme-Item-Lore").replace("%theme%", theme)
              .replace("%percent%", String.valueOf(NumberUtils.round(percent, 2))).replace("%time-left%", String.valueOf(arena.getTimer())).split(";"))
          .build();
      if (votePoll.getPlayerVote().containsKey(player) && votePoll.getPlayerVote().get(player).equals(theme)) {
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
      }
      setItem(stack, i * 9);
      for (int j = 0; j < 6; j++) {
        setItem(new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE.parseItem()).build(), (i * 9) + 1 + j + 1);
      }
      setItem(new ItemBuilder(new ItemStack(Material.PAPER))
          .name(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Super-Vote-Item-Name").replace("%theme%", theme))
          .lore(plugin.getChatManager().colorMessage("Menus.Theme-Voting.Super-Vote-Item-Lore").replace("%theme%", theme)
              .replace("%owned%", String.valueOf(user.getStat(StatsStorage.StatisticType.SUPER_VOTES))).split(";"))
          .build(), (i * 9) + 8);
      if (votePoll.getVotedThemes().get(theme) > 0) {
        double vote = 0;
        for (int j = 0; j < 6; j++) {
          if (vote > percent) {
            break;
          }
          setItem(new ItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE.parseItem()).build(), (i * 9) + 1 + j + 1);
          vote += 16.7;
        }
      }
      i++;
    }
    player.updateInventory();
  }

}
