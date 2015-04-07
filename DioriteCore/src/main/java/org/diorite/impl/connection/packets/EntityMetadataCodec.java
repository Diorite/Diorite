package org.diorite.impl.connection.packets;

import java.util.ArrayList;
import java.util.List;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.BlockLocation;
import org.diorite.ImmutableLocation;

public final class EntityMetadataCodec // TODO DataWatcher and other...
{
    private EntityMetadataCodec()
    {
    }

    public static void encode(PacketDataSerializer data, EntityMetadataObject metadataObject)
    {
        int i = (metadataObject.getDataType().getId() << 5 | metadataObject.getIndex() & 0x1F) & 0xFF;

        data.writeByte(i);
        switch (metadataObject.getDataType().getId())
        {
            case 0:
                data.writeByte(((Number) metadataObject.getData()).byteValue());
                break;
            case 1:
                data.writeShort(((Number)metadataObject.getData()).shortValue());
                break;
            case 2:
                data.writeInt(((Number)metadataObject.getData()).intValue());
                break;
            case 3:
                data.writeFloat(((Number)metadataObject.getData()).floatValue());
                break;
            case 4:
                data.writeText((String)metadataObject.getData());
                break;
            case 5:
                ItemStackImpl itemstack = (ItemStackImpl)metadataObject.getData();

                data.writeItemStack(itemstack);
                break;
            case 6:
                BlockLocation blockposition = (BlockLocation)metadataObject.getData();

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
        List<EntityMetadataObject> temp = new ArrayList<>(5);

        for (byte b = data.readByte(); b != 127; b = data.readByte())
        {
            int type = (b & 0xE0) >> 5;
            int index = b & 0x1F;

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
                    int x = data.readInt();
                    int y = data.readInt();
                    int z = data.readInt();

                    object = new EntityMetadataObject(DataType.LOCATION, index, new ImmutableLocation(x, y, z));
                    break;
                case 7:
                    float f = data.readFloat();
                    float f1 = data.readFloat();
                    float f2 = data.readFloat();

                    //object = new EntityMetadataObject(DataType.POSITION, index, new Vector3f(f, f1, f2)); // TODO
                    break;
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
        STRING(4), // UTF-8 String (VarInt prefixed)
        SLOT(5),
        LOCATION(6), // Int, Int, Int (x, y, z)
        POSITION(7); // Float, Float, Float (pitch, yaw, roll)

        private int id;

        DataType(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        public static DataType byId(int id)
        {
            for(DataType dp : DataType.values())
            {
                if(dp.getId() == id)
                {
                    return dp;
                }
            }
            return null;
        }
    }
}
