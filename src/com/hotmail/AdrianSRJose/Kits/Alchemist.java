package com.hotmail.AdrianSRJose.Kits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.hotmail.AdrianSRJose.AnniPro.anniEvents.AnniEvent;
import com.hotmail.AdrianSRJose.AnniPro.anniGame.AnniPlayer;
import com.hotmail.AdrianSRJose.AnniPro.anniGame.Game;
import com.hotmail.AdrianSRJose.AnniPro.kits.KitUtils;
import com.hotmail.AdrianSRJose.AnniPro.kits.Loadout;
import com.hotmail.AdrianSRJose.AnniPro.main.AnnihilationMain;
import com.hotmail.AdrianSRJose.AnniPro.utils.CompatibleUtils.CompatibleParticles;
import com.hotmail.AdrianSRJose.AnniPro.utils.Loc;
import com.hotmail.AdrianSRJose.AnniPro.utils.Material;
import com.hotmail.AdrianSRJose.AnniPro.utils.ServerVersion;
import com.hotmail.AdrianSRJose.AnniPro.utils.Util;
import com.hotmail.AdrianSRJose.base.ClassItemKit;
import com.hotmail.AdrianSRJose.events.Alchemist_OpenAlchemistsTomeEvent;

public class Alchemist extends ClassItemKit {
	
	private static Material STRING = Material.STRING;
	private static Material FERMENTED_SPIDER_EYE = Material.FERMENTED_SPIDER_EYE;
	private static String ownerNameMessage = "&cBrewing Stand by &6%w";
	public static String PRIVATE_BREWING_STAND_NAME = "&bAlchemist's Stand";
	public static String ALCHEMIST_TOME_INVENTORY_NAME = "&bAlchemist";
	
	//
	@Override
	protected String getInternalName() {
		return "Alchemist";
	}

	@Override
	protected ItemStack getDefaultIcon() {
		return new ItemStack(Material.ENCHANTED_BOOK.toBukkit());
	}
	
	@Override
	protected int setInConfig(ConfigurationSection section) {
		return Util.setDefaultIfNotSet(section, "BrewingStandOwnerMessage", "&cBrewing Stand by &6%w")
				+ Util.setDefaultIfNotSet(section, "BrewingStandName", PRIVATE_BREWING_STAND_NAME)
				+ Util.setDefaultIfNotSet(section, "AlchemistTomeInventoryName", ALCHEMIST_TOME_INVENTORY_NAME);
	}

	@Override
	protected void loadFromConfig(ConfigurationSection section) {
		if (section != null) {
			if (section.isString("BrewingStandOwnerMessage")) {
				ownerNameMessage = Util.wc(section.getString("BrewingStandOwnerMessage"));
			}
			
			PRIVATE_BREWING_STAND_NAME = Util.wc(section.getString("BrewingStandName", PRIVATE_BREWING_STAND_NAME));
			ALCHEMIST_TOME_INVENTORY_NAME = Util.wc(section.getString("AlchemistTomeInventoryName", ALCHEMIST_TOME_INVENTORY_NAME));
		}
	}

	@Override
	protected List<String> getDefaultDescription() {
		List<String> l = new ArrayList<>();

		addToList(l, aqua + "You are the brew.", "", aqua + "Set up your own personal brewing",
				aqua + "stand anywhere to brew in peace,", "", aqua + "but keep it away from enemies",
				aqua + "as they can break it.", "", aqua + "Your alchemist's tome will",
				aqua + "provide you with potion ingredients", aqua + "every 90 seconds.");
		return l;
	}

	@Override
	protected Loadout getFinalLoadout() {
		return new Loadout().addWoodSword().addWoodPick().addWoodAxe().addItem(BrewingStandPrivada.getBrewingStand())
				.addItem(getSpecialItem());
	}

	@Override
	public boolean onItemClick(Inventory inv, AnniPlayer ap) {
		this.addLoadoutToInventory(inv);
		return true;
	}

	@Override
	public void cleanup(Player pl) {
		BrewingStandPrivada.removeBrewing(pl);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent eve) {
		final Player p = eve.getPlayer();
		if (eve.isCancelled() || Game.isNotRunning() || !KitUtils.isOnGameMap(p)) {
			return;
		}

		final AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		final Location loc = eve.getBlock().getLocation();
		if (ap != null && ap.isOnline() && Util.isValidLoc(loc) && ap.getTeam() != null && hasThisKit(ap)) {
			if (KitUtils.itemHasName(eve.getItemInHand(),
					BrewingStandPrivada.getBrewingStand().getItemMeta().getDisplayName())) {
				if (Game.getGameMap().getAreas().getArea(new Loc(eve.getBlockPlaced().getLocation(), false)) != null) {
					eve.setCancelled(true);
					return;
				}
				//
				eve.getPlayer().setItemInHand(null);
				eve.getPlayer().updateInventory();
				BrewingStandPrivada.crearBrewingStandPrivada(loc, ap);
				//
				new BukkitRunnable() {
					@Override
					public void run() {
						if (BrewingStandPrivada.isBrewingStand(loc)
								&& loc.getBlock().getType() == Material.BREWING_STAND.toBukkit()) {
							if (ServerVersion.serverNewerEqualsThan(ServerVersion.v1_9_R1)) {
								CompatibleParticles.WITCH.displayNewerVersions().display(0.2F, 0.0F, 0.2F, 0.0F,
										4, loc.clone().add(0.5D, 0.0D, 0.5D), 10.0D);
							} else {
								CompatibleParticles.WITCH.displayOlderVersions().display(0.2F, 0.0F, 0.2F, 0.0F,
										4, loc.clone().add(0.5D, 0.0D, 0.5D), 10.0D);
							}
						}
						else {
							cancel();
						}
					}
				}.runTaskTimer(AnnihilationMain.INSTANCE, 0L, 0L);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onOpen(PlayerInteractEvent eve) {
		final Player p = eve.getPlayer();
		if (Game.isNotRunning() || !KitUtils.isOnGameMap(p)) {
			return;
		}

		if (eve.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		// Check
		final Location clicked = eve.getClickedBlock().getLocation();
		final BrewingStandPrivada br = BrewingStandPrivada.getBrewingStand(clicked);
		if (br == null) {
			return;
		}
		
		final AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		if (p.getUniqueId().equals(br.getOwnerID())) {
			if (hasThisKit(ap)) {
				return;
			}
			else {
				// Cancell
				eve.setCancelled(true);
			}
		}
		else {
			// Cancell
			eve.setCancelled(true);
			
			// Get Player
			final AnniPlayer APowner = br.getOwner();
			if (APowner != null && APowner.isOnline()) {
				// Owner
				final Player owner = APowner.getPlayer();
				if (APowner.getTeam().equals(ap.getTeam())) {
					p.sendMessage("" + this.ownerNameMessage.replace("%w",
							(owner.getName() != null ? owner.getName() : "Unknown")));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBreak(BlockBreakEvent eve) {
		final Player p = eve.getPlayer();
		if (Game.isNotRunning() || !KitUtils.isOnGameMap(p)) {
			return;
		}

		// Get anni player and check
		final AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		final Location bk = eve.getBlock().getLocation();
		if (ap != null && bk != null && BrewingStandPrivada.isBrewingStand(bk)) {
			final BrewingStandPrivada br = BrewingStandPrivada.getBrewingStand(bk);
			if (br == null) {
				return;
			}

			// Cancell
			eve.setCancelled(true);

			final Player owner = Bukkit.getPlayer(br.getOwnerID());
			final AnniPlayer aPowner = AnniPlayer.getPlayer(br.getOwnerID());
			if (KitUtils.isValidPlayer(owner) && aPowner != null) {
				if (ap.getTeam().equals(aPowner.getTeam())) {
					if (p.getUniqueId().equals(br.getOwnerID())) {
						BrewingStandPrivada.removeBrewing(owner);
						owner.getInventory().addItem(BrewingStandPrivada.getBrewingStand());
						owner.updateInventory();
					}
				} else {
					BrewingStandPrivada.removeBrewing(owner);
					owner.getInventory().addItem(BrewingStandPrivada.getBrewingStand());
					owner.updateInventory();
				}
			}
		}
	}

	@Override
	protected void onInitialize() {
	}

	@Override
	protected ItemStack specialItem() {
		ItemStack stack = KitUtils.addClassUndropabbleSoulbound(new ItemStack(Material.BOOK.toBukkit()));
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getSpecialItemName() + instance.getReadyPrefix());
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	protected String defaultSpecialItemName() {
		return ChatColor.AQUA + "Alchemist's Tome";
	}

	@Override
	protected boolean isSpecialItem(ItemStack stack) {
		if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
			String name = stack.getItemMeta().getDisplayName();
			if (name.contains(getSpecialItemName()) && KitUtils.isClassUndropabbleSoulbound(stack))
				return true;
		}
		//
		return false;
	}

	public boolean power(Player p, AnniPlayer ap) {
		if (p == null || ap == null)
			return false;
		//
		Inventory g = Bukkit.getServer().createInventory(null, 9, "");
		//
		Alchemist_OpenAlchemistsTomeEvent eve = new Alchemist_OpenAlchemistsTomeEvent(ap, g);
		eve.setAlchemistTomeInventoryName(eve.getAlchemistTomeInventoryName());
		AnniEvent.callEvent(eve);
		//
		if (eve.isCancelled())
			return false;

		Inventory inv = eve.getTomeInventory();
		//
		if (inv == null)
			return false;
		//
		Random r = new Random();
		int total = r.nextInt(105);

		// 32%
		if (total >= 0 && total <= 32) {
			Random a = new Random();
			int ai = a.nextInt(35);

			if (ai >= 0 && ai <= 8) {
				inv.setItem(3, new ItemStack(FERMENTED_SPIDER_EYE.toBukkit()));
				inv.setItem(8, new ItemStack(STRING.toBukkit()));
			}
			if (ai >= 9 && ai <= 17) {
				inv.setItem(7, new ItemStack(FERMENTED_SPIDER_EYE.toBukkit()));
				inv.setItem(1, new ItemStack(Material.SNOW_BALL.toBukkit()));
				inv.setItem(2, new ItemStack(Material.SEEDS.toBukkit()));
			}
			if (ai >= 18 && ai <= 26) {
				inv.setItem(6, new ItemStack(FERMENTED_SPIDER_EYE.toBukkit()));
				inv.setItem(0, new ItemStack(STRING.toBukkit()));
			}
			if (ai >= 27 && ai <= 35) {
				inv.setItem(0, new ItemStack(FERMENTED_SPIDER_EYE.toBukkit()));
				inv.setItem(5, new ItemStack(Material.COOKIE.toBukkit()));
				inv.setItem(8, new ItemStack(Material.MELON_SEEDS.toBukkit()));
			}
			// Fermented Spider Eye
		}

		// 28%
		if (total >= 33 && total <= 61) {
			Random a = new Random();
			int ai = a.nextInt(31);

			// Glistering Melon
			if (ai >= 0 && ai <= 7) {
				inv.setItem(5, new ItemStack(Material.SPECKLED_MELON.toBukkit()));
				inv.setItem(3, new ItemStack(Material.STRING.toBukkit()));
			}

			// Golden Carrot
			if (ai >= 8 && ai <= 15) {
				inv.setItem(0, new ItemStack(Material.GOLDEN_CARROT.toBukkit()));
				inv.setItem(5, new ItemStack(Material.SEEDS.toBukkit()));
			}

			// Sugar
			if (ai >= 16 && ai <= 23) {
				inv.setItem(8, new ItemStack(Material.SUGAR.toBukkit()));
				inv.setItem(3, new ItemStack(Material.STICK.toBukkit()));
				inv.setItem(7, new ItemStack(Material.SNOW_BALL.toBukkit()));
			}

			// Spider Eye, Magma Cream
			if (ai >= 24 && ai <= 31) {
				inv.setItem(3, new ItemStack(Material.SPIDER_EYE.toBukkit()));
				inv.setItem(5, new ItemStack(Material.ROTTEN_FLESH.toBukkit()));
				inv.setItem(1, new ItemStack(Material.MAGMA_CREAM.toBukkit()));
			}
		}

		// 15%
		if (total >= 62 && total <= 77) {
			Random f = new Random();
			int ai = f.nextInt(16);

			// Glowstone Dust
			if (ai >= 0 && ai <= 8) {
				inv.setItem(5, new ItemStack(Material.GLOWSTONE_DUST.toBukkit()));
				inv.setItem(8, new ItemStack(Material.SNOW_BALL.toBukkit()));
			}

			// Gunpowder
			if (ai >= 9 && ai <= 16) {
				inv.setItem(1, new ItemStack(Material.BLAZE_POWDER.toBukkit()));
				inv.setItem(6, new ItemStack(Material.STRING.toBukkit()));
				inv.setItem(2, new ItemStack(Material.COOKIE.toBukkit()));
			}
		}
		// 7%
		if (total >= 78 && total <= 85) {
			Random f = new Random();
			int ai = f.nextInt(8);

			if (ai >= 0 && ai <= 3) {
				inv.setItem(6, new ItemStack(Material.GHAST_TEAR.toBukkit()));
				inv.setItem(5, new ItemStack(Material.SNOW_BALL.toBukkit()));
			}

			if (ai >= 4 && ai <= 8) {
				inv.setItem(3, new ItemStack(Material.GHAST_TEAR.toBukkit()));
				inv.setItem(8, new ItemStack(Material.COOKIE.toBukkit()));
				inv.setItem(6, new ItemStack(Material.STRING.toBukkit()));
			}
			// Ghast Tear
		}

		// 3% and is > that phase 4
		if (total >= 86 && total <= 89 && Game.getGameMap().getCurrentPhase() >= 4) {
			// Blaze Powder
			inv.setItem(5, new ItemStack(Material.BLAZE_POWDER.toBukkit()));
			inv.setItem(8, new ItemStack(Material.POTATO_ITEM.toBukkit()));
			inv.setItem(1, new ItemStack(Material.SNOW_BALL.toBukkit()));
		}

		// 15%
		if (total >= 90 && total <= 105) {
			Random f = new Random();
			int ai = f.nextInt(16);

			// Rotten Flesh Poisonous Potato
			if (ai >= 0 && ai <= 8) {
				inv.setItem(1, new ItemStack(Material.ROTTEN_FLESH.toBukkit()));
				inv.setItem(5, new ItemStack(Material.POTATO_ITEM.toBukkit()));
			}

			// SNOW_BALL, String.
			if (ai >= 9 && ai <= 16) {
				inv.setItem(8, new ItemStack(Material.STRING.toBukkit()));
				inv.setItem(2, new ItemStack(Material.SNOW_BALL.toBukkit()));
			}
		}
		//
		p.openInventory(inv);
		return true;
	}

	@Override
	protected long getDefaultDelayLength() {
		return 90000;
	}

	@Override
	protected boolean useDefaultChecking() {
		return true;
	}

	@Override
	protected boolean performPrimaryAction(Player player, AnniPlayer p, PlayerInteractEvent event) {
		return power(player, p);
	}

	@Override
	protected boolean performSecondaryAction(Player player, AnniPlayer p, PlayerInteractEvent event) {
		return false;
	}

	@Override
	protected void onPlayerRespawn(Player p, AnniPlayer ap) {
	}

	@Override
	protected boolean useCustomMessage() {
		return false;
	}

	@Override
	protected String positiveMessage() {
		return null;
	}

	@Override
	protected String negativeMessage() {
		return null;
	}
}
