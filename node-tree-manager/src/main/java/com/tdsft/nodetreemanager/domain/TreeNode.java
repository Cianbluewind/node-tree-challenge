package com.tdsft.nodetreemanager.domain;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Node
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

@Id @GeneratedValue private Long id;

private Long uuid;

private Long parentId;

private Long rootNodeId;

private Integer height;

@Relationship(type="CHILDREN", direction = Direction.OUTGOING)
private List<TreeNode> children;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getParentId() {
	return parentId;
}

public Long getLabel() {
	return id;
}

public void setChildrenHeight(Integer parentHeight) { 
	if(this.children != null && !this.children.isEmpty()) { 
	this.children.forEach(children -> {children.setChildrenHeight(parentHeight+1);});
	}
	this.height = parentHeight + 1;
}

public void setParentId(Long parentId) {
	this.parentId = parentId;
}
public Long getRootNodeId() {
	return rootNodeId;
}
public void setRootNodeId(Long rootNodeId) {
	this.rootNodeId = rootNodeId;
}
public Integer getHeight() {
	return height;
}
public void setHeight(Integer height) {
	this.height = height;
}

public List<TreeNode> getChildren() {
	if(children == null) this.children = new LinkedList<TreeNode>();
	return children;
}

public void RemoveChildren(TreeNode node) {
	if(children == null) this.children = new LinkedList<TreeNode>();
	this.children.remove(node);
}

public void addChildren(TreeNode node) {
	if(children == null) this.children = new LinkedList<TreeNode>();
	this.children.add(node);
}

public void setChildren(List<TreeNode> children) {
	this.children = children;
}

public Long getUuid() {
	return uuid;
}

public void setUuid(Long uuid) {
	this.uuid = uuid;
}
}
