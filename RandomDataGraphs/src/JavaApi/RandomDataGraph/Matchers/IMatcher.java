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

package JavaApi.RandomDataGraph.Matchers;

import JavaApi.RandomDataGraph.GraphBuildingBlocks.Node;
import com.hp.hpl.jena.query.QuerySolution;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that enables us to map between general graph nodes to actual resources that are
 * returned by a given query
 * The idea is that the we have a structure represented by nodes and edges. In order to update
 * different parts of the graph we match this structure to actual parts of the graph.
 * By updating the mapping (via this class) we'll invoke our construction commands on different parts
 * of the graph that match the given structure
 */
public interface IMatcher {

    /**
     * Initializes the matcher instance with the given mapping of node-id to attribute name (in the query)
     * @param map - A map that will be used for getting the attribute name from a given node-id
     */
    public void init(HashMap<Integer, String> map);

    /**
     * Initializes the matcher instance with a default mapping of the given node-ids
     * attribute-name: id --> '?att'+id
     * @param idList A list that will be used for creating the default map of node-ids to
     * attribute names
     */
    public void init(ArrayList<Integer> idList);

    /**
     * Returns true iff the instance was initialized by any of the init methods
     * @return True iff the instance was initialized by any of the init methods
     */
    public boolean isInitialized();

    /**
     * Adds a new entry to the mapping
     * @param nodeId - The node-id that is being added
     * @param attributeName - The attribute name to be mapped to the given node-id
     */
    public void addMapping(Integer nodeId, String attributeName);

    /**
     * Returns the attribute-name that is mapped to the given node-id
     * @param nodeId - The requested mapping entry
     * @return The attribute-name that is mapped to the given node-id
     */
    public String getQueryAttributeById(int nodeId);

    /**
     * Updates the existing node entries which are in the given hash-map according to the
     * given query-solution and the mapping that this matcher holds
     * @param solution - The query solution that should have a specific set of attributes
     * and values (resources)
     * @param oldNodes - A mapping of node-ids to actual graph nodes which should be updated
     * and point to the new resources that can be found in query solution
     */
    public void setNodesMapping(
            QuerySolution solution,
            HashMap<Integer, Node> oldNodes);

    /**
     * Updates the existing node entries which are in the given hash-map according to the
     * given query-solution and the mapping that this matcher holds
     * @param solutions - An array of solutions which should have a specific set of attributes
     * and values (resources)
     * @param oldNodes - A mapping of node-ids to actual graph nodes which should be updated
     * and point to the new resources that can be found in query solutions
     */
    public void setNodesMapping(
            ArrayList<QuerySolution> solutions,
            HashMap<Integer, Node> oldNodes);
}
