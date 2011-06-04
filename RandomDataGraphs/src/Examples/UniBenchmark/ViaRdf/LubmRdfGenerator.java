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

package Examples.UniBenchmark.ViaRdf;

import RdfApi.QueryOptimizationMode;
import RdfApi.RdfBasedRandomGraphAPI;
import SolutionConfig.Consts;
import SolutionConfig.SolutionConfigFile;

/**
 *
 */
public class LubmRdfGenerator {

    /**
     * Consts for setting the input/output files
     */
    private static final String IN_FILE_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "RdfApi" + Consts.pathSep + "Examples" + Consts.pathSep + "RdfGeneratorInput.rdf";
    private static final String OUT_FILE_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "RdfApi" + Consts.pathSep + "Examples" + Consts.pathSep + "RdfUniOutput.rdf";

    /**
     * Static member for using the RDF based API of the system
     */
    private static RdfBasedRandomGraphAPI _rdfGraphApi = new RdfBasedRandomGraphAPI();

    /**
     * Main
     * @param args - Input params (aren't used)
     * @throws Exception - Any exception that could be thrown by the API (see RDF and Java layers documentation)
     */
    public static void main(String[] args) throws Exception {
        _rdfGraphApi.generateGraph(IN_FILE_PATH, OUT_FILE_PATH, QueryOptimizationMode.ALWAYS_CACHE, null, true);
    }


}
