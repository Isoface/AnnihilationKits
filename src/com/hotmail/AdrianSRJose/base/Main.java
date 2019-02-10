package com.hotmail.AdrianSRJose.base;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
//	@Override
//	public void onEnable() {
//		try {
//			// Get And Check File
//			File kits = getFile();
//			if (kits == null) {
//				return;
//			}
//
//			File kitsFolder = new File(new File(getDataFolder().getParent(), "Annihilation"), "Kits");
//			if (!kitsFolder.exists()) {
//				kitsFolder.mkdir();
//			}
//
//			// Check is directory
//			if (!kitsFolder.isDirectory()) {
//				return;
//			}
//
//			// Remove Kits File from the folder "Annihilation/Kits"
//			// Check rem File
//			File verify = new File(kitsFolder, kits.getName());
//			if (verify.exists()) {
//				if (remFile(verify)) {
//					verify.delete();
//				} else
//					return;
//			}
//
//			// Copy to the folder "Annihilation/Kits"
//			if (kits != null && kits.exists()) {
//				try {
//					FileUtils.copyFileToDirectory(kits, kitsFolder);
//				} catch (Throwable ParamException) {
//					// Ignore
//				}
//			}
//
//			// Disable This Plugin
//			Bukkit.getPluginManager().disablePlugin(this);
//			setEnabled(false);
//
//		} catch (Throwable ParamException) {
//			// Ignore
//		}
//	}

//	private boolean remFile(final File ikf) {
//		if (ikf != null) {
//			final String ver = getVersion(ikf);
//			if (ver == null) {
//				return true;
//			}
//
//			final String[] arr1 = ver.replace(".", "%n%").split("%n%");
//			final String   tver = getVersion();
//			final String[] arr2 = tver.replace(".", "%n%").split("%n%");
//			if (arr2.length >= 3 && arr1.length >= 3) {
//				try {
//					int dif = 0;
//					for (int x = 0; x < arr1.length; x++) {
//						Integer i = Integer.valueOf(arr1[x]);
//						Integer o = Integer.valueOf(arr2[x]);
//						if (i.intValue() < o.intValue()) {
//							dif++;
//						}
//					}
//
//					if (dif > 0) {
//						return true;
//					}
//				} catch (Throwable t) {
//					return true;
//				}
//			} else
//				return true;
//		}
//		return false;
//	}

//	@SuppressWarnings("deprecation")
//	private String getVersion(File jar) {
//		try {
//			ZipFile zipFile = new ZipFile(jar);
//			Enumeration<? extends ZipEntry> entries = zipFile.entries();
//			while (entries.hasMoreElements()) {
//				ZipEntry entry = entries.nextElement();
//				if (entry.getName().equals("plugin.yml")) {
//					InputStream stream = zipFile.getInputStream(entry);
//					YamlConfiguration yml = YamlConfiguration.loadConfiguration(stream);
//					String ver = yml.getString("version");
//					zipFile.close();
//					return ver;
//				}
//			}
//
//			// Close
//			zipFile.close();
//		} catch (Throwable t) {
//			return null;
//		}
//		return null;
//	}
//
//	private String getVersion() {
//		return getDescription().getVersion();
//	}
}
