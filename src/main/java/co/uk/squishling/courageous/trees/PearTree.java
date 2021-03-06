package co.uk.squishling.courageous.trees;

import co.uk.squishling.courageous.world.gen.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class PearTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return ModFeatures.PEAR_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG);
    }
}
