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

package NaturalLanguageApi.RdfGenObjects.SamplerFunctionParts;

import NaturalLanguageApi.RdfGenObjects.QueryParts.RdfMode;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that represents a StdDictionarySampler in RDF format
 */
public class RdfStandardDictionarySampler extends RdfDictionarySampler {

    /**
     * Class Members
     */
    private String _type;
    private RdfMode _mode;
    private String _source;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param type - The newly set rdf-type
     * @param mode - The newly set mode
     * @param source - The newly set source file which will be used as input 
     */
    public RdfStandardDictionarySampler(String type, RdfMode mode, String source) {
        _type = type;
        _mode = mode;
        _source = source;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {

        String stdDicSampler = "";
        ArrayList<String> lines = new ArrayList<String>();
        String type = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_TYPE, _type);
        lines.add(type);

        String modeEnum = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 2, RdfGenTypes.RDF_GEN_PREFIX + _mode.toString());
        ArrayList<String> modeLines = new ArrayList<String>();
        modeLines.add(modeEnum);
        String mode = RdfPrinter.printTabbedComposite(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_MODE, modeLines);
        lines.add(mode);

        String source = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_SOURCE, _source);
        lines.add(source);

        stdDicSampler += RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_STD_DIC_SAMPLER, lines);

        return stdDicSampler;
    }
}