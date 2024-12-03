package source;

import com.starrocks.connector.flink.StarRocksSink;
import com.starrocks.connector.flink.row.sink.StarRocksSinkOP;
import com.starrocks.connector.flink.row.sink.StarRocksSinkRowBuilder;
import com.starrocks.connector.flink.table.sink.StarRocksSinkOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.connector.pulsar.source.PulsarSource;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableSchema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;

public class MySqlPulsarToDB {
    public static void main(String[] args) throws Exception {

        MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        String serviceUrl = params.get("serviceUrl", "pulsar://localhost:6650");
        String topic = params.get("topic", "persistent://public/default/mysql-topic");

        // MySQL 连接参数
        String jdbcUrl = params.get("jdbcUrl", "jdbc:mysql://10.49.26.33:9030");
        String loadUrl = params.get("loadUrl", "10.49.26.33:8030");
        String jdbcUser = params.get("jdbcUser", "bigdata_user");
        String jdbcPassword = params.get("jdbcPassword", "stars@rNi0oG8xHe6yeeOIplm");

        // 创建 Pulsar Source
        PulsarSource<String> source = PulsarSource.<String>builder()
                .setServiceUrl(serviceUrl)
                .setStartCursor(StartCursor.latest())
                .setTopics(topic)
                .setDeserializationSchema(new SimpleStringSchema())
                .setSubscriptionName("mysql-subscription")
                .build();

        // 获取 Flink 执行环境
        Configuration config = new Configuration();

        config.set(ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH, true);
        config.setInteger(RestOptions.PORT, 8087);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(config);
        // enable checkpoint
        env.enableCheckpointing(3000);
        //region 表结构 schema+options
        StarRocksSinkOptions options = StarRocksSinkOptions.builder()
                .withProperty("jdbc-url", jdbcUrl)
                .withProperty("load-url", loadUrl) // 需要替换为实际的load URL
                .withProperty("database-name", "test_sr_sink")
                .withProperty("table-name", "product_mws")
                .withProperty("username", jdbcUser)
                .withProperty("password", jdbcPassword)
                .withProperty("sink.properties.format", "json")
                .withProperty("sink.properties.strip_outer_array", "true")
                .build();
        TableSchema schema = TableSchema.builder()
                .field("id", DataTypes.BIGINT().notNull()) // Assuming id is a unique identifier
                .field("zid", DataTypes.INT())
                .field("sid", DataTypes.INT())
                .field("id_hash", DataTypes.STRING())
                .field("bid", DataTypes.INT())
                .field("cid", DataTypes.INT())
                .field("pid", DataTypes.INT())
                .field("local_sku", DataTypes.STRING())
                .field("local_name", DataTypes.STRING())
                .field("item_name", DataTypes.STRING())
                .field("item_description", DataTypes.STRING())
                .field("listing_id", DataTypes.STRING())
                .field("seller_sku", DataTypes.STRING())
                .field("price", DataTypes.DECIMAL(19, 6)) // Precision and scale as needed
                .field("quantity", DataTypes.INT())
                .field("open_date", DataTypes.STRING())
                .field("open_date_display", DataTypes.STRING())
                .field("open_date_time", DataTypes.INT())
                .field("image_url", DataTypes.STRING())
                .field("item_is_marketplace", DataTypes.STRING())
                .field("product_id_type", DataTypes.INT())
                .field("zshop_shipping_fee", DataTypes.STRING())
                .field("item_note", DataTypes.STRING())
                .field("item_condition", DataTypes.INT())
                .field("zshop_category1", DataTypes.STRING())
                .field("zshop_browse_path", DataTypes.STRING())
                .field("zshop_storefront_feature", DataTypes.STRING())
                .field("is_parent", DataTypes.TINYINT())
                .field("parent_asin", DataTypes.STRING())
                .field("asin1", DataTypes.STRING())
                .field("asin2", DataTypes.STRING())
                .field("asin3", DataTypes.STRING())
                .field("will_ship_internationally", DataTypes.STRING())
                .field("expedited_shipping", DataTypes.STRING())
                .field("zshop_boldface", DataTypes.STRING())
                .field("product_id", DataTypes.STRING())
                .field("bid_for_featured_placement", DataTypes.STRING())
                .field("add_delete", DataTypes.STRING())
                .field("pending_quantity", DataTypes.INT())
                .field("fulfillment_channel", DataTypes.STRING())
                .field("merchant_shipping_group", DataTypes.STRING())
                .field("business_price", DataTypes.STRING())
                .field("quantity_price_type", DataTypes.STRING())
                .field("quantity_lower_bound_1", DataTypes.STRING())
                .field("quantity_price_1", DataTypes.STRING())
                .field("quantity_lower_bound_2", DataTypes.STRING())
                .field("quantity_price_2", DataTypes.STRING())
                .field("quantity_lower_bound_3", DataTypes.STRING())
                .field("quantity_price_3", DataTypes.STRING())
                .field("quantity_lower_bound_4", DataTypes.STRING())
                .field("quantity_price_4", DataTypes.STRING())
                .field("quantity_lower_bound_5", DataTypes.STRING())
                .field("quantity_price_5", DataTypes.STRING())
                .field("listing_price", DataTypes.STRING())
                .field("landed_price", DataTypes.STRING())
                .field("currency_code", DataTypes.STRING())
                .field("small_image_url", DataTypes.STRING())
                .field("fnsku", DataTypes.STRING())
                .field("total_fulfillable_quantity", DataTypes.INT())
                .field("afn_warehouse_quantity", DataTypes.INT())
                .field("afn_warehouse_quantity_update", DataTypes.STRING())
                .field("afn_fulfillable_quantity", DataTypes.INT())
                .field("afn_fulfillable_quantity_update", DataTypes.STRING())
                .field("afn_unsellable_quantity", DataTypes.INT())
                .field("afn_unsellable_quantity_update", DataTypes.STRING())
                .field("reserved_customerorders", DataTypes.INT())
                .field("reserved_customerorders_update", DataTypes.STRING())
                .field("reserved_fc_transfers", DataTypes.INT())
                .field("reserved_fc_transfers_update", DataTypes.STRING())
                .field("reserved_fc_processing", DataTypes.INT())
                .field("reserved_fc_processing_update", DataTypes.STRING())
                .field("afn_inbound_working_quantity", DataTypes.INT())
                .field("afn_inbound_working_quantity_update", DataTypes.STRING())
                .field("afn_inbound_shipped_quantity", DataTypes.INT())
                .field("afn_inbound_shipped_quantity_update", DataTypes.STRING())
                .field("afn_inbound_receiving_quantity", DataTypes.INT())
                .field("afn_inbound_receiving_quantity_update", DataTypes.STRING())
                .field("total_sales_quantity", DataTypes.INT())
                .field("total_sales_amount", DataTypes.STRING())
                .field("total_sales_stat_begin_time", DataTypes.STRING())
                .field("total_sales_stat_end_time", DataTypes.STRING())
                .field("unsupported_payment", DataTypes.STRING())
                .field("is_delete", DataTypes.TINYINT())
                .field("status", DataTypes.TINYINT())
                .field("gl_type", DataTypes.STRING())
                .field("brand_name", DataTypes.STRING())
                .field("product_type", DataTypes.STRING())
                .field("listing_update_date", DataTypes.STRING())
                .field("last_get_matching_product_time", DataTypes.INT())
                .field("last_get_my_price_for_sku_time", DataTypes.INT())
                .field("sales_rank", DataTypes.INT())
                .field("category", DataTypes.STRING())
                .field("small_rank", DataTypes.STRING())
                .field("reviews_num", DataTypes.INT())
                .field("stars", DataTypes.FLOAT())
                .field("sales_total_30", DataTypes.INT())
                .field("sales_total_30_version", DataTypes.INT())
                .field("gmt_modified", DataTypes.TIMESTAMP(3)) // Assuming TIMESTAMP type with millisecond precision
                .field("gmt_create", DataTypes.TIMESTAMP(3)) // Assuming TIMESTAMP type with millisecond precision
                .field("company_id", DataTypes.BIGINT())
                .field("v_uuid", DataTypes.STRING())
                .primaryKey("id") // Assuming 'id' is the primary key
                .build();
        //endregion

        StarRocksSinkRowBuilder<ProductTestErp1> ProductTestErp1Builder = new StarRocksSinkRowBuilder<ProductTestErp1>() {
            @Override
            public void accept(Object[] internalRow, source.ProductTestErp1 product) {
                internalRow[0] = product.getId();
                internalRow[1] = product.getZid();
                internalRow[2] = product.getSid();
                internalRow[3] = product.getId_hash();
                internalRow[4] = product.getBid();
                internalRow[5] = product.getCid();
                internalRow[6] = product.getPid();
                internalRow[7] = product.getLocal_sku();
                internalRow[8] = product.getLocal_name();
                internalRow[9] = product.getItem_name();
                internalRow[10] = product.getItem_description();
                internalRow[11] = product.getListing_id();
                internalRow[12] = product.getSeller_sku();
                internalRow[13] = product.getPrice();
                internalRow[14] = product.getQuantity();
                internalRow[15] = product.getOpen_date();
                internalRow[16] = product.getOpen_date_display();
                internalRow[17] = product.getOpen_date_time();
                internalRow[18] = product.getImage_url();
                internalRow[19] = product.getItem_is_marketplace();
                internalRow[20] = product.getProduct_id_type();
                internalRow[21] = product.getZshop_shipping_fee();
                internalRow[22] = product.getItem_note();
                internalRow[23] = product.getItem_condition();
                internalRow[24] = product.getZshop_category1();
                internalRow[25] = product.getZshop_browse_path();
                internalRow[26] = product.getZshop_storefront_feature();
                internalRow[27] = product.getIs_parent();
                internalRow[28] = product.getParent_asin();
                internalRow[29] = product.getAsin1();
                internalRow[30] = product.getAsin2();
                internalRow[31] = product.getAsin3();
                internalRow[32] = product.getWill_ship_internationally();
                internalRow[33] = product.getExpedited_shipping();
                internalRow[34] = product.getZshop_boldface();
                internalRow[35] = product.getProduct_id();
                internalRow[36] = product.getBid_for_featured_placement();
                internalRow[37] = product.getAdd_delete();
                internalRow[38] = product.getPending_quantity();
                internalRow[39] = product.getFulfillment_channel();
                internalRow[40] = product.getMerchant_shipping_group();
                internalRow[41] = product.getBusiness_price();
                internalRow[42] = product.getQuantity_price_type();
                internalRow[43] = product.getQuantity_lower_bound_1();
                internalRow[44] = product.getQuantity_price_1();
                internalRow[45] = product.getQuantity_lower_bound_2();
                internalRow[46] = product.getQuantity_price_2();
                internalRow[47] = product.getQuantity_lower_bound_3();
                internalRow[48] = product.getQuantity_price_3();
                internalRow[49] = product.getQuantity_lower_bound_4();
                internalRow[50] = product.getQuantity_price_4();
                internalRow[51] = product.getQuantity_lower_bound_5();
                internalRow[52] = product.getQuantity_price_5();
                internalRow[53] = product.getListing_price();
                internalRow[54] = product.getLanded_price();
                internalRow[55] = product.getCurrency_code();
                internalRow[56] = product.getSmall_image_url();
                internalRow[57] = product.getFnsku();
                internalRow[58] = product.getTotal_fulfillable_quantity();
                internalRow[59] = product.getAfn_warehouse_quantity();
                internalRow[60] = product.getAfn_warehouse_quantity_update();
                internalRow[61] = product.getAfn_fulfillable_quantity();
                internalRow[62] = product.getAfn_fulfillable_quantity_update();
                internalRow[63] = product.getAfn_unsellable_quantity();
                internalRow[64] = product.getAfn_unsellable_quantity_update();
                internalRow[65] = product.getReserved_customerorders();
                internalRow[66] = product.getReserved_customerorders_update();
                internalRow[67] = product.getReserved_fc_transfers();
                internalRow[68] = product.getReserved_fc_transfers_update();
                internalRow[69] = product.getReserved_fc_processing();
                internalRow[70] = product.getReserved_fc_processing_update();
                internalRow[71] = product.getAfn_inbound_working_quantity();
                internalRow[72] = product.getAfn_inbound_working_quantity_update();
                internalRow[73] = product.getAfn_inbound_shipped_quantity();
                internalRow[74] = product.getAfn_inbound_shipped_quantity_update();
                internalRow[75] = product.getAfn_inbound_receiving_quantity();
                internalRow[76] = product.getAfn_inbound_receiving_quantity_update();
                internalRow[77] = product.getTotal_sales_quantity();
                internalRow[78] = product.getTotal_sales_amount();
                internalRow[79] = product.getTotal_sales_stat_begin_time();
                internalRow[80] = product.getTotal_sales_stat_end_time();
                internalRow[81] = product.getUnsupported_payment();
                internalRow[82] = product.getIs_delete();
                internalRow[83] = product.getStatus();
                internalRow[84] = product.getGl_type();
                internalRow[85] = product.getBrand_name();
                internalRow[86] = product.getProduct_type();
                internalRow[87] = product.getListing_update_date();
                internalRow[88] = product.getLast_get_matching_product_time();
                internalRow[89] = product.getLast_get_my_price_for_sku_time();
                internalRow[90] = product.getSales_rank();
                internalRow[91] = product.getCategory();
                internalRow[92] = product.getSmall_rank();
                internalRow[93] = product.getReviews_num();
                internalRow[94] = product.getStars();
                internalRow[95] = product.getSales_total_30();
                internalRow[96] = product.getSales_total_30_version();
                internalRow[97] = product.getGmt_modified();
                internalRow[98] = product.getGmt_create();
                internalRow[99] = product.getCompany_id();
                internalRow[100] = product.getV_uuid();
                internalRow[internalRow.length - 1] = StarRocksSinkOP.UPSERT.ordinal();
            }
        };


        // Create a stream from the source
        DataStream<String> pulsarStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        pulsarStream.map(value -> {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(value);
            JsonNode afterNode = rootNode.path("after");


            // 解析fullDocument
            JsonNode after = objectMapper.readTree(afterNode.asText());
            BigInteger id = new BigInteger(after.path("id").asText());
            int zid = after.path("zid").asInt();
            int sid = after.path("sid").asInt();
            String id_hash = after.path("id_hash").asText();
            int bid = after.path("bid").asInt();
            int cid = after.path("cid").asInt();
            int pid = after.path("pid").asInt();
            String local_sku = after.path("local_sku").asText();
            String local_name = after.path("local_name").asText();
            String item_name = after.path("item_name").asText();
            String item_description = after.path("item_description").asText();
            String listing_id = after.path("listing_id").asText();
            String seller_sku = after.path("seller_sku").asText();
            String price = after.path("price").asText();
            int quantity = after.path("quantity").asInt();
            String open_date = after.path("open_date").asText();
            String open_date_display = after.path("open_date_display").asText();
            int open_date_time = after.path("open_date_time").asInt();
            String image_url = after.path("image_url").asText(); // Corrected from img_url to image_url
            String item_is_marketplace = after.path("item_is_marketplace").asText();
            int product_id_type = after.path("product_id_type").asInt();
            String zshop_shipping_fee = after.path("zshop_shipping_fee").asText();
            String item_note = after.path("item_note").asText();
            int item_condition = after.path("item_condition").asInt();
            String zshop_category1 = after.path("zshop_category1").asText();
            String zshop_browse_path = after.path("zshop_browse_path").asText();
            String zshop_storefront_feature = after.path("zshop_storefront_feature").asText();
            byte is_parent = (byte) after.path("is_parent").asInt();
            String parent_asin = after.path("parent_asin").asText();
            String asin1 = after.path("asin1").asText();
            String asin2 = after.path("asin2").asText();
            String asin3 = after.path("asin3").asText();
            String will_ship_internationally = after.path("will_ship_internationally").asText();
            String expedited_shipping = after.path("expedited_shipping").asText();
            String zshop_boldface = after.path("zshop_boldface").asText();
            String product_id = after.path("product_id").asText();
            String bid_for_featured_placement = after.path("bid_for_featured_placement").asText();
            String add_delete = after.path("add_delete").asText();
            int pending_quantity = after.path("pending_quantity").asInt();
            String fulfillment_channel = after.path("fulfillment_channel").asText();
            String merchant_shipping_group = after.path("merchant_shipping_group").asText();
            String business_price = after.path("business_price").asText();
            String quantity_price_type = after.path("quantity_price_type").asText();
            String quantity_lower_bound_1 = after.path("quantity_lower_bound_1").asText();
            String quantity_price_1 = after.path("quantity_price_1").asText();
            String quantity_lower_bound_2 = after.path("quantity_lower_bound_2").asText();
            String quantity_price_2 = after.path("quantity_price_2").asText();
            String quantity_lower_bound_3 = after.path("quantity_lower_bound_3").asText();
            String quantity_price_3 = after.path("quantity_price_3").asText();
            String quantity_lower_bound_4 = after.path("quantity_lower_bound_4").asText();
            String quantity_price_4 = after.path("quantity_price_4").asText();
            String quantity_lower_bound_5 = after.path("quantity_lower_bound_5").asText();
            String quantity_price_5 = after.path("quantity_price_5").asText();
            String listing_price = after.path("listing_price").asText();
            String landed_price = after.path("landed_price").asText();
            String currency_code = after.path("currency_code").asText();
            String small_img_url = after.path("small_image_url").asText();
            String fnsku = after.path("fnsku").asText();
            int total_fulfillable_quantity = after.path("total_fulfillable_quantity").asInt();
            int afn_warehouse_quantity = after.path("afn_warehouse_quantity").asInt();
            String afn_warehouse_quantity_update = after.path("afn_warehouse_quantity_update").asText();
            int afn_fulfillable_quantity = after.path("afn_fulfillable_quantity").asInt();
            String afn_fulfillable_quantity_update = after.path("afn_fulfillable_quantity_update").asText();
            int afn_unsellable_quantity = after.path("afn_unsellable_quantity").asInt();
            String afn_unsellable_quantity_update = after.path("afn_unsellable_quantity_update").asText();
            int reserved_customerorders = after.path("reserved_customerorders").asInt();
            String reserved_customerorders_update = after.path("reserved_customerorders_update").asText();
            int reserved_fc_transfers = after.path("reserved_fc_transfers").asInt();
            String reserved_fc_transfers_update = after.path("reserved_fc_transfers_update").asText();
            int reserved_fc_processing = after.path("reserved_fc_processing").asInt();
            String reserved_fc_processing_update = after.path("reserved_fc_processing_update").asText();
            int afn_inbound_working_quantity = after.path("afn_inbound_working_quantity").asInt();
            String afn_inbound_working_quantity_update = after.path("afn_inbound_working_quantity_update").asText();
            int afn_inbound_shipped_quantity = after.path("afn_inbound_shipped_quantity").asInt();
            String afn_inbound_shipped_quantity_update = after.path("afn_inbound_shipped_quantity_update").asText();
            int afn_inbound_receiving_quantity = after.path("afn_inbound_receiving_quantity").asInt();
            String afn_inbound_receiving_quantity_update = after.path("afn_inbound_receiving_quantity_update").asText();
            int total_sales_quantity = after.path("total_sales_quantity").asInt();
            String total_sales_amount = after.path("total_sales_amount").asText();
            String total_sales_stat_begin_time = after.path("total_sales_stat_begin_time").asText();
            String total_sales_stat_end_time = after.path("total_sales_stat_end_time").asText();
            String unsupported_payment = after.path("unsupported_payment").asText();
            byte is_delete = (byte) after.path("is_delete").asInt();
            byte status = (byte) after.path("status").asInt();
            String gl_type = after.path("gl_type").asText();
            String brand_name = after.path("brand_name").asText();
            String product_type = after.path("product_type").asText();
            String listing_update_date = after.path("listing_update_date").asText();
            int last_get_matching_product_time = after.path("last_get_matching_product_time").asInt(); // Corrected to asLong()
            int last_get_my_price_for_sku_time = after.path("last_get_my_price_for_sku_time").asInt(); // Corrected to asLong()
            int sales_rank = after.path("sales_rank").asInt();
            String category = after.path("category").asText();
            String small_rank = after.path("small_rank").asText();
            int reviews_num = after.path("reviews_num").asInt();
            float stars = (float) after.path("stars").asDouble();
            int sales_total_30 = after.path("sales_total_30").asInt();
            int sales_total_30_version = after.path("sales_total_30_version").asInt();
            Date gmt_modified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(after.path("gmt_modified").asText());
            Date gmt_create = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(after.path("gmt_create").asText());
            BigInteger company_id = new BigInteger(after.path("company_id").asText());
            String v_uuid = after.path("v_uuid").asText();


            return new ProductTestErp1(id,zid,sid,id_hash,bid,cid,pid,local_sku,local_name,item_name,item_description,listing_id,seller_sku,price,quantity,open_date,open_date_display,open_date_time,image_url,item_is_marketplace,product_id_type,zshop_shipping_fee,item_note,item_condition,zshop_category1,zshop_browse_path,zshop_storefront_feature,is_parent,parent_asin,asin1,asin2,asin3,will_ship_internationally,expedited_shipping,zshop_boldface,product_id,bid_for_featured_placement,add_delete,pending_quantity,fulfillment_channel,merchant_shipping_group,business_price,quantity_price_type,quantity_lower_bound_1,quantity_price_1,quantity_lower_bound_2,quantity_price_2,quantity_lower_bound_3,quantity_price_3,quantity_lower_bound_4,quantity_price_4,quantity_lower_bound_5,quantity_price_5,listing_price,landed_price,currency_code,small_img_url,fnsku,total_fulfillable_quantity,afn_warehouse_quantity,afn_warehouse_quantity_update,afn_fulfillable_quantity,afn_fulfillable_quantity_update,afn_unsellable_quantity,afn_unsellable_quantity_update,reserved_customerorders,reserved_customerorders_update,reserved_fc_transfers,reserved_fc_transfers_update,reserved_fc_processing,reserved_fc_processing_update,afn_inbound_working_quantity,afn_inbound_working_quantity_update,afn_inbound_shipped_quantity,afn_inbound_shipped_quantity_update,afn_inbound_receiving_quantity,afn_inbound_receiving_quantity_update,total_sales_quantity,total_sales_amount,total_sales_stat_begin_time,total_sales_stat_end_time,unsupported_payment,is_delete,status,gl_type,brand_name,product_type,listing_update_date,last_get_matching_product_time,last_get_my_price_for_sku_time,sales_rank,category,small_rank,reviews_num,stars,sales_total_30,sales_total_30_version,gmt_modified,gmt_create,company_id,v_uuid);
        }).addSink(StarRocksSink.sink(schema, options, ProductTestErp1Builder));


//        // Create the sink with schema, options, and transformer
//        ProductTestErp1Transformer transformer = new ProductTestErp1Transformer();
//        SinkFunction<ProductTestErp1> starRocksSink = StarRocksSink.sink(schema, options, transformer);
//
//        // Add the sink to the stream
//        transformedStream.addSink(starRocksSink);

        // Execute the Flink job
        env.execute("PulsarToMySQLJob");
    }
}
