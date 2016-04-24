/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class Sound extends ASimpleEnum<Sound>
{
    static
    {
        //noinspection MagicNumber
        init(Sound.class, 430);
    }

    public static final Sound AMBIENT_CAVE                        = new Sound("AMBIENT_CAVE", "ambient.cave", 0);
    public static final Sound BLOCK_ANVIL_BREAK                   = new Sound("BLOCK_ANVIL_BREAK", "block.anvil.break", 1);
    public static final Sound BLOCK_ANVIL_DESTROY                 = new Sound("BLOCK_ANVIL_DESTROY", "block.anvil.destroy", 2);
    public static final Sound BLOCK_ANVIL_FALL                    = new Sound("BLOCK_ANVIL_FALL", "block.anvil.fall", 3);
    public static final Sound BLOCK_ANVIL_HIT                     = new Sound("BLOCK_ANVIL_HIT", "block.anvil.hit", 4);
    public static final Sound BLOCK_ANVIL_LAND                    = new Sound("BLOCK_ANVIL_LAND", "block.anvil.land", 5);
    public static final Sound BLOCK_ANVIL_PLACE                   = new Sound("BLOCK_ANVIL_PLACE", "block.anvil.place", 6);
    public static final Sound BLOCK_ANVIL_STEP                    = new Sound("BLOCK_ANVIL_STEP", "block.anvil.step", 7);
    public static final Sound BLOCK_ANVIL_USE                     = new Sound("BLOCK_ANVIL_USE", "block.anvil.use", 8);
    public static final Sound BLOCK_BREWING_STAND_BREW            = new Sound("BLOCK_BREWING_STAND_BREW", "block.brewing_stand.brew", 9);
    public static final Sound BLOCK_CHEST_CLOSE                   = new Sound("BLOCK_CHEST_CLOSE", "block.chest.close", 10);
    public static final Sound BLOCK_CHEST_LOCKED                  = new Sound("BLOCK_CHEST_LOCKED", "block.chest.locked", 11);
    public static final Sound BLOCK_CHEST_OPEN                    = new Sound("BLOCK_CHEST_OPEN", "block.chest.open", 12);
    public static final Sound BLOCK_CHORUS_FLOWER_DEATH           = new Sound("BLOCK_CHORUS_FLOWER_DEATH", "block.chorus_flower.death", 13);
    public static final Sound BLOCK_CHORUS_FLOWER_GROW            = new Sound("BLOCK_CHORUS_FLOWER_GROW", "block.chorus_flower.grow", 14);
    public static final Sound BLOCK_CLOTH_BREAK                   = new Sound("BLOCK_CLOTH_BREAK", "block.cloth.break", 15);
    public static final Sound BLOCK_CLOTH_FALL                    = new Sound("BLOCK_CLOTH_FALL", "block.cloth.fall", 16);
    public static final Sound BLOCK_CLOTH_HIT                     = new Sound("BLOCK_CLOTH_HIT", "block.cloth.hit", 17);
    public static final Sound BLOCK_CLOTH_PLACE                   = new Sound("BLOCK_CLOTH_PLACE", "block.cloth.place", 18);
    public static final Sound BLOCK_CLOTH_STEP                    = new Sound("BLOCK_CLOTH_STEP", "block.cloth.step", 19);
    public static final Sound BLOCK_COMPARATOR_CLICK              = new Sound("BLOCK_COMPARATOR_CLICK", "block.comparator.click", 20);
    public static final Sound BLOCK_DISPENSER_DISPENSE            = new Sound("BLOCK_DISPENSER_DISPENSE", "block.dispenser.dispense", 21);
    public static final Sound BLOCK_DISPENSER_FAIL                = new Sound("BLOCK_DISPENSER_FAIL", "block.dispenser.fail", 22);
    public static final Sound BLOCK_DISPENSER_LAUNCH              = new Sound("BLOCK_DISPENSER_LAUNCH", "block.dispenser.launch", 23);
    public static final Sound BLOCK_END_GATEWAY_SPAWN             = new Sound("BLOCK_END_GATEWAY_SPAWN", "block.end_gateway.spawn", 24);
    public static final Sound BLOCK_ENDERCHEST_CLOSE              = new Sound("BLOCK_ENDERCHEST_CLOSE", "block.enderchest.close", 25);
    public static final Sound BLOCK_ENDERCHEST_OPEN               = new Sound("BLOCK_ENDERCHEST_OPEN", "block.enderchest.open", 26);
    public static final Sound BLOCK_FENCE_GATE_CLOSE              = new Sound("BLOCK_FENCE_GATE_CLOSE", "block.fence_gate.close", 27);
    public static final Sound BLOCK_FENCE_GATE_OPEN               = new Sound("BLOCK_FENCE_GATE_OPEN", "block.fence_gate.open", 28);
    public static final Sound BLOCK_FIRE_AMBIENT                  = new Sound("BLOCK_FIRE_AMBIENT", "block.fire.ambient", 29);
    public static final Sound BLOCK_FIRE_EXTINGUISH               = new Sound("BLOCK_FIRE_EXTINGUISH", "block.fire.extinguish", 30);
    public static final Sound BLOCK_FURNACE_FIRE_CRACKLE          = new Sound("BLOCK_FURNACE_FIRE_CRACKLE", "block.furnace.fire_crackle", 31);
    public static final Sound BLOCK_GLASS_BREAK                   = new Sound("BLOCK_GLASS_BREAK", "block.glass.break", 32);
    public static final Sound BLOCK_GLASS_FALL                    = new Sound("BLOCK_GLASS_FALL", "block.glass.fall", 33);
    public static final Sound BLOCK_GLASS_HIT                     = new Sound("BLOCK_GLASS_HIT", "block.glass.hit", 34);
    public static final Sound BLOCK_GLASS_PLACE                   = new Sound("BLOCK_GLASS_PLACE", "block.glass.place", 35);
    public static final Sound BLOCK_GLASS_STEP                    = new Sound("BLOCK_GLASS_STEP", "block.glass.step", 36);
    public static final Sound BLOCK_GRASS_BREAK                   = new Sound("BLOCK_GRASS_BREAK", "block.grass.break", 37);
    public static final Sound BLOCK_GRASS_FALL                    = new Sound("BLOCK_GRASS_FALL", "block.grass.fall", 38);
    public static final Sound BLOCK_GRASS_HIT                     = new Sound("BLOCK_GRASS_HIT", "block.grass.hit", 39);
    public static final Sound BLOCK_GRASS_PLACE                   = new Sound("BLOCK_GRASS_PLACE", "block.grass.place", 40);
    public static final Sound BLOCK_GRASS_STEP                    = new Sound("BLOCK_GRASS_STEP", "block.grass.step", 41);
    public static final Sound BLOCK_GRAVEL_BREAK                  = new Sound("BLOCK_GRAVEL_BREAK", "block.gravel.break", 42);
    public static final Sound BLOCK_GRAVEL_FALL                   = new Sound("BLOCK_GRAVEL_FALL", "block.gravel.fall", 43);
    public static final Sound BLOCK_GRAVEL_HIT                    = new Sound("BLOCK_GRAVEL_HIT", "block.gravel.hit", 44);
    public static final Sound BLOCK_GRAVEL_PLACE                  = new Sound("BLOCK_GRAVEL_PLACE", "block.gravel.place", 45);
    public static final Sound BLOCK_GRAVEL_STEP                   = new Sound("BLOCK_GRAVEL_STEP", "block.gravel.step", 46);
    public static final Sound BLOCK_IRON_DOOR_CLOSE               = new Sound("BLOCK_IRON_DOOR_CLOSE", "block.iron_door.close", 47);
    public static final Sound BLOCK_IRON_DOOR_OPEN                = new Sound("BLOCK_IRON_DOOR_OPEN", "block.iron_door.open", 48);
    public static final Sound BLOCK_LADDER_BREAK                  = new Sound("BLOCK_LADDER_BREAK", "block.ladder.break", 49);
    public static final Sound BLOCK_LADDER_FALL                   = new Sound("BLOCK_LADDER_FALL", "block.ladder.fall", 50);
    public static final Sound BLOCK_LADDER_HIT                    = new Sound("BLOCK_LADDER_HIT", "block.ladder.hit", 51);
    public static final Sound BLOCK_LADDER_PLACE                  = new Sound("BLOCK_LADDER_PLACE", "block.ladder.place", 52);
    public static final Sound BLOCK_LADDER_STEP                   = new Sound("BLOCK_LADDER_STEP", "block.ladder.step", 53);
    public static final Sound BLOCK_LAVA_AMBIENT                  = new Sound("BLOCK_LAVA_AMBIENT", "block.lava.ambient", 54);
    public static final Sound BLOCK_LAVA_EXTINGUISH               = new Sound("BLOCK_LAVA_EXTINGUISH", "block.lava.extinguish", 55);
    public static final Sound BLOCK_LAVA_POP                      = new Sound("BLOCK_LAVA_POP", "block.lava.pop", 56);
    public static final Sound BLOCK_LEVER_CLICK                   = new Sound("BLOCK_LEVER_CLICK", "block.lever.click", 57);
    public static final Sound BLOCK_METAL_BREAK                   = new Sound("BLOCK_METAL_BREAK", "block.metal.break", 58);
    public static final Sound BLOCK_METAL_FALL                    = new Sound("BLOCK_METAL_FALL", "block.metal.fall", 59);
    public static final Sound BLOCK_METAL_HIT                     = new Sound("BLOCK_METAL_HIT", "block.metal.hit", 60);
    public static final Sound BLOCK_METAL_PLACE                   = new Sound("BLOCK_METAL_PLACE", "block.metal.place", 61);
    public static final Sound BLOCK_METAL_STEP                    = new Sound("BLOCK_METAL_STEP", "block.metal.step", 62);
    public static final Sound BLOCK_METAL_PRESSUREPLATE_CLICK_OFF = new Sound("BLOCK_METAL_PRESSUREPLATE_CLICK_OFF", "block.metal_pressureplate.click_off", 63);
    public static final Sound BLOCK_METAL_PRESSUREPLATE_CLICK_ON  = new Sound("BLOCK_METAL_PRESSUREPLATE_CLICK_ON", "block.metal_pressureplate.click_on", 64);
    public static final Sound BLOCK_NOTE_BASEDRUM                 = new Sound("BLOCK_NOTE_BASEDRUM", "block.note.basedrum", 65);
    public static final Sound BLOCK_NOTE_BASS                     = new Sound("BLOCK_NOTE_BASS", "block.note.bass", 66);
    public static final Sound BLOCK_NOTE_HARP                     = new Sound("BLOCK_NOTE_HARP", "block.note.harp", 67);
    public static final Sound BLOCK_NOTE_HAT                      = new Sound("BLOCK_NOTE_HAT", "block.note.hat", 68);
    public static final Sound BLOCK_NOTE_SNARE                    = new Sound("BLOCK_NOTE_SNARE", "block.note.snare", 69);
    public static final Sound BLOCK_PISTON_CONTRACT               = new Sound("BLOCK_PISTON_CONTRACT", "block.piston.contract", 70);
    public static final Sound BLOCK_PISTON_EXTEND                 = new Sound("BLOCK_PISTON_EXTEND", "block.piston.extend", 71);
    public static final Sound BLOCK_PORTAL_AMBIENT                = new Sound("BLOCK_PORTAL_AMBIENT", "block.portal.ambient", 72);
    public static final Sound BLOCK_PORTAL_TRAVEL                 = new Sound("BLOCK_PORTAL_TRAVEL", "block.portal.travel", 73);
    public static final Sound BLOCK_PORTAL_TRIGGER                = new Sound("BLOCK_PORTAL_TRIGGER", "block.portal.trigger", 74);
    public static final Sound BLOCK_REDSTONE_TORCH_BURNOUT        = new Sound("BLOCK_REDSTONE_TORCH_BURNOUT", "block.redstone_torch.burnout", 75);
    public static final Sound BLOCK_SAND_BREAK                    = new Sound("BLOCK_SAND_BREAK", "block.sand.break", 76);
    public static final Sound BLOCK_SAND_FALL                     = new Sound("BLOCK_SAND_FALL", "block.sand.fall", 77);
    public static final Sound BLOCK_SAND_HIT                      = new Sound("BLOCK_SAND_HIT", "block.sand.hit", 78);
    public static final Sound BLOCK_SAND_PLACE                    = new Sound("BLOCK_SAND_PLACE", "block.sand.place", 79);
    public static final Sound BLOCK_SAND_STEP                     = new Sound("BLOCK_SAND_STEP", "block.sand.step", 80);
    public static final Sound BLOCK_SLIME_BREAK                   = new Sound("BLOCK_SLIME_BREAK", "block.slime.break", 81);
    public static final Sound BLOCK_SLIME_FALL                    = new Sound("BLOCK_SLIME_FALL", "block.slime.fall", 82);
    public static final Sound BLOCK_SLIME_HIT                     = new Sound("BLOCK_SLIME_HIT", "block.slime.hit", 83);
    public static final Sound BLOCK_SLIME_PLACE                   = new Sound("BLOCK_SLIME_PLACE", "block.slime.place", 84);
    public static final Sound BLOCK_SLIME_STEP                    = new Sound("BLOCK_SLIME_STEP", "block.slime.step", 85);
    public static final Sound BLOCK_SNOW_BREAK                    = new Sound("BLOCK_SNOW_BREAK", "block.snow.break", 86);
    public static final Sound BLOCK_SNOW_FALL                     = new Sound("BLOCK_SNOW_FALL", "block.snow.fall", 87);
    public static final Sound BLOCK_SNOW_HIT                      = new Sound("BLOCK_SNOW_HIT", "block.snow.hit", 88);
    public static final Sound BLOCK_SNOW_PLACE                    = new Sound("BLOCK_SNOW_PLACE", "block.snow.place", 89);
    public static final Sound BLOCK_SNOW_STEP                     = new Sound("BLOCK_SNOW_STEP", "block.snow.step", 90);
    public static final Sound BLOCK_STONE_BREAK                   = new Sound("BLOCK_STONE_BREAK", "block.stone.break", 91);
    public static final Sound BLOCK_STONE_FALL                    = new Sound("BLOCK_STONE_FALL", "block.stone.fall", 92);
    public static final Sound BLOCK_STONE_HIT                     = new Sound("BLOCK_STONE_HIT", "block.stone.hit", 93);
    public static final Sound BLOCK_STONE_PLACE                   = new Sound("BLOCK_STONE_PLACE", "block.stone.place", 94);
    public static final Sound BLOCK_STONE_STEP                    = new Sound("BLOCK_STONE_STEP", "block.stone.step", 95);
    public static final Sound BLOCK_STONE_BUTTON_CLICK_OFF        = new Sound("BLOCK_STONE_BUTTON_CLICK_OFF", "block.stone_button.click_off", 96);
    public static final Sound BLOCK_STONE_BUTTON_CLICK_ON         = new Sound("BLOCK_STONE_BUTTON_CLICK_ON", "block.stone_button.click_on", 97);
    public static final Sound BLOCK_STONE_PRESSUREPLATE_CLICK_OFF = new Sound("BLOCK_STONE_PRESSUREPLATE_CLICK_OFF", "block.stone_pressureplate.click_off", 98);
    public static final Sound BLOCK_STONE_PRESSUREPLATE_CLICK_ON  = new Sound("BLOCK_STONE_PRESSUREPLATE_CLICK_ON", "block.stone_pressureplate.click_on", 99);
    public static final Sound BLOCK_TRAPDOOR_CLOSE                = new Sound("BLOCK_TRAPDOOR_CLOSE", "block.trapdoor.close", 100);
    public static final Sound BLOCK_TRAPDOOR_OPEN                 = new Sound("BLOCK_TRAPDOOR_OPEN", "block.trapdoor.open", 101);
    public static final Sound BLOCK_TRIPWIRE_ATTACH               = new Sound("BLOCK_TRIPWIRE_ATTACH", "block.tripwire.attach", 102);
    public static final Sound BLOCK_TRIPWIRE_CLICK_OFF            = new Sound("BLOCK_TRIPWIRE_CLICK_OFF", "block.tripwire.click_off", 103);
    public static final Sound BLOCK_TRIPWIRE_CLICK_ON             = new Sound("BLOCK_TRIPWIRE_CLICK_ON", "block.tripwire.click_on", 104);
    public static final Sound BLOCK_TRIPWIRE_DETACH               = new Sound("BLOCK_TRIPWIRE_DETACH", "block.tripwire.detach", 105);
    public static final Sound BLOCK_WATER_AMBIENT                 = new Sound("BLOCK_WATER_AMBIENT", "block.water.ambient", 106);
    public static final Sound BLOCK_WATERLILY_PLACE               = new Sound("BLOCK_WATERLILY_PLACE", "block.waterlily.place", 107);
    public static final Sound BLOCK_WOOD_BREAK                    = new Sound("BLOCK_WOOD_BREAK", "block.wood.break", 108);
    public static final Sound BLOCK_WOOD_FALL                     = new Sound("BLOCK_WOOD_FALL", "block.wood.fall", 109);
    public static final Sound BLOCK_WOOD_HIT                      = new Sound("BLOCK_WOOD_HIT", "block.wood.hit", 110);
    public static final Sound BLOCK_WOOD_PLACE                    = new Sound("BLOCK_WOOD_PLACE", "block.wood.place", 111);
    public static final Sound BLOCK_WOOD_STEP                     = new Sound("BLOCK_WOOD_STEP", "block.wood.step", 112);
    public static final Sound BLOCK_WOOD_BUTTON_CLICK_OFF         = new Sound("BLOCK_WOOD_BUTTON_CLICK_OFF", "block.wood_button.click_off", 113);
    public static final Sound BLOCK_WOOD_BUTTON_CLICK_ON          = new Sound("BLOCK_WOOD_BUTTON_CLICK_ON", "block.wood_button.click_on", 114);
    public static final Sound BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF  = new Sound("BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF", "block.wood_pressureplate.click_off", 115);
    public static final Sound BLOCK_WOOD_PRESSUREPLATE_CLICK_ON   = new Sound("BLOCK_WOOD_PRESSUREPLATE_CLICK_ON", "block.wood_pressureplate.click_on", 116);
    public static final Sound BLOCK_WOODEN_DOOR_CLOSE             = new Sound("BLOCK_WOODEN_DOOR_CLOSE", "block.wooden_door.close", 117);
    public static final Sound BLOCK_WOODEN_DOOR_OPEN              = new Sound("BLOCK_WOODEN_DOOR_OPEN", "block.wooden_door.open", 118);
    public static final Sound ENCHANT_THORNS_HIT                  = new Sound("ENCHANT_THORNS_HIT", "enchant.thorns.hit", 119);
    public static final Sound ENTITY_ARMORSTAND_BREAK             = new Sound("ENTITY_ARMORSTAND_BREAK", "entity.armorstand.break", 120);
    public static final Sound ENTITY_ARMORSTAND_FALL              = new Sound("ENTITY_ARMORSTAND_FALL", "entity.armorstand.fall", 121);
    public static final Sound ENTITY_ARMORSTAND_HIT               = new Sound("ENTITY_ARMORSTAND_HIT", "entity.armorstand.hit", 122);
    public static final Sound ENTITY_ARMORSTAND_PLACE             = new Sound("ENTITY_ARMORSTAND_PLACE", "entity.armorstand.place", 123);
    public static final Sound ENTITY_ARROW_HIT                    = new Sound("ENTITY_ARROW_HIT", "entity.arrow.hit", 124);
    public static final Sound ENTITY_ARROW_HIT_PLAYER             = new Sound("ENTITY_ARROW_HIT_PLAYER", "entity.arrow.hit_player", 125);
    public static final Sound ENTITY_ARROW_SHOOT                  = new Sound("ENTITY_ARROW_SHOOT", "entity.arrow.shoot", 126);
    public static final Sound ENTITY_BAT_AMBIENT                  = new Sound("ENTITY_BAT_AMBIENT", "entity.bat.ambient", 127);
    public static final Sound ENTITY_BAT_DEATH                    = new Sound("ENTITY_BAT_DEATH", "entity.bat.death", 128);
    public static final Sound ENTITY_BAT_HURT                     = new Sound("ENTITY_BAT_HURT", "entity.bat.hurt", 129);
    public static final Sound ENTITY_BAT_TAKEOFF                  = new Sound("ENTITY_BAT_TAKEOFF", "entity.bat.takeoff", 130);
    public static final Sound ENTITY_BLAZE_AMBIENT                = new Sound("ENTITY_BLAZE_AMBIENT", "entity.blaze.ambient", 131);
    public static final Sound ENTITY_BLAZE_BURN                   = new Sound("ENTITY_BLAZE_BURN", "entity.blaze.burn", 132);
    public static final Sound ENTITY_BLAZE_DEATH                  = new Sound("ENTITY_BLAZE_DEATH", "entity.blaze.death", 133);
    public static final Sound ENTITY_BLAZE_HURT                   = new Sound("ENTITY_BLAZE_HURT", "entity.blaze.hurt", 134);
    public static final Sound ENTITY_BLAZE_SHOOT                  = new Sound("ENTITY_BLAZE_SHOOT", "entity.blaze.shoot", 135);
    public static final Sound ENTITY_BOBBER_SPLASH                = new Sound("ENTITY_BOBBER_SPLASH", "entity.bobber.splash", 136);
    public static final Sound ENTITY_BOBBER_THROW                 = new Sound("ENTITY_BOBBER_THROW", "entity.bobber.throw", 137);
    public static final Sound ENTITY_CAT_AMBIENT                  = new Sound("ENTITY_CAT_AMBIENT", "entity.cat.ambient", 138);
    public static final Sound ENTITY_CAT_DEATH                    = new Sound("ENTITY_CAT_DEATH", "entity.cat.death", 139);
    public static final Sound ENTITY_CAT_HURT                     = new Sound("ENTITY_CAT_HURT", "entity.cat.hurt", 140);
    public static final Sound ENTITY_CAT_PURR                     = new Sound("ENTITY_CAT_PURR", "entity.cat.purr", 141);
    public static final Sound ENTITY_CAT_PURREOW                  = new Sound("ENTITY_CAT_PURREOW", "entity.cat.purreow", 142);
    public static final Sound ENTITY_CHICKEN_AMBIENT              = new Sound("ENTITY_CHICKEN_AMBIENT", "entity.chicken.ambient", 143);
    public static final Sound ENTITY_CHICKEN_DEATH                = new Sound("ENTITY_CHICKEN_DEATH", "entity.chicken.death", 144);
    public static final Sound ENTITY_CHICKEN_EGG                  = new Sound("ENTITY_CHICKEN_EGG", "entity.chicken.egg", 145);
    public static final Sound ENTITY_CHICKEN_HURT                 = new Sound("ENTITY_CHICKEN_HURT", "entity.chicken.hurt", 146);
    public static final Sound ENTITY_CHICKEN_STEP                 = new Sound("ENTITY_CHICKEN_STEP", "entity.chicken.step", 147);
    public static final Sound ENTITY_COW_AMBIENT                  = new Sound("ENTITY_COW_AMBIENT", "entity.cow.ambient", 148);
    public static final Sound ENTITY_COW_DEATH                    = new Sound("ENTITY_COW_DEATH", "entity.cow.death", 149);
    public static final Sound ENTITY_COW_HURT                     = new Sound("ENTITY_COW_HURT", "entity.cow.hurt", 150);
    public static final Sound ENTITY_COW_MILK                     = new Sound("ENTITY_COW_MILK", "entity.cow.milk", 151);
    public static final Sound ENTITY_COW_STEP                     = new Sound("ENTITY_COW_STEP", "entity.cow.step", 152);
    public static final Sound ENTITY_CREEPER_DEATH                = new Sound("ENTITY_CREEPER_DEATH", "entity.creeper.death", 153);
    public static final Sound ENTITY_CREEPER_HURT                 = new Sound("ENTITY_CREEPER_HURT", "entity.creeper.hurt", 154);
    public static final Sound ENTITY_CREEPER_PRIMED               = new Sound("ENTITY_CREEPER_PRIMED", "entity.creeper.primed", 155);
    public static final Sound ENTITY_DONKEY_AMBIENT               = new Sound("ENTITY_DONKEY_AMBIENT", "entity.donkey.ambient", 156);
    public static final Sound ENTITY_DONKEY_ANGRY                 = new Sound("ENTITY_DONKEY_ANGRY", "entity.donkey.angry", 157);
    public static final Sound ENTITY_DONKEY_CHEST                 = new Sound("ENTITY_DONKEY_CHEST", "entity.donkey.chest", 158);
    public static final Sound ENTITY_DONKEY_DEATH                 = new Sound("ENTITY_DONKEY_DEATH", "entity.donkey.death", 159);
    public static final Sound ENTITY_DONKEY_HURT                  = new Sound("ENTITY_DONKEY_HURT", "entity.donkey.hurt", 160);
    public static final Sound ENTITY_EGG_THROW                    = new Sound("ENTITY_EGG_THROW", "entity.egg.throw", 161);
    public static final Sound ENTITY_ELDER_GUARDIAN_AMBIENT       = new Sound("ENTITY_ELDER_GUARDIAN_AMBIENT", "entity.elder_guardian.ambient", 162);
    public static final Sound ENTITY_ELDER_GUARDIAN_AMBIENT_LAND  = new Sound("ENTITY_ELDER_GUARDIAN_AMBIENT_LAND", "entity.elder_guardian.ambient_land", 163);
    public static final Sound ENTITY_ELDER_GUARDIAN_CURSE         = new Sound("ENTITY_ELDER_GUARDIAN_CURSE", "entity.elder_guardian.curse", 164);
    public static final Sound ENTITY_ELDER_GUARDIAN_DEATH         = new Sound("ENTITY_ELDER_GUARDIAN_DEATH", "entity.elder_guardian.death", 165);
    public static final Sound ENTITY_ELDER_GUARDIAN_DEATH_LAND    = new Sound("ENTITY_ELDER_GUARDIAN_DEATH_LAND", "entity.elder_guardian.death_land", 166);
    public static final Sound ENTITY_ELDER_GUARDIAN_HURT          = new Sound("ENTITY_ELDER_GUARDIAN_HURT", "entity.elder_guardian.hurt", 167);
    public static final Sound ENTITY_ELDER_GUARDIAN_HURT_LAND     = new Sound("ENTITY_ELDER_GUARDIAN_HURT_LAND", "entity.elder_guardian.hurt_land", 168);
    public static final Sound ENTITY_ENDERDRAGON_AMBIENT          = new Sound("ENTITY_ENDERDRAGON_AMBIENT", "entity.enderdragon.ambient", 169);
    public static final Sound ENTITY_ENDERDRAGON_DEATH            = new Sound("ENTITY_ENDERDRAGON_DEATH", "entity.enderdragon.death", 170);
    public static final Sound ENTITY_ENDERDRAGON_FLAP             = new Sound("ENTITY_ENDERDRAGON_FLAP", "entity.enderdragon.flap", 171);
    public static final Sound ENTITY_ENDERDRAGON_GROWL            = new Sound("ENTITY_ENDERDRAGON_GROWL", "entity.enderdragon.growl", 172);
    public static final Sound ENTITY_ENDERDRAGON_HURT             = new Sound("ENTITY_ENDERDRAGON_HURT", "entity.enderdragon.hurt", 173);
    public static final Sound ENTITY_ENDERDRAGON_SHOOT            = new Sound("ENTITY_ENDERDRAGON_SHOOT", "entity.enderdragon.shoot", 174);
    public static final Sound ENTITY_ENDERDRAGON_FIREBALL_EXPLODE = new Sound("ENTITY_ENDERDRAGON_FIREBALL_EXPLODE", "entity.enderdragon_fireball.explode", 175);
    public static final Sound ENTITY_ENDEREYE_LAUNCH              = new Sound("ENTITY_ENDEREYE_LAUNCH", "entity.endereye.launch", 176);
    public static final Sound ENTITY_ENDERMEN_AMBIENT             = new Sound("ENTITY_ENDERMEN_AMBIENT", "entity.endermen.ambient", 177);
    public static final Sound ENTITY_ENDERMEN_DEATH               = new Sound("ENTITY_ENDERMEN_DEATH", "entity.endermen.death", 178);
    public static final Sound ENTITY_ENDERMEN_HURT                = new Sound("ENTITY_ENDERMEN_HURT", "entity.endermen.hurt", 179);
    public static final Sound ENTITY_ENDERMEN_SCREAM              = new Sound("ENTITY_ENDERMEN_SCREAM", "entity.endermen.scream", 180);
    public static final Sound ENTITY_ENDERMEN_STARE               = new Sound("ENTITY_ENDERMEN_STARE", "entity.endermen.stare", 181);
    public static final Sound ENTITY_ENDERMEN_TELEPORT            = new Sound("ENTITY_ENDERMEN_TELEPORT", "entity.endermen.teleport", 182);
    public static final Sound ENTITY_ENDERMITE_AMBIENT            = new Sound("ENTITY_ENDERMITE_AMBIENT", "entity.endermite.ambient", 183);
    public static final Sound ENTITY_ENDERMITE_DEATH              = new Sound("ENTITY_ENDERMITE_DEATH", "entity.endermite.death", 184);
    public static final Sound ENTITY_ENDERMITE_HURT               = new Sound("ENTITY_ENDERMITE_HURT", "entity.endermite.hurt", 185);
    public static final Sound ENTITY_ENDERMITE_STEP               = new Sound("ENTITY_ENDERMITE_STEP", "entity.endermite.step", 186);
    public static final Sound ENTITY_ENDERPEARL_THROW             = new Sound("ENTITY_ENDERPEARL_THROW", "entity.enderpearl.throw", 187);
    public static final Sound ENTITY_EXPERIENCE_BOTTLE_THROW      = new Sound("ENTITY_EXPERIENCE_BOTTLE_THROW", "entity.experience_bottle.throw", 188);
    public static final Sound ENTITY_EXPERIENCE_ORB_PICKUP        = new Sound("ENTITY_EXPERIENCE_ORB_PICKUP", "entity.experience_orb.pickup", 189);
    public static final Sound ENTITY_EXPERIENCE_ORB_TOUCH         = new Sound("ENTITY_EXPERIENCE_ORB_TOUCH", "entity.experience_orb.touch", 190);
    public static final Sound ENTITY_FIREWORK_BLAST               = new Sound("ENTITY_FIREWORK_BLAST", "entity.firework.blast", 191);
    public static final Sound ENTITY_FIREWORK_BLAST_FAR           = new Sound("ENTITY_FIREWORK_BLAST_FAR", "entity.firework.blast_far", 192);
    public static final Sound ENTITY_FIREWORK_LARGE_BLAST         = new Sound("ENTITY_FIREWORK_LARGE_BLAST", "entity.firework.large_blast", 193);
    public static final Sound ENTITY_FIREWORK_LARGE_BLAST_FAR     = new Sound("ENTITY_FIREWORK_LARGE_BLAST_FAR", "entity.firework.large_blast_far", 194);
    public static final Sound ENTITY_FIREWORK_LAUNCH              = new Sound("ENTITY_FIREWORK_LAUNCH", "entity.firework.launch", 195);
    public static final Sound ENTITY_FIREWORK_SHOOT               = new Sound("ENTITY_FIREWORK_SHOOT", "entity.firework.shoot", 196);
    public static final Sound ENTITY_FIREWORK_TWINKLE             = new Sound("ENTITY_FIREWORK_TWINKLE", "entity.firework.twinkle", 197);
    public static final Sound ENTITY_FIREWORK_TWINKLE_FAR         = new Sound("ENTITY_FIREWORK_TWINKLE_FAR", "entity.firework.twinkle_far", 198);
    public static final Sound ENTITY_GENERIC_BIG_FALL             = new Sound("ENTITY_GENERIC_BIG_FALL", "entity.generic.big_fall", 199);
    public static final Sound ENTITY_GENERIC_BURN                 = new Sound("ENTITY_GENERIC_BURN", "entity.generic.burn", 200);
    public static final Sound ENTITY_GENERIC_DEATH                = new Sound("ENTITY_GENERIC_DEATH", "entity.generic.death", 201);
    public static final Sound ENTITY_GENERIC_DRINK                = new Sound("ENTITY_GENERIC_DRINK", "entity.generic.drink", 202);
    public static final Sound ENTITY_GENERIC_EAT                  = new Sound("ENTITY_GENERIC_EAT", "entity.generic.eat", 203);
    public static final Sound ENTITY_GENERIC_EXPLODE              = new Sound("ENTITY_GENERIC_EXPLODE", "entity.generic.explode", 204);
    public static final Sound ENTITY_GENERIC_EXTINGUISH_FIRE      = new Sound("ENTITY_GENERIC_EXTINGUISH_FIRE", "entity.generic.extinguish_fire", 205);
    public static final Sound ENTITY_GENERIC_HURT                 = new Sound("ENTITY_GENERIC_HURT", "entity.generic.hurt", 206);
    public static final Sound ENTITY_GENERIC_SMALL_FALL           = new Sound("ENTITY_GENERIC_SMALL_FALL", "entity.generic.small_fall", 207);
    public static final Sound ENTITY_GENERIC_SPLASH               = new Sound("ENTITY_GENERIC_SPLASH", "entity.generic.splash", 208);
    public static final Sound ENTITY_GENERIC_SWIM                 = new Sound("ENTITY_GENERIC_SWIM", "entity.generic.swim", 209);
    public static final Sound ENTITY_GHAST_AMBIENT                = new Sound("ENTITY_GHAST_AMBIENT", "entity.ghast.ambient", 210);
    public static final Sound ENTITY_GHAST_DEATH                  = new Sound("ENTITY_GHAST_DEATH", "entity.ghast.death", 211);
    public static final Sound ENTITY_GHAST_HURT                   = new Sound("ENTITY_GHAST_HURT", "entity.ghast.hurt", 212);
    public static final Sound ENTITY_GHAST_SHOOT                  = new Sound("ENTITY_GHAST_SHOOT", "entity.ghast.shoot", 213);
    public static final Sound ENTITY_GHAST_WARN                   = new Sound("ENTITY_GHAST_WARN", "entity.ghast.warn", 214);
    public static final Sound ENTITY_GUARDIAN_AMBIENT             = new Sound("ENTITY_GUARDIAN_AMBIENT", "entity.guardian.ambient", 215);
    public static final Sound ENTITY_GUARDIAN_AMBIENT_LAND        = new Sound("ENTITY_GUARDIAN_AMBIENT_LAND", "entity.guardian.ambient_land", 216);
    public static final Sound ENTITY_GUARDIAN_ATTACK              = new Sound("ENTITY_GUARDIAN_ATTACK", "entity.guardian.attack", 217);
    public static final Sound ENTITY_GUARDIAN_DEATH               = new Sound("ENTITY_GUARDIAN_DEATH", "entity.guardian.death", 218);
    public static final Sound ENTITY_GUARDIAN_DEATH_LAND          = new Sound("ENTITY_GUARDIAN_DEATH_LAND", "entity.guardian.death_land", 219);
    public static final Sound ENTITY_GUARDIAN_FLOP                = new Sound("ENTITY_GUARDIAN_FLOP", "entity.guardian.flop", 220);
    public static final Sound ENTITY_GUARDIAN_HURT                = new Sound("ENTITY_GUARDIAN_HURT", "entity.guardian.hurt", 221);
    public static final Sound ENTITY_GUARDIAN_HURT_LAND           = new Sound("ENTITY_GUARDIAN_HURT_LAND", "entity.guardian.hurt_land", 222);
    public static final Sound ENTITY_HORSE_AMBIENT                = new Sound("ENTITY_HORSE_AMBIENT", "entity.horse.ambient", 223);
    public static final Sound ENTITY_HORSE_ANGRY                  = new Sound("ENTITY_HORSE_ANGRY", "entity.horse.angry", 224);
    public static final Sound ENTITY_HORSE_ARMOR                  = new Sound("ENTITY_HORSE_ARMOR", "entity.horse.armor", 225);
    public static final Sound ENTITY_HORSE_BREATHE                = new Sound("ENTITY_HORSE_BREATHE", "entity.horse.breathe", 226);
    public static final Sound ENTITY_HORSE_DEATH                  = new Sound("ENTITY_HORSE_DEATH", "entity.horse.death", 227);
    public static final Sound ENTITY_HORSE_EAT                    = new Sound("ENTITY_HORSE_EAT", "entity.horse.eat", 228);
    public static final Sound ENTITY_HORSE_GALLOP                 = new Sound("ENTITY_HORSE_GALLOP", "entity.horse.gallop", 229);
    public static final Sound ENTITY_HORSE_HURT                   = new Sound("ENTITY_HORSE_HURT", "entity.horse.hurt", 230);
    public static final Sound ENTITY_HORSE_JUMP                   = new Sound("ENTITY_HORSE_JUMP", "entity.horse.jump", 231);
    public static final Sound ENTITY_HORSE_LAND                   = new Sound("ENTITY_HORSE_LAND", "entity.horse.land", 232);
    public static final Sound ENTITY_HORSE_SADDLE                 = new Sound("ENTITY_HORSE_SADDLE", "entity.horse.saddle", 233);
    public static final Sound ENTITY_HORSE_STEP                   = new Sound("ENTITY_HORSE_STEP", "entity.horse.step", 234);
    public static final Sound ENTITY_HORSE_STEP_WOOD              = new Sound("ENTITY_HORSE_STEP_WOOD", "entity.horse.step_wood", 235);
    public static final Sound ENTITY_HOSTILE_BIG_FALL             = new Sound("ENTITY_HOSTILE_BIG_FALL", "entity.hostile.big_fall", 236);
    public static final Sound ENTITY_HOSTILE_DEATH                = new Sound("ENTITY_HOSTILE_DEATH", "entity.hostile.death", 237);
    public static final Sound ENTITY_HOSTILE_HURT                 = new Sound("ENTITY_HOSTILE_HURT", "entity.hostile.hurt", 238);
    public static final Sound ENTITY_HOSTILE_SMALL_FALL           = new Sound("ENTITY_HOSTILE_SMALL_FALL", "entity.hostile.small_fall", 239);
    public static final Sound ENTITY_HOSTILE_SPLASH               = new Sound("ENTITY_HOSTILE_SPLASH", "entity.hostile.splash", 240);
    public static final Sound ENTITY_HOSTILE_SWIM                 = new Sound("ENTITY_HOSTILE_SWIM", "entity.hostile.swim", 241);
    public static final Sound ENTITY_IRONGOLEM_ATTACK             = new Sound("ENTITY_IRONGOLEM_ATTACK", "entity.irongolem.attack", 242);
    public static final Sound ENTITY_IRONGOLEM_DEATH              = new Sound("ENTITY_IRONGOLEM_DEATH", "entity.irongolem.death", 243);
    public static final Sound ENTITY_IRONGOLEM_HURT               = new Sound("ENTITY_IRONGOLEM_HURT", "entity.irongolem.hurt", 244);
    public static final Sound ENTITY_IRONGOLEM_STEP               = new Sound("ENTITY_IRONGOLEM_STEP", "entity.irongolem.step", 245);
    public static final Sound ENTITY_ITEM_BREAK                   = new Sound("ENTITY_ITEM_BREAK", "entity.item.break", 246);
    public static final Sound ENTITY_ITEM_PICKUP                  = new Sound("ENTITY_ITEM_PICKUP", "entity.item.pickup", 247);
    public static final Sound ENTITY_ITEMFRAME_ADD_ITEM           = new Sound("ENTITY_ITEMFRAME_ADD_ITEM", "entity.itemframe.add_item", 248);
    public static final Sound ENTITY_ITEMFRAME_BREAK              = new Sound("ENTITY_ITEMFRAME_BREAK", "entity.itemframe.break", 249);
    public static final Sound ENTITY_ITEMFRAME_PLACE              = new Sound("ENTITY_ITEMFRAME_PLACE", "entity.itemframe.place", 250);
    public static final Sound ENTITY_ITEMFRAME_REMOVE_ITEM        = new Sound("ENTITY_ITEMFRAME_REMOVE_ITEM", "entity.itemframe.remove_item", 251);
    public static final Sound ENTITY_ITEMFRAME_ROTATE_ITEM        = new Sound("ENTITY_ITEMFRAME_ROTATE_ITEM", "entity.itemframe.rotate_item", 252);
    public static final Sound ENTITY_LEASHKNOT_BREAK              = new Sound("ENTITY_LEASHKNOT_BREAK", "entity.leashknot.break", 253);
    public static final Sound ENTITY_LEASHKNOT_PLACE              = new Sound("ENTITY_LEASHKNOT_PLACE", "entity.leashknot.place", 254);
    public static final Sound ENTITY_LIGHTNING_IMPACT             = new Sound("ENTITY_LIGHTNING_IMPACT", "entity.lightning.impact", 255);
    public static final Sound ENTITY_LIGHTNING_THUNDER            = new Sound("ENTITY_LIGHTNING_THUNDER", "entity.lightning.thunder", 256);
    public static final Sound ENTITY_LINGERINGPOTION_THROW        = new Sound("ENTITY_LINGERINGPOTION_THROW", "entity.lingeringpotion.throw", 257);
    public static final Sound ENTITY_MAGMACUBE_DEATH              = new Sound("ENTITY_MAGMACUBE_DEATH", "entity.magmacube.death", 258);
    public static final Sound ENTITY_MAGMACUBE_HURT               = new Sound("ENTITY_MAGMACUBE_HURT", "entity.magmacube.hurt", 259);
    public static final Sound ENTITY_MAGMACUBE_JUMP               = new Sound("ENTITY_MAGMACUBE_JUMP", "entity.magmacube.jump", 260);
    public static final Sound ENTITY_MAGMACUBE_SQUISH             = new Sound("ENTITY_MAGMACUBE_SQUISH", "entity.magmacube.squish", 261);
    public static final Sound ENTITY_MINECART_INSIDE              = new Sound("ENTITY_MINECART_INSIDE", "entity.minecart.inside", 262);
    public static final Sound ENTITY_MINECART_RIDING              = new Sound("ENTITY_MINECART_RIDING", "entity.minecart.riding", 263);
    public static final Sound ENTITY_MOOSHROOM_SHEAR              = new Sound("ENTITY_MOOSHROOM_SHEAR", "entity.mooshroom.shear", 264);
    public static final Sound ENTITY_MULE_AMBIENT                 = new Sound("ENTITY_MULE_AMBIENT", "entity.mule.ambient", 265);
    public static final Sound ENTITY_MULE_DEATH                   = new Sound("ENTITY_MULE_DEATH", "entity.mule.death", 266);
    public static final Sound ENTITY_MULE_HURT                    = new Sound("ENTITY_MULE_HURT", "entity.mule.hurt", 267);
    public static final Sound ENTITY_PAINTING_BREAK               = new Sound("ENTITY_PAINTING_BREAK", "entity.painting.break", 268);
    public static final Sound ENTITY_PAINTING_PLACE               = new Sound("ENTITY_PAINTING_PLACE", "entity.painting.place", 269);
    public static final Sound ENTITY_PIG_AMBIENT                  = new Sound("ENTITY_PIG_AMBIENT", "entity.pig.ambient", 270);
    public static final Sound ENTITY_PIG_DEATH                    = new Sound("ENTITY_PIG_DEATH", "entity.pig.death", 271);
    public static final Sound ENTITY_PIG_HURT                     = new Sound("ENTITY_PIG_HURT", "entity.pig.hurt", 272);
    public static final Sound ENTITY_PIG_SADDLE                   = new Sound("ENTITY_PIG_SADDLE", "entity.pig.saddle", 273);
    public static final Sound ENTITY_PIG_STEP                     = new Sound("ENTITY_PIG_STEP", "entity.pig.step", 274);
    public static final Sound ENTITY_PLAYER_ATTACK_CRIT           = new Sound("ENTITY_PLAYER_ATTACK_CRIT", "entity.player.attack.crit", 275);
    public static final Sound ENTITY_PLAYER_ATTACK_KNOCKBACK      = new Sound("ENTITY_PLAYER_ATTACK_KNOCKBACK", "entity.player.attack.knockback", 276);
    public static final Sound ENTITY_PLAYER_ATTACK_NODAMAGE       = new Sound("ENTITY_PLAYER_ATTACK_NODAMAGE", "entity.player.attack.nodamage", 277);
    public static final Sound ENTITY_PLAYER_ATTACK_STRONG         = new Sound("ENTITY_PLAYER_ATTACK_STRONG", "entity.player.attack.strong", 278);
    public static final Sound ENTITY_PLAYER_ATTACK_SWEEP          = new Sound("ENTITY_PLAYER_ATTACK_SWEEP", "entity.player.attack.sweep", 279);
    public static final Sound ENTITY_PLAYER_ATTACK_WEAK           = new Sound("ENTITY_PLAYER_ATTACK_WEAK", "entity.player.attack.weak", 280);
    public static final Sound ENTITY_PLAYER_BIG_FALL              = new Sound("ENTITY_PLAYER_BIG_FALL", "entity.player.big_fall", 281);
    public static final Sound ENTITY_PLAYER_BURP                  = new Sound("ENTITY_PLAYER_BURP", "entity.player.burp", 282);
    public static final Sound ENTITY_PLAYER_DEATH                 = new Sound("ENTITY_PLAYER_DEATH", "entity.player.death", 283);
    public static final Sound ENTITY_PLAYER_HURT                  = new Sound("ENTITY_PLAYER_HURT", "entity.player.hurt", 284);
    public static final Sound ENTITY_PLAYER_LEVELUP               = new Sound("ENTITY_PLAYER_LEVELUP", "entity.player.levelup", 285);
    public static final Sound ENTITY_PLAYER_SMALL_FALL            = new Sound("ENTITY_PLAYER_SMALL_FALL", "entity.player.small_fall", 286);
    public static final Sound ENTITY_PLAYER_SPLASH                = new Sound("ENTITY_PLAYER_SPLASH", "entity.player.splash", 287);
    public static final Sound ENTITY_PLAYER_SWIM                  = new Sound("ENTITY_PLAYER_SWIM", "entity.player.swim", 288);
    public static final Sound ENTITY_RABBIT_AMBIENT               = new Sound("ENTITY_RABBIT_AMBIENT", "entity.rabbit.ambient", 289);
    public static final Sound ENTITY_RABBIT_ATTACK                = new Sound("ENTITY_RABBIT_ATTACK", "entity.rabbit.attack", 290);
    public static final Sound ENTITY_RABBIT_DEATH                 = new Sound("ENTITY_RABBIT_DEATH", "entity.rabbit.death", 291);
    public static final Sound ENTITY_RABBIT_HURT                  = new Sound("ENTITY_RABBIT_HURT", "entity.rabbit.hurt", 292);
    public static final Sound ENTITY_RABBIT_JUMP                  = new Sound("ENTITY_RABBIT_JUMP", "entity.rabbit.jump", 293);
    public static final Sound ENTITY_SHEEP_AMBIENT                = new Sound("ENTITY_SHEEP_AMBIENT", "entity.sheep.ambient", 294);
    public static final Sound ENTITY_SHEEP_DEATH                  = new Sound("ENTITY_SHEEP_DEATH", "entity.sheep.death", 295);
    public static final Sound ENTITY_SHEEP_HURT                   = new Sound("ENTITY_SHEEP_HURT", "entity.sheep.hurt", 296);
    public static final Sound ENTITY_SHEEP_SHEAR                  = new Sound("ENTITY_SHEEP_SHEAR", "entity.sheep.shear", 297);
    public static final Sound ENTITY_SHEEP_STEP                   = new Sound("ENTITY_SHEEP_STEP", "entity.sheep.step", 298);
    public static final Sound ENTITY_SHIELD_BLOCK                 = new Sound("ENTITY_SHIELD_BLOCK", "entity.shield.block", 299);
    public static final Sound ENTITY_SHIELD_BREAK                 = new Sound("ENTITY_SHIELD_BREAK", "entity.shield.break", 300);
    public static final Sound ENTITY_SHULKER_AMBIENT              = new Sound("ENTITY_SHULKER_AMBIENT", "entity.shulker.ambient", 301);
    public static final Sound ENTITY_SHULKER_CLOSE                = new Sound("ENTITY_SHULKER_CLOSE", "entity.shulker.close", 302);
    public static final Sound ENTITY_SHULKER_DEATH                = new Sound("ENTITY_SHULKER_DEATH", "entity.shulker.death", 303);
    public static final Sound ENTITY_SHULKER_HURT                 = new Sound("ENTITY_SHULKER_HURT", "entity.shulker.hurt", 304);
    public static final Sound ENTITY_SHULKER_HURT_CLOSED          = new Sound("ENTITY_SHULKER_HURT_CLOSED", "entity.shulker.hurt_closed", 305);
    public static final Sound ENTITY_SHULKER_OPEN                 = new Sound("ENTITY_SHULKER_OPEN", "entity.shulker.open", 306);
    public static final Sound ENTITY_SHULKER_SHOOT                = new Sound("ENTITY_SHULKER_SHOOT", "entity.shulker.shoot", 307);
    public static final Sound ENTITY_SHULKER_TELEPORT             = new Sound("ENTITY_SHULKER_TELEPORT", "entity.shulker.teleport", 308);
    public static final Sound ENTITY_SHULKER_BULLET_HIT           = new Sound("ENTITY_SHULKER_BULLET_HIT", "entity.shulker_bullet.hit", 309);
    public static final Sound ENTITY_SHULKER_BULLET_HURT          = new Sound("ENTITY_SHULKER_BULLET_HURT", "entity.shulker_bullet.hurt", 310);
    public static final Sound ENTITY_SILVERFISH_AMBIENT           = new Sound("ENTITY_SILVERFISH_AMBIENT", "entity.silverfish.ambient", 311);
    public static final Sound ENTITY_SILVERFISH_DEATH             = new Sound("ENTITY_SILVERFISH_DEATH", "entity.silverfish.death", 312);
    public static final Sound ENTITY_SILVERFISH_HURT              = new Sound("ENTITY_SILVERFISH_HURT", "entity.silverfish.hurt", 313);
    public static final Sound ENTITY_SILVERFISH_STEP              = new Sound("ENTITY_SILVERFISH_STEP", "entity.silverfish.step", 314);
    public static final Sound ENTITY_SKELETON_AMBIENT             = new Sound("ENTITY_SKELETON_AMBIENT", "entity.skeleton.ambient", 315);
    public static final Sound ENTITY_SKELETON_DEATH               = new Sound("ENTITY_SKELETON_DEATH", "entity.skeleton.death", 316);
    public static final Sound ENTITY_SKELETON_HURT                = new Sound("ENTITY_SKELETON_HURT", "entity.skeleton.hurt", 317);
    public static final Sound ENTITY_SKELETON_SHOOT               = new Sound("ENTITY_SKELETON_SHOOT", "entity.skeleton.shoot", 318);
    public static final Sound ENTITY_SKELETON_STEP                = new Sound("ENTITY_SKELETON_STEP", "entity.skeleton.step", 319);
    public static final Sound ENTITY_SKELETON_HORSE_AMBIENT       = new Sound("ENTITY_SKELETON_HORSE_AMBIENT", "entity.skeleton_horse.ambient", 320);
    public static final Sound ENTITY_SKELETON_HORSE_DEATH         = new Sound("ENTITY_SKELETON_HORSE_DEATH", "entity.skeleton_horse.death", 321);
    public static final Sound ENTITY_SKELETON_HORSE_HURT          = new Sound("ENTITY_SKELETON_HORSE_HURT", "entity.skeleton_horse.hurt", 322);
    public static final Sound ENTITY_SLIME_ATTACK                 = new Sound("ENTITY_SLIME_ATTACK", "entity.slime.attack", 323);
    public static final Sound ENTITY_SLIME_DEATH                  = new Sound("ENTITY_SLIME_DEATH", "entity.slime.death", 324);
    public static final Sound ENTITY_SLIME_HURT                   = new Sound("ENTITY_SLIME_HURT", "entity.slime.hurt", 325);
    public static final Sound ENTITY_SLIME_JUMP                   = new Sound("ENTITY_SLIME_JUMP", "entity.slime.jump", 326);
    public static final Sound ENTITY_SLIME_SQUISH                 = new Sound("ENTITY_SLIME_SQUISH", "entity.slime.squish", 327);
    public static final Sound ENTITY_SMALL_MAGMACUBE_DEATH        = new Sound("ENTITY_SMALL_MAGMACUBE_DEATH", "entity.small_magmacube.death", 328);
    public static final Sound ENTITY_SMALL_MAGMACUBE_HURT         = new Sound("ENTITY_SMALL_MAGMACUBE_HURT", "entity.small_magmacube.hurt", 329);
    public static final Sound ENTITY_SMALL_MAGMACUBE_SQUISH       = new Sound("ENTITY_SMALL_MAGMACUBE_SQUISH", "entity.small_magmacube.squish", 330);
    public static final Sound ENTITY_SMALL_SLIME_DEATH            = new Sound("ENTITY_SMALL_SLIME_DEATH", "entity.small_slime.death", 331);
    public static final Sound ENTITY_SMALL_SLIME_HURT             = new Sound("ENTITY_SMALL_SLIME_HURT", "entity.small_slime.hurt", 332);
    public static final Sound ENTITY_SMALL_SLIME_SQUISH           = new Sound("ENTITY_SMALL_SLIME_SQUISH", "entity.small_slime.squish", 333);
    public static final Sound ENTITY_SNOWBALL_THROW               = new Sound("ENTITY_SNOWBALL_THROW", "entity.snowball.throw", 334);
    public static final Sound ENTITY_SNOWMAN_AMBIENT              = new Sound("ENTITY_SNOWMAN_AMBIENT", "entity.snowman.ambient", 335);
    public static final Sound ENTITY_SNOWMAN_DEATH                = new Sound("ENTITY_SNOWMAN_DEATH", "entity.snowman.death", 336);
    public static final Sound ENTITY_SNOWMAN_HURT                 = new Sound("ENTITY_SNOWMAN_HURT", "entity.snowman.hurt", 337);
    public static final Sound ENTITY_SNOWMAN_SHOOT                = new Sound("ENTITY_SNOWMAN_SHOOT", "entity.snowman.shoot", 338);
    public static final Sound ENTITY_SPIDER_AMBIENT               = new Sound("ENTITY_SPIDER_AMBIENT", "entity.spider.ambient", 339);
    public static final Sound ENTITY_SPIDER_DEATH                 = new Sound("ENTITY_SPIDER_DEATH", "entity.spider.death", 340);
    public static final Sound ENTITY_SPIDER_HURT                  = new Sound("ENTITY_SPIDER_HURT", "entity.spider.hurt", 341);
    public static final Sound ENTITY_SPIDER_STEP                  = new Sound("ENTITY_SPIDER_STEP", "entity.spider.step", 342);
    public static final Sound ENTITY_SPLASH_POTION_BREAK          = new Sound("ENTITY_SPLASH_POTION_BREAK", "entity.splash_potion.break", 343);
    public static final Sound ENTITY_SPLASH_POTION_THROW          = new Sound("ENTITY_SPLASH_POTION_THROW", "entity.splash_potion.throw", 344);
    public static final Sound ENTITY_SQUID_AMBIENT                = new Sound("ENTITY_SQUID_AMBIENT", "entity.squid.ambient", 345);
    public static final Sound ENTITY_SQUID_DEATH                  = new Sound("ENTITY_SQUID_DEATH", "entity.squid.death", 346);
    public static final Sound ENTITY_SQUID_HURT                   = new Sound("ENTITY_SQUID_HURT", "entity.squid.hurt", 347);
    public static final Sound ENTITY_TNT_PRIMED                   = new Sound("ENTITY_TNT_PRIMED", "entity.tnt.primed", 348);
    public static final Sound ENTITY_VILLAGER_AMBIENT             = new Sound("ENTITY_VILLAGER_AMBIENT", "entity.villager.ambient", 349);
    public static final Sound ENTITY_VILLAGER_DEATH               = new Sound("ENTITY_VILLAGER_DEATH", "entity.villager.death", 350);
    public static final Sound ENTITY_VILLAGER_HURT                = new Sound("ENTITY_VILLAGER_HURT", "entity.villager.hurt", 351);
    public static final Sound ENTITY_VILLAGER_NO                  = new Sound("ENTITY_VILLAGER_NO", "entity.villager.no", 352);
    public static final Sound ENTITY_VILLAGER_TRADING             = new Sound("ENTITY_VILLAGER_TRADING", "entity.villager.trading", 353);
    public static final Sound ENTITY_VILLAGER_YES                 = new Sound("ENTITY_VILLAGER_YES", "entity.villager.yes", 354);
    public static final Sound ENTITY_WITCH_AMBIENT                = new Sound("ENTITY_WITCH_AMBIENT", "entity.witch.ambient", 355);
    public static final Sound ENTITY_WITCH_DEATH                  = new Sound("ENTITY_WITCH_DEATH", "entity.witch.death", 356);
    public static final Sound ENTITY_WITCH_DRINK                  = new Sound("ENTITY_WITCH_DRINK", "entity.witch.drink", 357);
    public static final Sound ENTITY_WITCH_HURT                   = new Sound("ENTITY_WITCH_HURT", "entity.witch.hurt", 358);
    public static final Sound ENTITY_WITCH_THROW                  = new Sound("ENTITY_WITCH_THROW", "entity.witch.throw", 359);
    public static final Sound ENTITY_WITHER_AMBIENT               = new Sound("ENTITY_WITHER_AMBIENT", "entity.wither.ambient", 360);
    public static final Sound ENTITY_WITHER_BREAK_BLOCK           = new Sound("ENTITY_WITHER_BREAK_BLOCK", "entity.wither.break_block", 361);
    public static final Sound ENTITY_WITHER_DEATH                 = new Sound("ENTITY_WITHER_DEATH", "entity.wither.death", 362);
    public static final Sound ENTITY_WITHER_HURT                  = new Sound("ENTITY_WITHER_HURT", "entity.wither.hurt", 363);
    public static final Sound ENTITY_WITHER_SHOOT                 = new Sound("ENTITY_WITHER_SHOOT", "entity.wither.shoot", 364);
    public static final Sound ENTITY_WITHER_SPAWN                 = new Sound("ENTITY_WITHER_SPAWN", "entity.wither.spawn", 365);
    public static final Sound ENTITY_WOLF_AMBIENT                 = new Sound("ENTITY_WOLF_AMBIENT", "entity.wolf.ambient", 366);
    public static final Sound ENTITY_WOLF_DEATH                   = new Sound("ENTITY_WOLF_DEATH", "entity.wolf.death", 367);
    public static final Sound ENTITY_WOLF_GROWL                   = new Sound("ENTITY_WOLF_GROWL", "entity.wolf.growl", 368);
    public static final Sound ENTITY_WOLF_HURT                    = new Sound("ENTITY_WOLF_HURT", "entity.wolf.hurt", 369);
    public static final Sound ENTITY_WOLF_PANT                    = new Sound("ENTITY_WOLF_PANT", "entity.wolf.pant", 370);
    public static final Sound ENTITY_WOLF_SHAKE                   = new Sound("ENTITY_WOLF_SHAKE", "entity.wolf.shake", 371);
    public static final Sound ENTITY_WOLF_STEP                    = new Sound("ENTITY_WOLF_STEP", "entity.wolf.step", 372);
    public static final Sound ENTITY_WOLF_WHINE                   = new Sound("ENTITY_WOLF_WHINE", "entity.wolf.whine", 373);
    public static final Sound ENTITY_ZOMBIE_AMBIENT               = new Sound("ENTITY_ZOMBIE_AMBIENT", "entity.zombie.ambient", 374);
    public static final Sound ENTITY_ZOMBIE_ATTACK_DOOR_WOOD      = new Sound("ENTITY_ZOMBIE_ATTACK_DOOR_WOOD", "entity.zombie.attack_door_wood", 375);
    public static final Sound ENTITY_ZOMBIE_ATTACK_IRON_DOOR      = new Sound("ENTITY_ZOMBIE_ATTACK_IRON_DOOR", "entity.zombie.attack_iron_door", 376);
    public static final Sound ENTITY_ZOMBIE_BREAK_DOOR_WOOD       = new Sound("ENTITY_ZOMBIE_BREAK_DOOR_WOOD", "entity.zombie.break_door_wood", 377);
    public static final Sound ENTITY_ZOMBIE_CURE                  = new Sound("ENTITY_ZOMBIE_CURE", "entity.zombie.cure", 378);
    public static final Sound ENTITY_ZOMBIE_DEATH                 = new Sound("ENTITY_ZOMBIE_DEATH", "entity.zombie.death", 379);
    public static final Sound ENTITY_ZOMBIE_HURT                  = new Sound("ENTITY_ZOMBIE_HURT", "entity.zombie.hurt", 380);
    public static final Sound ENTITY_ZOMBIE_INFECT                = new Sound("ENTITY_ZOMBIE_INFECT", "entity.zombie.infect", 381);
    public static final Sound ENTITY_ZOMBIE_STEP                  = new Sound("ENTITY_ZOMBIE_STEP", "entity.zombie.step", 382);
    public static final Sound ENTITY_ZOMBIE_UNFECT                = new Sound("ENTITY_ZOMBIE_UNFECT", "entity.zombie.unfect", 383);
    public static final Sound ENTITY_ZOMBIE_HORSE_AMBIENT         = new Sound("ENTITY_ZOMBIE_HORSE_AMBIENT", "entity.zombie_horse.ambient", 384);
    public static final Sound ENTITY_ZOMBIE_HORSE_DEATH           = new Sound("ENTITY_ZOMBIE_HORSE_DEATH", "entity.zombie_horse.death", 385);
    public static final Sound ENTITY_ZOMBIE_HORSE_HURT            = new Sound("ENTITY_ZOMBIE_HORSE_HURT", "entity.zombie_horse.hurt", 386);
    public static final Sound ENTITY_ZOMBIE_PIG_AMBIENT           = new Sound("ENTITY_ZOMBIE_PIG_AMBIENT", "entity.zombie_pig.ambient", 387);
    public static final Sound ENTITY_ZOMBIE_PIG_ANGRY             = new Sound("ENTITY_ZOMBIE_PIG_ANGRY", "entity.zombie_pig.angry", 388);
    public static final Sound ENTITY_ZOMBIE_PIG_DEATH             = new Sound("ENTITY_ZOMBIE_PIG_DEATH", "entity.zombie_pig.death", 389);
    public static final Sound ENTITY_ZOMBIE_PIG_HURT              = new Sound("ENTITY_ZOMBIE_PIG_HURT", "entity.zombie_pig.hurt", 390);
    public static final Sound ITEM_ARMOR_EQUIP_CHAIN              = new Sound("ITEM_ARMOR_EQUIP_CHAIN", "item.armor.equip_chain", 391);
    public static final Sound ITEM_ARMOR_EQUIP_DIAMOND            = new Sound("ITEM_ARMOR_EQUIP_DIAMOND", "item.armor.equip_diamond", 392);
    public static final Sound ITEM_ARMOR_EQUIP_GENERIC            = new Sound("ITEM_ARMOR_EQUIP_GENERIC", "item.armor.equip_generic", 393);
    public static final Sound ITEM_ARMOR_EQUIP_GOLD               = new Sound("ITEM_ARMOR_EQUIP_GOLD", "item.armor.equip_gold", 394);
    public static final Sound ITEM_ARMOR_EQUIP_IRON               = new Sound("ITEM_ARMOR_EQUIP_IRON", "item.armor.equip_iron", 395);
    public static final Sound ITEM_ARMOR_EQUIP_LEATHER            = new Sound("ITEM_ARMOR_EQUIP_LEATHER", "item.armor.equip_leather", 396);
    public static final Sound ITEM_BOTTLE_FILL                    = new Sound("ITEM_BOTTLE_FILL", "item.bottle.fill", 397);
    public static final Sound ITEM_BOTTLE_FILL_DRAGONBREATH       = new Sound("ITEM_BOTTLE_FILL_DRAGONBREATH", "item.bottle.fill_dragonbreath", 398);
    public static final Sound ITEM_BUCKET_EMPTY                   = new Sound("ITEM_BUCKET_EMPTY", "item.bucket.empty", 399);
    public static final Sound ITEM_BUCKET_EMPTY_LAVA              = new Sound("ITEM_BUCKET_EMPTY_LAVA", "item.bucket.empty_lava", 400);
    public static final Sound ITEM_BUCKET_FILL                    = new Sound("ITEM_BUCKET_FILL", "item.bucket.fill", 401);
    public static final Sound ITEM_BUCKET_FILL_LAVA               = new Sound("ITEM_BUCKET_FILL_LAVA", "item.bucket.fill_lava", 402);
    public static final Sound ITEM_CHORUS_FRUIT_TELEPORT          = new Sound("ITEM_CHORUS_FRUIT_TELEPORT", "item.chorus_fruit.teleport", 403);
    public static final Sound ITEM_FIRECHARGE_USE                 = new Sound("ITEM_FIRECHARGE_USE", "item.firecharge.use", 404);
    public static final Sound ITEM_FLINTANDSTEEL_USE              = new Sound("ITEM_FLINTANDSTEEL_USE", "item.flintandsteel.use", 405);
    public static final Sound ITEM_HOE_TILL                       = new Sound("ITEM_HOE_TILL", "item.hoe.till", 406);
    public static final Sound ITEM_SHOVEL_FLATTEN                 = new Sound("ITEM_SHOVEL_FLATTEN", "item.shovel.flatten", 407);
    public static final Sound MUSIC_CREATIVE                      = new Sound("MUSIC_CREATIVE", "music.creative", 408);
    public static final Sound MUSIC_CREDITS                       = new Sound("MUSIC_CREDITS", "music.credits", 409);
    public static final Sound MUSIC_DRAGON                        = new Sound("MUSIC_DRAGON", "music.dragon", 410);
    public static final Sound MUSIC_END                           = new Sound("MUSIC_END", "music.end", 411);
    public static final Sound MUSIC_GAME                          = new Sound("MUSIC_GAME", "music.game", 412);
    public static final Sound MUSIC_MENU                          = new Sound("MUSIC_MENU", "music.menu", 413);
    public static final Sound MUSIC_NETHER                        = new Sound("MUSIC_NETHER", "music.nether", 414);
    public static final Sound RECORD_11                           = new Sound("RECORD_11", "record.11", 415);
    public static final Sound RECORD_13                           = new Sound("RECORD_13", "record.13", 416);
    public static final Sound RECORD_BLOCKS                       = new Sound("RECORD_BLOCKS", "record.blocks", 417);
    public static final Sound RECORD_CAT                          = new Sound("RECORD_CAT", "record.cat", 418);
    public static final Sound RECORD_CHIRP                        = new Sound("RECORD_CHIRP", "record.chirp", 419);
    public static final Sound RECORD_FAR                          = new Sound("RECORD_FAR", "record.far", 420);
    public static final Sound RECORD_MALL                         = new Sound("RECORD_MALL", "record.mall", 421);
    public static final Sound RECORD_MELLOHI                      = new Sound("RECORD_MELLOHI", "record.mellohi", 422);
    public static final Sound RECORD_STAL                         = new Sound("RECORD_STAL", "record.stal", 423);
    public static final Sound RECORD_STRAD                        = new Sound("RECORD_STRAD", "record.strad", 424);
    public static final Sound RECORD_WAIT                         = new Sound("RECORD_WAIT", "record.wait", 425);
    public static final Sound RECORD_WARD                         = new Sound("RECORD_WARD", "record.ward", 426);
    public static final Sound UI_BUTTON_CLICK                     = new Sound("UI_BUTTON_CLICK", "ui.button.click", 427);
    public static final Sound WEATHER_RAIN                        = new Sound("WEATHER_RAIN", "weather.rain", 428);
    public static final Sound WEATHER_RAIN_ABOVE                  = new Sound("WEATHER_RAIN_ABOVE", "weather.rain.above", 429);

    private static final CaseInsensitiveMap<Sound> byName = new CaseInsensitiveMap<>(430, SMALL_LOAD_FACTOR);
    private static final Int2ObjectMap<Sound>      byId   = new Int2ObjectOpenHashMap<>(430, SMALL_LOAD_FACTOR);

    /**
     * Minecraft string id.
     */
    protected final String name;
    /**
     * Minecraft numeric id.
     */
    protected final int    id;

    public Sound(final String enumName, final int enumId, final String name, final int id)
    {
        super(enumName, enumId);
        this.name = name;
        this.id = id;
    }

    public Sound(final String enumName, final String name, final int id)
    {
        super(enumName);
        this.name = name;
        this.id = id;
    }

    /**
     * Returns sound minecraft id.
     *
     * @return sound minecraft id.
     */
    public String getSoundName()
    {
        return this.name;
    }

    /**
     * Returns sound minecraft numeric id.
     *
     * @return sound minecraft numeric id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Get one of Sound entry by its minecraft id.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getByName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Get one of Sound entry by its minecraft numeric id.
     *
     * @param id id of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getById(final int id)
    {
        return byId.get(id);
    }

    /**
     * Register new {@link Sound} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Sound element)
    {
        ASimpleEnum.register(Sound.class, element);
        byName.put(element.getSoundName(), element);
        byId.put(element.id, element);
    }

    /**
     * Get one of {@link Sound} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Sound.class, ordinal);
    }

    /**
     * Get one of Sound entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getByEnumName(final String name)
    {
        return getByEnumName(Sound.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Sound[] values()
    {
        final Int2ObjectMap<Sound> map = getByEnumOrdinal(Sound.class);
        return map.values().toArray(new Sound[map.size()]);
    }

    static
    {
        Sound.register(AMBIENT_CAVE);
        Sound.register(BLOCK_ANVIL_BREAK);
        Sound.register(BLOCK_ANVIL_DESTROY);
        Sound.register(BLOCK_ANVIL_FALL);
        Sound.register(BLOCK_ANVIL_HIT);
        Sound.register(BLOCK_ANVIL_LAND);
        Sound.register(BLOCK_ANVIL_PLACE);
        Sound.register(BLOCK_ANVIL_STEP);
        Sound.register(BLOCK_ANVIL_USE);
        Sound.register(BLOCK_BREWING_STAND_BREW);
        Sound.register(BLOCK_CHEST_CLOSE);
        Sound.register(BLOCK_CHEST_LOCKED);
        Sound.register(BLOCK_CHEST_OPEN);
        Sound.register(BLOCK_CHORUS_FLOWER_DEATH);
        Sound.register(BLOCK_CHORUS_FLOWER_GROW);
        Sound.register(BLOCK_CLOTH_BREAK);
        Sound.register(BLOCK_CLOTH_FALL);
        Sound.register(BLOCK_CLOTH_HIT);
        Sound.register(BLOCK_CLOTH_PLACE);
        Sound.register(BLOCK_CLOTH_STEP);
        Sound.register(BLOCK_COMPARATOR_CLICK);
        Sound.register(BLOCK_DISPENSER_DISPENSE);
        Sound.register(BLOCK_DISPENSER_FAIL);
        Sound.register(BLOCK_DISPENSER_LAUNCH);
        Sound.register(BLOCK_END_GATEWAY_SPAWN);
        Sound.register(BLOCK_ENDERCHEST_CLOSE);
        Sound.register(BLOCK_ENDERCHEST_OPEN);
        Sound.register(BLOCK_FENCE_GATE_CLOSE);
        Sound.register(BLOCK_FENCE_GATE_OPEN);
        Sound.register(BLOCK_FIRE_AMBIENT);
        Sound.register(BLOCK_FIRE_EXTINGUISH);
        Sound.register(BLOCK_FURNACE_FIRE_CRACKLE);
        Sound.register(BLOCK_GLASS_BREAK);
        Sound.register(BLOCK_GLASS_FALL);
        Sound.register(BLOCK_GLASS_HIT);
        Sound.register(BLOCK_GLASS_PLACE);
        Sound.register(BLOCK_GLASS_STEP);
        Sound.register(BLOCK_GRASS_BREAK);
        Sound.register(BLOCK_GRASS_FALL);
        Sound.register(BLOCK_GRASS_HIT);
        Sound.register(BLOCK_GRASS_PLACE);
        Sound.register(BLOCK_GRASS_STEP);
        Sound.register(BLOCK_GRAVEL_BREAK);
        Sound.register(BLOCK_GRAVEL_FALL);
        Sound.register(BLOCK_GRAVEL_HIT);
        Sound.register(BLOCK_GRAVEL_PLACE);
        Sound.register(BLOCK_GRAVEL_STEP);
        Sound.register(BLOCK_IRON_DOOR_CLOSE);
        Sound.register(BLOCK_IRON_DOOR_OPEN);
        Sound.register(BLOCK_LADDER_BREAK);
        Sound.register(BLOCK_LADDER_FALL);
        Sound.register(BLOCK_LADDER_HIT);
        Sound.register(BLOCK_LADDER_PLACE);
        Sound.register(BLOCK_LADDER_STEP);
        Sound.register(BLOCK_LAVA_AMBIENT);
        Sound.register(BLOCK_LAVA_EXTINGUISH);
        Sound.register(BLOCK_LAVA_POP);
        Sound.register(BLOCK_LEVER_CLICK);
        Sound.register(BLOCK_METAL_BREAK);
        Sound.register(BLOCK_METAL_FALL);
        Sound.register(BLOCK_METAL_HIT);
        Sound.register(BLOCK_METAL_PLACE);
        Sound.register(BLOCK_METAL_STEP);
        Sound.register(BLOCK_METAL_PRESSUREPLATE_CLICK_OFF);
        Sound.register(BLOCK_METAL_PRESSUREPLATE_CLICK_ON);
        Sound.register(BLOCK_NOTE_BASEDRUM);
        Sound.register(BLOCK_NOTE_BASS);
        Sound.register(BLOCK_NOTE_HARP);
        Sound.register(BLOCK_NOTE_HAT);
        Sound.register(BLOCK_NOTE_SNARE);
        Sound.register(BLOCK_PISTON_CONTRACT);
        Sound.register(BLOCK_PISTON_EXTEND);
        Sound.register(BLOCK_PORTAL_AMBIENT);
        Sound.register(BLOCK_PORTAL_TRAVEL);
        Sound.register(BLOCK_PORTAL_TRIGGER);
        Sound.register(BLOCK_REDSTONE_TORCH_BURNOUT);
        Sound.register(BLOCK_SAND_BREAK);
        Sound.register(BLOCK_SAND_FALL);
        Sound.register(BLOCK_SAND_HIT);
        Sound.register(BLOCK_SAND_PLACE);
        Sound.register(BLOCK_SAND_STEP);
        Sound.register(BLOCK_SLIME_BREAK);
        Sound.register(BLOCK_SLIME_FALL);
        Sound.register(BLOCK_SLIME_HIT);
        Sound.register(BLOCK_SLIME_PLACE);
        Sound.register(BLOCK_SLIME_STEP);
        Sound.register(BLOCK_SNOW_BREAK);
        Sound.register(BLOCK_SNOW_FALL);
        Sound.register(BLOCK_SNOW_HIT);
        Sound.register(BLOCK_SNOW_PLACE);
        Sound.register(BLOCK_SNOW_STEP);
        Sound.register(BLOCK_STONE_BREAK);
        Sound.register(BLOCK_STONE_FALL);
        Sound.register(BLOCK_STONE_HIT);
        Sound.register(BLOCK_STONE_PLACE);
        Sound.register(BLOCK_STONE_STEP);
        Sound.register(BLOCK_STONE_BUTTON_CLICK_OFF);
        Sound.register(BLOCK_STONE_BUTTON_CLICK_ON);
        Sound.register(BLOCK_STONE_PRESSUREPLATE_CLICK_OFF);
        Sound.register(BLOCK_STONE_PRESSUREPLATE_CLICK_ON);
        Sound.register(BLOCK_TRAPDOOR_CLOSE);
        Sound.register(BLOCK_TRAPDOOR_OPEN);
        Sound.register(BLOCK_TRIPWIRE_ATTACH);
        Sound.register(BLOCK_TRIPWIRE_CLICK_OFF);
        Sound.register(BLOCK_TRIPWIRE_CLICK_ON);
        Sound.register(BLOCK_TRIPWIRE_DETACH);
        Sound.register(BLOCK_WATER_AMBIENT);
        Sound.register(BLOCK_WATERLILY_PLACE);
        Sound.register(BLOCK_WOOD_BREAK);
        Sound.register(BLOCK_WOOD_FALL);
        Sound.register(BLOCK_WOOD_HIT);
        Sound.register(BLOCK_WOOD_PLACE);
        Sound.register(BLOCK_WOOD_STEP);
        Sound.register(BLOCK_WOOD_BUTTON_CLICK_OFF);
        Sound.register(BLOCK_WOOD_BUTTON_CLICK_ON);
        Sound.register(BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF);
        Sound.register(BLOCK_WOOD_PRESSUREPLATE_CLICK_ON);
        Sound.register(BLOCK_WOODEN_DOOR_CLOSE);
        Sound.register(BLOCK_WOODEN_DOOR_OPEN);
        Sound.register(ENCHANT_THORNS_HIT);
        Sound.register(ENTITY_ARMORSTAND_BREAK);
        Sound.register(ENTITY_ARMORSTAND_FALL);
        Sound.register(ENTITY_ARMORSTAND_HIT);
        Sound.register(ENTITY_ARMORSTAND_PLACE);
        Sound.register(ENTITY_ARROW_HIT);
        Sound.register(ENTITY_ARROW_HIT_PLAYER);
        Sound.register(ENTITY_ARROW_SHOOT);
        Sound.register(ENTITY_BAT_AMBIENT);
        Sound.register(ENTITY_BAT_DEATH);
        Sound.register(ENTITY_BAT_HURT);
        Sound.register(ENTITY_BAT_TAKEOFF);
        Sound.register(ENTITY_BLAZE_AMBIENT);
        Sound.register(ENTITY_BLAZE_BURN);
        Sound.register(ENTITY_BLAZE_DEATH);
        Sound.register(ENTITY_BLAZE_HURT);
        Sound.register(ENTITY_BLAZE_SHOOT);
        Sound.register(ENTITY_BOBBER_SPLASH);
        Sound.register(ENTITY_BOBBER_THROW);
        Sound.register(ENTITY_CAT_AMBIENT);
        Sound.register(ENTITY_CAT_DEATH);
        Sound.register(ENTITY_CAT_HURT);
        Sound.register(ENTITY_CAT_PURR);
        Sound.register(ENTITY_CAT_PURREOW);
        Sound.register(ENTITY_CHICKEN_AMBIENT);
        Sound.register(ENTITY_CHICKEN_DEATH);
        Sound.register(ENTITY_CHICKEN_EGG);
        Sound.register(ENTITY_CHICKEN_HURT);
        Sound.register(ENTITY_CHICKEN_STEP);
        Sound.register(ENTITY_COW_AMBIENT);
        Sound.register(ENTITY_COW_DEATH);
        Sound.register(ENTITY_COW_HURT);
        Sound.register(ENTITY_COW_MILK);
        Sound.register(ENTITY_COW_STEP);
        Sound.register(ENTITY_CREEPER_DEATH);
        Sound.register(ENTITY_CREEPER_HURT);
        Sound.register(ENTITY_CREEPER_PRIMED);
        Sound.register(ENTITY_DONKEY_AMBIENT);
        Sound.register(ENTITY_DONKEY_ANGRY);
        Sound.register(ENTITY_DONKEY_CHEST);
        Sound.register(ENTITY_DONKEY_DEATH);
        Sound.register(ENTITY_DONKEY_HURT);
        Sound.register(ENTITY_EGG_THROW);
        Sound.register(ENTITY_ELDER_GUARDIAN_AMBIENT);
        Sound.register(ENTITY_ELDER_GUARDIAN_AMBIENT_LAND);
        Sound.register(ENTITY_ELDER_GUARDIAN_CURSE);
        Sound.register(ENTITY_ELDER_GUARDIAN_DEATH);
        Sound.register(ENTITY_ELDER_GUARDIAN_DEATH_LAND);
        Sound.register(ENTITY_ELDER_GUARDIAN_HURT);
        Sound.register(ENTITY_ELDER_GUARDIAN_HURT_LAND);
        Sound.register(ENTITY_ENDERDRAGON_AMBIENT);
        Sound.register(ENTITY_ENDERDRAGON_DEATH);
        Sound.register(ENTITY_ENDERDRAGON_FLAP);
        Sound.register(ENTITY_ENDERDRAGON_GROWL);
        Sound.register(ENTITY_ENDERDRAGON_HURT);
        Sound.register(ENTITY_ENDERDRAGON_SHOOT);
        Sound.register(ENTITY_ENDERDRAGON_FIREBALL_EXPLODE);
        Sound.register(ENTITY_ENDEREYE_LAUNCH);
        Sound.register(ENTITY_ENDERMEN_AMBIENT);
        Sound.register(ENTITY_ENDERMEN_DEATH);
        Sound.register(ENTITY_ENDERMEN_HURT);
        Sound.register(ENTITY_ENDERMEN_SCREAM);
        Sound.register(ENTITY_ENDERMEN_STARE);
        Sound.register(ENTITY_ENDERMEN_TELEPORT);
        Sound.register(ENTITY_ENDERMITE_AMBIENT);
        Sound.register(ENTITY_ENDERMITE_DEATH);
        Sound.register(ENTITY_ENDERMITE_HURT);
        Sound.register(ENTITY_ENDERMITE_STEP);
        Sound.register(ENTITY_ENDERPEARL_THROW);
        Sound.register(ENTITY_EXPERIENCE_BOTTLE_THROW);
        Sound.register(ENTITY_EXPERIENCE_ORB_PICKUP);
        Sound.register(ENTITY_EXPERIENCE_ORB_TOUCH);
        Sound.register(ENTITY_FIREWORK_BLAST);
        Sound.register(ENTITY_FIREWORK_BLAST_FAR);
        Sound.register(ENTITY_FIREWORK_LARGE_BLAST);
        Sound.register(ENTITY_FIREWORK_LARGE_BLAST_FAR);
        Sound.register(ENTITY_FIREWORK_LAUNCH);
        Sound.register(ENTITY_FIREWORK_SHOOT);
        Sound.register(ENTITY_FIREWORK_TWINKLE);
        Sound.register(ENTITY_FIREWORK_TWINKLE_FAR);
        Sound.register(ENTITY_GENERIC_BIG_FALL);
        Sound.register(ENTITY_GENERIC_BURN);
        Sound.register(ENTITY_GENERIC_DEATH);
        Sound.register(ENTITY_GENERIC_DRINK);
        Sound.register(ENTITY_GENERIC_EAT);
        Sound.register(ENTITY_GENERIC_EXPLODE);
        Sound.register(ENTITY_GENERIC_EXTINGUISH_FIRE);
        Sound.register(ENTITY_GENERIC_HURT);
        Sound.register(ENTITY_GENERIC_SMALL_FALL);
        Sound.register(ENTITY_GENERIC_SPLASH);
        Sound.register(ENTITY_GENERIC_SWIM);
        Sound.register(ENTITY_GHAST_AMBIENT);
        Sound.register(ENTITY_GHAST_DEATH);
        Sound.register(ENTITY_GHAST_HURT);
        Sound.register(ENTITY_GHAST_SHOOT);
        Sound.register(ENTITY_GHAST_WARN);
        Sound.register(ENTITY_GUARDIAN_AMBIENT);
        Sound.register(ENTITY_GUARDIAN_AMBIENT_LAND);
        Sound.register(ENTITY_GUARDIAN_ATTACK);
        Sound.register(ENTITY_GUARDIAN_DEATH);
        Sound.register(ENTITY_GUARDIAN_DEATH_LAND);
        Sound.register(ENTITY_GUARDIAN_FLOP);
        Sound.register(ENTITY_GUARDIAN_HURT);
        Sound.register(ENTITY_GUARDIAN_HURT_LAND);
        Sound.register(ENTITY_HORSE_AMBIENT);
        Sound.register(ENTITY_HORSE_ANGRY);
        Sound.register(ENTITY_HORSE_ARMOR);
        Sound.register(ENTITY_HORSE_BREATHE);
        Sound.register(ENTITY_HORSE_DEATH);
        Sound.register(ENTITY_HORSE_EAT);
        Sound.register(ENTITY_HORSE_GALLOP);
        Sound.register(ENTITY_HORSE_HURT);
        Sound.register(ENTITY_HORSE_JUMP);
        Sound.register(ENTITY_HORSE_LAND);
        Sound.register(ENTITY_HORSE_SADDLE);
        Sound.register(ENTITY_HORSE_STEP);
        Sound.register(ENTITY_HORSE_STEP_WOOD);
        Sound.register(ENTITY_HOSTILE_BIG_FALL);
        Sound.register(ENTITY_HOSTILE_DEATH);
        Sound.register(ENTITY_HOSTILE_HURT);
        Sound.register(ENTITY_HOSTILE_SMALL_FALL);
        Sound.register(ENTITY_HOSTILE_SPLASH);
        Sound.register(ENTITY_HOSTILE_SWIM);
        Sound.register(ENTITY_IRONGOLEM_ATTACK);
        Sound.register(ENTITY_IRONGOLEM_DEATH);
        Sound.register(ENTITY_IRONGOLEM_HURT);
        Sound.register(ENTITY_IRONGOLEM_STEP);
        Sound.register(ENTITY_ITEM_BREAK);
        Sound.register(ENTITY_ITEM_PICKUP);
        Sound.register(ENTITY_ITEMFRAME_ADD_ITEM);
        Sound.register(ENTITY_ITEMFRAME_BREAK);
        Sound.register(ENTITY_ITEMFRAME_PLACE);
        Sound.register(ENTITY_ITEMFRAME_REMOVE_ITEM);
        Sound.register(ENTITY_ITEMFRAME_ROTATE_ITEM);
        Sound.register(ENTITY_LEASHKNOT_BREAK);
        Sound.register(ENTITY_LEASHKNOT_PLACE);
        Sound.register(ENTITY_LIGHTNING_IMPACT);
        Sound.register(ENTITY_LIGHTNING_THUNDER);
        Sound.register(ENTITY_LINGERINGPOTION_THROW);
        Sound.register(ENTITY_MAGMACUBE_DEATH);
        Sound.register(ENTITY_MAGMACUBE_HURT);
        Sound.register(ENTITY_MAGMACUBE_JUMP);
        Sound.register(ENTITY_MAGMACUBE_SQUISH);
        Sound.register(ENTITY_MINECART_INSIDE);
        Sound.register(ENTITY_MINECART_RIDING);
        Sound.register(ENTITY_MOOSHROOM_SHEAR);
        Sound.register(ENTITY_MULE_AMBIENT);
        Sound.register(ENTITY_MULE_DEATH);
        Sound.register(ENTITY_MULE_HURT);
        Sound.register(ENTITY_PAINTING_BREAK);
        Sound.register(ENTITY_PAINTING_PLACE);
        Sound.register(ENTITY_PIG_AMBIENT);
        Sound.register(ENTITY_PIG_DEATH);
        Sound.register(ENTITY_PIG_HURT);
        Sound.register(ENTITY_PIG_SADDLE);
        Sound.register(ENTITY_PIG_STEP);
        Sound.register(ENTITY_PLAYER_ATTACK_CRIT);
        Sound.register(ENTITY_PLAYER_ATTACK_KNOCKBACK);
        Sound.register(ENTITY_PLAYER_ATTACK_NODAMAGE);
        Sound.register(ENTITY_PLAYER_ATTACK_STRONG);
        Sound.register(ENTITY_PLAYER_ATTACK_SWEEP);
        Sound.register(ENTITY_PLAYER_ATTACK_WEAK);
        Sound.register(ENTITY_PLAYER_BIG_FALL);
        Sound.register(ENTITY_PLAYER_BURP);
        Sound.register(ENTITY_PLAYER_DEATH);
        Sound.register(ENTITY_PLAYER_HURT);
        Sound.register(ENTITY_PLAYER_LEVELUP);
        Sound.register(ENTITY_PLAYER_SMALL_FALL);
        Sound.register(ENTITY_PLAYER_SPLASH);
        Sound.register(ENTITY_PLAYER_SWIM);
        Sound.register(ENTITY_RABBIT_AMBIENT);
        Sound.register(ENTITY_RABBIT_ATTACK);
        Sound.register(ENTITY_RABBIT_DEATH);
        Sound.register(ENTITY_RABBIT_HURT);
        Sound.register(ENTITY_RABBIT_JUMP);
        Sound.register(ENTITY_SHEEP_AMBIENT);
        Sound.register(ENTITY_SHEEP_DEATH);
        Sound.register(ENTITY_SHEEP_HURT);
        Sound.register(ENTITY_SHEEP_SHEAR);
        Sound.register(ENTITY_SHEEP_STEP);
        Sound.register(ENTITY_SHIELD_BLOCK);
        Sound.register(ENTITY_SHIELD_BREAK);
        Sound.register(ENTITY_SHULKER_AMBIENT);
        Sound.register(ENTITY_SHULKER_CLOSE);
        Sound.register(ENTITY_SHULKER_DEATH);
        Sound.register(ENTITY_SHULKER_HURT);
        Sound.register(ENTITY_SHULKER_HURT_CLOSED);
        Sound.register(ENTITY_SHULKER_OPEN);
        Sound.register(ENTITY_SHULKER_SHOOT);
        Sound.register(ENTITY_SHULKER_TELEPORT);
        Sound.register(ENTITY_SHULKER_BULLET_HIT);
        Sound.register(ENTITY_SHULKER_BULLET_HURT);
        Sound.register(ENTITY_SILVERFISH_AMBIENT);
        Sound.register(ENTITY_SILVERFISH_DEATH);
        Sound.register(ENTITY_SILVERFISH_HURT);
        Sound.register(ENTITY_SILVERFISH_STEP);
        Sound.register(ENTITY_SKELETON_AMBIENT);
        Sound.register(ENTITY_SKELETON_DEATH);
        Sound.register(ENTITY_SKELETON_HURT);
        Sound.register(ENTITY_SKELETON_SHOOT);
        Sound.register(ENTITY_SKELETON_STEP);
        Sound.register(ENTITY_SKELETON_HORSE_AMBIENT);
        Sound.register(ENTITY_SKELETON_HORSE_DEATH);
        Sound.register(ENTITY_SKELETON_HORSE_HURT);
        Sound.register(ENTITY_SLIME_ATTACK);
        Sound.register(ENTITY_SLIME_DEATH);
        Sound.register(ENTITY_SLIME_HURT);
        Sound.register(ENTITY_SLIME_JUMP);
        Sound.register(ENTITY_SLIME_SQUISH);
        Sound.register(ENTITY_SMALL_MAGMACUBE_DEATH);
        Sound.register(ENTITY_SMALL_MAGMACUBE_HURT);
        Sound.register(ENTITY_SMALL_MAGMACUBE_SQUISH);
        Sound.register(ENTITY_SMALL_SLIME_DEATH);
        Sound.register(ENTITY_SMALL_SLIME_HURT);
        Sound.register(ENTITY_SMALL_SLIME_SQUISH);
        Sound.register(ENTITY_SNOWBALL_THROW);
        Sound.register(ENTITY_SNOWMAN_AMBIENT);
        Sound.register(ENTITY_SNOWMAN_DEATH);
        Sound.register(ENTITY_SNOWMAN_HURT);
        Sound.register(ENTITY_SNOWMAN_SHOOT);
        Sound.register(ENTITY_SPIDER_AMBIENT);
        Sound.register(ENTITY_SPIDER_DEATH);
        Sound.register(ENTITY_SPIDER_HURT);
        Sound.register(ENTITY_SPIDER_STEP);
        Sound.register(ENTITY_SPLASH_POTION_BREAK);
        Sound.register(ENTITY_SPLASH_POTION_THROW);
        Sound.register(ENTITY_SQUID_AMBIENT);
        Sound.register(ENTITY_SQUID_DEATH);
        Sound.register(ENTITY_SQUID_HURT);
        Sound.register(ENTITY_TNT_PRIMED);
        Sound.register(ENTITY_VILLAGER_AMBIENT);
        Sound.register(ENTITY_VILLAGER_DEATH);
        Sound.register(ENTITY_VILLAGER_HURT);
        Sound.register(ENTITY_VILLAGER_NO);
        Sound.register(ENTITY_VILLAGER_TRADING);
        Sound.register(ENTITY_VILLAGER_YES);
        Sound.register(ENTITY_WITCH_AMBIENT);
        Sound.register(ENTITY_WITCH_DEATH);
        Sound.register(ENTITY_WITCH_DRINK);
        Sound.register(ENTITY_WITCH_HURT);
        Sound.register(ENTITY_WITCH_THROW);
        Sound.register(ENTITY_WITHER_AMBIENT);
        Sound.register(ENTITY_WITHER_BREAK_BLOCK);
        Sound.register(ENTITY_WITHER_DEATH);
        Sound.register(ENTITY_WITHER_HURT);
        Sound.register(ENTITY_WITHER_SHOOT);
        Sound.register(ENTITY_WITHER_SPAWN);
        Sound.register(ENTITY_WOLF_AMBIENT);
        Sound.register(ENTITY_WOLF_DEATH);
        Sound.register(ENTITY_WOLF_GROWL);
        Sound.register(ENTITY_WOLF_HURT);
        Sound.register(ENTITY_WOLF_PANT);
        Sound.register(ENTITY_WOLF_SHAKE);
        Sound.register(ENTITY_WOLF_STEP);
        Sound.register(ENTITY_WOLF_WHINE);
        Sound.register(ENTITY_ZOMBIE_AMBIENT);
        Sound.register(ENTITY_ZOMBIE_ATTACK_DOOR_WOOD);
        Sound.register(ENTITY_ZOMBIE_ATTACK_IRON_DOOR);
        Sound.register(ENTITY_ZOMBIE_BREAK_DOOR_WOOD);
        Sound.register(ENTITY_ZOMBIE_CURE);
        Sound.register(ENTITY_ZOMBIE_DEATH);
        Sound.register(ENTITY_ZOMBIE_HURT);
        Sound.register(ENTITY_ZOMBIE_INFECT);
        Sound.register(ENTITY_ZOMBIE_STEP);
        Sound.register(ENTITY_ZOMBIE_UNFECT);
        Sound.register(ENTITY_ZOMBIE_HORSE_AMBIENT);
        Sound.register(ENTITY_ZOMBIE_HORSE_DEATH);
        Sound.register(ENTITY_ZOMBIE_HORSE_HURT);
        Sound.register(ENTITY_ZOMBIE_PIG_AMBIENT);
        Sound.register(ENTITY_ZOMBIE_PIG_ANGRY);
        Sound.register(ENTITY_ZOMBIE_PIG_DEATH);
        Sound.register(ENTITY_ZOMBIE_PIG_HURT);
        Sound.register(ITEM_ARMOR_EQUIP_CHAIN);
        Sound.register(ITEM_ARMOR_EQUIP_DIAMOND);
        Sound.register(ITEM_ARMOR_EQUIP_GENERIC);
        Sound.register(ITEM_ARMOR_EQUIP_GOLD);
        Sound.register(ITEM_ARMOR_EQUIP_IRON);
        Sound.register(ITEM_ARMOR_EQUIP_LEATHER);
        Sound.register(ITEM_BOTTLE_FILL);
        Sound.register(ITEM_BOTTLE_FILL_DRAGONBREATH);
        Sound.register(ITEM_BUCKET_EMPTY);
        Sound.register(ITEM_BUCKET_EMPTY_LAVA);
        Sound.register(ITEM_BUCKET_FILL);
        Sound.register(ITEM_BUCKET_FILL_LAVA);
        Sound.register(ITEM_CHORUS_FRUIT_TELEPORT);
        Sound.register(ITEM_FIRECHARGE_USE);
        Sound.register(ITEM_FLINTANDSTEEL_USE);
        Sound.register(ITEM_HOE_TILL);
        Sound.register(ITEM_SHOVEL_FLATTEN);
        Sound.register(MUSIC_CREATIVE);
        Sound.register(MUSIC_CREDITS);
        Sound.register(MUSIC_DRAGON);
        Sound.register(MUSIC_END);
        Sound.register(MUSIC_GAME);
        Sound.register(MUSIC_MENU);
        Sound.register(MUSIC_NETHER);
        Sound.register(RECORD_11);
        Sound.register(RECORD_13);
        Sound.register(RECORD_BLOCKS);
        Sound.register(RECORD_CAT);
        Sound.register(RECORD_CHIRP);
        Sound.register(RECORD_FAR);
        Sound.register(RECORD_MALL);
        Sound.register(RECORD_MELLOHI);
        Sound.register(RECORD_STAL);
        Sound.register(RECORD_STRAD);
        Sound.register(RECORD_WAIT);
        Sound.register(RECORD_WARD);
        Sound.register(UI_BUTTON_CLICK);
        Sound.register(WEATHER_RAIN);
        Sound.register(WEATHER_RAIN_ABOVE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
