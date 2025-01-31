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

package pl.plajer.buildbattle.menus.options.registry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import pl.plajer.buildbattle.arena.ArenaRegistry;
import pl.plajer.buildbattle.arena.impl.BaseArena;
import pl.plajer.buildbattle.arena.managers.plots.Plot;
import pl.plajer.buildbattle.menus.options.MenuOption;
import pl.plajer.buildbattle.menus.options.OptionsRegistry;
import pl.plajerlair.commonsbox.minecraft.compat.XMaterial;
import pl.plajerlair.commonsbox.minecraft.item.ItemBuilder;

/**
 * @author Plajer
 * <p>
 * Created at 23.12.2018
 */
public class TimeChangeOption {

  public TimeChangeOption(OptionsRegistry registry) {
    registry.registerOption(new MenuOption(30, "TIME", new ItemBuilder(XMaterial.CLOCK.parseItem())
        .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Item-Name"))
        .lore(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Item-Lore"))
        .build(), registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Inventory-Name")) {

      @Override
      public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();

        Inventory timeInv = Bukkit.createInventory(null, 9, registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Inventory-Name"));
        timeInv.setItem(0, new ItemBuilder(XMaterial.CLOCK.parseItem())
            .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Type.World-Time")).build());
        timeInv.setItem(1, new ItemBuilder(XMaterial.CLOCK.parseItem())
            .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Type.Day")).build());
        timeInv.setItem(2, new ItemBuilder(XMaterial.CLOCK.parseItem())
            .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Type.Sunset")).build());
        timeInv.setItem(3, new ItemBuilder(XMaterial.CLOCK.parseItem())
            .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Type.Night")).build());
        timeInv.setItem(4, new ItemBuilder(XMaterial.CLOCK.parseItem())
            .name(registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Type.Sunrise")).build());
        e.getWhoClicked().openInventory(timeInv);
      }

      @Override
      public void onTargetClick(InventoryClickEvent e) {
        BaseArena arena = ArenaRegistry.getArena((Player) e.getWhoClicked());
        if (arena == null) {
          return;
        }
        Plot plot = arena.getPlotManager().getPlot((Player) e.getWhoClicked());
        plot.setTime(Plot.Time.valueOf(TimeClickPosition.getByPosition(e.getSlot()).toString()));
        for (Player p : plot.getOwners()) {
          p.setPlayerTime(Plot.Time.format(plot.getTime(), p.getWorld().getTime()), false);
          p.sendMessage(registry.getPlugin().getChatManager().getPrefix() + registry.getPlugin().getChatManager().colorMessage("Menus.Option-Menu.Items.Time.Time-Set"));
        }
      }
    });
  }

  public enum TimeClickPosition {
    WORLD_TIME(0), DAY(1), SUNSET(2), NIGHT(3), SUNRISE(4);

    private int position;

    TimeClickPosition(int position) {
      this.position = position;
    }

    /**
     * Get time by clicked inventory position
     *
     * @param pos clicked position
     * @return clicked time, returns WORLD_TIME if clicked not matching results
     */
    public static TimeClickPosition getByPosition(int pos) {
      for (TimeClickPosition position : values()) {
        if (position.getPosition() == pos) {
          return position;
        }
      }
      return TimeClickPosition.WORLD_TIME;
    }

    public int getPosition() {
      return position;
    }
  }

}
