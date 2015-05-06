package org.diorite.impl.connection.packets;

import java.util.ArrayList;
import java.util.List;

import org.diorite.BlockLocation;
import org.diorite.ImmutableLocation;
import org.diorite.inventory.item.ItemStack;

@SuppressWarnings("MagicNumber")
public final class EntityMetadataCodec // TODO DataWatcher and other...
{
    private EntityMetadataCodec()
    {
    }

    public static void encode(final PacketDataSerializer data, final EntityMetadataObject metadataObject)
    {
        final int i = ((metadataObject.getDataType().getId() << 5) | (metadataObject.getIndex() & 0x1F)) & 0xFF;

        data.writeByte(i);
        switch (metadataObject.getDataType().getId())
        {
            case 0:
                data.writeByte(((Number) metadataObject.getData()).byteValue());
                break;
            case 1:
                data.writeShort(((Number) metadataObject.getData()).shortValue());
                break;
            case 2:
                data.writeInt(((Number) metadataObject.getData()).intValue());
                break;
            case 3:
                data.writeFloat(((Number) metadataObject.getData()).floatValue());
                break;
            case 4:
                data.writeText((String) metadataObject.getData());
                break;
            case 5:
                final ItemStack itemstack = (ItemStack) metadataObject.getData();
                data.writeItemStack(itemstack);
                break;
            case 6:
                final BlockLocation blockposition = (BlockLocation) metadataObject.getData();

                data.writeInt(blockposition.getX());
                data.writeInt(blockposition.getY());
                data.writeInt(blockposition.getZ());
                break;
            case 7:
                //Vector3f vector3f = (Vector3f)watchableobject.b();

                //packetdataserializer.writeFloat(vector3f.getX());
                //packetdataserializer.writeFloat(vector3f.getY());
                //packetdataserializer.writeFloat(vector3f.getZ()); // TODO
        }
    }

    public static List<EntityMetadataObject> decode(final PacketDataSerializer data)
    {
        final List<EntityMetadataObject> temp = new ArrayList<>(5);

        for (byte b = data.readByte(); b != 127; b = data.readByte())
        {
            final int type = (b & 0xE0) >> 5;
            final int index = b & 0x1F;

            EntityMetadataObject object = null;
            switch (type)
            {
                case 0:
                    object = new EntityMetadataObject(DataType.BYTE, index, data.readByte());
                    break;
                case 1:
                    object = new EntityMetadataObject(DataType.SHORT, index, data.readShort());
                    break;
                case 2:
                    object = new EntityMetadataObject(DataType.INT, index, data.readInt());
                    break;
                case 3:
                    object = new EntityMetadataObject(DataType.FLOAT, index, data.readFloat());
                    break;
                case 4:
                    object = new EntityMetadataObject(DataType.STRING, index, data.readText(32767));
                    break;
                case 5:
                    object = new EntityMetadataObject(DataType.SLOT, index, data.readItemStack());
                    break;
                case 6:
                    final int x = data.readInt();
                    final int y = data.readInt();
                    final int z = data.readInt();

                    object = new EntityMetadataObject(DataType.LOCATION, index, new ImmutableLocation(x, y, z));
                    break;
                case 7:
                    final float f = data.readFloat();
                    final float f1 = data.readFloat();
                    final float f2 = data.readFloat();

                    //object = new EntityMetadataObject(DataType.POSITION, index, new Vector3f(f, f1, f2)); // TODO
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity metadata type: " + type);
            }
            temp.add(object);
        }

        return temp;
    }

    public enum DataType
    {
        BYTE(0),
        SHORT(1),
        INT(2),
        FLOAT(3),
        STRING(4),
        // UTF-8 String (VarInt prefixed)
        SLOT(5),
        LOCATION(6),
        // Int, Int, Int (x, y, z)
        POSITION(7); // Float, Float, Float (pitch, yaw, roll)

        private final int id;

        DataType(final int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }
    }
}
