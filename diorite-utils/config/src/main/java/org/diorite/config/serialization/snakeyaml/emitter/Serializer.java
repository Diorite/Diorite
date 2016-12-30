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

package org.diorite.config.serialization.snakeyaml.emitter;

import javax.annotation.Nullable;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.Version;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.ImplicitTuple;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import org.yaml.snakeyaml.serializer.SerializerException;

import org.diorite.config.serialization.Serialization;
import org.diorite.config.serialization.comments.DocumentComments;

public final class Serializer
{
    private final     Serialization       serialization;
    private final     Emitable            emitter;
    private final     Resolver            resolver;
    private           boolean             explicitStart;
    private           boolean             explicitEnd;
    @Nullable private Version             useVersion;
    private           Map<String, String> useTags;
    private           Set<Node>           serializedNodes;
    private           Map<Node, String>   anchors;
    private           AnchorGenerator     anchorGenerator;
    @Nullable private Boolean             closed;
    @Nullable private Tag                 explicitRoot;

    private DocumentComments        comments    = DocumentComments.getEmpty();
    private Set<Collection<String>> commentsSet = new HashSet<>(20);

    boolean checkCommentsSet(String[] path)
    {
        this.comments.fixPath(path);
        List<String> key = List.of(path);
        return this.commentsSet.add(key);
    }

    public Serializer(Serialization serialization, Emitable emitter, Resolver resolver, DumperOptions opts, @Nullable Tag rootTag)
    {
        this.serialization = serialization;
        this.emitter = emitter;
        this.resolver = resolver;
        this.explicitStart = opts.isExplicitStart();
        this.explicitEnd = opts.isExplicitEnd();
        if (opts.getVersion() != null)
        {
            this.useVersion = opts.getVersion();
        }
        this.useTags = opts.getTags();
        this.serializedNodes = new HashSet<>(50);
        this.anchors = new HashMap<>(10);
        this.anchorGenerator = opts.getAnchorGenerator();
        this.closed = null;
        this.explicitRoot = rootTag;
    }

    public DocumentComments getComments()
    {
        return this.comments;
    }

    public void setComments(DocumentComments comments)
    {
        this.comments = comments;
    }

    public void open() throws IOException
    {
        if (this.closed == null)
        {
            this.emitter.emit(new StreamStartEvent(null, null));
            this.closed = Boolean.FALSE;
        }
        else if (Boolean.TRUE.equals(this.closed))
        {
            throw new SerializerException("serializer is closed");
        }
        else
        {
            throw new SerializerException("serializer is already opened");
        }
    }

    public void close() throws IOException
    {
        if (this.closed == null)
        {
            throw new SerializerException("serializer is not opened");
        }
        else if (! Boolean.TRUE.equals(this.closed))
        {
            this.emitter.emit(new StreamEndEvent(null, null));
            this.closed = Boolean.TRUE;
        }
    }

    public void serialize(Node node) throws IOException
    {
        if (this.closed == null)
        {
            throw new SerializerException("serializer is not opened");
        }
        if (this.closed)
        {
            throw new SerializerException("serializer is closed");
        }
//        if (this.explicitRoot != null)
//        {
//            if (this.explicitRoot.getValue().startsWith(Tag.PREFIX))
//            {
//                try
//                {
//                    Class<?> type = Class.forName(this.explicitRoot.getClassName(), false, Thread.currentThread().getContextClassLoader());
//                    node.setType(type);
//                }
//                catch (Exception ignored)
//                {
//                }
//            }
//        }
        this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion, this.useTags));
        this.anchorNode(node);
        if (this.explicitRoot != null)
        {
            node.setTag(this.explicitRoot);
        }

        this.serializeNode(node, null, new LinkedList<>());
        this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
    }

    private void anchorNode(Node node)
    {
        if (node.getNodeId() == NodeId.anchor)
        {
            node = ((AnchorNode) node).getRealNode();
        }
        Tag tag = node.getTag();
//        if (tag.getValue().startsWith(Tag.PREFIX))
//        {
//            try
//            {
//                Class<?> type = Class.forName(tag.getClassName(), false, Thread.currentThread().getContextClassLoader());
//                node.setType(type);
//            }
//            catch (Exception ignored)
//            {
//            }
//        }
        if (this.anchors.containsKey(node))
        {
            String anchor = this.anchors.get(node);
            if (null == anchor)
            {
                anchor = this.anchorGenerator.nextAnchor(node);
                this.anchors.put(node, anchor);
            }
        }
        else
        {
            this.anchors.put(node, null);
            switch (node.getNodeId())
            {
                case sequence:
                    SequenceNode seqNode = (SequenceNode) node;
                    List<Node> list = seqNode.getValue();
                    for (Node item : list)
                    {
                        this.anchorNode(item);
                    }
                    break;
                case mapping:
                    MappingNode mnode = (MappingNode) node;
                    List<NodeTuple> map = mnode.getValue();
                    for (NodeTuple object : map)
                    {
                        Node key = object.getKeyNode();
                        Node value = object.getValueNode();
                        this.anchorNode(key);
                        this.anchorNode(value);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void serializeNode(Node node, @Nullable Node parent, LinkedList<String> commentPath) throws IOException
    {
        if (node.getNodeId() == NodeId.anchor)
        {
            node = ((AnchorNode) node).getRealNode();
        }
        String tAlias = this.anchors.get(node);
        if (this.serializedNodes.contains(node))
        {
            this.emitter.emit(new AliasEvent(tAlias, null, null));
        }
        else
        {
            this.serializedNodes.add(node);
            switch (node.getNodeId())
            {
                case scalar:
                    ScalarNode scalarNode = (ScalarNode) node;
                    Tag detectedTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
                    Tag defaultTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
                    String[] pathNodes = commentPath.toArray(new String[commentPath.size()]);
                    String comment;
                    if (this.checkCommentsSet(pathNodes))
                    {
                        comment = this.comments.getComment(pathNodes);
                    }
                    else
                    {
                        comment = null;
                    }
                    ImplicitTuple tuple = new ImplicitTupleExtension(node.getTag().equals(detectedTag), node.getTag().equals(defaultTag), comment);
                    ScalarEvent event = new ScalarEvent(tAlias, node.getTag().getValue(), tuple, scalarNode.getValue(), null, null, scalarNode.getStyle());
                    this.emitter.emit(event);
                    break;
                case sequence:
                    SequenceNode seqNode = (SequenceNode) node;
                    boolean implicitS = node.getTag().equals(this.resolver.resolve(NodeId.sequence, null, true));
                    this.emitter.emit(new SequenceStartEvent(tAlias, node.getTag().getValue(), implicitS, null, null, seqNode.getFlowStyle()));
                    List<Node> list = seqNode.getValue();
                    for (Node item : list)
                    {
                        this.serializeNode(item, node, commentPath);
                    }
                    this.emitter.emit(new SequenceEndEvent(null, null));
                    break;
                default:// instance of MappingNode
                    Tag implicitTag = this.resolver.resolve(NodeId.mapping, null, true);
                    boolean implicitM = node.getTag().equals(implicitTag);
                    this.emitter.emit(new MappingStartEvent(tAlias, node.getTag().getValue(), implicitM, null, null, ((CollectionNode) node).getFlowStyle()));
                    MappingNode mnode = (MappingNode) node;
                    List<NodeTuple> map = mnode.getValue();
                    for (NodeTuple row : map)
                    {
                        Node key = row.getKeyNode();
                        Node value = row.getValueNode();
                        if (key instanceof ScalarNode)
                        {
                            commentPath.add(((ScalarNode) key).getValue());
                        }
                        this.serializeNode(key, mnode, commentPath);
                        this.serializeNode(value, mnode, commentPath);
                        if (key instanceof ScalarNode)
                        {
                            commentPath.removeLast();
                        }
                    }
                    this.emitter.emit(new MappingEndEvent(null, null));
            }
        }
    }
}
