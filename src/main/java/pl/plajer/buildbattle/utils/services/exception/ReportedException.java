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

package pl.plajer.buildbattle.utils.services.exception;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import pl.plajer.buildbattle.utils.services.ServiceRegistry;

/**
 * Create reported exception with data sent to plajer.xyz reporter service
 */
public class ReportedException {

  private ReporterService reporterService;

  public ReportedException(JavaPlugin plugin, Exception e) {
    e.printStackTrace();
    if (!ServiceRegistry.isServiceEnabled()) {
      return;
    }
    if (System.currentTimeMillis() - ServiceRegistry.getServiceCooldown() < 900000) {
      return;
    }
    if (plugin.getDescription().getVersion().contains("b")) {
      return;
    }
    ServiceRegistry.setServiceCooldown(System.currentTimeMillis());
    new BukkitRunnable() {
      @Override
      public void run() {
        StringBuffer stacktrace = new StringBuffer(e.getClass().getSimpleName());
        if (e.getMessage() != null) {
          stacktrace.append(" (").append(e.getMessage()).append(")");
        }
        stacktrace.append("\n");
        for (StackTraceElement str : e.getStackTrace()) {
          stacktrace.append(str.toString()).append("\n");
        }
        reporterService = new ReporterService(plugin.getName(), plugin.getDescription().getVersion(), plugin.getServer().getBukkitVersion() + " " + plugin.getServer().getVersion(), stacktrace.toString());
        reporterService.reportException();
      }
    }.runTaskAsynchronously(plugin);
  }

}
