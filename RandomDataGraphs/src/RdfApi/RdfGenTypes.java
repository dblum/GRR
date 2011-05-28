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

/**
 * Class containing all of the RDF resources and properties needed for the RDF layer
 */
public class RdfGenTypes {

    public static final String RDF_GEN_PREFIX = "rdfGen:";
    /**
     * Consts - Resource Types
     */
    public static final String RES_RDF_TYPE_SAMPLER_FUNCTION = "SamplerFunction";
    public static final String RES_RDF_TYPE_NS_MAPPINGS = "NameSpaceMappings";
    public static final String RES_RDF_TYPE_TYPE_PROPERTIES_MAP = "TypePropertiesMappings";
    public static final String RES_RDF_TYPE_GRAPH_CREATOR = "GraphCreator";
    public static final String RES_RDF_TYPE_CONSTRUCT = "Construct";
    public static final String RES_RDF_TYPE_QUERY = "Query";
    public static final String RES_RDF_TYPE_MODE = "Mode";
    public static final String RES_RDF_TYPE_GLOBAL_DIT_MODE = "GlobalDistinctMode";
    public static final String RES_RDF_TYPE_LOCAL_DIS_MODE = "LocalDistinctMode";
    public static final String RES_RDF_TYPE_REPEATABLE_MODE = "RepeatableMode";
    public static final String RES_RDF_TYPE_QUERY_RES_SELECTION = "QueryResultSelection";
    public static final String RES_RDF_TYPE_SELECT_ALL = "SelectAll";
    public static final String RES_RDF_TYPE_SELECT_CONST = "SelectConst";
    public static final String RES_RDF_TYPE_SELECT_NUMBER_RANGE = "SelectNumberRange";
    public static final String RES_RDF_TYPE_SELECT_RANGE = "SelectRange";
    public static final String RES_RDF_TYPE_MAP_PAIR = "MapPair";
    public static final String RES_RDF_TYPE_TYPE_PROPS_PAIR = "TypePropsPair";
    public static final String RES_RDF_TYPE_ATT_VAR_PAIR = "AttVarPair";
    public static final String RES_RDF_TYPE_CREATION_PATTERN = "CreationPattern";
    public static final String RES_RDF_TYPE_PATTERN_REPEAT_MODE = "PatternRepeatMode";
    public static final String RES_RDF_TYPE_REPEAT_CONST = "RepeatConst";
    public static final String RES_RDF_TYPE_REPEAT_RANGE = "RepeatRange";
    public static final String RES_RDF_TYPE_DIST_FUNCTION = "DistributionFunction";
    public static final String RES_RDF_TYPE_UNIFORM = "Uniform";
    public static final String RES_RDF_TYPE_ALIAS_NS_PAIR = "AliasNsPair";
    public static final String RES_RDF_TYPE_PROPERTY = "Property";
    public static final String RES_RDF_TYPE_NODE = "Node";
    public static final String RES_RDF_TYPE_OLD_NODE = "OldNode";
    public static final String RES_RDF_TYPE_NEW_NODE = "NewNode";
    public static final String RES_RDF_TYPE_EDGE = "Edge";
    public static final String RES_RDF_TYPE_NEW_EDGE = "NewEdge";
    public static final String RES_RDF_TYPE_DIC_SAMPLER = "DictionarySampler";
    public static final String RES_RDF_TYPE_CUSTOM_DIC_SAMPLER = "CustomDictionarySampler";
    public static final String RES_RDF_TYPE_STD_DIC_SAMPLER = "StandardDictionarySampler";
    public static final String RES_RDF_TYPE_CTR_DIC_SAMPLER = "CounterDictionarySampler";
    public static final String RES_RDF_TYPE_EXT_DIC_SAMPLER = "ExternalConstDictionarySampler";
    public static final String RES_RDF_TYPE_CONST_DIC_SAMPLER = "ConstDictionarySampler";

    /**
     * Consts - Properties Types
     */
    public static final String PROP_RDF_TYPE_TYPE_DIC_MAP = "typeDictionaryMappings";
    public static final String PROP_RDF_TYPE_TYPE_ALIAS_NS_MAPPINGS = "aliasNsMappings";
    public static final String PROP_RDF_TYPE_TYPE_TYPE_PROPS_MAPPINGS = "typePropertiesMappings";
    public static final String PROP_RDF_TYPE_PROP_LIST = "propertiesList";
    public static final String PROP_RDF_TYPE_CONSTRUCT_LIST = "constructList";
    public static final String PROP_RDF_TYPE_QUERIES = "queries";
    public static final String PROP_RDF_TYPE_CREATION_PATTERN = "creationPattern";
    public static final String PROP_RDF_TYPE_MODE = "mode";
    public static final String PROP_RDF_TYPE_QUERY_RES_SELECTION = "queryResultSelection";
    public static final String PROP_RDF_TYPE_ATT_NODE_ID_MAP = "attributeNodeIdMap";
    public static final String PROP_RDF_TYPE_OLD_NODES = "oldNodes";
    public static final String PROP_RDF_TYPE_NEW_NODES = "newNodes";
    public static final String PROP_RDF_TYPE_ATT_DYN_VAR_MAP = "attributeDynamicVarsMap";
    public static final String PROP_RDF_TYPE_CREATION_PATTERN_REP = "creationPatternRepetitions";
    public static final String PROP_RDF_TYPE_RANGE_DIST = "rangeDistribution";
    public static final String PROP_RDF_TYPE_EDGES = "edges";
    public static final String PROP_RDF_TYPE_SPARQL_QUERY = "sparqlQuery";
    public static final String PROP_RDF_TYPE_IS_DYNAMIC = "isDynamic";
    public static final String PROP_RDF_TYPE_CONST_VALUE = "constValue";
    public static final String PROP_RDF_TYPE_MIN_VALUE = "minValue";
    public static final String PROP_RDF_TYPE_MAX_VALUE = "maxValue";
    public static final String PROP_RDF_TYPE_NAMESPACE = "nameSpace";
    public static final String PROP_RDF_TYPE_TYPE = "type";
    public static final String PROP_RDF_TYPE_ALIAS = "alias";
    public static final String PROP_RDF_TYPE_ID = "id";
    public static final String PROP_RDF_TYPE_POINTS_TO_NODE_ID = "pointsToNodeId";
    public static final String PROP_RDF_TYPE_ATTRIBUTE_NAME = "attributeName";
    public static final String PROP_RDF_TYPE_VARIABLE_NAME = "variableName";
    public static final String PROP_RDF_TYPE_SOURCE = "source";
    public static final String PROP_RDF_TYPE_LABEL = "label";
}