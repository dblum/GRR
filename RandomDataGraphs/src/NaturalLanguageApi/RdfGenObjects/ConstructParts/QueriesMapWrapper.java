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

/**
 * Class that acts as a wrapper class for RdfQuery[] and RdfMapPair[] in RDF format
 */
public class QueriesMapWrapper {

    /**
     * Class Members
     */
    private RdfQuery[] _queries;
    private RdfMapPair[] _mapPairs;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param queries - The newly set RdfQuery[]
     * @param mapPairs - The newly set RdfMapPair[] 
     */
    public QueriesMapWrapper(RdfQuery[] queries, RdfMapPair[] mapPairs) {
        _queries = queries;
        _mapPairs = mapPairs;
    }

    /**
     * Returns the RdfQuery[]
     * @return - The RdfQuery[]
     */
    public RdfQuery[] getQueries() {
        return _queries;
    }

    /**
     * Returns the RdfMapPair[]
     * @return - The RdfMapPair[]
     */
    public RdfMapPair[] getMapPair() {
        return _mapPairs;
    }
}
