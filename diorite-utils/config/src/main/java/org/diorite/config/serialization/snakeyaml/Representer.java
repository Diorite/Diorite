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

import javax.annotation.Nullable;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;

/**
 * Represent standard Java classes
 */
public class Representer extends BaseRepresenter
{
    private static final int MAX_WIDTH = 100;

    protected final Map<Class<?>, Tag> classTags;
    @Nullable protected TimeZone timeZone = null;

    public Representer()
    {
        this.nullRepresenter = new RepresentNull(this);
        this.representers.put(String.class, new RepresentString(this));
        this.representers.put(Boolean.class, new RepresentBoolean(this));
        this.representers.put(Character.class, new RepresentString(this));
        this.representers.put(UUID.class, new RepresentUuid(this));
        this.representers.put(byte[].class, new RepresentByteArray(this));

        Represent primitiveArray = new RepresentPrimitiveArray(this);
        this.representers.put(short[].class, primitiveArray);
        this.representers.put(int[].class, primitiveArray);
        this.representers.put(long[].class, primitiveArray);
        this.representers.put(float[].class, primitiveArray);
        this.representers.put(double[].class, primitiveArray);
        this.representers.put(char[].class, primitiveArray);
        this.representers.put(boolean[].class, primitiveArray);

        this.classTags = new HashMap<>(10);


        this.representers.put(null, new RepresentJavaBean(this));
    }

    public void addRepresenter(Class<?> type, Represent represent)
    {
        this.representers.put(type, represent);
        LinkedHashMap<Class<?>, Represent> multiRepresenters = (LinkedHashMap<Class<?>, Represent>) this.multiRepresenters;
        multiRepresenters.put(type, represent);
    }

    private boolean init = false;

    public void initMultiRepresenters()
    {
        if (this.init)
        {
            return;
        }
        this.init = true;
        this.multiRepresenters.put(Number.class, new RepresentNumber(this));
        this.multiRepresenters.put(List.class, new RepresentList(this));
        this.multiRepresenters.put(Map.class, new RepresentMap(this));
        this.multiRepresenters.put(Set.class, new RepresentSet(this));
        this.multiRepresenters.put(Iterator.class, new RepresentIterator(this));
        this.multiRepresenters.put(Object[].class, new RepresentArray(this));
        this.multiRepresenters.put(Date.class, new RepresentDate(this));
        this.multiRepresenters.put(Enum.class, new RepresentEnum(this));
        this.multiRepresenters.put(Calendar.class, new RepresentDate(this));
        this.multiRepresenters.put(Collection.class, new RepresentCollection(this));
    }

    public Tag getTag(Class<?> clazz, Tag defaultTag)
    {
        return this.classTags.getOrDefault(clazz, defaultTag);
    }

    /**
     * Define a tag for the <code>Class</code> to serialize.
     *
     * @param clazz
     *         <code>Class</code> which tag is changed
     * @param tag
     *         new tag to be used for every instance of the specified <code>Class</code>
     *
     * @return the previous tag associated with the <code>Class</code>
     */
    public Tag addClassTag(Class<?> clazz, @Nullable Tag tag)
    {
        if (tag == null)
        {
            throw new NullPointerException("Tag must be provided.");
        }
        return this.classTags.put(clazz, tag);
    }

    public static final Pattern MULTILINE_PATTERN = Pattern.compile("[\n\u0085\u2028\u2029]");

    @Override
    public Node representScalar(Tag tag, String value, @Nullable Character style)
    {
        return super.representScalar(tag, value, style);
    }

    @Override
    public Node representScalar(Tag tag, String value)
    {
        return super.representScalar(tag, value);
    }

    @Override
    public Node representSequence(Tag tag, Iterable<?> sequence, @Nullable Boolean flowStyle)
    {
        int size = 10;// default for ArrayList
        if (sequence instanceof Collection<?>)
        {
            size = ((Collection<?>) sequence).size();
        }
        List<Node> value = new ArrayList<>(size);
        SequenceNode node = new SequenceNode(tag, value, flowStyle);
        this.representedObjects.put(this.objectToRepresent, node);
        boolean bestStyle = true;
        long width = 0;
        for (Object item : sequence)
        {
            Node nodeItem = this.representData(item);
            if (bestStyle)
            {
                if ((nodeItem instanceof ScalarNode) && (((ScalarNode) nodeItem).getStyle() == null))
                {
                    String val = ((ScalarNode) nodeItem).getValue();
                    width += (val == null) ? 0 : val.length();
                    width += 2; // comma and space.
                    if (width > MAX_WIDTH)
                    {
                        bestStyle = false;
                    }
                }
                else
                {
                    bestStyle = false;
                }
            }
            value.add(nodeItem);
        }
        if (flowStyle == null)
        {
            // we ignore this for diorite representer, configs should look nice.
//            if (this.defaultFlowStyle != FlowStyle.AUTO)
//            {
//                node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
//            }
//            else
//            {
            node.setFlowStyle(bestStyle);
//            }
        }
        return node;
    }

    @Override
    public Node representMapping(Tag tag, Map<?, ?> mapping, @Nullable Boolean flowStyle)
    {
        List<NodeTuple> value = new ArrayList<>(mapping.size());
        MappingNode node = new MappingNode(tag, value, flowStyle);
        this.representedObjects.put(this.objectToRepresent, node);
        boolean bestStyle = true;
        for (Map.Entry<?, ?> entry : mapping.entrySet())
        {
            Node nodeKey = this.representData(entry.getKey());
            Node nodeValue;
            if (entry.getValue() != null)
            {
                nodeValue = this.representData(entry.getValue());
            }
            else
            {
                nodeValue = this.representScalar(Tag.NULL, "~");
            }
            if (! ((nodeKey instanceof ScalarNode) && (((ScalarNode) nodeKey).getStyle() == null)))
            {
                bestStyle = false;
            }
            if (! ((nodeValue instanceof ScalarNode) && (((ScalarNode) nodeValue).getStyle() == null)))
            {
                bestStyle = false;
            }
            value.add(new NodeTuple(nodeKey, nodeValue));
        }
        if (flowStyle == null)
        {
            if (this.defaultFlowStyle != FlowStyle.AUTO)
            {
                node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
            }
            else
            {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }

    @Nullable
    public TimeZone getTimeZone()
    {
        return this.timeZone;
    }

    public void setTimeZone(@Nullable TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    /**
     * Tag logic:<br>
     * - explicit root tag is set in serializer <br>
     * - if there is a predefined class tag it is used<br>
     * - a global tag with class name is always used as tag. The JavaBean parent
     * of the specified JavaBean may set another tag (tag:yaml.org,2002:map)
     * when the property class is the same as runtime class
     *
     * @param properties
     *         JavaBean getters
     * @param javaBean
     *         instance for Node
     *
     * @return Node to get serialized
     */
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean)
    {
        List<NodeTuple> value = new ArrayList<>(properties.size());
        Tag tag;
        Tag customTag = this.classTags.get(javaBean.getClass());
        tag = (customTag != null) ? customTag : new Tag(javaBean.getClass());
        // flow style will be chosen by BaseRepresenter
        MappingNode node = new MappingNode(tag, value, null);
        this.representedObjects.put(javaBean, node);
        boolean bestStyle = true;
        for (Property property : properties)
        {
            Object memberValue = property.get(javaBean);
            Tag customPropertyTag = (memberValue == null) ? null : this.classTags.get(memberValue.getClass());
            NodeTuple tuple = this.representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
            if (tuple == null)
            {
                continue;
            }
            if (((ScalarNode) tuple.getKeyNode()).getStyle() != null)
            {
                bestStyle = false;
            }
            Node nodeValue = tuple.getValueNode();
            if (! ((nodeValue instanceof ScalarNode) && (((ScalarNode) nodeValue).getStyle() == null)))
            {
                bestStyle = false;
            }
            value.add(tuple);
        }
        if (this.defaultFlowStyle != FlowStyle.AUTO)
        {
            node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
        }
        else
        {
            node.setFlowStyle(bestStyle);
        }
        return node;
    }

    /**
     * Represent one JavaBean property.
     *
     * @param javaBean
     *         - the instance to be represented
     * @param property
     *         - the property of the instance
     * @param propertyValue
     *         - value to be represented
     * @param customTag
     *         - user defined Tag
     *
     * @return NodeTuple to be used in a MappingNode. Return null to skip the property
     */
    @Nullable
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, @Nullable Object propertyValue, @Nullable Tag customTag)
    {
        ScalarNode nodeKey = (ScalarNode) this.representData(property.getName());
        // the first occurrence of the node must keep the tag
        boolean hasAlias = this.representedObjects.containsKey(propertyValue);

        Node nodeValue = this.representData(propertyValue);

        if ((propertyValue != null) && ! hasAlias)
        {
            NodeId nodeId = nodeValue.getNodeId();
            if (customTag == null)
            {
                if (nodeId == NodeId.scalar)
                {
                    if (propertyValue instanceof Enum<?>)
                    {
                        nodeValue.setTag(Tag.STR);
                    }
                }
                else
                {
                    if (nodeId == NodeId.mapping)
                    {
                        if (property.getType() == propertyValue.getClass())
                        {
                            if (! (propertyValue instanceof Map<?, ?>))
                            {
                                if (! nodeValue.getTag().equals(Tag.SET))
                                {
                                    nodeValue.setTag(Tag.MAP);
                                }
                            }
                        }
                    }
                    this.checkGlobalTag(property, nodeValue, propertyValue);
                }
            }
        }

        return new NodeTuple(nodeKey, nodeValue);
    }

    /**
     * Remove redundant global tag for a type safe (generic) collection if it is
     * the same as defined by the JavaBean property
     *
     * @param property
     *         - JavaBean property
     * @param node
     *         - representation of the property
     * @param object
     *         - instance represented by the node
     */
    @SuppressWarnings("unchecked")
    protected void checkGlobalTag(Property property, Node node, Object object)
    {
        // Skip primitive arrays.
        if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive())
        {
            return;
        }

        Class<?>[] arguments = property.getActualTypeArguments();
        if (arguments != null)
        {
            if (node.getNodeId() == NodeId.sequence)
            {
                // apply map tag where class is the same
                Class<?> t = arguments[0];
                SequenceNode snode = (SequenceNode) node;
                Iterable<Object> memberList = Collections.emptyList();
                if (object.getClass().isArray())
                {
                    memberList = Arrays.asList((Object[]) object);
                }
                else if (object instanceof Iterable<?>)
                {
                    // list
                    memberList = (Iterable<Object>) object;
                }
                Iterator<Object> iter = memberList.iterator();
                if (iter.hasNext())
                {
                    for (Node childNode : snode.getValue())
                    {
                        Object member = iter.next();
                        if (member != null)
                        {
                            if (t.equals(member.getClass()))
                            {
                                if (childNode.getNodeId() == NodeId.mapping)
                                {
                                    childNode.setTag(Tag.MAP);
                                }
                            }
                        }
                    }
                }
            }
            else if (object instanceof Set)
            {
                Class<?> t = arguments[0];
                MappingNode mnode = (MappingNode) node;
                Iterator<NodeTuple> iter = mnode.getValue().iterator();
                Set<?> set = (Set<?>) object;
                for (Object member : set)
                {
                    NodeTuple tuple = iter.next();
                    Node keyNode = tuple.getKeyNode();
                    if (t.equals(member.getClass()))
                    {
                        if (keyNode.getNodeId() == NodeId.mapping)
                        {
                            keyNode.setTag(Tag.MAP);
                        }
                    }
                }
            }
            else if (object instanceof Map)
            {
                Class<?> keyType = arguments[0];
                Class<?> valueType = arguments[1];
                MappingNode mnode = (MappingNode) node;
                for (NodeTuple tuple : mnode.getValue())
                {
                    this.resetTag(keyType, tuple.getKeyNode());
                    this.resetTag(valueType, tuple.getValueNode());
                }
            }
//            else
//            {
//                // the type for collection entries cannot be
//                // detected
//            }
        }
    }

    private void resetTag(Class<?> type, Node node)
    {
        Tag tag = node.getTag();
        if (tag.matches(type))
        {
            if (Enum.class.isAssignableFrom(type))
            {
                node.setTag(Tag.STR);
            }
            else
            {
                node.setTag(Tag.MAP);
            }
        }
    }

    /**
     * Get JavaBean properties to be serialised. The order is respected. This
     * method may be overridden to provide custom property selection or order.
     *
     * @param type
     *         - JavaBean to inspect the properties
     *
     * @return properties to serialise
     *
     * @throws IntrospectionException
     *         if property scan fails.
     */
    protected Set<Property> getProperties(Class<?> type) throws IntrospectionException
    {
        return this.getPropertyUtils().getProperties(type);
    }

    public abstract static class AbstractRepresent implements Represent
    {
        protected final Representer representer;

        public AbstractRepresent(Representer representer)
        {
            this.representer = representer;
        }

        @Nullable
        public final Character getDefaultScalarStyle()
        {
            return this.representer.defaultScalarStyle;
        }
    }
}
