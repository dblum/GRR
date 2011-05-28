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

package NaturalLanguageApi.RdfGenObjects.CreationParts;

import NaturalLanguageApi.RdfGenObjects.RdfObject;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that represents an RDF node (old/new node, rdf-type, node-id, edge array) in RDF format
 */
public class RdfNode extends RdfObject {

    /**
     * Class Members
     */
    private String _nodeType;
    private String _type;
    private int _id;
    private RdfEdge[] _edges;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param nodeType - The newly set node type (old/new)
     * @param type - The newly set rdf-type of this node
     * @param id - The newly set node-id
     * @param edges - The newly set edge array
     */
    public RdfNode(String nodeType, String type, int id, RdfEdge[] edges) {
        _nodeType = nodeType;
        _type = type;
        _id = id;
        _edges = edges;
    }

    /**
     * Returns the node-id of this node
     * @return - The node-id of this node
     */
    public int getId() {
        return _id;
    }

    /**
     * Re-Sets the edge array of this node
     * @param edges - The newly set edge array of this node
     */
    public void setEdges(RdfEdge[] edges) {
        _edges = edges;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {
        ArrayList<String> lines = new ArrayList<String>();
        String id = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ID, "" + _id);
        lines.add(id);
        String type = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_TYPE, _type);
        lines.add(type);
        String edges;
        if (_edges==null || _edges.length==0)
            edges = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_EDGES);
        else
            edges = RdfPrinter.printTabbedRdfContainer(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_EDGES, "Bag", _edges);
        lines.add(edges);

        return RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + _nodeType, lines);
    }
}