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

package NaturalLanguageApi.ParsingUtils;

import NaturalLanguageApi.RdfGenObjects.AliasNsParts.RdfAliasNsPair;
import NaturalLanguageApi.RdfGenObjects.ConstructParts.RdfConstruct;

/**
 * Helper class that acts as a wrapper of RdfConstruct[] and RdfAliasNsPair[]
 */
public class ConstructsNsMapPair {

    /**
     * Class Members
     */
    private RdfConstruct[] _constructs;
    private RdfAliasNsPair[] _aliasNsPairs;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param constructs - The newly set RdfConstructs[]
     * @param aliasNsPairs - The newly set RdfAliasNsPair[]
     */
    public ConstructsNsMapPair(RdfConstruct[] constructs, RdfAliasNsPair[] aliasNsPairs) {
        _constructs = constructs;
        _aliasNsPairs = aliasNsPairs;
    }

    /**
     * Returns the RdfConstructs[]
     * @return - The RdfConstructs[]
     */
    public RdfConstruct[] getConstructs() {
        return _constructs;
    }

    /**
     * Returns the RdfAliasNsPair[]
     * @return - the RdfAliasNsPair[]
     */
    public RdfAliasNsPair[] getAliasNsPairs() {
        return _aliasNsPairs;
    }
}