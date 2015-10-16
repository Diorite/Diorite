/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
