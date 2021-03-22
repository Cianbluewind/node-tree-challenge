package com.tdsft.nodetreemanager.services;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdsft.nodetreemanager.domain.TreeNode;
import com.tdsft.nodetreemanager.repositories.TreeNodeRepository;
@Service
public class NodeTreeService {
	@Autowired
	TreeNodeRepository treeNodeRepository;
	
	public Optional<TreeNode> findById(Long id) { 
		return treeNodeRepository.findByUuid(id);
	}

	public Collection<TreeNode> getAllNodes() { 
		return treeNodeRepository.findAll().stream().collect(Collectors.toList());
	}
	

	public TreeNode getNodeById(Long id) {
		return treeNodeRepository.findById(id).orElse(null);
		
	}

	public Collection<TreeNode> getNodeChildren(Long id) {
		return treeNodeRepository.findAllChildren(id);
	}
	
	@Transactional
	public Collection<TreeNode> updateParentNode(Long nodeId,Long parentId) { 
 		TreeNode child = findById(nodeId).orElse(null);
		TreeNode parent = findById(parentId).orElse(null);
		if(child == null || parent == null) return null;
		Collection<TreeNode> subtree = getNodeChildren(nodeId);
		if(subtree.stream().anyMatch(treeNode -> { return treeNode.getUuid() == parent.getUuid();})) { 
				
				TreeNode childParent = findById(child.getParentId()).orElse(null);
				TreeNode parentParent = findById(parent.getParentId()).orElse(null);

				childParent.getChildren().removeIf(node -> { return node.getUuid() == child.getUuid(); });
				childParent.getChildren().add(parent);
				treeNodeRepository.save(childParent);
				
				parentParent.getChildren().removeIf(node -> { return node.getUuid() == parent.getUuid(); });
				treeNodeRepository.save(parentParent);
				
				TreeNode newChild = treeNodeRepository.findByUuid(nodeId).get();
				newChild.setParentId(parent.getUuid());
				
				parent.getChildren().add(newChild);
				parent.setChildrenHeight(childParent.getHeight());
				parent.setParentId(childParent.getUuid());
				treeNodeRepository.save(parent);
				treeNodeRepository.save(newChild);
		} else {
			
			TreeNode newChild = treeNodeRepository.findByUuid(nodeId).get();
			newChild.setParentId(parent.getUuid());
			treeNodeRepository.save(newChild);
			parent.getChildren().add(newChild);
			parent.setChildrenHeight(parent.getHeight() -1);
			treeNodeRepository.save(parent);
			TreeNode childParent = findById(child.getParentId()).orElse(null);
			childParent.getChildren().removeIf(node -> { return node.getUuid() == child.getUuid(); });
			treeNodeRepository.save(childParent);
			
}
		return treeNodeRepository.findAll().stream().collect(Collectors.toList());
	}
}
