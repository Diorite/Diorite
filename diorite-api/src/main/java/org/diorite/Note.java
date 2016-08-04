package org.diorite;

public class Note
{

    private static final Note[] NOTES = new Note[25];

    static
    {
        NOTES[0] = new Note(0, Tone.F, true, 0.5);
        NOTES[1] = new Note(1, Tone.G, false, 0.53);
        NOTES[2] = new Note(2, Tone.G, true, 0.56);
        NOTES[3] = new Note(3, Tone.A, false, 0.6);
        NOTES[4] = new Note(4, Tone.A, true, 0.63);
        NOTES[5] = new Note(5, Tone.B, false, 0.67);

        NOTES[6] = new Note(6, Tone.C, false, 0.7);
        NOTES[7] = new Note(7, Tone.C, true, 0.75);
        NOTES[8] = new Note(8, Tone.D, false, 0.8);
        NOTES[9] = new Note(9, Tone.D, true, 0.84);
        NOTES[10] = new Note(10, Tone.E, false, 0.9);
        NOTES[11] = new Note(11, Tone.F, false, 0.95);
        NOTES[12] = new Note(12, Tone.F, true, 1.0);
        NOTES[13] = new Note(13, Tone.G, false, 1.05);
        NOTES[14] = new Note(14, Tone.G, true, 1.1);
        NOTES[15] = new Note(15, Tone.A, false, 1.2);
        NOTES[16] = new Note(16, Tone.A, true, 1.25);
        NOTES[17] = new Note(17, Tone.B, false, 1.32);

        NOTES[18] = new Note(18, Tone.C, false, 1.4);
        NOTES[19] = new Note(19, Tone.C, true, 1.5);
        NOTES[20] = new Note(20, Tone.D, false, 1.6);
        NOTES[21] = new Note(21, Tone.D, true, 1.7);
        NOTES[22] = new Note(22, Tone.E, false, 1.8);
        NOTES[23] = new Note(23, Tone.F, false, 1.9);
        NOTES[24] = new Note(24, Tone.F, true, 2.0);
    }

    public static final Note G3 = NOTES[1];
    public static final Note A3 = NOTES[3];
    public static final Note B3 = NOTES[5];

    public static final Note C4 = NOTES[6];
    public static final Note D4 = NOTES[8];
    public static final Note E4 = NOTES[10];
    public static final Note F4 = NOTES[11];
    public static final Note G4 = NOTES[13];
    public static final Note A4 = NOTES[15];
    public static final Note B4 = NOTES[17];

    public static final Note C5 = NOTES[18];
    public static final Note D5 = NOTES[20];
    public static final Note E5 = NOTES[22];
    public static final Note F5 = NOTES[23];

    private final byte id;
    private final Tone tone;
    private final boolean sharp;
    private final double pitch;

    public Note(int id, Tone tone, boolean sharp, double pitch)
    {
        if (id < 0 || id > 24)
        {
            throw new IllegalArgumentException("Note ID have to be a number between 0 and 24");
        }

        this.id = (byte) id;
        this.tone = tone;
        this.sharp = sharp;
        this.pitch = pitch;
    }

    public Note next()
    {
        for (int i = id; id < NOTES.length; i++)
        {
            Note note = NOTES[i];

            if (note.hasSharp())
            {
                continue;
            }

            return note;
        }

        return null;
    }

    public Note previous()
    {
        for (int i = id; id > -1; i--)
        {
            Note note = NOTES[i];

            if (note.hasSharp())
            {
                continue;
            }

            return note;
        }

        return null;
    }

    public Note primary()
    {
        return hasSharp() ? previous() : this;
    }

    public Note sharp()
    {
        return id + 1 < NOTES.length ? NOTES[id + 1] : null;
    }

    public Note flat()
    {
        return id - 1 > -1 ? NOTES[id - 1] : null;
    }

    public boolean hasSharp()
    {
        return sharp;
    }

    public int getMinecraftOctave()
    {
        return id / 12;
    }

    public int getOctave()
    {
        if (id < 6)
        {
            return 1;
        }
        return id > 5 && id < 18 ? 2 : 3;
    }

    public double getPitch()
    {
        return pitch;
    }

    public byte getID()
    {
        return id;
    }

    public String getName()
    {
        return tone.getName() + (hasSharp() ? "#" : "") + getOctave();
    }

    public static Note[] values() {
        return NOTES;
    }

    public enum Tone
    {

        C(0),
        D(1),
        E(2),
        F(3),
        G(4),
        A(5),
        B(6);

        private final byte id;

        Tone(int id)
        {
            this.id = (byte) id;
        }

        public Tone next()
        {
            return id + 1 < 6 ? values()[id + 1] : Tone.C;
        }

        public Tone previous()
        {
            return id - 1 > -1 ? values()[id - 1] : Tone.B;
        }

        public String getName()
        {
            return toString();
        }

        public byte getID()
        {
            return id;
        }

    }

}
