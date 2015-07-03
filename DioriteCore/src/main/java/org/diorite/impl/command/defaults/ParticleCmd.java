package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Particle;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;
import org.diorite.utils.math.DioriteMathUtils;

public class ParticleCmd extends SystemCommandImpl
{
    public ParticleCmd()
    {
        super("particle", Pattern.compile("(particle)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (args.length() < 10)
            {
                sender.sendMessage("&cUsage: /particle <id or name> <x> <y> <z> <offsetX> <offsetY> <offsetZ> <particleData> <count> [<data...>]");
                return;
            }

            final String particles = args.asString(0);
            final Integer particleID = DioriteMathUtils.asInt(particles);
            final Particle particle = (particleID == null) ? Particle.getByParticleName(particles.toUpperCase()) : Particle.getByParticleId(particleID);
            if (particle == null)
            {
                sender.sendSimpleColoredMessage("&cSorry, this particle isn't found (" + particles + ")");
                return;
            }

            final boolean isLongDistance = args.asBoolean(1);
            final float x = args.asFloat(2);
            final float y = args.asFloat(3);
            final float z = args.asFloat(4);
            final float offsetX = args.asFloat(5);
            final float offsetY = args.asFloat(6);
            final float offsetZ = args.asFloat(7);
            final float particleData = args.asFloat(8);
            final int particleCount = args.asInt(9);
            int[] data = new int[particle.getDataSize()];
            for (int i = 0; i < particle.getDataSize(); i++)
            {
                data[i] = args.asInt(10 + i);
            }

            ((Player) sender).showParticle(particle, isLongDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data);
            sender.sendSimpleColoredMessage("&aParticle " + particle.name() + " (" + particle.ordinal() + "/" + particle.getParticleName() + ") has been created!");
        });
    }
}
