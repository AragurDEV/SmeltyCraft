package net.aragurlp.smeltycraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.aragurlp.smeltycraft.tile.TileSmeltingFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerSmeltingFurnace extends Container
{
    private TileSmeltingFurnace smeltingFurnace;
    /** The time this furnace will continue for (fuel) */
    public int lastBurnTime;
    /** The start time for this fuel */
    public int lastCurrentItemBurnTime;
    /** How much time left before cooked */
    public int lastCookTime;

    public ContainerSmeltingFurnace(InventoryPlayer inventory, TileSmeltingFurnace tileentity)
    {
        this.smeltingFurnace = tileentity;

        this.addSlotToContainer(new Slot(tileentity, 0, 56, 17));
        this.addSlotToContainer(new Slot(tileentity, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(inventory.player, tileentity, 2, 116, 35));

        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                this.addSlotToContainer(new Slot(inventory, j + i*9 + 9, 8+ j*18,  84 + i*18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting iCrafting)
    {
        super.addCraftingToCrafters(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, this.smeltingFurnace.cookTime);
        iCrafting.sendProgressBarUpdate(this, 1, this.smeltingFurnace.burnTime);
        iCrafting.sendProgressBarUpdate(this, 2, this.smeltingFurnace.currentItemBurnTime);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot, int newValue)
    {
        if(slot == 0) this.smeltingFurnace.cookTime = newValue;
        if(slot == 1) this.smeltingFurnace.burnTime = newValue;
        if(slot == 2) this.smeltingFurnace.currentItemBurnTime = newValue;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.smeltingFurnace.isUseableByPlayer(player);
    }
}
