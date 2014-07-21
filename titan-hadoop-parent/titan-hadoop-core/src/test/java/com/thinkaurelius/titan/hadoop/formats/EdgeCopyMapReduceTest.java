package com.thinkaurelius.titan.hadoop.formats;

import static com.thinkaurelius.titan.hadoop.compat.HadoopCompatLoader.DEFAULT_COMPAT;

import com.thinkaurelius.titan.hadoop.BaseTest;
import com.thinkaurelius.titan.hadoop.FaunusVertex;
import com.thinkaurelius.titan.hadoop.Holder;
import com.thinkaurelius.titan.hadoop.Tokens;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class EdgeCopyMapReduceTest extends BaseTest {

    MapReduceDriver<NullWritable, FaunusVertex, LongWritable, Holder<FaunusVertex>, NullWritable, FaunusVertex> mapReduceDriver;

    public void setUp() {
        mapReduceDriver = new MapReduceDriver<NullWritable, FaunusVertex, LongWritable, Holder<FaunusVertex>, NullWritable, FaunusVertex>();
        mapReduceDriver.setMapper(new EdgeCopyMapReduce.Map());
        mapReduceDriver.setReducer(new EdgeCopyMapReduce.Reduce());
    }

    public void testInversionWithTinkerGraphOutEdges() throws Exception {
        mapReduceDriver.setConfiguration(EdgeCopyMapReduce.createConfiguration(Direction.OUT));
        Map<Long, FaunusVertex> halfGraph = BaseTest.generateGraph(ExampleGraph.TINKERGRAPH);
        for (FaunusVertex vertex : halfGraph.values()) {
            vertex.removeEdges(Tokens.Action.DROP, Direction.IN);
        }
        Map<Long, FaunusVertex> graph = runWithGraph(startPath(halfGraph, Vertex.class), mapReduceDriver);
        BaseTest.identicalStructure(graph, ExampleGraph.TINKERGRAPH);
        assertEquals(DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_COPIED),
                DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_ADDED));
    }

    public void testInversionWithTinkerGraphInEdges() throws Exception {
        mapReduceDriver.setConfiguration(EdgeCopyMapReduce.createConfiguration(Direction.IN));
        Map<Long, FaunusVertex> halfGraph = BaseTest.generateGraph(ExampleGraph.TINKERGRAPH);
        for (FaunusVertex vertex : halfGraph.values()) {
            vertex.removeEdges(Tokens.Action.DROP, Direction.OUT);
        }
        Map<Long, FaunusVertex> graph = runWithGraph(startPath(halfGraph, Vertex.class), mapReduceDriver);
        BaseTest.identicalStructure(graph, ExampleGraph.TINKERGRAPH);
        assertEquals(DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_COPIED),
                DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_ADDED));
    }

    public void testInversionWithGraphOfTheGodsOutEdges() throws Exception {
        mapReduceDriver.setConfiguration(EdgeCopyMapReduce.createConfiguration(Direction.OUT));
        Map<Long, FaunusVertex> halfGraph = BaseTest.generateGraph(ExampleGraph.GRAPH_OF_THE_GODS);
        for (FaunusVertex vertex : halfGraph.values()) {
            vertex.removeEdges(Tokens.Action.DROP, Direction.IN);
        }
        Map<Long, FaunusVertex> graph = runWithGraph(startPath(halfGraph, Vertex.class), mapReduceDriver);
        BaseTest.identicalStructure(graph, ExampleGraph.GRAPH_OF_THE_GODS);
        assertEquals(DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_COPIED),
                DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_ADDED));
    }

    public void testInversionWithGraphOfTheGodsInEdges() throws Exception {
        mapReduceDriver.setConfiguration(EdgeCopyMapReduce.createConfiguration(Direction.IN));
        Map<Long, FaunusVertex> halfGraph = BaseTest.generateGraph(ExampleGraph.GRAPH_OF_THE_GODS);
        for (FaunusVertex vertex : halfGraph.values()) {
            vertex.removeEdges(Tokens.Action.DROP, Direction.OUT);
        }
        Map<Long, FaunusVertex> graph = runWithGraph(startPath(halfGraph, Vertex.class), mapReduceDriver);
        BaseTest.identicalStructure(graph, ExampleGraph.GRAPH_OF_THE_GODS);
        assertEquals(DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_COPIED),
                DEFAULT_COMPAT.getCounter(mapReduceDriver, EdgeCopyMapReduce.Counters.EDGES_ADDED));
    }

}
