/****************************************************************************************
 * Copyright (C) 2010,2011 The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering
 * Project: GRR - Generating Random RDF
 * Author:	Daniel Blum, The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering, http://www.cs.huji.ac.il/~danieb12/
 *
 * This file is part of GRR.
 * GRR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Please note that no part of this code is officially supported by HUJI
 * (The Hebrew University of Jerusalem) or any of its developers.
 *
 *  GRR is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GRR (COPYRIGHT-gpl.txt).  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************************/

package JavaApi.RandomDataGraph.Patterns;

import JavaApi.RandomDataGraph.GraphBuildingBlocks.Edge;
import JavaApi.RandomDataGraph.GraphBuildingBlocks.Node;
import JavaApi.Samplers.DictionarySamplers.ExternalConstDictionarySampler;
import JavaApi.Samplers.DictionarySamplers.IDictionarySampler;
import JavaApi.Samplers.SamplerFunctions.SamplerFunction;
import JavaApi.Samplers.SamplerFunctions.TypePropertiesFunction;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.vocabulary.RDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * One of the key classes in this solution which represents a construction pattern
 * Each instance contains the following:
 * - A hash-map which maps between node-id to nodes which represent existing nodes that we
 * might want to update or connect to.
 * - A hash-map which maps between node-id to nodes which represents new nodes that will be
 * created and maybe connected to existing nodes
 * - A sampler function that associates each rdf-type to a given dictionary sampler that
 * enables us to generate random values for generating the new nodes and edges
 * - A type-property function that has the mapping of rdf-type to a list of properties. Used
 * for creating new properties for each newly created resource
 *
 * All of the above are used to represent a construction pattern that will be applied on the graph 
 */
public class ConstructionPattern {

    /**
     * Class Members
     */
    private HashMap<Integer, Node> _oldNodes;
    private HashMap<Integer, Node> _newNodes;
    private SamplerFunction _samplerFunction;
    private TypePropertiesFunction _typePropsFunction;

    /**
     * Constructor
     * @param oldNodes - A hash-map which maps between node-id to nodes which represent existing nodes that we
     * might want to update or connect to
     * @param newNodes - A hash-map which maps between node-id to nodes which represents new nodes that will be
     * created and maybe connected to existing nodes
     * @param sFunction - A sampler function that associates each rdf-type to a given dictionary sampler that
     * enables us to generate random values for generating the new nodes and edges
     * @param typePropsFunction - A type-property function that has the mapping of rdf-type to a list of properties. Used
     * for creating new properties for each newly created resource 
     */
    public ConstructionPattern(
            HashMap<Integer, Node> oldNodes,
            HashMap<Integer, Node> newNodes,
            SamplerFunction sFunction,
            TypePropertiesFunction typePropsFunction)
    {
        _oldNodes = oldNodes;
        _newNodes = newNodes;
        _samplerFunction = sFunction;
        _typePropsFunction = typePropsFunction;
    }

    /**
     * Public Methods
     */

    /**
     * Main method of this class which has 3 main steps:
     * 1) Creating new nodes with random labels and properties (if defined in the type-property
     *    mappings)
     * 2) Add the new edges to the new nodes (each node might point either to old or new nodes)
     * 3) Add the new edges to the old nodes (that might also point to a new node)
     * @param model - The model that this pattern will be applied on
     * @throws IOException - Depending on the different dictionary sampler used for creating the labels for
     * the new nodes, some read the labels from files (which might result in IOException) 
     */
    public void applyPatternOnModel(Model model) throws IOException {

        // Add the new nodes and assign them a dSampler (by the sampler-function) and give them a label
        // We don't set the new edges yet since we need to create all nodes first

        for (Integer id : _newNodes.keySet()) {
            // Get the node
            Node node = _newNodes.get(id);
            // Get the rdf:type and get the relevant d-sampler
            String rdfType = node.getRdfType();
            IDictionarySampler sampler = _samplerFunction.getDSampler(rdfType);
            // Generate a random label
            String randVal = sampler.getRandomLabel();
            String[] typeParts = rdfType.split(":");
            if (typeParts.length!=2)
                throw new IllegalArgumentException("The node's type has illegal format: " + rdfType + " while expecting the following format: <alias-namespace:type>");
            String rdfNs = model.getNsPrefixURI(typeParts[0]);
            if (rdfNs == null)
                throw new IllegalArgumentException("The node's alias namespace wasn't loaded into the model:" + typeParts[0]);
            String nodeType = typeParts[1];

            // Create the new node
            Resource r = model.createResource(rdfNs + nodeType + "/" + randVal);
            // Add some basic properties
            r.addProperty(RDF.type, new ResourceImpl(rdfNs + nodeType));
            // Get the list of auto-generated properties from the mapping and add them
            String[] props = _typePropsFunction.getProperties(rdfType);
            for (String property : props) {
                IDictionarySampler propSampler = _samplerFunction.getDSampler(property);
                if (propSampler.getClass()== ExternalConstDictionarySampler.class)
                    propSampler.init(randVal);
                String[] propParts = property.split(":");
                r.addLiteral(new PropertyImpl(rdfNs + propParts[1]), propSampler.getRandomLabel());
            }
            // Connect the node to the actual resource that was created
            node.setRDFNode(r);           
        }

        // Iterate a second time to connect each node's edge list
        for (Integer id : _newNodes.keySet()) {
            // Get the node
            Node node = _newNodes.get(id);
            // Get the list of edges
            ArrayList<Edge> edges = node.getEdgeList();
            // Add the edges
            if (edges != null)
                addEdges(model, node.getRDFNode().as(Resource.class), edges);
        }

        //  Finally if we have old nodes we might want to add them their new edges to the new nodes
        if (_oldNodes != null)   // Might be in case we are creating a new model
            for (Integer id : _oldNodes.keySet()) {
                // Get the node
                Node node = _oldNodes.get(id);
                // Get the true resource which is part of the model
                Resource r = node.getRDFNode().as(Resource.class);

                // CREATE A COPY OF THAT EXISTING RESOURCE AND MANIPULATE IT!
                String uri = r.getURI();
                Resource rCopy = model.createResource(uri);

                // Get the list of edges
                ArrayList<Edge> edges = node.getEdgeList();
                // Add the edges
                if (edges != null)
                    addEdges(model, rCopy, edges);
            }
    }

    /**
     * Returns the hash-map holding the mapping of node-ids to nodes for the 'old-nodes'
     * which are the nodes that exist in the graph
     * @return - The hash-map holding the mapping of node-ids to nodes for the 'old-nodes'
     */
    public HashMap<Integer, Node> getOldNodes() {
        return _oldNodes;
    }

    /**
     * Private methods
     */

    /**
     * A methods that adds a list of properties to a given resource in the given model
     * @param model - The model in which the each property will be created
     * @param r - The resource that will be given the list of new edges
     * @param edges - The list of edges to be added
     */
    private void addEdges(Model model, Resource r, ArrayList<Edge> edges) {

        for (Edge edge : edges) {
            // Get the id of the 'to-node' and find it in the old/new nodes maps
            int toNodeId = edge.getIdOfConnectedNode();
            Node toNode;
            if (_newNodes.containsKey(toNodeId))
                toNode = _newNodes.get(toNodeId);
            else if (_oldNodes.containsKey(toNodeId))
                toNode = _oldNodes.get(toNodeId);
            else
                throw new IllegalStateException("Both old and new lists of nodes don't contain the given node ID: " + toNodeId);
            RDFNode rdfNode = toNode.getRDFNode();

            // Create the new property
            String fullType = edge.getRdfType();
            String[] typeParts = fullType.split(":");
            if (typeParts.length!=2)
                throw new IllegalArgumentException("The edge's type has illegal format: " + fullType + " while expecting the following format: <alias-namespace:type>");
            String edgeNs = model.getNsPrefixURI(typeParts[0]);
            if (edgeNs == null)
                throw new IllegalArgumentException("The node's alias namespace wasn't loaded into the model:" + typeParts[0]);
            String edgeType = typeParts[1];
            // This creates the property in the given model
            Property p = model.createProperty(edgeNs + edgeType); 

            // Add the edge to the given resource as a literal or property according to
            // the to-node of this edge
            if (rdfNode.canAs(Literal.class))
                r.addLiteral(p, rdfNode);
            else
                r.addProperty(p, rdfNode);
        }
    }
}
