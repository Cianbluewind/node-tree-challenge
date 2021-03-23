# node-tree-challenge
Node Tree Challenge for Tradeshift

## Featuring
- [Node.js / npm]
- [Angular]
- [neo4j]
- [JAVA]
- [Spring]
- [Docker]

## Instructions

```
Install Docker
git clone github.com/Cianbluewind/node-tree-challenge.git
cd node-tree-challenge
docker-compose up 
```

## User Interface
>UI: localhost:4301
```
Click on a node to select it and see the related properties on the right side. 

Select a node from the dropdown and click on the "Change Parent" button to change the parent node of the selected node.

Click the "Get All Child Nodes" to get all (direct or indirect) child nodes of the selected node
```
## API
>API: localhost:8080
```
The API is located at localhost:8080 and it is comprised of the following endpoints:
/nodes - (GET) Get all the graph nodes.
/node/{id} - (GET) Get the node with the following UUID
/node/{id}/children - (GET) Get all the direct or indirect child nodes of the node with the following UUID
/updateParentNode?nodeId={nodeId}&parentId={parentId} - (POST)  Sets parent of the node with UUID = nodeId as the node with UUID = parentId
```
## NEO4J
>Neo4j: localhost:7474
```
Access Neo4j to get real time feedback on the graph and also for CRUD operations directly on the database for testing!
```
