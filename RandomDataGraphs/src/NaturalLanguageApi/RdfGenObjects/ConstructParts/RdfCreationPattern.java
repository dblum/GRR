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

import NaturalLanguageApi.RdfGenObjects.CreationParts.RdfNode;
import NaturalLanguageApi.RdfGenObjects.CreationParts.RdfPatternRepeatMode;
import NaturalLanguageApi.RdfGenObjects.RdfObject;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that acts as a wrapper for a creation pattern (see members) in RDF format
 */
public class RdfCreationPattern extends RdfObject {

    /**
     * Class Members
     */
    private RdfPatternRepeatMode _repeatMode;
    private RdfNode[] _oldNodes;
    private RdfNode[] _newNodes;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param repeatMode - The newly set RdfPatternRepeatMode
     * @param oldNodes - The newly set RdfNode[] (old nodes)
     * @param newNodes - The newly set RdfNode[] (new nodes)
     */
    public RdfCreationPattern(RdfPatternRepeatMode repeatMode, RdfNode[] oldNodes, RdfNode[] newNodes) {
        _repeatMode = repeatMode;
        _oldNodes = oldNodes;
        _newNodes = newNodes;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {
        String cPattern = "";

        ArrayList<String> externalLines = new ArrayList<String>();
        ArrayList<String> internalLines = new ArrayList<String>();
        String repeatMode = _repeatMode.toRdfString(tabOffset + 2);
        internalLines.add(repeatMode);
        String creationPatternRepetitions = RdfPrinter.printTabbedComposite(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_CREATION_PATTERN_REP, internalLines);
        externalLines.add(creationPatternRepetitions);

        String oldNodes;
        if (_oldNodes==null || _oldNodes.length==0)
            oldNodes = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_OLD_NODES);
        else
            oldNodes = RdfPrinter.printTabbedRdfContainer(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_OLD_NODES,"Bag", _oldNodes);
        externalLines.add(oldNodes);
        String newNodes;
        if (_newNodes==null || _newNodes.length==0)
            newNodes = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_NEW_NODES);
        else
            newNodes = RdfPrinter.printTabbedRdfContainer(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_NEW_NODES,"Bag", _newNodes);
        externalLines.add(newNodes);

        cPattern += RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_CREATION_PATTERN, externalLines);
        return cPattern;
    }
}
