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

package JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers;

/**
 * Factory class for creating NumberSamplers without repetitions
 */
public class NumberSamplerNoRepFactory {

    /**
     * A static method that returns an instance of a NumberSamplerNoRep class/sub-class
     * according to the given mode. (See additional documentation for more information on implementation details).
     * @param mode - The NoRepetitionMode that will be used to decide which type to create
     * @return - Returns an instance of a NumberSamplerNoRep class/sub-class
     * according to the given mode.
     */
    public static NumberSamplerNoRep getNoRepNumberSampler(NoRepetitionMode mode) {
        switch (mode) {
            case HASE_SET_WITH_REMAINING_NUMBERS:
                return new NumberSamplerNoRepRemImpl();
            case HASH_SET_WITH_REMAINING_NUMBERS_AND_SWAP:
                return new NumberSamplerNoRepRemSwapImpl();
            case HASH_SET_WITH_USED_NUMBERS:
                return new NumberSamplerNoRepUsedImpl();
            default:
                throw new IllegalArgumentException("The given SamplingMode isn't supported: " + mode);
        }
    }

}
