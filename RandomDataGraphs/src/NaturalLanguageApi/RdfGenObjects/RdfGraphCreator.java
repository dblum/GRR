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

package NaturalLanguageApi.RdfGenObjects;

import NaturalLanguageApi.RdfGenObjects.ConstructParts.RdfConstruct;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that represents the graph-creator node containing RdfConstruct[] in RDF format
 */
public class RdfGraphCreator extends RdfObject {

    /**
     * Class Members
     */
    private RdfConstruct[] _constructArray;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param constructArray - The newly set RdfConstruct[]
     */
    public RdfGraphCreator(RdfConstruct[] constructArray) {
        _constructArray = constructArray;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {

        String graphCreator = "";

        String constructList = RdfPrinter.printTabbedRdfContainer(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_CONSTRUCT_LIST, "Seq", _constructArray);
        ArrayList<String> internalLines = new ArrayList<String>();
        internalLines.add(constructList);

        graphCreator += RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_GRAPH_CREATOR, internalLines);

        return graphCreator;
    }
}