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

package JavaApi.Samplers.SamplerFunctions;

import JavaApi.Samplers.DictionarySamplers.IDictionarySampler;

/**
 * A wrapper class for holding a pair of rdf-type (string) and a matching dictionary-smapler
 */
public class TypeDSamplerPair {

    /**
     * Class Members
     */
    private String _type;
    private IDictionarySampler _dSampler;

    /**
     * Constructor
     * @param type - The rdf-type in the pair
     * @param dSampler - The dictionary-sampler in the pair
     */
    public TypeDSamplerPair(String type, IDictionarySampler dSampler) {
        _type = type;
        _dSampler = dSampler;
    }

    /**
     * Returns the rdf-type of this pair
     * @return - The rdf-type of this pair
     */
    public String getType() {
        return _type;
    }

    /**
     * Returns the dictionary-sampler of this pair
     * @return - The dictionary-sampler of this pair
     */
    public IDictionarySampler getDSampler() {
        return _dSampler;
    }
}
