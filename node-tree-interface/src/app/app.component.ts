import { Component, OnInit } from '@angular/core';
import { Node, Edge, ClusterNode } from '@swimlane/ngx-graph';
import { NgbTooltip} from '@ng-bootstrap/ng-bootstrap'
import { TreeNode } from './domain/tree-node';
import { NodeTreeService } from './node-tree.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  selectedNode: TreeNode;
  searchString: String
  parentNodeId: number;
  links = [];
  nodes = [];
  childNodes : String = ""
  ngOnInit() { 
    
    this.nodeTreeService.getNodes().subscribe(nodes => { 
      this.nodes = this.nodeTreeService.modelNodes(nodes);
      this.links = this.nodeTreeService.modelLinks(nodes);
    })
  }
  
constructor(private nodeTreeService: NodeTreeService){ }



  setSelectedNode(node) {
    this.selectedNode = node;
  }

  setParentNode(event) {
    this.parentNodeId = event;
  }
  searchForNode() { 
    let node = this.nodes.find(node => { return node.label == this.searchString})
    if(!node) alert("Select a valid Node!")
    else this.selectedNode = node
  }

  getChildNodes() {
    this.nodeTreeService.getNodeChildren(this.selectedNode.label).subscribe(nodes => { 
      this.childNodes = "["+nodes.map(treeNode => treeNode.uuid).toString()+"]"
  })
}

  changeNodeParent(){ 
    let node = this.nodes.find(node => { return node.label == this.parentNodeId})
    if(!node) alert("Select a valid Node!")
    else { 
      if(!this.selectedNode.label || this.selectedNode.label == this.selectedNode.rootNodeId) alert("Select a valid Node!")
      this.nodeTreeService.changeParentNode(this.selectedNode.id, this.parentNodeId).subscribe(nodes => { 
        this.nodes = this.nodeTreeService.modelNodes(nodes);
        this.links = this.nodeTreeService.modelLinks(nodes);
      })
    }
    
  }
 getValidNodes(){ 
   return this.nodes.filter(node => node.label != this.selectedNode.id).map(node => node.label);
 }
}
