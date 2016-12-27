/*
 * This class is mostly created using SnakeYaml (https://bitbucket.org/asomov/snakeyaml/) library source code, so proper license is used:
 *
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 * TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
 *
 * 1. Definitions.
 *
 * "License" shall mean the terms and conditions for use, reproduction,
 * and distribution as defined by Sections 1 through 9 of this document.
 *
 * "Licensor" shall mean the copyright owner or entity authorized by
 * the copyright owner that is granting the License.
 *
 * "Legal Entity" shall mean the union of the acting entity and all
 * other entities that control, are controlled by, or are under common
 * control with that entity. For the purposes of this definition,
 * "control" means (i) the power, direct or indirect, to cause the
 * direction or management of such entity, whether by contract or
 * otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 *
 * "You" (or "Your") shall mean an individual or Legal Entity
 * exercising permissions granted by this License.
 *
 * "Source" form shall mean the preferred form for making modifications,
 * including but not limited to software source code, documentation
 * source, and configuration files.
 *
 * "Object" form shall mean any form resulting from mechanical
 * transformation or translation of a Source form, including but
 * not limited to compiled object code, generated documentation,
 * and conversions to other media types.
 *
 * "Work" shall mean the work of authorship, whether in Source or
 * Object form, made available under the License, as indicated by a
 * copyright notice that is included in or attached to the work
 * (an example is provided in the Appendix below).
 *
 * "Derivative Works" shall mean any work, whether in Source or Object
 * form, that is based on (or derived from) the Work and for which the
 * editorial revisions, annotations, elaborations, or other modifications
 * represent, as a whole, an original work of authorship. For the purposes
 * of this License, Derivative Works shall not include works that remain
 * separable from, or merely link (or bind by name) to the interfaces of,
 * the Work and Derivative Works thereof.
 *
 * "Contribution" shall mean any work of authorship, including
 * the original version of the Work and any modifications or additions
 * to that Work or Derivative Works thereof, that is intentionally
 * submitted to Licensor for inclusion in the Work by the copyright owner
 * or by an individual or Legal Entity authorized to submit on behalf of
 * the copyright owner. For the purposes of this definition, "submitted"
 * means any form of electronic, verbal, or written communication sent
 * to the Licensor or its representatives, including but not limited to
 * communication on electronic mailing lists, source code control systems,
 * and issue tracking systems that are managed by, or on behalf of, the
 * Licensor for the purpose of discussing and improving the Work, but
 * excluding communication that is conspicuously marked or otherwise
 * designated in writing by the copyright owner as "Not a Contribution."
 *
 * "Contributor" shall mean Licensor and any individual or Legal Entity
 * on behalf of whom a Contribution has been received by Licensor and
 * subsequently incorporated within the Work.
 *
 * 2. Grant of Copyright License. Subject to the terms and conditions of
 * this License, each Contributor hereby grants to You a perpetual,
 * worldwide, non-exclusive, no-charge, royalty-free, irrevocable
 * copyright license to reproduce, prepare Derivative Works of,
 * publicly display, publicly perform, sublicense, and distribute the
 * Work and such Derivative Works in Source or Object form.
 *
 * 3. Grant of Patent License. Subject to the terms and conditions of
 * this License, each Contributor hereby grants to You a perpetual,
 * worldwide, non-exclusive, no-charge, royalty-free, irrevocable
 * (except as stated in this section) patent license to make, have made,
 * use, offer to sell, sell, import, and otherwise transfer the Work,
 * where such license applies only to those patent claims licensable
 * by such Contributor that are necessarily infringed by their
 * Contribution(s) alone or by combination of their Contribution(s)
 * with the Work to which such Contribution(s) was submitted. If You
 * institute patent litigation against any entity (including a
 * cross-claim or counterclaim in a lawsuit) alleging that the Work
 * or a Contribution incorporated within the Work constitutes direct
 * or contributory patent infringement, then any patent licenses
 * granted to You under this License for that Work shall terminate
 * as of the date such litigation is filed.
 *
 * 4. Redistribution. You may reproduce and distribute copies of the
 * Work or Derivative Works thereof in any medium, with or without
 * modifications, and in Source or Object form, provided that You
 * meet the following conditions:
 *
 * (a) You must give any other recipients of the Work or
 * Derivative Works a copy of this License; and
 *
 * (b) You must cause any modified files to carry prominent notices
 * stating that You changed the files; and
 *
 * (c) You must retain, in the Source form of any Derivative Works
 * that You distribute, all copyright, patent, trademark, and
 * attribution notices from the Source form of the Work,
 * excluding those notices that do not pertain to any part of
 * the Derivative Works; and
 *
 * (d) If the Work includes a "NOTICE" text file as part of its
 * distribution, then any Derivative Works that You distribute must
 * include a readable copy of the attribution notices contained
 * within such NOTICE file, excluding those notices that do not
 * pertain to any part of the Derivative Works, in at least one
 * of the following places: within a NOTICE text file distributed
 * as part of the Derivative Works; within the Source form or
 * documentation, if provided along with the Derivative Works; or,
 * within a display generated by the Derivative Works, if and
 * wherever such third-party notices normally appear. The contents
 * of the NOTICE file are for informational purposes only and
 * do not modify the License. You may add Your own attribution
 * notices within Derivative Works that You distribute, alongside
 * or as an addendum to the NOTICE text from the Work, provided
 * that such additional attribution notices cannot be construed
 * as modifying the License.
 *
 * You may add Your own copyright statement to Your modifications and
 * may provide additional or different license terms and conditions
 * for use, reproduction, or distribution of Your modifications, or
 * for any such Derivative Works as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with
 * the conditions stated in this License.
 *
 * 5. Submission of Contributions. Unless You explicitly state otherwise,
 * any Contribution intentionally submitted for inclusion in the Work
 * by You to the Licensor shall be under the terms and conditions of
 * this License, without any additional terms or conditions.
 * Notwithstanding the above, nothing herein shall supersede or modify
 * the terms of any separate license agreement you may have executed
 * with Licensor regarding such Contributions.
 *
 * 6. Trademarks. This License does not grant permission to use the trade
 * names, trademarks, service marks, or product names of the Licensor,
 * except as required for reasonable and customary use in describing the
 * origin of the Work and reproducing the content of the NOTICE file.
 *
 * 7. Disclaimer of Warranty. Unless required by applicable law or
 * agreed to in writing, Licensor provides the Work (and each
 * Contributor provides its Contributions) on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied, including, without limitation, any warranties or conditions
 * of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
 * PARTICULAR PURPOSE. You are solely responsible for determining the
 * appropriateness of using or redistributing the Work and assume any
 * risks associated with Your exercise of permissions under this License.
 *
 * 8. Limitation of Liability. In no event and under no legal theory,
 * whether in tort (including negligence), contract, or otherwise,
 * unless required by applicable law (such as deliberate and grossly
 * negligent acts) or agreed to in writing, shall any Contributor be
 * liable to You for damages, including any direct, indirect, special,
 * incidental, or consequential damages of any character arising as a
 * result of this License or out of the use or inability to use the
 * Work (including but not limited to damages for loss of goodwill,
 * work stoppage, computer failure or malfunction, or any and all
 * other commercial damages or losses), even if such Contributor
 * has been advised of the possibility of such damages.
 *
 * 9. Accepting Warranty or Additional Liability. While redistributing
 * the Work or Derivative Works thereof, You may choose to offer,
 * and charge a fee for, acceptance of support, warranty, indemnity,
 * or other liability obligations and/or rights consistent with this
 * License. However, in accepting such obligations, You may act only
 * on Your own behalf and on Your sole responsibility, not on behalf
 * of any other Contributor, and only if You agree to indemnify,
 * defend, and hold each Contributor harmless for any liability
 * incurred by, or claims asserted against, such Contributor by reason
 * of your accepting any such warranty or additional liability.
 *
 * END OF TERMS AND CONDITIONS
 */
package org.diorite.config.serialization.snakeyaml;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;

public abstract class AbstractRepresent extends Representer.AbstractRepresent
{
    public AbstractRepresent(Representer representer)
    {
        super(representer);
    }

    public final Node representUUID(UUID uuid)
    {
        return this.representer.representScalar(this.representer.getTag(uuid.getClass(), new Tag(UUID.class)), uuid.toString());
    }

    public final Node representString(String data)
    {
        Tag tag = Tag.STR;
        Character style = null;
        if (StreamReader.NON_PRINTABLE.matcher(data).find())
        {
            tag = Tag.BINARY;
            char[] binary;
            try
            {
                binary = Base64Coder.encode(data.getBytes("UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                throw new YAMLException(e);
            }
            data = String.valueOf(binary);
            style = '|';
        }
        // if no other scalar style is explicitly set, use literal style for
        // multiline scalars
        if ((this.getDefaultScalarStyle() == null) && Representer.MULTILINE_PATTERN.matcher(data).find())
        {
            style = '|';
        }
        return this.representer.representScalar(tag, data, style);
    }

    public final Node representSet(Set<?> data)
    {
        Map<Object, Object> value = new LinkedHashMap<>(data.size());
        for (Object key : data)
        {
            value.put(key, null);
        }
        return this.representer.representMapping(this.representer.getTag(data.getClass(), Tag.SET), value, null);
    }

    public final Node representPrimitiveArray(Object data)
    {
        Class<?> type = data.getClass().getComponentType();

        if (byte.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asByteList(data), null);
        }
        if (short.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asShortList(data), null);
        }
        if (int.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asIntList(data), null);
        }
        if (long.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asLongList(data), null);
        }
        if (float.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asFloatList(data), null);
        }
        if (double.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asDoubleList(data), null);
        }
        if (char.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asCharList(data), null);
        }
        if (boolean.class == type)
        {
            return this.representer.representSequence(Tag.SEQ, this.asBooleanList(data), null);
        }

        throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
    }

    public final Node representNumber(Number data)
    {
        Tag tag;
        String value;
        if ((data instanceof Byte) || (data instanceof Short) || (data instanceof Integer) || (data instanceof Long) || (data instanceof BigInteger))
        {
            tag = Tag.INT;
            value = data.toString();
        }
        else
        {
            tag = Tag.FLOAT;
            if (data.equals(Double.NaN))
            {
                value = ".NaN";
            }
            else if (data.equals(Double.POSITIVE_INFINITY))
            {
                value = ".inf";
            }
            else if (data.equals(Double.NEGATIVE_INFINITY))
            {
                value = "-.inf";
            }
            else
            {
                value = data.toString();
            }
        }
        return this.representer.representScalar(this.representer.getTag(data.getClass(), tag), value);
    }

    public final Node representMap(Map<?, ?> data)
    {
        return this.representer.representMapping(this.representer.getTag(data.getClass(), Tag.MAP), data, null);
    }

    public final Node representList(List<?> data)
    {
        return this.representer.representSequence(this.representer.getTag(data.getClass(), Tag.SEQ), data, null);
    }

    public final Node representCollection(Collection<?> data)
    {
        return this.representList(new ArrayList<>(data));
    }

    public final Node representIterator(Iterator<?> data)
    {
        return this.representer.representSequence(this.representer.getTag(data.getClass(), Tag.SEQ), new IteratorWrapper(data), null);
    }

    public final Node representEnum(Enum<?> data)
    {
        return this.representer.representScalar(this.representer.getTag(data.getClass(), new Tag(data.getClass())), data.name());
    }

    public final Node representDate(Date data)
    {
        // because SimpleDateFormat ignores timezone we have to use Calendar
        Calendar calendar = Calendar.getInstance((this.representer.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : this.representer.timeZone);
        calendar.setTime(data);
        return this.representCalendar(calendar);
    }

    public final Node representByteArray(byte[] data)
    {
        char[] binary = Base64Coder.encode(data);
        return this.representer.representScalar(Tag.BINARY, String.valueOf(binary), '|');
    }

    public final Node representBoolean(Boolean data)
    {
        return this.representBoolean(data, Boolean.TRUE.toString(), Boolean.FALSE.toString());
    }

    public final Node representBoolean(Boolean data, String trueValue, String falseValue)
    {
        String value = data ? trueValue : falseValue;
        return this.representer.representScalar(Tag.BOOL, value);
    }

    public final Node representArray(Object[] data)
    {
        List<Object> list = Arrays.asList(data);
        return this.representer.representSequence(Tag.SEQ, list, null);
    }

    @SuppressWarnings("MagicNumber")
    public final Node representCalendar(Calendar data)
    {
        // because SimpleDateFormat ignores timezone we have to use Calendar
        int years = data.get(Calendar.YEAR);
        int months = data.get(Calendar.MONTH) + 1; // 0..12
        int days = data.get(Calendar.DAY_OF_MONTH); // 1..31
        int hour24 = data.get(Calendar.HOUR_OF_DAY); // 0..24
        int minutes = data.get(Calendar.MINUTE); // 0..59
        int seconds = data.get(Calendar.SECOND); // 0..59
        int millis = data.get(Calendar.MILLISECOND);
        StringBuilder buffer = new StringBuilder(String.valueOf(years));
        while (buffer.length() < 4)
        {
            // ancient years
            buffer.insert(0, "0");
        }
        buffer.append("-");
        if (months < 10)
        {
            buffer.append("0");
        }
        buffer.append(String.valueOf(months));
        buffer.append("-");
        if (days < 10)
        {
            buffer.append("0");
        }
        buffer.append(String.valueOf(days));
        buffer.append("T");
        if (hour24 < 10)
        {
            buffer.append("0");
        }
        buffer.append(String.valueOf(hour24));
        buffer.append(":");
        if (minutes < 10)
        {
            buffer.append("0");
        }
        buffer.append(String.valueOf(minutes));
        buffer.append(":");
        if (seconds < 10)
        {
            buffer.append("0");
        }
        buffer.append(String.valueOf(seconds));
        if (millis > 0)
        {
            if (millis < 10)
            {
                buffer.append(".00");
            }
            else if (millis < 100)
            {
                buffer.append(".0");
            }
            else
            {
                buffer.append(".");
            }
            buffer.append(String.valueOf(millis));
        }
        if (TimeZone.getTimeZone("UTC").equals(data.getTimeZone()))
        {
            buffer.append("Z");
        }
        else
        {
            // Get the Offset from GMT taking DST into account
            int gmtOffset = data.getTimeZone().getOffset(data.get(Calendar.ERA),
                                                         data.get(Calendar.YEAR), data.get(Calendar.MONTH),
                                                         data.get(Calendar.DAY_OF_MONTH), data.get(Calendar.DAY_OF_WEEK),
                                                         data.get(Calendar.MILLISECOND));
            int minutesOffset = gmtOffset / (60 * 1000);
            int hoursOffset = minutesOffset / 60;
            int partOfHour = minutesOffset % 60;
            buffer.append((hoursOffset > 0) ? "+" : "").append(hoursOffset).append(":").append((partOfHour < 10) ? ("0" + partOfHour) : partOfHour);
        }
        return this.representer.representScalar(this.representer.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
    }

    public Node tryRepresentJavaBean(Object data) throws IntrospectionException
    {
        return this.representer.representJavaBean(this.representer.getProperties(data.getClass()), data);
    }

    public Node representJavaBean(Object data) throws YAMLException
    {
        try
        {
            return this.tryRepresentJavaBean(data);
        }
        catch (IntrospectionException e)
        {
            throw new YAMLException(e);
        }
    }

    protected final List<Byte> asByteList(Object in)
    {
        byte[] array = (byte[]) in;
        List<Byte> list = new ArrayList<>(array.length);
        for (byte anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Short> asShortList(Object in)
    {
        short[] array = (short[]) in;
        List<Short> list = new ArrayList<>(array.length);
        for (short anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Integer> asIntList(Object in)
    {
        int[] array = (int[]) in;
        List<Integer> list = new ArrayList<>(array.length);
        for (int anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Long> asLongList(Object in)
    {
        long[] array = (long[]) in;
        List<Long> list = new ArrayList<>(array.length);
        for (long anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Float> asFloatList(Object in)
    {
        float[] array = (float[]) in;
        List<Float> list = new ArrayList<>(array.length);
        for (float anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Double> asDoubleList(Object in)
    {
        double[] array = (double[]) in;
        List<Double> list = new ArrayList<>(array.length);
        for (double anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Character> asCharList(Object in)
    {
        char[] array = (char[]) in;
        List<Character> list = new ArrayList<>(array.length);
        for (char anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }

    protected final List<Boolean> asBooleanList(Object in)
    {
        boolean[] array = (boolean[]) in;
        List<Boolean> list = new ArrayList<>(array.length);
        for (boolean anArray : array)
        {
            list.add(anArray);
        }
        return list;
    }
}
