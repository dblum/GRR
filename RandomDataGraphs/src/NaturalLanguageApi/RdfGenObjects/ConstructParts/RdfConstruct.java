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

package NaturalLanguageApi.RdfGenObjects.ConstructParts;

import NaturalLanguageApi.RdfGenObjects.RdfObject;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that acts as a wrapper for a construct (see members) in RDF format
 */
public class RdfConstruct extends RdfObject {

    /**
     * Class Members
     */
    private RdfQuery[] _queries;
    private RdfMapPair[] _mapPairs;
    private RdfCreationPattern _cPattern;
    private RdfQueryAttVarMap _attDynamicVarMap;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param queries - The newly set RdfQuery[]
     * @param mapPairs - The newly set RdfMapPair[]
     * @param cPattern - The newly set RdfCreationPattern
     * @param attDynamicVarMap - The newly set RdfQueryAttVarMap
     */
    public RdfConstruct(RdfQuery[] queries, RdfMapPair[] mapPairs, RdfCreationPattern cPattern, RdfQueryAttVarMap attDynamicVarMap) {
        _queries = queries;
        _mapPairs = mapPairs;
        _cPattern = cPattern;
        _attDynamicVarMap = attDynamicVarMap;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {
        String construct = "";

        ArrayList<String> lines = new ArrayList<String>();
        String queries;
        if (_queries==null || _queries.length==0)
            queries = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_QUERIES);
        else
            queries = RdfPrinter.printTabbedRdfContainer(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_QUERIES, "Seq", _queries);
        lines.add(queries);
        String attNodeIdMap;
        if (_mapPairs==null || _mapPairs.length==0)
            attNodeIdMap = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ATT_NODE_ID_MAP);
        else
            attNodeIdMap = RdfPrinter.printTabbedRdfContainer(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ATT_NODE_ID_MAP, "Bag", _mapPairs);
        lines.add(attNodeIdMap);
        String attDynamicVarMapStr = _attDynamicVarMap.toRdfString(tabOffset + 1);
        lines.add(attDynamicVarMapStr);
        ArrayList<String> cPatternLines = new ArrayList<String>();
        cPatternLines.add(_cPattern.toRdfString(tabOffset + 2));
        String creationPattern = RdfPrinter.printTabbedComposite(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_CREATION_PATTERN, cPatternLines);
        lines.add(creationPattern);

        construct += RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_CONSTRUCT, lines);

        return construct;
    }
}
