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

/**
 * A simple cyclic int counter
 */
public class CyclicIntCounter {

    /**
     * Class member - Holds the current counter value
     */
    private int _value;

    /**
     * Constructor - Sets the counter to 0
     */
    public CyclicIntCounter()
    {
        _value = 0;
    }

    /**
     * Returns the value of the counter and increments the counter by 1.
     * In case that we reach max-int we re-set the counter to 0.
     * @return - The value of the counter
     */
    public int getNextValue()
    {
        int value = _value;
        _value = ((_value + 1) >= Integer.MAX_VALUE) ? 0 : _value+1;        
        return value;
    }

}
