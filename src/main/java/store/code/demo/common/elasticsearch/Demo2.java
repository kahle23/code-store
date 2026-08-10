package store.code.demo.common.elasticsearch;

/*
 * 迁移注记：依赖 elasticsearch 5.x transport client 老 API，与现有 7.17 rest-client 不匹配，已整体注释
 */
// 
// import com.fasterxml.jackson.core.JsonProcessingException;
// import org.elasticsearch.ElasticsearchException;
// import org.elasticsearch.action.bulk.BulkItemResponse;
// import org.elasticsearch.action.bulk.BulkRequestBuilder;
// import org.elasticsearch.action.bulk.BulkResponse;
// import org.elasticsearch.action.delete.DeleteRequest;
// import org.elasticsearch.action.delete.DeleteResponse;
// import org.elasticsearch.action.get.GetResponse;
// import org.elasticsearch.action.index.IndexRequest;
// import org.elasticsearch.action.index.IndexResponse;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.action.search.SearchType;
// import org.elasticsearch.action.update.UpdateRequest;
// import org.elasticsearch.action.update.UpdateResponse;
// import org.elasticsearch.client.transport.TransportClient;
// import org.elasticsearch.cluster.node.DiscoveryNode;
// import org.elasticsearch.common.settings.Settings;
// import org.elasticsearch.common.text.Text;
// import org.elasticsearch.common.transport.InetSocketTransportAddress;
// import org.elasticsearch.common.unit.TimeValue;
// import org.elasticsearch.common.xcontent.XContentBuilder;
// import org.elasticsearch.common.xcontent.XContentFactory;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.elasticsearch.search.SearchHit;
// import org.elasticsearch.search.SearchHits;
// import org.elasticsearch.search.aggregations.AggregationBuilders;
// import org.elasticsearch.search.aggregations.bucket.terms.Terms;
// import org.elasticsearch.search.aggregations.metrics.sum.Sum;
// import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
// import org.elasticsearch.search.sort.SortOrder;
// import org.elasticsearch.transport.client.PreBuiltTransportClient;
// import org.junit.Before;
// import org.junit.Test;
// 
// import java.io.IOException;
// import java.net.InetAddress;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ExecutionException;
// 
// public class Demo2 {
// 
//     TransportClient client;
//     String index = "blog";
//     String type = "article";
// 
//     @Before
//     public void init() throws Exception {
//         Settings settings = Settings.builder()
//                 .put("cluster.name", "QIN") // 指定集群名称
//                 .build();
//         client = new PreBuiltTransportClient(settings)
//                 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("114.67.232.225"), 9300));
// 
//         List<DiscoveryNode> connectedNodes = client.connectedNodes();
//         for (DiscoveryNode node : connectedNodes) {
//             System.out.println(node.getHostAddress());
//         }
//     }
// 
//      * 通过prepareGet方法获取指定文档信息
//     @Test
//     public void testPrepareGet() {
//         GetResponse resp = client.prepareGet(index, type, "2").get();
//         System.out.println(resp.getSourceAsString());
//     }
// 
//      * prepareUpdate更新索引库中文档，如果文档不存在则会报错
//     @Test
//     public void testUpdate() throws IOException {
//         XContentBuilder source = XContentFactory.jsonBuilder()
//                 .startObject()
//                 .field("title", "Hello, World! ")
//                 .field("content", "我擦嘞，厉害！ ")
//                 .endObject();
// 
//         UpdateResponse updateResponse = client
//                 .prepareUpdate(index, type, "1").setDoc(source).get();
// 
//         System.out.println(updateResponse.getVersion());
//     }
// 
//     @Test
//     public void testIndexJson() {
//         Map<String, Object> source = new HashMap<>();
//         source.put("Hello", "你好");
//         source.put("卧槽", "厉害");
//         IndexResponse indexResponse = client
//                 .prepareIndex(index, type, "2").setSource(source).get();
//         System.out.println(indexResponse.getVersion());
//     }
// 
//      * 通过prepareIndex增加文档，参数为javaBean
//     @Test
//     public void testIndexBean() throws ElasticsearchException, JsonProcessingException {
//         User zhangsan = new User().setName("寮犱笁").setAge(19).setEmail("zhangsan@email.com");
//         IndexResponse indexResponse = client
//                 .prepareIndex(index, type, "2")
//                 .setSource("zhangsan", zhangsan)
//                 .get();
//         System.out.println(indexResponse.getVersion());
//     }
// 
//      * 通过prepareIndex增加文档，参数为XContentBuilder
//     @Test
//     public void testIndexXContentBuilder() throws IOException, InterruptedException, ExecutionException {
//         XContentBuilder builder = XContentFactory.jsonBuilder()
//                 .startObject()
//                 .field("name", "李四")
//                 .field("age", 20)
//                 .field("email", "lisi@email.com")
//                 .endObject();
//         IndexResponse indexResponse = client
//                 .prepareIndex(index, type, "2")
//                 .setSource(builder)
//                 .execute().get();
//     }
// 
//      * 通过prepareDelete删除文档
//     @Test
//     public void testDelete() {
//         String id = "2";
//         DeleteResponse deleteResponse = client.prepareDelete(index, type, id).get();
//         System.out.println(deleteResponse.getVersion());
//     }
// 
//     @Test
//     public void testDeleteAll() {
// 
//     @Test
//     public void testDeleteeIndex() {
//         client.admin().indices().prepareDelete("shb01","shb02").get();
//     }
// 
//      * 求索引库文档总数
//     @Test
//     public void testCount() {
//     }
// 
//     @Test
//     public void testBulk() throws IOException {
//         BulkRequestBuilder bulk = client.prepareBulk();
// 
//         IndexRequest add = new IndexRequest(index, type, "10");
//         add.source(XContentFactory.jsonBuilder()
//                 .startObject()
//                 .field("name", "Henrry").field("age", 30)
//                 .endObject());
// 
//         DeleteRequest del = new DeleteRequest(index, type, "1");
// 
//         XContentBuilder source = XContentFactory.jsonBuilder().startObject().field("name", "jack_1").field("age", 19).endObject();
//         UpdateRequest update = new UpdateRequest(index, type, "2");
//         update.doc(source);
// 
//         bulk.add(del);
//         bulk.add(add);
//         bulk.add(update);
//         if(bulkResponse.hasFailures()) {
//             BulkItemResponse[] items = bulkResponse.getItems();
//             for(BulkItemResponse item : items) {
//                 System.out.println(item.getFailureMessage());
//             }
//         } else {
//             System.out.println("全部执行成功！");
//         }
//     }
// 
//      * 通过prepareSearch查询索引。     * setQuery(QueryBuilders.matchQuery("name", "jack"))
//      * setSearchType(SearchType.QUERY_THEN_FETCH)
//     @Test
//     public void testSearch() {
//         SearchResponse searchResponse = client.prepareSearch(index)
//                 .setTypes(type)
//                 .setQuery(QueryBuilders.matchAllQuery()) //查询所有                //.setQuery(QueryBuilders.matchQuery("name", "tom").operator(Operator.AND)) //根据tom分词查询name,默认or
//                 .setSearchType(SearchType.QUERY_THEN_FETCH)
//                 .setFrom(0).setSize(10)//分页
//                 .addSort("age", SortOrder.DESC)//排序
//                 .get();
// 
//         SearchHits hits = searchResponse.getHits();
//         long total = hits.getTotalHits();
//         System.out.println(total);
//         SearchHit[] searchHits = hits.getHits();
//         for(SearchHit s : searchHits) {
//             System.out.println(s.getSourceAsString());
//         }
//     }
// 
//     @Test
//     public void testSearchsAndTimeout() {
//         SearchResponse searchResponse = client.prepareSearch("shb01","shb02").setTypes("stu","tea")
//                 .setQuery(QueryBuilders.matchAllQuery())
//                 .setSearchType(SearchType.QUERY_THEN_FETCH)
//                 .setTimeout(new TimeValue(3000))
//                 .get();
// 
//         SearchHits hits = searchResponse.getHits();
//         long totalHits = hits.getTotalHits();
//         System.out.println(totalHits);
//         SearchHit[] hits2 = hits.getHits();
//         for(SearchHit h : hits2) {
//             System.out.println(h.getSourceAsString());
//         }
//     }
// 
//      * 杩囨护：     * lt 灏忎簬
//      * gt 澶т簬
//      * lte 灏忎簬绛変簬
//      * gte 澶т簬绛変簬
//     @Test
//     public void testFilter() {
//         SearchResponse searchResponse = client.prepareSearch(index)
//                 .setTypes(type)
//                 .setQuery(QueryBuilders.matchAllQuery()) //查询扢：                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                 .setPostFilter(QueryBuilders.rangeQuery("age").gte(20).lte(24))
//                 .setExplain(true) //explain为true表示根据数据相关度排序，和关键字匹配度高的排在前面
//                 .get();
// 
//         SearchHits hits = searchResponse.getHits();
//         long total = hits.getTotalHits();
//         System.out.println(total);
//         SearchHit[] searchHits = hits.getHits();
//         for(SearchHit s : searchHits) {
//             System.out.println(s.getSourceAsString());
//         }
//     }
// 
//      * 楂樹寒
//     @Test
//     public void testHighLight() {
//         SearchResponse searchResponse = client.prepareSearch(index)
//                 .setTypes(type)
//                 .get();
// 
//         SearchHits hits = searchResponse.getHits();
//         System.out.println("sum:" + hits.getTotalHits());
// 
//         SearchHit[] hits2 = hits.getHits();
//         for(SearchHit s : hits2) {
//             Map<String, HighlightField> highlightFields = s.getHighlightFields();
//             HighlightField highlightField = highlightFields.get("name");
//             if (null != highlightField) {
//                 Text[] fragments = highlightField.fragments();
//                 System.out.println(fragments[0]);
//             }
//             System.out.println(s.getSourceAsString());
//         }
//     }
// 
//      * 分组
//     @Test
//     public void testGroupBy() {
//         SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
//                 .setQuery(QueryBuilders.matchAllQuery())
//                 .setSearchType(SearchType.QUERY_THEN_FETCH)
//                 .addAggregation(AggregationBuilders.terms("group_age")
//                         .field("age").size(0))//根据age分组，默认返回10，size(0)也是10
//                 .get();
// 
//         Terms terms = searchResponse.getAggregations().get("group_age");
//         List<? extends Terms.Bucket> buckets = terms.getBuckets();
//         for (Terms.Bucket bt : buckets) {
//             System.out.println(bt.getKey() + " " + bt.getDocCount());
//         }
//     }
// 
//     @Test
//     public void testMethod() {
//         SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
//                 .setQuery(QueryBuilders.matchAllQuery())
//                 .setSearchType(SearchType.QUERY_THEN_FETCH)
//                 .addAggregation(AggregationBuilders.terms("group_name").field("name")
//                         .subAggregation(AggregationBuilders.sum("sum_age").field("age")))
//                 .get();
// 
//         Terms terms = searchResponse.getAggregations().get("group_name");
//         List<? extends Terms.Bucket> buckets = terms.getBuckets();
//         for(Terms.Bucket bt : buckets) {
//             Sum sum = bt.getAggregations().get("sum_age");
//             System.out.println(bt.getKey() + "  " + bt.getDocCount() + " "+ sum.getValue());
//         }
//     }
// 
//     @Test
//     public void testIK() {
// 
//     }
// 
// }
