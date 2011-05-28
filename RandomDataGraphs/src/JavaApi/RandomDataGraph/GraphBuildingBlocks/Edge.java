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

/**
 * Represents an internal implementation of an edge in the graph
 */
public class Edge {

    /**
     * Class Members
     */
    private String _rdfType;
    private int _edgeId;
    private int _conntectsToNodeId;

    /**
     * Constructor
     * @param rdfType - A string representing the rdf:type of this edge (Can be null)
     * @param edgeId   - Id for identifying this edge
     * @param connectsToNodeId - The id of the node that this edge connects to
     */
    public Edge(String rdfType, int edgeId, int connectsToNodeId) {
        _rdfType = rdfType;
        _edgeId = edgeId;
        _conntectsToNodeId = connectsToNodeId;
    }

    /**
     * Public Methods
     */

    /**
     * Method that returns the rdf:type of this edge
     * @return the rdf:type of this edge
     */
    public String getRdfType() {
        return _rdfType;
    }

    /**
     * Returns the id of this edge
     * @return id of this edge
     */
    public int getEdgeId() {
        return _edgeId;
    }

    /**
     * Returns the id of the node that this edge connects to (the node that this edge
     * points to)
     * @return The id of the node that this edge connects to
     */
    public int getIdOfConnectedNode() {
        return _conntectsToNodeId;
    }

    /**
     * Method that sets the rdf:type of this edge
     * @param rdfType - the new rdf:type of this edge
     */
    public void setRdfType(String rdfType) {
        _rdfType = rdfType;
    }

}
