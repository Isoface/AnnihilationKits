The Source Code of the Annihilation Kits. Using this Source Code you can create new kits.

### How to create a kit:
1. To start, just create a new project with the source code of the kits, this project must to have the file "Annihilation.jar" library.
2. Now you have to create a new class, that class must to extends any of these classes: "ConfigurableKit", "ClassItemKit" or "ProlongedDelayKit". You can use as a guide any of the kits in the source code.
3.* Now you have to set up some methods:
    - * Method "getInternalName": This method must to return the internal name of the kit.
    - * Method: "getDefaultIcon": This method must to return an ItemStack that will be displayed as icon in the Kits Menu.
    - * Method: "getFinalLoadout": This method must to return the Loadout player will be using this kit. Example:
        protected Loadout getFinalLoadout() {
            return new Loadout().addWoodSword().addWoodPick().addWoodAxe().addBow().addSoulboundItem(new ItemStack(Material.ARROW, 10));
        }
4. Now just compile the project and drop it inside the folder "plugins/Annihilation/Kits".
