package com.hotmail.AdrianSRJose.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.hotmail.AdrianSRJose.AnniPro.anniGame.AnniPlayer;
import com.hotmail.AdrianSRJose.AnniPro.anniGame.Game;
import com.hotmail.AdrianSRJose.AnniPro.kits.Loadout;
import com.hotmail.AdrianSRJose.AnniPro.main.AnnihilationMain;
import com.hotmail.AdrianSRJose.AnniPro.utils.CompatibleUtils;
import com.hotmail.AdrianSRJose.AnniPro.utils.Material;
import com.hotmail.AdrianSRJose.AnniPro.utils.Util;
import com.hotmail.AdrianSRJose.AnniPro.utils.VersionUtils;
import com.hotmail.AdrianSRJose.base.ConfigurableKit;

public class Farmer extends ConfigurableKit {
	
//	private static Material CROPS = Material.CROPS.toBukkit();
	private static Material SOIL  = Material.SOIL;
	
	private static final Map<Location, String> datas = new HashMap<Location, String>();
	private static String getData(final Location key) {
		return key != null ? datas.get(key) : null;
	}

	private static void setData(final Location key, final String value) {
		datas.put(key, value);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent eve) {
		final Player p = eve.getPlayer();
		final AnniPlayer ap = AnniPlayer.getPlayer(p);
		final Block b = eve.getBlock();
		final ItemStack ih = p.getItemInHand();
		//
		if (Game.isGameRunning() && p != null && ap != null && this.hasThisKit(ap) && b != null
				&& b.getLocation() != null && b.getType().name().equals("CROPS") && ih != null) {
			if (ih.getType().name().contains("_HOE") || ih.getType().equals(Material.AIR.toBukkit())) {
				final byte data = b.getData();
				final Location lock = b.getLocation();
				String bd = getData(lock);
				if (bd == null) {
					setData(lock, "used");
					b.setType(org.bukkit.Material.valueOf("CROPS"));
					//
					if (data == 7) {
						
//						lock.getBlock().setData((byte) 0);
						CompatibleUtils.setData(lock.getBlock(), (byte) 0);
						
						set(lock, (byte) 1);
						specialDrop(p, lock);
					} else
						set(lock, (byte) (data + (byte) 1));
				} else
					eve.setCancelled(true);
			}
		}
	}

	private void specialDrop(final Player p, final Location broken) {
		if (!(p != null && Util.isValidLoc(p.getLocation()) && Util.isValidLoc(broken)))
			return;
		//
		final Random r = new Random();
		int Randomr = r.nextInt(52);
		//
		Material drop = Material.BONE;
		//
		switch (Randomr) {
		case 40:
			drop = Material.APPLE;
			break;
		case 1:
			drop = Material.SEEDS;
			break;
		case 2:
			drop = Material.GOLD_ORE;
			break;
		case 3:
			drop = Material.BONE;
			break;
		case 4:
			drop = Material.SEEDS;
			break;
		case 5:
			drop = Material.IRON_ORE;
			break;
		case 6:
			drop = Material.BONE;
			break;
		case 7:
			drop = Material.SEEDS;
			break;
		case 8:
			drop = Material.BONE;
			break;
		case 9:
			drop = Material.NETHER_WARTS;
			break;
		case 10:
			drop = Material.BONE;
			break;
		case 11:
			drop = Material.SEEDS;
			break;
		case 12:
			drop = Material.GOLD_NUGGET;
			break;
		case 13:
			drop = Material.GOLD_NUGGET;
			break;
		case 14:
			drop = Material.GOLD_ORE;
			break;
		case 15:
			drop = Material.WHEAT;
			break;
		case 16:
			drop = Material.NETHER_WARTS;
			break;
		case 17:
			drop = Material.GOLD_NUGGET;
			break;
		case 18:
			drop = Material.WHEAT;
			break;
		case 19:
			drop = Material.BONE;
			break;
		case 20:
			drop = Material.NETHER_WARTS;
			break;
		case 21:
			drop = Material.COAL;
			break;
		case 22:
			drop = Material.IRON_ORE;
			break;
		case 23:
			drop = Material.WHEAT;
			break;
		case 24:
			drop = Material.GOLD_NUGGET;
			break;
		case 25:
			drop = Material.COAL;
			break;
		case 26:
			drop = Material.NETHER_WARTS;
			break;
		case 27:
			drop = Material.WHEAT;
			break;
		case 28:
			drop = Material.IRON_ORE;
			break;
		case 29:
			drop = Material.COAL;
			break;
		case 30:
			drop = Material.BONE;
			break;
		case 31:
			drop = Material.COAL;
			break;
		case 32:
			drop = Material.GOLD_NUGGET;
			break;
		case 33:
			drop = Material.COAL;
			break;
		case 34:
			drop = Material.NETHER_WARTS;
			break;
		case 35:
			drop = Material.WHEAT;
			break;
		case 36:
			drop = Material.IRON_ORE;
			break;
		case 37:
			drop = Material.COAL;
			break;
		case 38:
			drop = Material.WHEAT;
			break;
		case 39:
			drop = Material.GOLD_ORE;
			break;
		case 41:
			drop = Material.NETHER_WARTS;
			break;
		case 42:
			drop = Material.GOLD_ORE;
			break;
		case 43:
			drop = Material.WHEAT;
			break;
		case 44:
			drop = Material.GOLD_ORE;
			break;
		case 45:
			drop = Material.APPLE;
			break;
		case 46:
			drop = Material.WHEAT;
			break;
		case 47:
			drop = Material.NETHER_WARTS;
			break;
		case 48:
			drop = Material.SEEDS;
			break;
		case 49:
			drop = Material.GHAST_TEAR;
			break;
		case 50:
			drop = Material.IRON_ORE;
			break;
		case 51:
			drop = Material.GHAST_TEAR;
			break;
		}
		//
		p.getWorld().dropItem(broken, new ItemStack(drop.toBukkit()));
	}

	@EventHandler
	public void onPl(BlockPlaceEvent eve) {
		final Player p = eve.getPlayer();
		final AnniPlayer ap = AnniPlayer.getPlayer(p);
		final Block b = eve.getBlock();
		final Location lock = b.getLocation();
		if (Game.isGameRunning() && ap != null && ap.isOnline() && b != null && lock != null
				&& b.getType().name().equals("CROPS")) {
			if (ap.hasTeam() && this.hasThisKit(ap)) {
				setData(lock, "used");
				set(lock, (byte) 2);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent eve) {
		final Player p = eve.getPlayer();
		final AnniPlayer ap = AnniPlayer.getPlayer(p.getUniqueId());
		//
		if (Game.isGameRunning() && ap != null && ap.getTeam() != null && this.hasThisKit(ap)) {
			final Block down = eve.getTo().getBlock().getRelative(BlockFace.DOWN);
			final Block from = eve.getFrom().getBlock().getRelative(BlockFace.DOWN);
			//
			if (down != null && down.getType() == SOIL.toBukkit()) {
				down.setType(SOIL.toBukkit());
				
//				down.setData((byte) 0);
				CompatibleUtils.setData(down, (byte) 0);
			}
			//
			if (from != null && from.getType() == SOIL.toBukkit()) {
				from.setType(SOIL.toBukkit());
				
//				from.setData((byte) 0);
				CompatibleUtils.setData(from, (byte) 0);
			}
		}
	}

	private void set(Location block, byte newData) {
		if (block != null && getData(block) != null) {
			if (newData <= (byte) 7) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(AnnihilationMain.INSTANCE, new Runnable() {
					@Override
					public void run() {
						final Block b = block.getBlock();
						if (b != null) {
							if (!b.getType().name().equals("CROPS") && VersionUtils.getIntVersion() <= 12)
								b.setType(org.bukkit.Material.valueOf("CROPS"));
							//
							
//							b.setData(newData);
							CompatibleUtils.setData(b, newData);
							
							set(b.getLocation(), (byte) (newData + (byte) 1));
						}
					}
				}, getDlayFromData(newData));
			} else if (newData > (byte) 7) {
				setData(block, null);
			}
		}
	}

	private int getDlayFromData(byte data) {
		switch (data) {
		case 1:
			return 50;
		case 2:
			return 110;
		case 3:
			return 210;
		case 4:
			return 270;
		case 5:
			return 270;
		case 6:
			return 270;
		case 7:
			return 270;
		}
		//
		return 40;
	}

	@Override
	protected void setUp() {
	}

	@Override
	protected String getInternalName() {
		return "Farmer";
	}

	@Override
	protected ItemStack getDefaultIcon() {
		return new ItemStack(Material.SEEDS.toBukkit());
	}

	@Override
	protected int setDefaults(ConfigurationSection section) {
		return 0;
	}

	@Override
	protected void loadFromConfig(ConfigurationSection section) {
	}

	@Override
	protected List<String> getDefaultDescription() {
		List<String> l = new ArrayList<>();

		addToList(l, aqua + "You are the supplier.", "", aqua + "Keep your team fed", aqua + "and at maximum health.",
				"", aqua + "Your Feast ability instantly", aqua + "replenishes the hunger",
				aqua + "of any allies near you.", aqua + "", aqua + "You also find items",
				aqua + "while breaking grass.");
		return l;
	}

	@Override
	protected Loadout getFinalLoadout() {
		return new Loadout().addGoldSword().addWoodPick().addWoodAxe()
				.addSoulboundItem(new ItemStack(Material.GOLD_HOE.toBukkit()))
				.addSoulboundItem(new ItemStack(Material.BONE.toBukkit(), 12));
	}

	@Override
	protected void onPlayerRespawn(Player p, AnniPlayer ap) {
	}

	@Override
	public void cleanup(Player arg0) {
	}

	@Override
	public boolean onItemClick(Inventory inv, AnniPlayer ap) {
		this.addLoadoutToInventory(inv);
		return true;
	}
}
