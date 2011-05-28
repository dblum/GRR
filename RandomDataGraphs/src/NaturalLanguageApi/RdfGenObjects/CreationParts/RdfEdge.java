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
 * Class that represents an RDF edge (edge-id, rdf-type, node-id to which it points) in RDF format
 */
public class RdfEdge extends RdfObject {

    /**
     * Class Members
     */
    private int _id;
    private String _type;
    private int _pointsToNodeId;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param id - The newly set edge-id
     * @param type - The newly set edge type
     * @param pointsToNodeId - The newly set 'to' node-id
     */
    public RdfEdge(int id, String type, int pointsToNodeId) {
        _id = id;
        _type = type;
        _pointsToNodeId = pointsToNodeId;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset)
    {
        ArrayList<String> lines = new ArrayList<String>();
        String id = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ID, "" + _id);
        lines.add(id);
        String type = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_TYPE, _type);
        lines.add(type);
        String toId = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_POINTS_TO_NODE_ID, "" + _pointsToNodeId);
        lines.add(toId);

        String rdfString = RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_EDGE, lines);
        return rdfString;
    }

}
