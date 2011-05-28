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

package RdfApi;

import Examples.Utils.ExpLogger;
import JavaApi.RandomDataGraph.Patterns.ConstructionPattern;
import JavaApi.RandomDataGraph.RandomGraphAPI;
import JavaApi.Samplers.SamplerFunctions.SamplerFunction;
import JavaApi.Samplers.SamplerFunctions.TypePropertiesFunction;
import SolutionConfig.SolutionConfigFile;
import StorageWrappers.*;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The RDF Based API that implements the logic and algorithm of the GRR system (see relevant paper and documentation)
 */
public class RdfBasedRandomGraphAPI {

    /**
     * Const values
     */
    private static final String TDB_CONFIG_PATH = SolutionConfigFile.BASE_PATH + "TDB\\tdbConfig.xml";

    private static final String PARSING_SCHEMA_FILE = "RdfGeneratorSchema.rdf";
    private static final String PARSING_SCHEMA_FILE_WEB_BASE = "http://www.cs.huji.ac.il/~danieb12/";
    private static final String PARSING_SCHEMA_FILE_WEB_PATH = PARSING_SCHEMA_FILE_WEB_BASE + PARSING_SCHEMA_FILE;
    private static final String RDF_GEN_NS = PARSING_SCHEMA_FILE_WEB_PATH + "#";
    
    /**
     * Class Members
     */
    private static Model _graphModel;
    private static Model _schema;
    private static InfModel _infModel;
    private static IDBWrapper _dbw;
    private static RandomGraphAPI _randGraphApi = new RandomGraphAPI();

    /**
     * Public Methods
     */


    /**
     * The main of this API that parses and executes all of the construction commands located in the given RDF input file
     * @param fileInput - The RDF based input file
     * @param fileOutput - Optional path to a file that will store the new model in addition to the actual DB
     * @param mode - The query optimization mode to be used (caching mechanism)
     * @param expLogger - Optional debugging object that enables monitoring of performance (users could pass here null)
     * @param storeToFile - Boolean indicating if the system should store the updated model into the output file at the
     * end of the execution
     * @throws Exception - Currently a generic since an Exception might occur in the either the Java layer or in the
     * parsing process of the RDF input file
     */
    public void generateGraph(String fileInput, String fileOutput, QueryOptimizationMode mode, ExpLogger expLogger, boolean storeToFile) throws Exception {
        RdfParser rdfParser = new RdfParser();
        rdfParser.parseRdfGeneratorFile(fileInput);

        SamplerFunction sFunction = rdfParser.getSamplerFunction();
        TypePropertiesFunction typePropFunction = rdfParser.getTypePropsFunction();
        HashMap<String, String> nsMap = rdfParser.getAliasNsMap();
        ArrayList<ConstructionWrapper> cWrappers = rdfParser.getConstructionWrapperList();

        if (expLogger != null)
            expLogger.startLogging();

        // First we want to create a TDB based model
        try {
            String configFile = TDB_CONFIG_PATH;
            _dbw = new TDBWrapper();
            _dbw.init(configFile);
            _graphModel = _dbw.getEmptyDbBasedModel();

            // TODO: Add future support of multiple schema files
            if (nsMap.size() != 1)
                throw new IllegalStateException("Currently the system supports only one schema!");

            for (String fullAlias : nsMap.keySet()) {
                String alias = fullAlias.substring(0, fullAlias.length() - 1);
                String fullNs = nsMap.get(fullAlias);
                String ns = fullNs.substring(0, fullNs.length() - 1);

                _graphModel.setNsPrefix(alias, fullNs);
                _graphModel.setNsPrefix("rdfGen", RDF_GEN_NS);
                _schema = FileManager.get().loadModel(ns);

                _infModel = ModelFactory.createRDFSModel(_schema, _graphModel);
                _infModel.setNsPrefix(alias, fullNs);
                _infModel.setNsPrefix("rdfGen", RDF_GEN_NS);

                _dbw.storeModel(_infModel);
            }

        }
        catch (IOException ioe) {
            System.out.println("Failed to load the configuration file for the TDB");
        }

        // We run each construction-pattern
        for (int k = 0; k < cWrappers.size(); k++) {
            System.out.println("In construction number: " + k);
            applyConstruction(cWrappers.get(k), sFunction, typePropFunction, mode, expLogger);
        }

        if (expLogger != null)
            expLogger.endLogging();

        // If saveToFile is true we save the model into a file 
        if (storeToFile) {
            IFileWrapper fw = new StdFileWrapper();
            try {
                fw.storeModel(_graphModel, fileOutput, RdfFormat.RDF_XML_FORMAT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException("Output file wasn't found!");
            }
        }

        //----------------- DEBUG - Print the graph -----------------------------------
        //_graphModel.write(System.out, RdfFormat.RDF_XML_FORMAT.toString());
        //-----------------------------------------------------------------------------

        if (expLogger != null)
            expLogger.collectData(_infModel, nsMap);

        // Close all models
        _graphModel.close();
        _schema.close();
        _infModel.close();
        _dbw.close();


    }

    /**
     * A method that uses the Java API layer in order to execute the construction command on the model
     * After having all of the relevant object de-serialized from the RDF input file we can apply the construction
     * command
     * @param cWrapper - Ordered Array-List of QueryWrapper instances which represent the queries and relevant query
     * meta-data needed for execution
     * @param sFunction - The sampler-function that will be used in the construction pattern (this class maps between
     * rdf-types and the dictionary sampler to be used for creating values
     * @param typePropFunction - Another mapping function, this time it maps between each rdf-type and a list of
     * rdf-type properties that will be generated for it
     * @param mode - The query optimization mode to be used
     * @param expLogger - Internal class used for performance monitoring. Users should pass null here
     * @throws Exception - Currently a generic since an Exception might occur in the either the Java layer or in the
     * parsing process of the RDF input file
     */
    public void applyConstruction(ConstructionWrapper cWrapper, SamplerFunction sFunction, TypePropertiesFunction typePropFunction, QueryOptimizationMode mode, ExpLogger expLogger) throws Exception {

        ConstructionPattern cPattern = new ConstructionPattern(cWrapper.getOldNodes(), cWrapper.getNewNodes(), sFunction, typePropFunction);
        HashMap<String, String> queryAttributeVariableMap = cWrapper.getQueryAttributeVariableMap();

        if (queryAttributeVariableMap == null || queryAttributeVariableMap.size() == 0)
            _randGraphApi.construct(
                    _infModel,
                    cWrapper.getQWrappers(),
                    cWrapper.getNSampler(),
                    cPattern,
                    cWrapper.getMatcher(),
                    mode,
                    expLogger);
        else
            _randGraphApi.constructDynamic(
                    _infModel,
                    cWrapper.getQWrappers(),
                    queryAttributeVariableMap,
                    cWrapper.getNSampler(),
                    cPattern,
                    cWrapper.getMatcher(),
                    mode,
                    expLogger);

    }
}

