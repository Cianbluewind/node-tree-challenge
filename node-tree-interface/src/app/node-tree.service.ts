import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TreeNode } from './domain/tree-node';
import {environment}  from '../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class NodeTreeService {
  nodeUrl = '/nodes';
  nodeChildrenUrl = '/node/';
   constructor(private http: HttpClient) { }

  getNodes(): Observable<TreeNode[]> { 
    return this.http.get<TreeNode[]>(environment.apiURL +this.nodeUrl);
  }

  getNodeChildren(nodeId : number): Observable<TreeNode[]> { 
    return this.http.get<TreeNode[]>(environment.apiURL + this.nodeChildrenUrl + nodeId + "/children");
  }

  changeParentNode(nodeId: number, parentId: number): Observable<TreeNode[]> { 
    return this.http.post<TreeNode[]>(environment.apiURL + `/updateParentNode?nodeId=${nodeId}&parentId=${parentId}`, null);
  }

modelNodes(nodes: TreeNode[]) { 
    let nodeList = []
    console.log(nodes)
      nodes.forEach(node => { 
        nodeList.push({id: ""+node.uuid, label: ""+node.uuid, height:node.height, parentId: node.parentId, rootNodeId: node.rootNodeId})
       })
       console.warn(nodeList)
  return nodeList;
}

  modelLinks(nodes : TreeNode[]) { 
    let linkList = []
    nodes.forEach(node => {
      node.children.map(
        child => { 
      return {id:`${node.uuid}-${child.uuid}`,source:`${node.uuid}`, target:`${child.uuid}`,label:`${node.uuid}_${node.uuid}`};
    }).forEach(link => { 
      linkList.push(link)
    })
  })
  return linkList;
}
}
 