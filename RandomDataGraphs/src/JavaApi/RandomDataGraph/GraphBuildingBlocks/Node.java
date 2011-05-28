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

package JavaApi.RandomDataGraph.GraphBuildingBlocks;

import com.hp.hpl.jena.rdf.model.RDFNode;

import java.util.ArrayList;

/**
 * Represents an internal implementation of a node in the graph
 */
public class Node {

    /**
     * Class Members
     */
    private RDFNode _rdfNode;
    private String _rdfType;
    private int _nodeId;
    private ArrayList<Edge> _edgeList;

    /**
     * Constructors
     */

    /**
     * Constructor that gives only the general structure of the node (without the actual mapping
     * to the RDFNode)
     * This is useful when we want to add the RDFNode and update the other parameters later on via set commands
     *
     * @param nodeId   - Id for identifying this node
     * @param edgeList -  A list containing the edges that connect this node to other nodes
     */
    public Node(int nodeId, ArrayList<Edge> edgeList) {
        _nodeId = nodeId;
        _edgeList = edgeList;
    }

    /**
     * Constructor which gets all of the parameters upon creation
     *
     * @param rdfNode  - A facade for the actual resource or literal that this node represents (can be null)
     * @param rdfType  - A string representing the rdf:type of this node (Can be null)
     * @param nodeId   - Id for identifying this node
     * @param edgeList -  A list containing the edges that connect this node to other nodes
     */
    public Node(RDFNode rdfNode, String rdfType, int nodeId, ArrayList<Edge> edgeList) {
        _rdfNode = rdfNode;
        _rdfType = rdfType;
        _nodeId = nodeId;
        _edgeList = edgeList;
    }

    /**
     * Public Methods
     */

    /**
     * Method that return the RDFNode of this node
     *
     * @return the RDFNode of this node
     */
    public RDFNode getRDFNode() {
        return _rdfNode;
    }

    /**
     * Method that returns the rdf:type of this node
     *
     * @return the rdf:type of this node
     */
    public String getRdfType() {
        return _rdfType;
    }

    /**
     * Returns this node's id
     *
     * @return This node's id
     */
    public int getNodeId() {
        return _nodeId;
    }

    /**
     * Returns the edge list of this node
     *
     * @return The edge list of this node
     */
    public ArrayList<Edge> getEdgeList() {
        return _edgeList;
    }


    /**
     * Sets the RDFNode of this node to the given one
     *
     * @param rdfNode - The new RDFNode of this node
     */
    public void setRDFNode(RDFNode rdfNode) {
        _rdfNode = rdfNode;
    }

    /**
     * Method that sets the rdf:type of this node
     *
     * @param rdfType - the new rdf:type of this node
     */
    public void setRdfType(String rdfType) {
        _rdfType = rdfType;
    }


}
