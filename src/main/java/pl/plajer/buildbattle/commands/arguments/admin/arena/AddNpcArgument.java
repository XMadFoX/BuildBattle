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

package pl.plajer.buildbattle.commands.arguments.admin.arena;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import pl.plajer.buildbattle.commands.arguments.ArgumentsRegistry;
import pl.plajer.buildbattle.commands.arguments.data.CommandArgument;
import pl.plajer.buildbattle.commands.arguments.data.LabelData;
import pl.plajer.buildbattle.commands.arguments.data.LabeledCommandArgument;

/**
 * @author Plajer
 * <p>
 * Created at 11.01.2019
 */
public class AddNpcArgument {

  public AddNpcArgument(ArgumentsRegistry registry) {
    registry.mapArgument("buildbattleadmin", new LabeledCommandArgument("addnpc", "buildbattle.admin.addnpc", CommandArgument.ExecutorType.PLAYER,
        new LabelData("/bba addnpc &6<arena>", "/bba addnpc <arena>",
            "&7Deletes specified arena\n&6Permission: &7buildbattle.admin.addnpc")) {
      @Override
      public void execute(CommandSender sender, String[] args) {
        if (registry.getPlugin().getServer().getPluginManager().isPluginEnabled("Citizens")) {
          NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, registry.getPlugin().getChatManager().colorMessage("In-Game.NPC.Floor-Change-NPC-Name"));
          npc.spawn(((Player) sender).getLocation());
          npc.setProtected(true);
          npc.setName(registry.getPlugin().getChatManager().colorMessage("In-Game.NPC.Floor-Change-NPC-Name"));
          sender.sendMessage(registry.getPlugin().getChatManager().colorMessage("In-Game.NPC.NPC-Created"));
        } else {
          sender.sendMessage(registry.getPlugin().getChatManager().colorMessage("In-Game.NPC.Install-Citizens"));
        }
      }
    });
  }

}
