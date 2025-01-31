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

package pl.plajer.buildbattle.arena.options;

/**
 * @author Plajer
 * <p>
 * Created at 11.01.2019
 */
public enum ArenaOption {
  /**
   * Current arena timer, ex. 30 seconds before game starts.
   */
  TIMER(0),
  /**
   * Minimum players in arena needed to start.
   */
  MINIMUM_PLAYERS(2),
  /**
   * Maximum players arena can hold, users with full games permission can bypass this!
   */
  MAXIMUM_PLAYERS(10),
  /**
   * Value for checking whether player is in plot or not, otherwise teleport him back.
   * <p>
   * Each time values reach 1 check will be run.
   */
  IN_PLOT_CHECKER(0);

  private int defaultValue;

  ArenaOption(int defaultValue) {
    this.defaultValue = defaultValue;
  }

  public int getDefaultValue() {
    return defaultValue;
  }

}
