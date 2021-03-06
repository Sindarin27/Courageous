package co.uk.squishling.courageous.items;

import co.uk.squishling.courageous.util.Reference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.Food.Builder;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class CustomFood extends Item {

    private SoundEvent sound = SoundEvents.ENTITY_GENERIC_EAT;
    private UseAction action = UseAction.EAT;

    public CustomFood(String name, Food food, ItemGroup group) {
        super(new Item.Properties().group(group).food(food));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS_ARRAY.add(this);
    }

    public CustomFood(String name, int hunger, float saturation, ItemGroup group) {
        this(name, new Builder().hunger(hunger).saturation(saturation).build(), group);
    }

    public CustomFood(String name, Food food, Item containerItem, ItemGroup group) {
        super(new Item.Properties().group(group).food(food).containerItem(containerItem));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS_ARRAY.add(this);
    }

    public Item sound(SoundEvent sound) {
        this.sound = sound;
        return this;
    }

    public Item drink() {
        this.sound = SoundEvents.ENTITY_GENERIC_DRINK;
        this.action = UseAction.DRINK;
        return this;
    }

    @Override
    public UseAction getUseAction(ItemStack p_77661_1_) {
        return action;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (Reference.isServer(world)) {
            entity.playSound(sound, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            world.playSound((PlayerEntity)null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), sound, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            stack.shrink(1);

            if (entity instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity)entity);
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + getFood().getHealing());
                player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + getFood().getSaturation());

                if (getContainerItem() != null && !player.inventory.addItemStackToInventory(new ItemStack(getContainerItem()))) {
                    world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosX(), new ItemStack(getContainerItem())));
                }
            }

            for(Pair<EffectInstance, Float> pair : this.getFood().getEffects()) {
                if (pair.getLeft() != null) entity.addPotionEffect(pair.getLeft());
            }
        }

        return stack;
    }

}
