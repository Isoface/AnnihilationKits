package com.hotmail.AdrianSRJose.Kits;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.hotmail.AdrianSRJose.AnniPro.anniGame.AnniPlayer;
import com.hotmail.AdrianSRJose.AnniPro.anniGame.Game;
import com.hotmail.AdrianSRJose.AnniPro.kits.KitUtils;
import com.hotmail.AdrianSRJose.AnniPro.kits.Loadout;
import com.hotmail.AdrianSRJose.AnniPro.main.AnnihilationMain;
import com.hotmail.AdrianSRJose.AnniPro.utils.Material;
import com.hotmail.AdrianSRJose.AnniPro.utils.Loc;
import com.hotmail.AdrianSRJose.AnniPro.utils.VersionUtils;
import com.hotmail.AdrianSRJose.base.ConfigurableKit;

public class Tinkerer extends ConfigurableKit {
	private List<UUID> pls;
	
	private static final Material            AIR = Material.AIR;
	private static final Material     IRON_BLOCK = Material.IRON_BLOCK;
	private static final Material REDSTONE_BLOCK = Material.REDSTONE_BLOCK;
	private static final Material     COAL_BLOCK = Material.COAL_BLOCK;
	private static final Material  DIAMOND_BLOCK = Material.DIAMOND_BLOCK;
	private static final Material     GOLD_BLOCK = Material.GOLD_BLOCK;
	private static final Material  EMERALD_BLOCK = Material.EMERALD_BLOCK;
	private static final Material    STONE_PLATE = Material.STONE_PLATE;

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onP(BlockPlaceEvent eve) {
		if (Game.isNotRunning())
			return;
		if (eve.getBlock().getType() != IRON_BLOCK.toBukkit() && eve.getBlock().getType() != COAL_BLOCK.toBukkit()
				&& eve.getBlock().getType() != REDSTONE_BLOCK.toBukkit()
				&& eve.getBlock().getType() != DIAMOND_BLOCK.toBukkit() && eve.getBlock().getType() != GOLD_BLOCK.toBukkit()
				&& eve.getBlock().getType() != EMERALD_BLOCK.toBukkit())
			return;

		if (Game.getGameMap().getAreas().getArea(new Loc(eve.getBlockPlaced().getLocation(), false)) != null) {
			eve.setCancelled(true);
			return;
		}
		//
		final Player p = eve.getPlayer();
		AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		//
		if (ap != null && ap.getTeam() != null && ap.getKit().equals(this)) {
			eve.getBlockPlaced().setMetadata("tink", new FixedMetadataValue(AnnihilationMain.INSTANCE, "tink"));
			eve.getBlockPlaced().setMetadata(p.getName(),
					new FixedMetadataValue(AnnihilationMain.INSTANCE, p.getName()));

			if (ap.getData("tinkBList") == null) {
				List<Block> bls = new ArrayList<Block>();
				ap.setData("tinkBList", bls);
			}

			List<Block> ls = ((List<Block>) ap.getData("tinkBList"));

			if (!ls.contains(eve.getBlockPlaced()))
				ls.add(eve.getBlockPlaced());

			eve.getBlockPlaced().getRelative(BlockFace.UP).setType(STONE_PLATE.toBukkit());
			eve.getBlockPlaced().getRelative(BlockFace.UP).setMetadata("tink",
					new FixedMetadataValue(AnnihilationMain.INSTANCE, "tink"));
			eve.getBlockPlaced().getRelative(BlockFace.UP).setMetadata(p.getName(),
					new FixedMetadataValue(AnnihilationMain.INSTANCE, p.getName()));
		}
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onB(BlockBreakEvent eve) {
		if (Game.isNotRunning())
			return;
		if (eve.getBlock().getType() != IRON_BLOCK.toBukkit() && eve.getBlock().getType() != COAL_BLOCK.toBukkit()
				&& eve.getBlock().getType() != REDSTONE_BLOCK.toBukkit()
				&& eve.getBlock().getType() != DIAMOND_BLOCK.toBukkit() && eve.getBlock().getType() != GOLD_BLOCK.toBukkit()
				&& eve.getBlock().getType() != EMERALD_BLOCK.toBukkit()
				&& eve.getBlock().getType() != STONE_PLATE.toBukkit())
			return;

		if (!eve.getBlock().hasMetadata("tink"))
			return;
		//
		Player p = eve.getPlayer();
		AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		//
		Block bl = eve.getBlock();
		//
		if (bl == null)
			return;
		//
		if (ap != null && ap.getTeam() != null) {
			if (bl.hasMetadata(p.getName())) {
				eve.setCancelled(true);
				//
				if (eve.getBlock().getType() != STONE_PLATE.toBukkit()) {
					p.getInventory().addItem(KitUtils.addSoulbound(new ItemStack(eve.getBlock().getType())));
					eve.getBlock().getRelative(BlockFace.UP).setType(AIR.toBukkit());
				} else {
					p.getInventory().addItem(
							KitUtils.addSoulbound(new ItemStack(eve.getBlock().getRelative(BlockFace.DOWN).getType())));

					eve.getBlock().setType(AIR.toBukkit());
					eve.getBlock().getRelative(BlockFace.DOWN).setType(AIR.toBukkit());
				}
				//
				List<Block> ls = ((List<Block>) ap.getData("tinkBList"));
				if (ls != null)
					ls.remove(eve.getBlock());

				eve.getBlock().setType(AIR.toBukkit());

				bl.removeMetadata("tink", AnnihilationMain.INSTANCE);
				bl.removeMetadata(p.getName(), AnnihilationMain.INSTANCE);
			} else {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (KitUtils.isValidPlayer(all)) {
						if (bl.hasMetadata(all.getName())) {
							eve.setCancelled(true);
							//
							AnniPlayer allP = AnniPlayer.getPlayer(all.getUniqueId());
							//
							if (allP != null && !allP.getTeam().equals(ap.getTeam())) {
								if (bl.getType() != STONE_PLATE.toBukkit()) {
									bl.getRelative(BlockFace.UP).setType(AIR.toBukkit());
									bl.setType(AIR.toBukkit());
								} else {
									bl.setType(AIR.toBukkit());
									bl.getRelative(BlockFace.DOWN).setType(AIR.toBukkit());
								}
								//
								bl.removeMetadata("tink", AnnihilationMain.INSTANCE);
								bl.removeMetadata(p.getName(), AnnihilationMain.INSTANCE);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onM(PlayerMoveEvent eve) {
		if (Game.isNotRunning())
			return;
		if (eve.getTo() == null)
			return;
		//
		final Player p = eve.getPlayer();
		AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		//
		if (ap != null && ap.getTeam() != null) {
			Block down = eve.getTo().getBlock().getRelative(BlockFace.SELF);
			//
			if (!down.hasMetadata("tink"))
				return;
			//
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (KitUtils.isValidPlayer(all)) {
					if (down.hasMetadata(all.getName())) {
						AnniPlayer allP = AnniPlayer.getPlayer(all.getUniqueId());
						//
						if (allP != null && !allP.getTeam().equals(ap.getTeam()))
							return;
					}
				}
			}
			//
			if (!eve.getFrom().getBlock().getRelative(BlockFace.SELF).getLocation().equals(down.getLocation())) {
				if (down.getType() == STONE_PLATE.toBukkit()) {
					Block bl = down.getRelative(BlockFace.DOWN);
					//
					if (bl.getType() != IRON_BLOCK.toBukkit() && bl.getType() != COAL_BLOCK.toBukkit()
							&& bl.getType() != REDSTONE_BLOCK.toBukkit() && bl.getType() != DIAMOND_BLOCK.toBukkit()
							&& bl.getType() != GOLD_BLOCK.toBukkit() && bl.getType() != EMERALD_BLOCK.toBukkit())
						return;

					if (bl.getType() != IRON_BLOCK.toBukkit()) {
						if (VersionUtils.getVersion().contains("v1_7") || VersionUtils.getVersion().contains("v1_8"))
							p.playSound(p.getLocation(), Sound.valueOf("BLAZE_BREATH"), 2.0F, 8.0F);
						else
							p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 2.0F, 8.0F);
					}

					if (bl.getType() == IRON_BLOCK.toBukkit()) {
						p.setVelocity(eve.getTo().getDirection().multiply(4));

						if (pls == null)
							pls = new ArrayList<UUID>();

						if (!pls.contains(p.getUniqueId()))
							pls.add(p.getUniqueId());

						if (VersionUtils.getVersion().contains("v1_7") || VersionUtils.getVersion().contains("v1_8"))
							p.playSound(p.getLocation(), Sound.valueOf("WITHER_SHOOT"), 4.0F, 2.0F);
						else
							p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 4.0F, 2.0F);

					} else if (bl.getType() == COAL_BLOCK.toBukkit()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 45 * 20, 0));
					} else if (bl.getType() == REDSTONE_BLOCK.toBukkit()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45 * 20, 0));
					} else if (bl.getType() == DIAMOND_BLOCK.toBukkit()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45 * 20, 1));
					} else if (bl.getType() == GOLD_BLOCK.toBukkit()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 45 * 20, 1));
					} else if (bl.getType() == EMERALD_BLOCK.toBukkit()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 45 * 20, 0));
					}
				}
			}
		}
	}

	@EventHandler
	public void onD(EntityDamageEvent eve) {
		if (pls == null || pls.isEmpty())
			return;
		//
		if (Game.isNotRunning())
			return;
		if (eve.getCause() != DamageCause.FALL)
			return;
		//
		if (eve.getEntity() instanceof Player) {
			Player p = (Player) eve.getEntity();
			AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
			//
			if (ap != null && ap.getTeam() != null) {
				if (pls.contains(p.getUniqueId())) {
					pls.remove(p.getUniqueId());
					eve.setCancelled(true);
				}
			}
		}
	}

	@Override
	protected void setUp() {
	}

	@Override
	protected String getInternalName() {
		return "Tinkerer";
	}

	@Override
	protected ItemStack getDefaultIcon() {
		return new ItemStack(STONE_PLATE.toBukkit());
	}

	@Override
	protected int setDefaults(ConfigurationSection section) {
		return 0;
	}

	@Override
	protected List<String> getDefaultDescription() {
		List<String> l = new ArrayList<String>();
		addToList(l, aqua + "You are the innovator!.", "", aqua + "The tinkerer has focused",
				aqua + "on improving technology", aqua + "in the battlefield and have",
				aqua + "developed PowerPads that buff", aqua + "them selves or their team when",
				aqua + "they walk over them.");
		return l;
	}

	@Override
	protected Loadout getFinalLoadout() {
		return new Loadout().addStoneSword().addWoodPick().addWoodAxe()
				.addSoulboundItem(new ItemStack(REDSTONE_BLOCK.toBukkit()))
				.addSoulboundItem(new ItemStack(COAL_BLOCK.toBukkit()));
	}

	@Override
	protected void onPlayerRespawn(Player p, AnniPlayer ap) {
	}

	@Override
	public void cleanup(Player p) {
		AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		//
		Object lits = ap.getData("tinkBList");
		if (ap != null && lits != null && lits instanceof List) {
			for (Block b : ((List<Block>) ap.getData("tinkBList"))) {
				if (b != null) {
					b.removeMetadata("tink", AnnihilationMain.INSTANCE);
					b.getLocation().getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
					b.getLocation().getWorld().playEffect(b.getLocation(), Effect.SMOKE, 4);
					b.getRelative(BlockFace.UP).setType(AIR.toBukkit());
					b.setType(AIR.toBukkit());
				}
			}
			//
			try {
				Bukkit.getScheduler().runTaskLater(AnnihilationMain.INSTANCE, new Runnable() {
					@Override
					public void run() {
						((List<Block>) ap.getData("tinkBList")).clear();
						ap.setData("tinkBList", null);
					}
				}, 50);
			} catch (IllegalPluginAccessException e) {
			}
			//
		}
	}

	@Override
	public boolean onItemClick(Inventory paramInventory, AnniPlayer paramAnniPlayer) {
		this.addLoadoutToInventory(paramInventory);
		return true;
	}

	@Override
	protected void loadFromConfig(ConfigurationSection section) {
	}
}
