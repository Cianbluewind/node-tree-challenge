package com.tdsft.nodetreemanager.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdsft.nodetreemanager.domain.TreeNode;
import com.tdsft.nodetreemanager.services.NodeTreeService;

@RestController
@CrossOrigin(origins="*", exposedHeaders="Access-Control-Allow-Origin")
public class NodeTreeController {

	
	@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	}    
	
	@Autowired
	NodeTreeService nodeTreeService;
	
	@GetMapping("/nodes")
	@CrossOrigin(origins="*", exposedHeaders="Access-Control-Allow-Origin")
	public Collection<TreeNode> getAllNodes() { 
		return nodeTreeService.getAllNodes();
	}
	
	@GetMapping("/node/{id}")
	@CrossOrigin(origins="*", exposedHeaders="Access-Control-Allow-Origin")
	public TreeNode getNodeById(@PathVariable("id") Long id) {
		return nodeTreeService.getNodeById(id);
		
	}
	@GetMapping("/node/{id}/children")
	@CrossOrigin(origins="*", exposedHeaders="Access-Control-Allow-Origin")
	public Collection<TreeNode> getNodeChildren(@PathVariable("id") Long id) {
		return nodeTreeService.getNodeChildren(id);
	}

	@PostMapping("/updateParentNode")
	@CrossOrigin(origins="*", exposedHeaders="Access-Control-Allow-Origin")
	public Collection<TreeNode> updateParentNode(@Param("nodeId") Long nodeId,@Param("parentId") Long parentId) { 
		return nodeTreeService.updateParentNode(nodeId, parentId);
	}
}
