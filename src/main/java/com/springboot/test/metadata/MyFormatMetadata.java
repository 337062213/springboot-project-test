package com.springboot.test.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class MyFormatMetadata extends IIOMetadata {

    static final boolean standardMetadataFormatSupported = false;
    static final String nativeMetadataFormatName = "com.mycompany.imageio.MyFormatMetadata_1.0";
    static final String nativeMetadataFormatClassName =  "com.mycompany.imageio.MyFormatMetadata";
    static final String[] extraMetadataFormatNames = null;
    static final String[] extraMetadataFormatClassNames = null;

    List<String> keywords = new ArrayList<>();
    List<String> values = new ArrayList<>();

    public MyFormatMetadata() {
            super(standardMetadataFormatSupported, nativeMetadataFormatName,
                  nativeMetadataFormatClassName, extraMetadataFormatNames,
                  extraMetadataFormatClassNames);
    }

    public IIOMetadataFormat getMetadataFormat(String formatName) {
            if (!formatName.equals(nativeMetadataFormatName)) {
                    throw new IllegalArgumentException("Bad format name!");
            }
            return MyFormatMetadataFormat.getDefaultInstance();
    }


    public Node getAsTree(String formatName) {
            if (!formatName.equals(nativeMetadataFormatName)) {
                    throw new IllegalArgumentException("Bad format name!");
            }

            IIOMetadataNode root = new IIOMetadataNode(nativeMetadataFormatName);

            Iterator<String> keywordIter = keywords.iterator();
            Iterator<String> valueIter = values.iterator();
            while (keywordIter.hasNext()) {
                    IIOMetadataNode node = new IIOMetadataNode("KeywordValuePair");
                    node.setAttribute("keyword", (String)keywordIter.next());
                    node.setAttribute("value", (String)valueIter.next());
                    root.appendChild(node);
            }

            return root;
    }


    public boolean isReadOnly() {
        return false;
    }

    public void reset() {
        this.keywords = new ArrayList<String>();
        this.values = new ArrayList<String>();
    }

    public void mergeTree(String formatName, Node root)
            throws IIOInvalidTreeException {
            if (!formatName.equals(nativeMetadataFormatName)) {
                    throw new IllegalArgumentException("Bad format name!");
            }

            Node node = root;
            if (!node.getNodeName().equals(nativeMetadataFormatName)) {
                    fatal(node, "Root must be " + nativeMetadataFormatName);
            }
            node = node.getFirstChild();
            while (node != null) {
                    if (!node.getNodeName().equals("KeywordValuePair")) {
                            fatal(node, "Node name not KeywordValuePair!");
                    }
                    NamedNodeMap attributes = node.getAttributes();
                    Node keywordNode = attributes.getNamedItem("keyword");
                    Node valueNode = attributes.getNamedItem("value");
                    if (keywordNode == null || valueNode == null) {
                            fatal(node, "Keyword or value missing!");
                    }

                    keywords.add((String)keywordNode.getNodeValue());
                    values.add((String)valueNode.getNodeValue());

                    node = node.getNextSibling();
            }
    }

    private void fatal(Node node, String reason)
            throws IIOInvalidTreeException {
            throw new IIOInvalidTreeException(reason, node);
    }
}

