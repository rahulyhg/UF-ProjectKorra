package com.projectkorra.projectkorra.airbending;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AbilityModuleManager;
import com.projectkorra.projectkorra.ability.StockAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AirMethods {

    private static ProjectKorra plugin;
    private static FileConfiguration config = ProjectKorra.plugin.getConfig();

    public AirMethods(ProjectKorra plugin) {
        AirMethods.plugin = plugin;
    }

    /**
     * Checks to see if a player can use Flight.
     *
     * @param player The player to check
     * @return true If player has permission node "bending.air.flight"
     */
    public static boolean canAirFlight(Player player) {
        if (player.hasPermission("bending.air.flight")) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if a player can use SpiritualProjection.
     *
     * @param player The player to check
     * @return true If player has permission node
     * "bending.air.spiritualprojection"
     */
    public static boolean canUseSpiritualProjection(Player player) {
        if (player.hasPermission("bending.air.spiritualprojection")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the AirColor from the config.
     *
     * @return Config specified ChatColor
     */
    public static ChatColor getAirColor() {
        return ChatColor.valueOf(config.getString("Properties.Chat.Colors.Air"));
    }

    /**
     * Gets the AirSubColor from the config.
     *
     * @return Config specified ChatColor
     */
    public static ChatColor getAirSubColor() {
        return ChatColor.valueOf(config.getString("Properties.Chat.Colors.AirSub"));
    }

    /**
     * Checks whether an ability is an air ability.
     *
     * @param ability The ability to check
     * @return true If the ability is an air ability.
     */
    public static boolean isAirAbility(String ability) {
        return AbilityModuleManager.airbendingabilities.contains(ability);
    }

    /**
     * Checks whether an ability is a Flight ability.
     *
     * @param ability The ability to check
     * @return true If the ability is a Flight ability.
     */
    public static boolean isFlightAbility(String ability) {
        return AbilityModuleManager.flightabilities.contains(ability);
    }

    /**
     * Checks whether an ability is a SpiritualProjection ability.
     *
     * @param ability The ability to check
     * @return true If the ability is a SpiritualProjection ability.
     */
    public static boolean isSpiritualProjectionAbility(String ability) {
        return AbilityModuleManager.spiritualprojectionabilities.contains(ability);
    }

    /**
     * Gets the Air Particles from the config.
     *
     * @return Config specified ParticleEffect
     */
    public static ParticleEffect getAirbendingParticles() {
        String particle = plugin.getConfig().getString("Properties.Air.Particles");
        if (particle == null) {
            return ParticleEffect.CLOUD;
        } else if (particle.equalsIgnoreCase("spell")) {
            return ParticleEffect.SPELL;
        } else if (particle.equalsIgnoreCase("blacksmoke")) {
            return ParticleEffect.SMOKE;
        } else if (particle.equalsIgnoreCase("smoke")) {
            return ParticleEffect.CLOUD;
        } else {
            return ParticleEffect.CLOUD;
        }
    }

    /**
     * Plays an integer amount of air particles in a location.
     *
     * @param loc The location to use
     * @param amount The amount of particles
     */
    public static void playAirbendingParticles(Location loc, int amount) {
        playAirbendingParticles(loc, amount, (float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    /**
     * Plays an integer amount of air particles in a location with a given
     * xOffset, yOffset, and zOffset.
     *
     * @param loc The location to use
     * @param amount The amount of particles
     * @param xOffset The xOffset to use
     * @param yOffset The yOffset to use
     * @param zOffset The zOffset to use
     */
    public static void playAirbendingParticles(Location loc, int amount, float xOffset, float yOffset, float zOffset) {
        switch (getAirbendingParticles()) {
            case SPELL:
                for (int i = 0; i < amount; i++) {
                    ParticleEffect.SPELL.display(loc, xOffset, yOffset, zOffset, 0, 1);
                }
                break;
            case SMOKE:
                for (int i = 0; i < amount; i++) {
                    ParticleEffect.SMOKE.display(loc, xOffset, yOffset, zOffset, 0, 1);
                }
                break;
            default:
                for (int i = 0; i < amount; i++) {
                    ParticleEffect.CLOUD.display(loc, xOffset, yOffset, zOffset, 0, 1);
                }
                break;
        }
    }

    /**
     * Removes all air spouts in a location within a certain radius.
     *
     * @param loc The location to use
     * @param radius The radius around the location to remove spouts in
     * @param source The player causing the removal
     */
    public static void removeAirSpouts(Location loc, double radius, Player source) {
        AirSpout.removeSpouts(loc, radius, source);
    }

    /**
     * Removes all air spouts in a location with a radius of 1.5.
     *
     * @param loc The location to use
     * @param source The player causing the removal
     */
    public static void removeAirSpouts(Location loc, Player source) {
        removeAirSpouts(loc, 1.5, source);
    }

    /**
     * Stops all airbending systems. SHOULD ONLY BE USED ON PLUGIN DISABLING!
     */
    public static void stopBending() {
        AirBlast.removeAll(StockAbility.AirBlast);
        AirBubble.removeAll(StockAbility.AirBubble);
        AirShield.removeAll(StockAbility.AirShield);
        AirSuction.removeAll(StockAbility.AirSuction);
        AirScooter.removeAll(StockAbility.AirScooter);
        AirSpout.removeAll(StockAbility.AirSpout);
        AirSwipe.removeAll(StockAbility.AirSwipe);
        Tornado.removeAll(StockAbility.Tornado);
        ;
        AirBurst.removeAll(StockAbility.AirBurst);
        Suffocate.removeAll(StockAbility.Suffocate);
        AirCombo.removeAll();
        FlightAbility.removeAll();
    }

    /**
     * Breaks a breathbendng hold on an entity or one a player is inflicting on
     * an entity.
     *
     * @param entity The entity to be acted upon
     */
    public static void breakBreathbendingHold(Entity entity) {
        if (Suffocate.isBreathbent(entity)) {
            Suffocate.breakSuffocate(entity);
            return;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (Suffocate.isChannelingSphere(player)) {
                Suffocate.remove(player);
            }
        }
    }

    /**
     * Plays the Airbending Sound at a location if enabled in the config.
     *
     * @param loc The location to play the sound at
     */
    public static void playAirbendingSound(Location loc) {
        if (plugin.getConfig().getBoolean("Properties.Air.PlaySound")) {
            loc.getWorld().playSound(loc, Sound.CREEPER_HISS, 1, 5);
        }
    }

    /**
     * Checks whether a location is within an AirShield.
     *
     * @param loc The location to check
     * @return true If the location is inside an AirShield.
     */
    public static boolean isWithinAirShield(Location loc) {
        List<String> list = new ArrayList<String>();
        list.add("AirShield");
        return GeneralMethods.blockAbilities(null, list, loc, 0);
    }

    /**
     * Checks whether a player can use the Flight ability.
     *
     * @param player The player to check
     * @param first If the move is being activated
     * @param hovering If the player is using the move to hover
     * @return true If the player can use the move.
     */
    public static boolean canFly(Player player, boolean first, boolean hovering) {
        BendingPlayer bender = GeneralMethods.getBendingPlayer(player.getName());

        if (!player.isOnline()) {
            return false;
        }
        if (!player.isSneaking()) {
            if (first) {
            } else if (hovering) {

            } else {
                return false;
            }
        }
        if (bender.isChiBlocked()) {
            return false;
        }
        if (!player.isOnline()) {
            return false;
        }
        if (bender.isPermaRemoved()) {
            return false;
        }
        if (!bender.getElements().contains(Element.Air)) {
            return false;
        }
        if (!GeneralMethods.canBend(player.getName(), "Flight")) {
            return false;
        }
        if (!GeneralMethods.getBoundAbility(player).equalsIgnoreCase("Flight")) {
            return false;
        }
        if (GeneralMethods.isRegionProtectedFromBuild(player, "Flight", player.getLocation())) {
            return false;
        }
        if (player.getLocation().subtract(0, 0.5, 0).getBlock().getType() != Material.AIR) {
            return false;
        }
        return true;
    }

}
