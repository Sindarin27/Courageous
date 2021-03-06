package co.uk.squishling.courageous.util.networking;

import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelContainer;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PacketPotterySelect implements IPacket {

    private final int index;

    public PacketPotterySelect(int index) {
        this.index = index;
    }

    public PacketPotterySelect(PacketBuffer buffer) {
        index = buffer.readInt();
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(index);
    }

    @Override
    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            World world = player.world;
            PotteryWheelTileEntity te = (PotteryWheelTileEntity) world.getTileEntity(((PotteryWheelContainer) player.openContainer).tileEntity.getPos());

            te.select(index);

            world.notifyBlockUpdate(te.getPos(), te.getBlockState(), te.getBlockState(), 0);
            context.get().setPacketHandled(true);
        });
    }

}
