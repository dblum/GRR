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

import NaturalLanguageApi.RdfGenObjects.CreationParts.RdfNode;
import NaturalLanguageApi.RdfGenObjects.CreationParts.RdfPatternRepeatMode;

/**
 * Helper class that acts as a wrapper of RdfNode[] and RdfPatternRepeatMode
 */
public class NewNodesModePair {

    /**
     * Class Members
     */
    private RdfNode[] _newNodes;
    private RdfPatternRepeatMode _mode;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param newNodes - The newly set RdfNode[]
     * @param mode - The newly set RdfPatternRepeatMode
     */
    public NewNodesModePair(RdfNode[] newNodes, RdfPatternRepeatMode mode) {
        _newNodes = newNodes;
        _mode = mode;
    }

    /**
     * Returns the RdfNode[]
     * @return - The RdfNode[]
     */
    public RdfNode[] getNewNodes() {
        return _newNodes;
    }

    /**
     * Returns the RdfPatternRepeatMode
     * @return - The RdfPatternRepeatMode
     */
    public RdfPatternRepeatMode getMode() {
        return _mode;
    }
}
