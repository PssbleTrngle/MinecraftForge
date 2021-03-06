package net.minecraftforge.debug.world;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraftforge.event.world.StructureDataMarkerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod("structure_data_marker_event_test")
@EventBusSubscriber
public class StructureDataMarkerTest
{

    @SubscribeEvent
    public static void onDataMarker(StructureDataMarkerEvent event)
    {
        if (event.getStructurePiece() instanceof EndCityPieces.CityTemplate && event.getFunction().startsWith("Chest"))
        {
            // Prevent generation of loot in end cities
            event.setCanceled(true);
        }
        else if (event.getStructurePiece() instanceof WoodlandMansionPieces.MansionTemplate && event.getFunction().equals("Warrior"))
        {
            // Replace naturally generated vindicators in mansion with wither skeletons
            event.setCanceled(true);
            WitherSkeletonEntity warrior = EntityType.WITHER_SKELETON.create(event.getWorld().getWorld());
            warrior.enablePersistence();
            warrior.moveToBlockPosAndAngles(event.getPos(), 0.0F, 0.0F);
            warrior.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(warrior.func_233580_cy_()), SpawnReason.STRUCTURE, null, null);
            event.getWorld().addEntity(warrior);
        }
    }

}
