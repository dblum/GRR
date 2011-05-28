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

/**
 * The abstract class from which all of the parsed entities will inherit from
 * General idea is that each section that is parsed will be represented as some class that inherits form
 * this class enabling us at the end to generate the RDF based input file that will be given to the Java Layer
 * for parsing
 */
public abstract class RdfObject {

    /**
     * Abstract method that is similar to a regular toString but uses the given tab offset
     * in order to print this instance into RDF based string
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this instance shifted by tabs to the tab-offset
     */
    public abstract String toRdfString(int tabOffset);

}
