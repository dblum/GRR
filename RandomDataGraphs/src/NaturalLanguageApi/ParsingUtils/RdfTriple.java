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
 * Helper class that acts as a wrapper of and rdf triple in String format:
 * (1) String - source class type
 * (2) String - property class type
 * (3) String - target class type
 */
public class RdfTriple {

    /**
     * Class Members
     */
    private String _classTypeSource;
    private String _classTypeProperty;
    private String _classTypeTarget;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param classTypeSource - The newly set source
     * @param classTypeProperty - The newly set property
     * @param classTypeTarget - The newly set target
     */
    public RdfTriple(String classTypeSource, String classTypeProperty, String classTypeTarget) {
        _classTypeSource = classTypeSource;
        _classTypeProperty = classTypeProperty;
        _classTypeTarget = classTypeTarget;
    }

    /**
     * Returns the source class type in string format
     * @return - The source class type in string format
     */
    public String getClassTypeSource() {
        return _classTypeSource;
    }

    /**
     * Returns the property class type in string format
     * @return - The property class type in string format
     */
    public String getClassTypeProperty() {
        return _classTypeProperty;
    }

    /**
     * Returns the target class type in string format
     * @return - The target class type in string format
     */
    public String getClassTypeTarget() {
        return _classTypeTarget;
    }
}
