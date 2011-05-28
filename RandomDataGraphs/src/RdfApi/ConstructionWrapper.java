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

package RdfApi;

import JavaApi.RandomDataGraph.GraphBuildingBlocks.Node;
import JavaApi.RandomDataGraph.Matchers.IMatcher;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.INaturalNumberSampler;
import JavaApi.Samplers.QuerySamplers.QueryWrapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A wrapper for a construction command in the RDF API layer
 */
public class ConstructionWrapper {

    /**
     * Class Members
     */
    private ArrayList<QueryWrapper> _qWrappers;
    private IMatcher _matcher;
    private INaturalNumberSampler _nSampler;
    private HashMap<Integer, Node> _oldNodes;
    private HashMap<Integer, Node> _newNodes;
    private HashMap<String, String> _queryAttributeVariableMap;

    /**
     * Constructor - Sets the wrapper with all of the relevant data
     * @param qWrappers - Array-List of QueryWrapper used in this construction command
     * @param matcher - The matcher used for mapping between the results and the construction pattern
     * @param nSampler - The n-sampler used to determine the number of times that the construction command will be
     * executed
     * @param oldNodes - A hash-map of node-id (key) to node (value) of the old nodes (nodes existing in the model)
     * @param newNodes - A hash-map of node-id (key) to node (value) of the new nodes to be added
     * @param queryAttributeVariableMap - A mapping between attributes to query variables
     */
    public ConstructionWrapper(
            ArrayList<QueryWrapper> qWrappers,
            IMatcher matcher,
            INaturalNumberSampler nSampler,
            HashMap<Integer, Node> oldNodes,
            HashMap<Integer, Node> newNodes,
            HashMap<String, String> queryAttributeVariableMap) {
        _qWrappers = qWrappers;
        _matcher = matcher;
        _nSampler = nSampler;
        _oldNodes = oldNodes;
        _newNodes = newNodes;
        _queryAttributeVariableMap = queryAttributeVariableMap;
    }

    /**
     * Returns the array-list of QueryWrappers
     * @return - The array-list of QueryWrappers
     */
    public ArrayList<QueryWrapper> getQWrappers() {
        return _qWrappers;
    }

    /**
     * Returns the matcher
     * @return - The matcher
     */
    public IMatcher getMatcher() {
        return _matcher;
    }

    /**
     * Returns the n-sampler
     * @return - The n-sampler
     */
    public INaturalNumberSampler getNSampler() {
        return _nSampler;
    }

    /**
     * Returns the id-node mapping of the old nodes
     * @return - The id-node mapping of the old nodes
     */
    public HashMap<Integer, Node> getOldNodes() {
        return _oldNodes;
    }

    /**
     * Returns the id-node mapping of the new nodes
     * @return - The id-node mapping of the new nodes
     */
    public HashMap<Integer, Node> getNewNodes() {
        return _newNodes;
    }

    /**
     * Returns the attribute-variable hash-map
     * @return - The attribute-variable hash-map
     */
    public HashMap<String, String> getQueryAttributeVariableMap() {
        return _queryAttributeVariableMap;
    }
}
