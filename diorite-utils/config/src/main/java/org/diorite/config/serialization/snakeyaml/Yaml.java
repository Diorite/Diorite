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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.resolver.Resolver;

import org.diorite.commons.io.StringBuilderWriter;
import org.diorite.commons.threads.DioriteThreadUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.impl.ConfigTemplateResolver;
import org.diorite.config.serialization.Serialization;
import org.diorite.config.serialization.comments.DocumentComments;
import org.diorite.config.serialization.snakeyaml.emitter.Emitter;
import org.diorite.config.serialization.snakeyaml.emitter.Serializer;

/**
 * Public YAML interface. Each Thread must have its own instance.
 */
public class Yaml
{
    protected final Serialization   serialization;
    protected final Resolver        resolver;
    private         String          name;
    protected final YamlConstructor constructor;
    protected final Representer     representer;
    protected final DumperOptions   dumperOptions;

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param serialization
     *         serialization instance.
     */
    public Yaml(Serialization serialization)
    {
        this(serialization, new YamlConstructor(), new Representer(), new DumperOptions(), new Resolver());
    }

    /**
     * Create Yaml instance. It is safe to create a few instances and use them
     * in different Threads.
     *
     * @param serialization
     *         serialization instance.
     * @param constructor
     *         BaseConstructor to construct incoming documents
     * @param representer
     *         Representer to emit outgoing objects
     * @param dumperOptions
     *         DumperOptions to configure outgoing objects
     * @param resolver
     *         Resolver to detect implicit type
     */
    public Yaml(Serialization serialization, YamlConstructor constructor, Representer representer, DumperOptions dumperOptions, Resolver resolver)
    {
        representer.initMultiRepresenters();
        this.serialization = serialization;
        if (! constructor.isExplicitPropertyUtils())
        {
            constructor.setPropertyUtils(representer.getPropertyUtils());
        }
        else if (! representer.isExplicitPropertyUtils())
        {
            representer.setPropertyUtils(constructor.getPropertyUtils());
        }
        this.constructor = constructor;
        representer.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
        representer.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        representer.getPropertyUtils().setAllowReadOnlyProperties(dumperOptions.isAllowReadOnlyProperties());
        representer.setTimeZone(dumperOptions.getTimeZone());
        this.representer = representer;
        this.dumperOptions = dumperOptions;
        this.resolver = resolver;
        this.name = "Yaml:" + DioriteThreadUtils.getFullThreadName(Thread.currentThread());
    }

    /**
     * Serialize a Java object into a YAML String.
     *
     * @param data
     *         Java object to be Serialized to YAML
     * @param comments
     *         comments node.
     *
     * @return YAML String
     */
    public String toYamlWithComments(Object data, DocumentComments comments)
    {
        StringBuilderWriter stringWriter = new StringBuilderWriter();
        this.toYamlWithComments(data, stringWriter, comments);
        return stringWriter.toString();
    }

    /**
     * Serialize a Java object into a YAML String.
     *
     * @param data
     *         Java object to be Serialized to YAML
     *
     * @return YAML String
     */
    public String toYaml(Object data)
    {
        List<Object> list = new ArrayList<>(1);
        list.add(data);
        return this.toYaml(list.iterator());
    }

    /**
     * Produce the corresponding representation tree for a given Object.
     *
     * @param data
     *         instance to build the representation tree for
     *
     * @return representation tree
     *
     * @see <a href="http://yaml.org/spec/1.1/#id859333">Figure 3.1. Processing Overview</a>
     */
    public Node represent(@Nullable Object data)
    {
        return this.representer.represent(data);
    }

    /**
     * Serialize a sequence of Java objects into a YAML String.
     *
     * @param data
     *         Iterator with Objects
     *
     * @return YAML String with all the objects in proper sequence
     */
    public String toYaml(Iterator<?> data)
    {
        StringWriter buffer = new StringWriter();
        this.dumpAll(data, buffer, null);
        return buffer.toString();
    }

    /**
     * Serialize a Java object into a YAML stream.
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param output
     *         stream to write to
     */
    public void toYaml(Object data, Writer output)
    {
        List<Object> list = new ArrayList<>(1);
        list.add(data);
        this.dumpAll(list.iterator(), output, null);
    }

    /**
     * Serialize a sequence of Java objects into a YAML stream.
     *
     * @param data
     *         Iterator with Objects
     * @param output
     *         stream to write to
     */
    public void toYaml(Iterator<?> data, Writer output)
    {
        this.dumpAll(data, output, null);
    }

    /**
     * Serialize a Java object into a YAML stream.
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param output
     *         output for yaml.
     * @param comments
     *         comments node.
     */
    public void toYamlWithComments(Object data, Writer output, DocumentComments comments)
    {
        Serializer serializer =
                new Serializer(this.serialization, new Emitter(this.serialization, output, this.dumperOptions), this.resolver, this.dumperOptions, null);
        serializer.setComments(comments);
        try
        {
            serializer.open();
            Node node = this.representer.represent(data);
            serializer.serialize(node);
            serializer.close();
        }
        catch (IOException e)
        {
            throw new YAMLException(e);
        }
    }

    private void dumpAll(Iterator<?> data, Writer output, @Nullable Tag rootTag)
    {
        Serializer serializer =
                new Serializer(this.serialization, new Emitter(this.serialization, output, this.dumperOptions), this.resolver, this.dumperOptions, rootTag);
        try
        {
            serializer.open();
            while (data.hasNext())
            {
                Node node = this.representer.represent(data.next());
                serializer.serialize(node);
            }
            serializer.close();
        }
        catch (IOException e)
        {
            throw new YAMLException(e);
        }
    }

    /**
     * <p>
     * Serialize a Java object into a YAML string. Override the default root tag
     * with <code>rootTag</code>.
     * </p>
     *
     * <p>
     * This method is similar to <code>Yaml.dump(data)</code> except that the
     * root tag for the whole document is replaced with the given tag. This has
     * two main uses.
     * </p>
     *
     * <p>
     * First, if the root tag is replaced with a standard YAML tag, such as
     * <code>Tag.MAP</code>, then the object will be dumped as a map. The root
     * tag will appear as <code>!!map</code>, or blank (implicit !!map).
     * </p>
     *
     * <p>
     * Second, if the root tag is replaced by a different custom tag, then the
     * document appears to be a different type when loaded. For example, if an
     * instance of MyClass is dumped with the tag !!YourClass, then it will be
     * handled as an instance of YourClass when loaded.
     * </p>
     *
     * @param data
     *         Java object to be serialized to YAML
     * @param rootTag
     *         the tag for the whole YAML document. The tag should be Tag.MAP for a JavaBean to make the tag disappear (to use implicit tag !!map). If
     *         <code>null</code> is provided then the standard tag with the full class name is used.
     * @param flowStyle
     *         flow style for the whole document. See Chapter 10. Collection Styles http://yaml.org/spec/1.1/#id930798. If <code>null</code> is provided then
     *         the flow style from DumperOptions is used.
     *
     * @return YAML String
     */
    public String toYaml(Object data, Tag rootTag, @Nullable FlowStyle flowStyle)
    {
        FlowStyle oldStyle = this.representer.getDefaultFlowStyle();
        if (flowStyle != null)
        {
            this.representer.setDefaultFlowStyle(flowStyle);
        }
        List<Object> list = new ArrayList<>(1);
        list.add(data);
        StringWriter buffer = new StringWriter();
        this.dumpAll(list.iterator(), buffer, rootTag);
        this.representer.setDefaultFlowStyle(oldStyle);
        return buffer.toString();
    }

    /**
     * <p>
     * Serialize a Java object into a YAML string. Override the default root tag
     * with <code>Tag.MAP</code>.
     * </p>
     * <p>
     * This method is similar to <code>Yaml.dump(data)</code> except that the
     * root tag for the whole document is replaced with <code>Tag.MAP</code> tag
     * (implicit !!map).
     * </p>
     * <p>
     * Block Mapping is used as the collection style. See 10.2.2. Block Mappings
     * (http://yaml.org/spec/1.1/#id934537)
     * </p>
     *
     * @param data
     *         Java object to be serialized to YAML
     *
     * @return YAML String
     */
    public String toYamlAsMap(Object data)
    {
        return this.toYaml(data, Tag.MAP, FlowStyle.BLOCK);
    }

    /**
     * Serialize the representation tree into Events.
     *
     * @param data
     *         representation tree
     *
     * @return Event list
     *
     * @see <a href="http://yaml.org/spec/1.1/#id859333">Processing Overview</a>
     */
    public List<Event> serialize(Node data)
    {
        YamlSilentEmitter emitter = new YamlSilentEmitter();
        Serializer serializer = new Serializer(this.serialization, emitter, this.resolver, this.dumperOptions, null);
        try
        {
            serializer.open();
            serializer.serialize(data);
            serializer.close();
        }
        catch (IOException e)
        {
            throw new YAMLException(e);
        }
        return emitter.getEvents();
    }

    /**
     * Parse the only YAML document in a String and produce the corresponding
     * Java object. (Because the encoding in known BOM is not respected.)
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(String yaml)
    {
        return this.loadFromReader(new StreamReader(yaml), Object.class);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param io
     *         data to load from (BOM is respected and removed)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(InputStream io)
    {
        return this.loadFromReader(new StreamReader(new UnicodeReader(io)), Object.class);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param io
     *         data to load from (BOM must not be present)
     *
     * @return parsed object
     */
    @Nullable
    public Object fromYaml(Reader io)
    {
        return this.loadFromReader(new StreamReader(io), Object.class);
    }

    /**
     * Parse the only YAML document in a stream as configuration object.
     *
     * @param template
     *         template of config object.
     * @param io
     *         data to load from (BOM must not be present)
     * @param <T>
     *         type of config object.
     *
     * @return parsed object
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T extends Config> T fromYaml(ConfigTemplate<T> template, Reader io)
    {
        Composer composer = new Composer(new ParserImpl(new StreamReader(io)), new ConfigTemplateResolver(template));
        this.constructor.setComposer(composer);
        return (T) this.constructor.getSingleData(template.getConfigType());
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param <T>
     *         Class is defined by the second argument
     * @param io
     *         data to load from (BOM must not be present)
     * @param type
     *         Class of the object to be created
     *
     * @return parsed object
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T fromYaml(Reader io, Class<T> type)
    {
        return (T) this.loadFromReader(new StreamReader(io), type);
    }

    /**
     * Parse the only YAML document in a String and produce the corresponding
     * Java object. (Because the encoding in known BOM is not respected.)
     *
     * @param <T>
     *         Class is defined by the second argument
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     * @param type
     *         Class of the object to be created
     *
     * @return parsed object
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T fromYaml(String yaml, Class<T> type)
    {
        return (T) this.loadFromReader(new StreamReader(yaml), type);
    }

    /**
     * Parse the only YAML document in a stream and produce the corresponding
     * Java object.
     *
     * @param <T>
     *         Class is defined by the second argument
     * @param input
     *         data to load from (BOM is respected and removed)
     * @param type
     *         Class of the object to be created
     *
     * @return parsed object
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T fromYaml(InputStream input, Class<T> type)
    {
        return (T) this.loadFromReader(new StreamReader(new UnicodeReader(input)), type);
    }

    /**
     * Construct object from given node, node must have set type tags.
     *
     * @param node
     *         node to load.
     * @param <T>
     *         type of node.
     *
     * @return loaded object.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T fromYamlNode(Node node)
    {
        return (T) this.constructor.constructFromNode(node);
    }

    /**
     * Construct object from given node.
     *
     * @param node
     *         node to load.
     * @param type
     *         type of node.
     * @param <T>
     *         type of node.
     *
     * @return loaded object.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T fromYamlNode(Node node, Class<T> type)
    {
        node.setTag(new Tag(type));
        node.setType(type);
        return (T) this.constructor.constructFromNode(node);
    }

    @Nullable
    private Object loadFromReader(StreamReader sreader, Class<?> type)
    {
        Composer composer = new Composer(new ParserImpl(sreader), this.resolver);
        this.constructor.setComposer(composer);
        return this.constructor.getSingleData(type);
    }

    /**
     * Parse all YAML documents in a String and produce corresponding Java
     * objects. The documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return an iterator over the parsed Java objects in this String in proper sequence
     */
    public Iterable<Object> fromAllYaml(Reader yaml)
    {
        Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        Iterator<Object> result = new YamlLoaderIterator(this);
        return new YamlIterable(result);
    }

    /**
     * Parse all YAML documents in a String and produce corresponding Java
     * objects. (Because the encoding in known BOM is not respected.) The
     * documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM must not be present)
     *
     * @return an iterator over the parsed Java objects in this String in proper sequence
     */
    public Iterable<Object> fromAllYaml(String yaml)
    {
        return this.fromAllYaml(new StringReader(yaml));
    }

    /**
     * Parse all YAML documents in a stream and produce corresponding Java
     * objects. The documents are parsed only when the iterator is invoked.
     *
     * @param yaml
     *         YAML data to load from (BOM is respected and ignored)
     *
     * @return an iterator over the parsed Java objects in this stream in proper sequence
     */
    public Iterable<Object> fromAllYaml(InputStream yaml)
    {
        return this.fromAllYaml(new UnicodeReader(yaml));
    }

    /**
     * Parse the first YAML document in a stream and produce the corresponding
     * representation tree. (This is the opposite of the represent() method)
     *
     * @param yaml
     *         YAML document
     *
     * @return parsed root Node for the specified YAML document
     *
     * @see <a href="http://yaml.org/spec/1.1/#id859333">Figure 3.1. Processing Overview</a>
     */
    public Node compose(Reader yaml)
    {
        Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        return composer.getSingleNode();
    }

    /**
     * Parse all YAML documents in a stream and produce corresponding
     * representation trees.
     *
     * @param yaml
     *         stream of YAML documents
     *
     * @return parsed root Nodes for all the specified YAML documents
     *
     * @see <a href="http://yaml.org/spec/1.1/#id859333">Processing Overview</a>
     */
    public Iterable<Node> composeAll(Reader yaml)
    {
        Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        Iterator<Node> result = new YamlComposerNodeIterator(composer);
        return new YamlNodeIterable(result);
    }

    /**
     * Add an implicit scalar detector. If an implicit scalar value matches the
     * given regexp, the corresponding tag is assigned to the scalar.
     *
     * @param tag
     *         tag to assign to the node
     * @param regexp
     *         regular expression to match against
     * @param first
     *         a sequence of possible initial characters or null (which means any).
     */
    public void addImplicitResolver(Tag tag, Pattern regexp, String first)
    {
        this.resolver.addImplicitResolver(tag, regexp, first);
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    /**
     * Get a meaningful name. It simplifies debugging in a multi-threaded
     * environment. If nothing is set explicitly the address of the instance is
     * returned.
     *
     * @return human readable name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set a meaningful name to be shown in toString()
     *
     * @param name
     *         human readable name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Parse a YAML stream and produce parsing events.
     *
     * @param yaml
     *         YAML document(s)
     *
     * @return parsed events
     *
     * @see <a href="http://yaml.org/spec/1.1/#id859333">Processing Overview</a>
     */
    public Iterable<Event> parse(Reader yaml)
    {
        Parser parser = new ParserImpl(new StreamReader(yaml));
        Iterator<Event> result = new YamlParserEventIterator(parser);
        return new YamlEventIterable(result);
    }

    public void setBeanAccess(BeanAccess beanAccess)
    {
        this.constructor.getPropertyUtils().setBeanAccess(beanAccess);
        this.representer.getPropertyUtils().setBeanAccess(beanAccess);
    }
}