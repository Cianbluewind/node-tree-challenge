package com.tdsft.nodetreemanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tdsft.nodetreemanager.domain.TreeNode;

@Repository
public interface TreeNodeRepository extends Neo4jRepository<TreeNode, Long>{
	
	  Optional<TreeNode> findByUuid(Long uuid);
		
	  @Query("MATCH (origin:TreeNode {uuid: $nodeId})-[:CHILDREN*]->(children) RETURN children")
	  @RestResource(exported = true)
	  @ResponseBody
	  List<TreeNode> findAllChildren(@Param("nodeId") Long nodeId);
	 
	  @Override
	  @RestResource(exported = false)
	  void deleteById(Long aLong);
	  
	  @Override
	  @RestResource(exported = false)
	  void deleteAll();
	  
	  @Query("MATCH (node:TreeNode) WHERE node.uuid = $nodeId MATCH (parent:TreeNode {uuid: node.parentId}) MATCH (newParent:TreeNode {uuid: $parentId}) MATCH (parent)-[r:CHILDREN]->(node) DELETE r CREATE (newParent)-[:CHILDREN]->(node)  set node.parentId = newParent.uuid RETURN newParent.height")
	  @RestResource(exported = true)
	  @ResponseBody
	  int setNewParent(@Param("nodeId") Long nodeId,@Param("parentId") Long parentId);
	  
	  @Query("MATCH (node:TreeNode{uuid: $nodeId}) MATCH (parent:TreeNode {uuid: $parentId}) MATCH (childParent:TreeNode {uuid: node.parentId}) MATCH (parentParent:TreeNode {uuid: parent.parentId}) MATCH (parentParent)-[s:CHILDREN]->(parent) MATCH (childParent)-[r:CHILDREN]->(node) set parent.parentId = childParent.uuid set node.parentId = parent.uuid DELETE s DELETE r CREATE (childParent)-[:CHILDREN]->(parent) CREATE (parent)-[:CHILDREN]->(node) return childParent.height")
	  @RestResource(exported = true)
	  @ResponseBody
	  int setNewParentOnSubTree(Long nodeId, Long parentId);
	  
}
