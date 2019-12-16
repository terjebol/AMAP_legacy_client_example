package com.equinor.servicebustest.ui;

import com.equinor.servicebustest.messages.MessageHandler;
import com.equinor.servicebustest.messages.MessagePackage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeComponent {
    private final DefaultTreeModel treeModel;
    protected JTree treeComponent;
    private final DefaultMutableTreeNode topNode;
    private static TreeComponent instance;

    public TreeComponent(String rootName) {
        topNode = new DefaultMutableTreeNode(rootName);
        treeModel = new DefaultTreeModel(topNode);
        treeComponent = new JTree(treeModel);
    }

    public static TreeComponent create(String rootName) {
        instance = new TreeComponent(rootName);
        return instance;
    }

    public static TreeComponent getInstance() {
        return instance;
    }

    public DefaultMutableTreeNode getTrajectoryNode(String trajectoryId) {
        for (int i = 0; i < topNode.getChildCount(); i++) {
            var node = topNode.getChildAt(i);
            if (node.toString().equals(trajectoryId)) {
                return (DefaultMutableTreeNode) node;
            }
        }
        return null;
    }

    public void addEvent(MessageHandler messageHandler, MessagePackage<?> eventMessagePackage) {
        var trajectory = eventMessagePackage.getInnerData();
        var trajectoryId = trajectory.getOrDefault("TrajectoryId", "UNKNOWN").toString();

        var existingNode = getTrajectoryNode(trajectoryId);
        if (existingNode == null) {
            existingNode = new DefaultMutableTreeNode(trajectoryId);
            topNode.add(existingNode);
            treeModel.nodesWereInserted(topNode, new int[] { topNode.getChildCount()-1});
        }

        var newNode = new DefaultMutableTreeNode(messageHandler.friendlyName()+":"+ eventMessagePackage);
        existingNode.add(newNode);
        var paths = treeComponent.getSelectionPaths();
        treeModel.nodesWereInserted(existingNode, new int[]{existingNode.getChildCount()-1});
        if (paths != null) {
            for (var path : paths) {
                treeComponent.setSelectionPath(path);
                treeComponent.expandPath(path);
            }
        }
    }
}
